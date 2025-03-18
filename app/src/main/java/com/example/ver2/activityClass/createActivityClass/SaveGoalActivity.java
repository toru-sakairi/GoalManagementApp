/*
    サブクラスの内容（フレームワーク五との内容）を前までのActivityで入力し、ここではGoalクラスの入力を行う
    名前、詳細、作成日（これは自動で）、開始日、終了日、タスク、これらを入力する
 */

package com.example.ver2.activityClass.createActivityClass;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ver2.recyclerViewClass.RecyclerViewTaskListAdapter;
import com.example.ver2.activityClass.MainActivity;
import com.example.ver2.dataClass.GoalDataViewModel;
import com.example.ver2.dataClass.goalManagement.GoalType;
import com.example.ver2.fragmentClass.AddTaskFragment;
import com.example.ver2.R;
import com.example.ver2.fragmentClass.viewModels.SaveGoalFragmentViewModel;
import com.example.ver2.dataClass.Task;
import com.example.ver2.dataClass.goalManagement.Benchmarking;
import com.example.ver2.dataClass.goalManagement.Goal;
import com.example.ver2.dataClass.goalManagement.Memo_Goal;
import com.example.ver2.dataClass.goalManagement.SMART;
import com.example.ver2.dataClass.goalManagement.WillCanMust;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SaveGoalActivity extends AppCompatActivity {
    //データベースとのやり取りを行うViewModel
    private GoalDataViewModel goalDataViewModel;

    private WillCanMust wcm;
    private SMART smart;
    private Benchmarking benchmarking;
    private Memo_Goal memo;

    private Goal goal;
    private List<Task> taskList = new ArrayList<>(); // タスクリスト

    private EditText goalNameEditText;
    private EditText goalDescriptionEditText;

    private Date startDate;
    private Date finishDate;

    @Override
    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.save_goal_layout);

        goalDataViewModel = new ViewModelProvider(this).get(GoalDataViewModel.class);

        Intent intent = getIntent();
        //前の画面で作ったオブジェクトを生成
        if (intent != null) {
            //オブジェクトの種類に応じて分けている。初めてインスタンス化する場合は、それぞれのタイプを設定する
            if (intent.hasExtra("willCanMust")) {
                wcm = intent.getParcelableExtra("willCanMust");
                //二回目にこのクラスに遷移した際に前に入力した情報を保持するためのコード。初めての場合はここで初めてインスタンス化する
                if (wcm != null && wcm.isGoalExist()) {
                    goal = new Goal(wcm.getName(), wcm.getDescription(), wcm.getCreateDate(), wcm.getStartDate(), wcm.getFinishDate(), wcm.isState(), wcm.getTasks(), wcm.getType());
                } else {
                    goal = new Goal(null, null, null, null, null, false, null, GoalType.WILL_CAN_MUST);
                }
            } else if (intent.hasExtra("smart")) {
                smart = intent.getParcelableExtra("smart");
                if (smart != null && smart.isGoalExist()) {
                    goal = new Goal(smart.getName(), smart.getDescription(), smart.getCreateDate(), smart.getStartDate(), smart.getFinishDate(), smart.isState(), smart.getTasks(), smart.getType());
                } else {
                    goal = new Goal(null, null, null, null, null, false, null, GoalType.SMART);
                }
            } else if (intent.hasExtra("benchmarking")) {
                benchmarking = intent.getParcelableExtra("benchmarking");
                if (benchmarking != null && benchmarking.isGoalExist()) {
                    goal = new Goal(benchmarking.getName(), benchmarking.getDescription(), benchmarking.getCreateDate(), benchmarking.getStartDate(), benchmarking.getFinishDate(), benchmarking.isState(), benchmarking.getTasks(), benchmarking.getType());
                } else {
                    goal = new Goal(null, null, null, null, null, false, null, GoalType.BENCHMARKING);
                }
            } else if (intent.hasExtra("memo_goal")) {
                memo = intent.getParcelableExtra("memo_goal");
                if (memo != null && memo.isGoalExist()) {
                    goal = new Goal(memo.getName(), memo.getDescription(), memo.getCreateDate(), memo.getStartDate(), memo.getFinishDate(), memo.isState(), memo.getTasks(), memo.getType());
                } else {
                    goal = new Goal(null, null, null, null, null, false, null, GoalType.MEMO_GOAL);
                }
            }
        } else {
            Log.d(TAG, "break:Not intentObject");
        }

        //UIを設定するメソッド
        setActivityComponent(goal);

        //タスク追加ボタン(新規作成だから openNewTaskFragment()メソッドを使用)
        Button addTaskButton = findViewById(R.id.addTaskButton);
        addTaskButton.setOnClickListener(view -> openNewTaskFragment());

        //データベースに保存する
        Button saveButton = findViewById(R.id.saveGoalButton);
        saveButton.setOnClickListener(view -> {
            String goalName = goalNameEditText.getText().toString();
            String goalDescription = goalDescriptionEditText.getText().toString();

            //goalの中身を設定
            goal.setName(goalName);
            goal.setDescription(goalDescription);
            goal.setCreateDate(new Date());
            goal.setStartDate(startDate);
            goal.setFinishDate(finishDate);
            goal.setState(false);
            goal.setTasks(taskList);

            //それぞれのサブクラスのスーパークラスの属性を上書きする
            if (wcm != null) {
                wcm.updateGoal(goal);
            } else if (benchmarking != null) {
                benchmarking.updateGoal(goal);
            } else if (smart != null) {
                smart.updateGoal(goal);
            } else if (memo != null) {
                memo.updateGoal(goal);
            }

            //データベースに保存。引数は5つで、goalはupdateGoal(goal)に使用、そのほか4つはnullチェックを用いたViewModel側での処理
            goalDataViewModel.insertGoal(goal, wcm, benchmarking, smart, memo);

            //ホームに戻る
            Intent intent_next = new Intent(SaveGoalActivity.this, MainActivity.class);
            startActivity(intent_next);
        });

        //前のActivityに遷移する。現在目標設定中のクラスの種類ごとに別れる。
        Button backButton = findViewById(R.id.backButton_saveGoallayout);
        backButton.setOnClickListener(view -> {
            String goalName = goalNameEditText.getText().toString();
            String goalDescription = goalDescriptionEditText.getText().toString();

            //goalのアップデート
            goal.setName(goalName);
            goal.setDescription(goalDescription);
            goal.setCreateDate(new Date());
            goal.setStartDate(startDate);
            goal.setFinishDate(finishDate);
            goal.setState(false);
            goal.setTasks(taskList);

            //オブジェクトに保存 ここで前のActivityに遷移
            if (wcm != null) {
                wcm.updateGoal(goal);
                Intent intent_before = new Intent(SaveGoalActivity.this, WillCanMustActivity.class);
                intent_before.putExtra("willCanMust", wcm);
                startActivity(intent_before);
            } else if (benchmarking != null) {
                benchmarking.updateGoal(goal);
                Intent intent_before = new Intent(SaveGoalActivity.this, BenchmarkingActivity.class);
                intent_before.putExtra("benchmarking", benchmarking);
                startActivity(intent);
            } else if (smart != null) {
                smart.updateGoal(goal);
                Intent intent_before = new Intent(SaveGoalActivity.this, SMARTActivity.class);
                intent_before.putExtra("smart", smart);
                startActivity(intent);
            } else if (memo != null) {
                memo.updateGoal(goal);
                Intent intent_before = new Intent(SaveGoalActivity.this, MemoGoalActivity.class);
                intent_before.putExtra("memo_goal", memo);
                startActivity(intent);
            }
        });

    }

    //UIの設定を行うメソッド
    private void setActivityComponent(Goal goal) {
        goalNameEditText = findViewById(R.id.goalNameEditText);
        goalDescriptionEditText = findViewById(R.id.goalDescriptionEditText);
        DatePicker startDatePicker = findViewById(R.id.startDatePicker);
        DatePicker finishDatePicker = findViewById(R.id.finishDatePicker);

        Calendar calendar = Calendar.getInstance();

        // Goal オブジェクトの値を UI に設定
        //中身がnullでも、初期値で設定されるらしい
        if (goal != null) {
            goalNameEditText.setText(goal.getName());
            goalDescriptionEditText.setText(goal.getDescription());

            //日付の初期値の設定
            if (goal.getStartDate() != null) {
                calendar.setTime(goal.getStartDate());
                startDatePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                //日付をこのクラスの変数にアタッチしておく
                startDate = goal.getStartDate();
            } else {
                //nullだった場合は、現在の日付を入れておく
                startDate = calendar.getTime();
            }
            if (goal.getFinishDate() != null) {
                calendar.setTime(goal.getFinishDate());
                finishDatePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                finishDate = goal.getFinishDate();
            } else {
                finishDate = calendar.getTime();
            }
        }

        // タスクリストのRecyclerView の設定
        RecyclerView taskRecyclerView = findViewById(R.id.SaveGoal_taskList);
        taskList = new ArrayList<>(); // taskList を空の ArrayList で初期化

        if (goal != null && goal.getTasks() != null) { // goal と goal.getTasks() が null でない場合
            taskList = goal.getTasks(); // Goal オブジェクトの TaskList を taskList に代入
        }

        //タスクリストのAdapter
        RecyclerViewTaskListAdapter adapter = new RecyclerViewTaskListAdapter(taskList);
        taskRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> openChangeTaskFragment(position));

        // DatePicker のリスナー設定 (API レベル 26 以上)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startDatePicker.setOnDateChangedListener((view, year, monthOfYear, dayOfMonth) -> {
                calendar.set(year, monthOfYear, dayOfMonth);
                startDate = calendar.getTime();
            });

            finishDatePicker.setOnDateChangedListener((view, year, monthOfYear, dayOfMonth) -> {
                calendar.set(year, monthOfYear, dayOfMonth);
                finishDate = calendar.getTime();
            });
        }
    }

    //新しいタスクを生成する際に使用するFragmentを生成するメソッド
    private void openNewTaskFragment() {
        //ViewModelを生成：Activity側とFragment側との情報を行き来させるViewModel
        SaveGoalFragmentViewModel viewModel = new ViewModelProvider(this).get(SaveGoalFragmentViewModel.class);
        viewModel.setActivity(this);
        //フラグメントを生成
        AddTaskFragment addTaskFragment = new AddTaskFragment();
        addTaskFragment.show(getSupportFragmentManager(), "AddTaskFragment");
    }

    //すでにあるタスクを編集する際に使用する。Fragmentを生成するメソッド
    private void openChangeTaskFragment(int id) {
        //ViewModelを生成
        SaveGoalFragmentViewModel viewModel = new ViewModelProvider(this).get(SaveGoalFragmentViewModel.class);
        viewModel.setActivity(this);

        Task changeTask = findTaskByID(id);
        //Fragmentに初期値を設定する
        Bundle bundle = new Bundle();
        bundle.putParcelable("changeTask", changeTask);
        //Fragmentを開始
        AddTaskFragment addTaskFragment = new AddTaskFragment();
        addTaskFragment.setArguments(bundle);
        addTaskFragment.show(getSupportFragmentManager(), "AddTaskFragment");
    }

    //ViewModelから呼ばれる、タスクを保存した際にtaskListにタスクを追加するメソッド。RecyclerViewもアップデートさせる
    public void addTask(Task task) {
        if (goal == null) {
            goal = new Goal(null, null, null, null, null, false, null, null);
        }
        if (taskList == null) {
            taskList = new ArrayList<>();
        }

        //goal.addTask(task);
        taskList.add(task);

        // アダプターにデータが変更されたことを通知
        RecyclerView taskRecyclerView = findViewById(R.id.SaveGoal_taskList);
        RecyclerViewTaskListAdapter adapter = (RecyclerViewTaskListAdapter) taskRecyclerView.getAdapter();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }

        logTasks();
    }

    //タスクを探すメソッド openChangeTaskFragmentで使用
    private Task findTaskByID(int id) {
        for (Task task : taskList) {
            if (task.getID() == id) {
                return task;
            }
        }
        return null;
    }

    //ViewModelから呼ばれる、タスクのIDを設定する際に用いる。
    public int getTaskNum() {
        if (taskList != null) {
            return taskList.size();
        } else {
            taskList = new ArrayList<>();
            return taskList.size();
        }
    }

    //ViewModelから呼ばれる、編集されたTaskを適用するメソッド
    public void changeTask(Task task) {
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).getID() == task.getID()) {
                taskList.set(i, task); // リスト内のオブジェクトを新しいTaskオブジェクトで置き換える
                break; // IDが一致するオブジェクトは1つしかないため、ループを抜ける
            }
        }
        RecyclerView taskRecyclerView = findViewById(R.id.SaveGoal_taskList);
        RecyclerViewTaskListAdapter adapter = (RecyclerViewTaskListAdapter) taskRecyclerView.getAdapter();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void logTasks() {
        for (Task task : taskList) {
            Log.d("task description", "ID:" + task.getID() + " NAME:" + task.getName() + " DESCRIPTION:" + task.getDescription());
            Log.d("task description", "createDate:" + task.getCreateDate() + " startDate:" + task.getStartDate() + " finishDate:" + task.getFinishDate());
        }
    }
}


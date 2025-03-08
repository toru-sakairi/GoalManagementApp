package com.example.ver2.activityClass.createActivityClass;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ver2.RecyclerViewTaskListAdapter;
import com.example.ver2.activityClass.MainActivity;
import com.example.ver2.dataClass.GoalSaveViewModel;
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
    private GoalSaveViewModel goalSaveViewModel;

    private WillCanMust wcm;
    private SMART smart;
    private Benchmarking benchmarking;
    private Memo_Goal memo;

    private Goal goal;
    private List<Task> taskList = new ArrayList<>(); // タスクリスト

    private EditText goalNameEditText;
    private EditText goalDescriptionEditText;
    private DatePicker startDatePicker;
    private DatePicker finishDatePicker;

    private Date startDate;
    private Date finishDate;

    @Override
    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.save_goal_layout);

        Intent intent = getIntent();

        goalSaveViewModel = new ViewModelProvider(this).get(GoalSaveViewModel.class);
        //前の画面で作ったオブジェクトを生成
        if (intent != null) {
            if (intent.hasExtra("willCanMust")) {
                wcm = (WillCanMust) intent.getParcelableExtra("willCanMust");
                //戻ったときの挙動：すでにオブジェクトがある場合はということ
                if (wcm.isGoalExist()) {
                    goal = new Goal(wcm.getName(), wcm.getDescription(), wcm.getCreateDate(), wcm.getStartDate(), wcm.getFinishDate(), wcm.isState(), wcm.getTasks(), wcm.getType());
                } else {
                    goal = new Goal(null, null, null, null, null, false, null, GoalType.WILL_CAN_MUST);
                }
            } else if (intent.hasExtra("smart")) {
                smart = (SMART) intent.getParcelableExtra("smart");
                if (smart.isGoalExist()) {
                    goal = new Goal(smart.getName(), smart.getDescription(), smart.getCreateDate(), smart.getStartDate(), smart.getFinishDate(), smart.isState(), smart.getTasks(), smart.getType());
                } else {
                    goal = new Goal(null, null, null, null, null, false, null, GoalType.SMART);
                }
            } else if (intent.hasExtra("benchmarking")) {
                benchmarking = (Benchmarking) intent.getParcelableExtra("benchmarking");
                if (benchmarking.isGoalExist()) {
                    goal = new Goal(benchmarking.getName(), benchmarking.getDescription(), benchmarking.getCreateDate(), benchmarking.getStartDate(), benchmarking.getFinishDate(), benchmarking.isState(), benchmarking.getTasks(), benchmarking.getType());
                } else {
                    goal = new Goal(null, null, null, null, null, false, null, GoalType.BENCHMARKING);
                }
            } else if (intent.hasExtra("memo_goal")) {
                memo = (Memo_Goal) intent.getParcelableExtra("memo_goal");
                if (memo.isGoalExist()) {
                    goal = new Goal(memo.getName(), memo.getDescription(), memo.getCreateDate(), memo.getStartDate(), memo.getFinishDate(), memo.isState(), memo.getTasks(), memo.getType());
                } else {
                    goal = new Goal(null, null, null, null, null, false, null, GoalType.MEMO_GOAL);
                }
            }
        } else {
            Log.d(TAG, "break:Not intentObject");
        }

        setActivityComponent(goal);

        Button addTaskButton = findViewById(R.id.addTaskButton);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewTaskFragment();
            }
        });

        //データベースに保存する
        Button saveButton = findViewById(R.id.saveGoalButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String goalName = goalNameEditText.getText().toString();
                String goalDescription = goalDescriptionEditText.getText().toString();

                goal.setName(goalName);
                goal.setDescription(goalDescription);
                goal.setCreateDate(new Date());
                goal.setStartDate(startDate);
                goal.setFinishDate(finishDate);
                goal.setState(false);
                goal.setTasks(taskList);

                //データベースに保存
                if (wcm != null) {
                    wcm.setGoal(goal);
                } else if (benchmarking != null) {
                    benchmarking.setGoal(goal);
                } else if (smart != null) {
                    smart.setGoal(goal);
                } else if (memo != null) {
                    memo.setGoal(goal);
                }

                goalSaveViewModel.insertGoal(goal, wcm, benchmarking, smart, memo);

                //ホームに戻る
                Intent intent = new Intent(SaveGoalActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //Activityの遷移でもいったんオブジェクトを保存してそのオブジェクトを渡さないとだめだと思われ
        Button backButton = findViewById(R.id.backButton_saveGoallayout);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String goalName = goalNameEditText.getText().toString();
                String goalDescription = goalDescriptionEditText.getText().toString();

                goal.setName(goalName);
                goal.setDescription(goalDescription);
                goal.setCreateDate(new Date());
                goal.setStartDate(startDate);
                goal.setFinishDate(finishDate);
                goal.setState(false);
                goal.setTasks(taskList);

                //オブジェクトに保存 ここで前のActivityに遷移
                if (wcm != null) {
                    wcm.setGoal(goal);
                    Intent intent = new Intent(SaveGoalActivity.this, WillCanMustActivity.class);
                    intent.putExtra("willCanMust", wcm);
                    startActivity(intent);
                } else if (benchmarking != null) {
                    benchmarking.setGoal(goal);
                    Intent intent = new Intent(SaveGoalActivity.this, BenchmarkingActivity.class);
                    intent.putExtra("benchmarking", benchmarking);
                    startActivity(intent);
                } else if (smart != null) {
                    smart.setGoal(goal);
                    Intent intent = new Intent(SaveGoalActivity.this, SMARTActivity.class);
                    intent.putExtra("smart", smart);
                    startActivity(intent);
                } else if (memo != null) {
                    memo.setGoal(goal);
                    Intent intent = new Intent(SaveGoalActivity.this, MemoGoalActivity.class);
                    intent.putExtra("memo_goal", memo);
                    startActivity(intent);
                }
            }
        });

    }

    private void setActivityComponent(Goal goal) {
        goalNameEditText = findViewById(R.id.goalNameEditText);
        goalDescriptionEditText = findViewById(R.id.goalDescriptionEditText);
        startDatePicker = findViewById(R.id.startDatePicker);
        finishDatePicker = findViewById(R.id.finishDatePicker);

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
                startDate = goal.getStartDate();
            }else{
                startDate = calendar.getTime();
            }
            if (goal.getFinishDate() != null) {
                calendar.setTime(goal.getFinishDate());
                finishDatePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                finishDate = goal.getFinishDate();
            }else{
                finishDate = calendar.getTime();
            }
        }

        // RecyclerView の設定
        RecyclerView taskRecyclerView = findViewById(R.id.SaveGoal_taskList);
        taskList = new ArrayList<>(); // taskList を空の ArrayList で初期化

        if (goal != null && goal.getTasks() != null) { // goal と goal.getTasks() が null でない場合
            taskList = goal.getTasks(); // Goal オブジェクトの TaskList を taskList に代入
        }

        RecyclerViewTaskListAdapter adapter = new RecyclerViewTaskListAdapter(taskList);
        taskRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecyclerViewTaskListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                openChangeTaskFragment(position);
            }
        });

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

    private void openNewTaskFragment() {
        //ViewModelを生成
        SaveGoalFragmentViewModel viewModel = new ViewModelProvider(this).get(SaveGoalFragmentViewModel.class);
        viewModel.setActivity(this);
        //フラグメントを生成
        AddTaskFragment addTaskFragment = new AddTaskFragment();
        addTaskFragment.show(getSupportFragmentManager(), "AddTaskFragment");
        //フラグメントトランザクションを開始(いらない可能性あり)
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //フラグメントをFrameLayoutに追加
        //transaction.add(R.id.addTaskFragmentView, addTaskFragment);
        //トランザクションをコミット
        transaction.commit();
    }

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
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //transaction.add(R.id.addTaskFragmentView, addTaskFragment);
        transaction.commit();
    }

    //とりあえず、Goalオブジェクトにアタッチする感じで、最後にそれぞれのフレームワークのやつに代入させる
    public void addTask(Task task) {
        if (goal == null) {
            goal = new Goal(null, null, null, null, null, false, null, null);
        }
        if (taskList == null) {
            taskList = new ArrayList<Task>();
        }
        goal.addTask(task);
        taskList.add(task);

        // アダプターにデータが変更されたことを通知
        RecyclerView taskRecyclerView = findViewById(R.id.SaveGoal_taskList);
        RecyclerViewTaskListAdapter adapter = (RecyclerViewTaskListAdapter) taskRecyclerView.getAdapter();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    //タスクを探すメソッド
    private Task findTaskByID(int id) {
        for (Task task : taskList) {
            if (task.getID() == id) {
                return task;
            }
        }
        return null;
    }

    public int getTaskNum() {
        if (taskList != null) {
            return taskList.size();
        } else {
            taskList = new ArrayList<Task>();
            return taskList.size();
        }
    }

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
}


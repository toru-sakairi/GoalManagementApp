package com.example.ver2.activityClass.confirmActivityClass;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ver2.Converters;
import com.example.ver2.R;
import com.example.ver2.RecyclerViewTaskListAdapter;
import com.example.ver2.dataClass.GoalDataViewModel;
import com.example.ver2.dataClass.Task;
import com.example.ver2.dataClass.goalManagement.Benchmarking;
import com.example.ver2.dataClass.goalManagement.Goal;
import com.example.ver2.dataClass.goalManagement.GoalType;
import com.example.ver2.dataClass.goalManagement.Memo_Goal;
import com.example.ver2.dataClass.goalManagement.SMART;
import com.example.ver2.dataClass.goalManagement.WillCanMust;
import com.example.ver2.fragmentClass.confirmFragments.ConfirmGoalEditFragment;
import com.example.ver2.fragmentClass.confirmFragments.ConfirmTaskFragment;
import com.example.ver2.fragmentClass.viewModels.ConfirmGoalViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConfirmGoalActivity extends AppCompatActivity {
    private GoalDataViewModel goalDataViewModel;

    private WillCanMust wcm;
    private SMART smart;
    private Benchmarking benchmarking;
    private Memo_Goal memo;

    private Goal goal;
    private List<Task> taskList = new ArrayList<>(); // タスクリスト

    private TextView goalNameTextView;
    private TextView goalDescriptionTextView;
    private CalendarView startDateCalendar;
    private CalendarView finishDateCalendar;

    private Date startDate;
    private Date finishDate;

    private ConfirmGoalViewModel confirmGoalViewModel;
    RecyclerView taskRecyclerView;
    RecyclerViewTaskListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_goal_layout);

        goalNameTextView = findViewById(R.id.confirmGoalLayout_goalName);
        goalDescriptionTextView = findViewById(R.id.confirmGoalLayout_goalDescription);
        startDateCalendar = findViewById(R.id.confirmGoalLayout_startDateCalendar);
        finishDateCalendar = findViewById(R.id.confirmGoalLayout_finishDateCalendar);

        Intent intent = getIntent();

        //Debug用
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Log.d("Intent Debug", "Extras: " + extras.keySet());
        }


        //保存用のViewModel
        goalDataViewModel = new ViewModelProvider(ConfirmGoalActivity.this).get(GoalDataViewModel.class);

        if (intent != null) {
            if (intent.hasExtra("wcm")) {
                wcm = intent.getParcelableExtra("wcm");
                //戻ったときの挙動：すでにオブジェクトがある場合はということ
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
                //if(benchmarking == null)
                //Log.d("benchmarking is Existing", " benchmarking is not Existing");
                if (benchmarking != null && benchmarking.isGoalExist()) {
                    //Log.d("benchmarking is having goal", "benchmarking is having goal");
                    goal = new Goal(benchmarking.getName(), benchmarking.getDescription(), benchmarking.getCreateDate(), benchmarking.getStartDate(), benchmarking.getFinishDate(), benchmarking.isState(), benchmarking.getTasks(), benchmarking.getType());
                } else {
                    goal = new Goal(null, null, null, null, null, false, null, GoalType.BENCHMARKING);
                    //Log.d("benchmarking is not having goal", "benchmarking is not having goal");
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

        setActivityComponent(goal);

        //いらないかも？それか編集ボタンにしたほうが分かりやすいかも
//        Button confirmTaskButton = findViewById(R.id.confirmGoalLayout_confirmGoalButton);
//        confirmTaskButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        //オブジェクトを保持しながら的な
        Button backButton = findViewById(R.id.confirmGoalLayout_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Goalそのままやったほうがいい気がする　原因がまだわからない 2/21
                /*if (goal != null) {
                    String goalName = goalNameTextView.getText().toString();
                    String goalDescription = goalDescriptionTextView.getText().toString();

                    goal.setName(goalName);
                    goal.setDescription(goalDescription);
                    //作成日がしっかりそのままか確認する必要あるかも
                    //goal.setCreateDate(new Date());
                    //初期の値をデータと合わせる
                    goal.setStartDate(startDate);
                    goal.setFinishDate(finishDate);
                    //達成した場合とかによってTrueにしたいけどとりあえずこのまま
                    goal.setState(false);
                    goal.setTasks(taskList);
                }*/
                //機能してるっぽい
                //Log.d("Debug", "goalNameTextView: " + goalNameTextView.getText().toString());
                //Log.d("Debug", "goalDescriptionTextView: " + goalDescriptionTextView.getText().toString());
                //Log.d("goal confirm Null", goal.getName() + ":" + goal.getDescription());

                //オブジェクトに保存 ここで前のActivityに遷移
                if (wcm != null) {
                    wcm.setGoal(goal);
                    Intent intent = new Intent(ConfirmGoalActivity.this, ConfirmWillCanMustActivity.class);
                    intent.putExtra("willCanMust", wcm);
                    startActivity(intent);
                } else if (benchmarking != null) {
                    benchmarking.setGoal(goal);
                    Intent intent = new Intent(ConfirmGoalActivity.this, ConfirmBenchmarkingActivity.class);
                    intent.putExtra("benchmarking", benchmarking);
                    Log.d("Intent has benchmarking ConfirmGoalActivity", benchmarking.getName() + ":" + benchmarking.getDescription());
                    startActivity(intent);
                } else if (smart != null) {
                    smart.setGoal(goal);
                    Intent intent = new Intent(ConfirmGoalActivity.this, ConfirmSMARTActivity.class);
                    intent.putExtra("smart", smart);
                    startActivity(intent);
                } else if (memo != null) {
                    memo.setGoal(goal);
                    Intent intent = new Intent(ConfirmGoalActivity.this, ConfirmMemoGoalActivity.class);
                    intent.putExtra("memo_goal", memo);
                    startActivity(intent);
                }
            }
        });

        Button confirmGoalButton = findViewById(R.id.confirmGoalLayout_confirmGoalButton);
        confirmGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //編集しなかった場合でも保存する感じにしとく
                String goalName = goalNameTextView.getText().toString();
                String goalDescription = goalDescriptionTextView.getText().toString();

                goal.setName(goalName);
                goal.setDescription(goalDescription);
                //作成日がしっかりそのままか確認する必要あるかも
                //goal.setCreateDate(new Date());
                //このstartDateとfinishDateが設定されてないとnullになるからこれを最初に入ったときとか、アップデートする際にnullにしない
                goal.setStartDate(startDate);
                goal.setFinishDate(finishDate);
                //達成した場合とかによってTrueにしたいけどとりあえずこのまま
                goal.setState(false);
                goal.setTasks(taskList);

                //日付のnullデバッグ
                if (goal.getStartDate() != null)
                    Log.d("ConfirmGoalStartDate", startDate.toString());
                else
                    Log.d("ConfirmGoalStartDate", "NULL");
                if (goal.getFinishDate() != null)
                    Log.d("ConfirmGoalFinishDate", finishDate.toString());
                else
                    Log.d("ConfirmGoalFinishDate", "NULL");


                //データベースに保存
                if (wcm != null) {
                    wcm.setGoal(goal);
                    goalDataViewModel.updateWCM(wcm);
                } else if (benchmarking != null) {
                    benchmarking.setGoal(goal);
                    goalDataViewModel.updateBenchmarking(benchmarking);
                } else if (smart != null) {
                    smart.setGoal(goal);
                    goalDataViewModel.updateSMART(smart);
                } else if (memo != null) {
                    memo.setGoal(goal);
                    goalDataViewModel.updateMemo_Goal(memo);
                }

                Intent intent = new Intent(ConfirmGoalActivity.this, ConfirmGoalListActivity.class);
                startActivity(intent);
            }
        });

        Button editButton = findViewById(R.id.confirm_goal_Edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmGoalViewModel = new ViewModelProvider(ConfirmGoalActivity.this).get(ConfirmGoalViewModel.class);
                confirmGoalViewModel.setActivity(ConfirmGoalActivity.this);

                Bundle bundle = new Bundle();
                bundle.putParcelable("goal", goal);

                ConfirmGoalEditFragment fragment = new ConfirmGoalEditFragment();
                fragment.setArguments(bundle);
                fragment.show(getSupportFragmentManager(), "ConfirmGoalEditFragment");

            }
        });


    }


    private void setActivityComponent(Goal goal) {
        // Goal オブジェクトの値を UI に設定
        //中身がnullでも、初期値で設定されるらしい
        startDate = goal.getStartDate();
        finishDate = goal.getFinishDate();


        if (goal != null) {
            Log.d("isGoal setActivityComponent", "goal is Existing");
            //Log.d("isGoalName setActivityComponent",goal.getName());
            //Log.d("isGoalDescription setActivityComponent",goal.getDescription());
            goalNameTextView.setText(goal.getName());
            goalDescriptionTextView.setText(goal.getDescription());

            if (goal.getStartDate() != null) {
                startDateCalendar.setDate(Converters.dateToTimestamp(startDate));
            }

            if (goal.getFinishDate() != null) {
                finishDateCalendar.setDate(Converters.dateToTimestamp(finishDate));
            }
        } else {
            Log.d("isGoal setActivityComponent", "goal is not Existing");
        }

        // RecyclerView の設定
        taskRecyclerView = findViewById(R.id.confirmGoalLayout_taskList);
        taskList = new ArrayList<>(); // taskList を空の ArrayList で初期化

        if (goal != null && goal.getTasks() != null) { // goal と goal.getTasks() が null でない場合
            taskList = goal.getTasks(); // Goal オブジェクトの TaskList を taskList に代入
        }

        adapter = new RecyclerViewTaskListAdapter(taskList);
        taskRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecyclerViewTaskListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                openConfirmTaskFragment(position);
            }
        });

    }

    public void updateLayoutComponent(Goal goal_changed) {
        goal = goal_changed;

        if (goal != null) {
            //この二つを忘れない
            startDate = goal.getStartDate();
            finishDate = goal.getFinishDate();

            goalNameTextView.setText(goal.getName());
            goalDescriptionTextView.setText(goal.getDescription());
            if (goal.getStartDate() != null) {
                startDateCalendar.setDate(Converters.dateToTimestamp(startDate));

            }
            if (goal.getFinishDate() != null) {
                finishDateCalendar.setDate(Converters.dateToTimestamp(finishDate));
            }
        } else {
            Log.d("isGoal setActivityComponent", "goal is not Existing");
        }
        if (goal != null && goal.getTasks() != null) { // goal と goal.getTasks() が null でない場合
            taskList = goal.getTasks(); // Goal オブジェクトの TaskList を taskList に代入
            // アダプターにデータが変更されたことを通知
            adapter = (RecyclerViewTaskListAdapter) taskRecyclerView.getAdapter();
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }

    }

    private void openConfirmTaskFragment(int id) {
        ConfirmGoalViewModel viewModel = new ViewModelProvider(this).get(ConfirmGoalViewModel.class);
        viewModel.setActivity(this);

        Task confirmTask = findTaskByID(id);
        //Fragmentに初期値を設定する
        Bundle bundle = new Bundle();
        bundle.putParcelable("task", confirmTask);
        //Fragmentを開始
        ConfirmTaskFragment confirmTaskFragment = new ConfirmTaskFragment();
        confirmTaskFragment.setArguments(bundle);
        confirmTaskFragment.show(getSupportFragmentManager(), "ConfirmTaskFragment");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //transaction.add(R.id.addTaskFragmentView, addTaskFragment);
        transaction.commit();
    }

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

}

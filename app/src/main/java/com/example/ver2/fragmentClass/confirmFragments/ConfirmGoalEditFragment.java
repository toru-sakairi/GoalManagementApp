package com.example.ver2.fragmentClass.confirmFragments;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ver2.R;
import com.example.ver2.recyclerViewClass.RecyclerViewTaskListAdapter;
import com.example.ver2.dataClass.GoalDataViewModel;
import com.example.ver2.dataClass.Task;
import com.example.ver2.dataClass.goalManagement.Goal;
import com.example.ver2.fragmentClass.viewModels.ConfirmGoalViewModel;
import com.example.ver2.fragmentClass.viewModels.ConfirmTaskEditViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ConfirmGoalEditFragment extends DialogFragment {
    private EditText goalNameEditText;
    private EditText goalDescriptionEditText;
    private DatePicker startDatePicker;
    private DatePicker finishDatePicker;

    private Goal goal;
    private List<Task> taskList;

    private Date startDate;
    private Date finishDate;

    private GoalDataViewModel goalDataViewModel;

    ConfirmGoalViewModel confirmGoalViewModel;

    ConfirmTaskEditViewModel confirmTaskEditViewModel;

    RecyclerViewTaskListAdapter adapter;

    RecyclerView taskRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //save_goal_layoutを流用する
        View view = inflater.inflate(R.layout.save_goal_layout, container, false);

        goalDataViewModel = new ViewModelProvider(requireActivity()).get(GoalDataViewModel.class);

        //ConfirmGoalActivityのメソッド呼び出しに使用
        confirmGoalViewModel = new ViewModelProvider(requireActivity()).get(ConfirmGoalViewModel.class);

        //confirmTaskEditFragmentとのやり取りで使用
        confirmTaskEditViewModel = new ViewModelProvider(requireActivity()).get(ConfirmTaskEditViewModel.class);
        confirmTaskEditViewModel.setFragment(ConfirmGoalEditFragment.this);

        goalNameEditText = view.findViewById(R.id.goalNameEditText);
        goalDescriptionEditText = view.findViewById(R.id.goalDescriptionEditText);
        startDatePicker = view.findViewById(R.id.startDatePicker);
        finishDatePicker = view.findViewById(R.id.finishDatePicker);

        //goalオブジェクトが送られてくるという想定で、保存するときはメソッド呼び出しであちら側で処理すればいいかもしれない
        Bundle bundle = getArguments();
        if(bundle != null){
            goal = bundle.getParcelable("goal");
            if(goal != null){
                //メソッドでクラスの変数を使うのかそれとも引数のほうがいいのか、今回はいろんなところでgoal変数を使うから今回のメソッドは引数なしでいいかも？
                setFragmentComponent(view);
            }
        }

        Button saveButton = view.findViewById(R.id.saveGoalButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //goalがここでNullになる可能性あり
                if(goal != null){
                    goal.setName(goalNameEditText.getText().toString());
                    goal.setDescription(goalDescriptionEditText.getText().toString());
                    goal.setStartDate(startDate);
                    goal.setFinishDate(finishDate);

                    //ここでエラーが発生：つまり、ここでGoalオブジェクトがNullになっている
                    Log.d("goalName",goal.getName());
                    Log.d("goalDescription",goal.getDescription());
                    Log.d("goalStartDate",goal.getStartDate().toString());
                    Log.d("goalFinishDate",goal.getFinishDate().toString());

                }
                confirmGoalViewModel.callActivityMethod_updateLayoutComponent(goal);
                dismiss();
            }
        });

        Button backButton = view.findViewById(R.id.backButton_saveGoallayout);
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                dismiss();
            }
        });

        return view;
    }

    private void setFragmentComponent(View view){

        goalNameEditText.setText(goal.getName());
        goalDescriptionEditText.setText(goal.getDescription());

        // カレンダーインスタンスの再利用
        Calendar calendar = Calendar.getInstance();

        if (goal.getStartDate() != null) {
            calendar.setTime(goal.getStartDate());
            startDatePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            startDate = goal.getStartDate();
        }

        if (goal.getFinishDate() != null) {
            calendar.setTime(goal.getFinishDate());
            finishDatePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            finishDate = goal.getFinishDate();
        }

        taskRecyclerView = view.findViewById(R.id.SaveGoal_taskList);
        taskList = new ArrayList<>();
        if(goal != null && goal.getTasks() != null){
            taskList = goal.getTasks();
        }

        adapter = new RecyclerViewTaskListAdapter(taskList);
        taskRecyclerView.setAdapter(adapter);

        //Taskの変更を反映するコード？
        //RecyclerViewをタップしたときにTaskEditFragmentを表示するようにする
        adapter.setOnItemClickListener(new RecyclerViewTaskListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //タスクを編集する場合
                openChangeTaskFragment(position);
            }
        });

        // DatePicker のリスナー設定 (API レベル 26 以上)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startDatePicker.setOnDateChangedListener((datePickerView, year, monthOfYear, dayOfMonth) -> {
                calendar.set(year, monthOfYear, dayOfMonth);
                startDate = calendar.getTime();
            });

            finishDatePicker.setOnDateChangedListener((datePickerView, year, monthOfYear, dayOfMonth) -> {
                calendar.set(year, monthOfYear, dayOfMonth);
                finishDate = calendar.getTime();
            });
        }
    }

    //これもSaveGoalActivityから持ってきたやつ
    //新しく作る場合
    private void openNewTaskFragment() {
        ConfirmTaskEditFragment fragment = new ConfirmTaskEditFragment();
        //FragmentからFragmentを生成してるからgetActivity()が必要になるんだと思う
        fragment.show(getActivity().getSupportFragmentManager(), "ConfirmTaskEditFragment");
    }

    //現在のものを編集する場合
    private void openChangeTaskFragment(int id) {
        //id(RecyclerViewでタップしたところのposition)の編集
        Bundle bundle = new Bundle();
        bundle.putParcelable("task", findTaskByID(id));
        ConfirmTaskEditFragment fragment = new ConfirmTaskEditFragment();
        fragment.setArguments(bundle);
        //FragmentからFragmentを生成してるからgetActivity()が必要になるんだと思う
        fragment.show(getActivity().getSupportFragmentManager(), "ConfirmTaskEditFragment");
    }

    //Task変更後のUI（RecyclerView）Updateを行うメソッド
    public void updateTaskList(){
        if (goal != null && goal.getTasks() != null) { // goal と goal.getTasks() が null でない場合
            taskList = goal.getTasks(); // Goal オブジェクトの TaskList を taskList に代入
            // アダプターにデータが変更されたことを通知
            adapter = (RecyclerViewTaskListAdapter) taskRecyclerView.getAdapter();
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }
    }



    //これはSaveGoalActivityから持ってきたやつ
    //とりあえず、Goalオブジェクトにアタッチする感じで、最後にそれぞれのフレームワークのやつに代入させる
    public void addTask(Task task) {

//        if(goal != null) {
//            goal.addTask(task);
//        }
        if(taskList != null){
            taskList.add(task);
        }else{
            taskList = new ArrayList<Task>();
            taskList.add(task);
        }

        // アダプターにデータが変更されたことを通知
        adapter = (RecyclerViewTaskListAdapter) taskRecyclerView.getAdapter();
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
    }

}

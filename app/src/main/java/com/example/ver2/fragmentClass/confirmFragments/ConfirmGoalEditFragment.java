/*
    Goalの内容を編集するFragment
 */
package com.example.ver2.fragmentClass.confirmFragments;

import android.os.Build;
import android.os.Bundle;
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

    //Activity側に変更されたことを通知するためのViewModel
    ConfirmGoalViewModel confirmGoalViewModel;

    //TaskFragmentと情報を共有、更新するときに使用
    ConfirmTaskEditViewModel confirmTaskEditViewModel;

    //TaskリストをUIでリスト化して表示するため
    RecyclerViewTaskListAdapter adapter;
    RecyclerView taskRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //save_goal_layoutを流用する
        View view = inflater.inflate(R.layout.save_goal_layout, container, false);

        //ConfirmGoalActivityのメソッド呼び出しに使用
        confirmGoalViewModel = new ViewModelProvider(requireActivity()).get(ConfirmGoalViewModel.class);
        //LiveDataを監視
        confirmGoalViewModel.getGoalLiveData().observe(this, goalLiveData -> {
            //アップデートする
            goal = goalLiveData;
            setFragmentComponent(view);
        });

        //confirmTaskEditFragmentとのやり取りで使用
        confirmTaskEditViewModel = new ViewModelProvider(requireActivity()).get(ConfirmTaskEditViewModel.class);
        confirmTaskEditViewModel.updateTaskList(taskList);
        //LiveDataを監視、TaskListに変更が加えられた際にgoalオブジェクトにもその値を変更し通知する
        confirmTaskEditViewModel.getTaskListLiveData().observe(this, taskListLiveData -> {
            if(goal != null){
                goal.setTasks(taskListLiveData);
                confirmGoalViewModel.updateGoal(goal);
            }
        });

        goalNameEditText = view.findViewById(R.id.goalNameEditText);
        goalDescriptionEditText = view.findViewById(R.id.goalDescriptionEditText);
        startDatePicker = view.findViewById(R.id.startDatePicker);
        finishDatePicker = view.findViewById(R.id.finishDatePicker);

        //セーブボタンの処理
        Button saveButton = view.findViewById(R.id.saveGoalButton);
        saveButton.setOnClickListener(view1 -> {
                //goalがここでNullになる可能性あり
                if(goal != null){
                    //内容を取得して更新
                    goal.setName(goalNameEditText.getText().toString());
                    goal.setDescription(goalDescriptionEditText.getText().toString());
                    goal.setStartDate(startDate);
                    goal.setFinishDate(finishDate);
                }
                //ViewModelのMutableLiveDataを変更してActivityのUIやオブジェクトに変更されたことを通知
                confirmGoalViewModel.updateGoal(goal);

                //Fragmentを終了する
                dismiss();
        });

        //前の画面（元のActivity）に戻るボタン
        Button backButton = view.findViewById(R.id.backButton_saveGoallayout);
        backButton.setOnClickListener(view_before -> dismiss());

        return view;
    }

    //UIを設定
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
        if(goal != null){
            taskList = goal.getTasks() != null ? new ArrayList<>(goal.getTasks()) : new ArrayList<>();
        }

        adapter = new RecyclerViewTaskListAdapter(taskList);
        taskRecyclerView.setAdapter(adapter);

        //RecyclerViewをタップしたときにTaskEditFragmentを表示するようにする
        //タスクを編集する場合
        adapter.setOnItemClickListener(this::openChangeTaskFragment);

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

    //タスクを探すメソッド
    private Task findTaskByID(int id) {
        for (Task task : taskList) {
            if (task.getID() == id) {
                return task;
            }
        }
        return null;
    }
}

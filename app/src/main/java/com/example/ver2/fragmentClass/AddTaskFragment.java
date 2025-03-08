package com.example.ver2.fragmentClass;

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

import com.example.ver2.R;
import com.example.ver2.dataClass.Task;
import com.example.ver2.fragmentClass.viewModels.SaveGoalFragmentViewModel;

import java.util.Calendar;
import java.util.Date;

public class AddTaskFragment extends DialogFragment {
    private EditText taskNameEditText, taskDescriptionEditText;
    private DatePicker taskStartDatePicker, taskFinishDatePicker;
    private Button saveTaskButton;
    private Task task;

    private SaveGoalFragmentViewModel viewModel;

    private boolean changeFlag;

    private Date taskStartDate, taskFinishDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_task_layout, container, false);
        taskNameEditText = view.findViewById(R.id.taskName);
        taskDescriptionEditText = view.findViewById(R.id.taskDescription);
        taskStartDatePicker = view.findViewById(R.id.taskStartDate);
        taskFinishDatePicker = view.findViewById(R.id.taskFinishDate);

        //SaveGoalActivityのメソッド呼び出しに使用
        viewModel = new ViewModelProvider(requireActivity()).get(SaveGoalFragmentViewModel.class);

        //タスクを上書きする場合にTrueにして使用
        changeFlag = false;

        Bundle bundle = getArguments();

        //新規の場合
        if (bundle != null) {
            task = bundle.getParcelable("changeTask");
            if (task != null) {
                taskNameEditText.setText(task.getName());
                taskDescriptionEditText.setText(task.getDescription());
                if (task.getStartDate() != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(task.getStartDate());
                    taskStartDatePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    taskStartDate = task.getStartDate();
                }
                if (task.getFinishDate() != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(task.getFinishDate());
                    taskFinishDatePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    taskFinishDate = task.getFinishDate();
                }
                changeFlag = true;
            }
        }else{
            //新規の場合はここでTaskを作成
            changeFlag = false;
            task = new Task(viewModel.callActivityMethod_getTaskNum(), null,null,new Date(),null,null,false);
        }

        // DatePicker のリスナー設定 (API レベル 26 以上)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            taskStartDatePicker.setOnDateChangedListener((datePickerView, year, monthOfYear, dayOfMonth) -> {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);
                taskStartDate = calendar.getTime();
            });

            taskFinishDatePicker.setOnDateChangedListener((datePickerView, year, monthOfYear, dayOfMonth) -> {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);
                taskFinishDate = calendar.getTime();
            });
        }

        saveTaskButton = view.findViewById(R.id.saveTaskButton);
        saveTaskButton.setOnClickListener(view1 -> {
            String taskName = taskNameEditText.getText().toString();
            String taskDescription = taskDescriptionEditText.getText().toString();

            task.setName(taskName);
            task.setDescription(taskDescription);
            task.setCreateDate(new Date());
            task.setStartDate(taskStartDate);
            task.setFinishDate(taskFinishDate);

            //上書か新規保存か(ChangeTask() か　AddTask()　か)
            if (changeFlag) {
                viewModel.callActivityMethod_changeTask(task);
            } else {
                viewModel.callActivityMethod_AddTask(task);
            }
            dismiss();
        });

        Button backButton = view.findViewById(R.id.backButton_addTasklayout);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return view;
    }

}

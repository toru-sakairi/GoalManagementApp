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

import com.example.ver2.R;
import com.example.ver2.dataClass.Task;
import com.example.ver2.fragmentClass.viewModels.ConfirmTaskEditViewModel;
import com.example.ver2.fragmentClass.viewModels.SaveGoalFragmentViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.zip.Inflater;

public class ConfirmTaskEditFragment extends DialogFragment {
    private EditText taskNameEditText;
    private EditText taskDescriptionEditText;
    private DatePicker startDatePicker;
    private DatePicker finishDatePicker;

    private Task task;

    private Date startDate;
    private Date finishDate;

    //編集する場合か新規かで分けるための変数
    private boolean isNew;

    private ConfirmTaskEditViewModel viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //add_task_layoutを流用
        View view = inflater.inflate(R.layout.add_task_layout, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(ConfirmTaskEditViewModel.class);

        taskNameEditText = view.findViewById(R.id.taskName);
        taskDescriptionEditText = view.findViewById(R.id.taskDescription);
        startDatePicker = view.findViewById(R.id.taskStartDate);
        finishDatePicker = view.findViewById(R.id.taskFinishDate);

        Bundle bundle = getArguments();

        // カレンダーインスタンスの再利用
        Calendar calendar = Calendar.getInstance();

        //タスク編集する場合はこれ
        if (bundle != null) {
            task = bundle.getParcelable("task");
            if (task != null) {
                isNew = false;
                taskNameEditText.setText(task.getName());
                taskDescriptionEditText.setText(task.getDescription());
                //DatePickerの初期値を設定するのとDate型の変数の初期値を設定する
                if (task.getStartDate() != null) {
                    calendar.setTime(task.getStartDate());
                    startDatePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    startDate = task.getStartDate();
                }
                if (task.getFinishDate() != null) {
                    calendar.setTime(task.getFinishDate());
                    finishDatePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    finishDate = task.getFinishDate();
                }
            }
        } else {
            isNew = true;
        }

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

        //ここに編集中のConfirmGoalEditFragmentのタスクリスト（RecyclerView）の反映をする
        Button saveButton = view.findViewById(R.id.saveTaskButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                task.setName(taskNameEditText.getText().toString());
                task.setDescription(taskDescriptionEditText.getText().toString());
                task.setStartDate(startDate);
                task.setFinishDate(finishDate);
                task.setCreateDate(new Date());

                if (isNew) {
                    task.setID(viewModel.callFragmentMethod_getTaskNum());
                    viewModel.callFragmentMethod_addTask(task);
                    viewModel.callFragmentMethod_updateTaskList();
                    dismiss();
                }else{
                    //IDはBundleで送られてきたTaskオブジェクトにそのままあってへんこうしてないから何もしない
                    viewModel.callFragmentMethod_changeTask(task);
                    viewModel.callFragmentMethod_updateTaskList();
                    dismiss();
                }
            }
        });

        //何も反映しない
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

package com.example.ver2.fragmentClass.confirmFragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.ver2.Converters;
import com.example.ver2.R;
import com.example.ver2.dataClass.Task;

import java.util.Calendar;

public class ConfirmTaskFragment extends DialogFragment {
    private Task task;

    private TextView taskNameText;
    private TextView taskDescriptionText;

    private CalendarView startCalendarView;
    private CalendarView finishCalendarView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.confirm_task_layout, container, false);
        taskNameText = view.findViewById(R.id.confirm_TaskLayout_taskName);
        taskDescriptionText = view.findViewById(R.id.confirm_TaskLayout_taskDescription);
        startCalendarView = view.findViewById(R.id.confirm_TaskLayout_taskStartDate);
        finishCalendarView = view.findViewById(R.id.confirm_TaskLayout_taskFinishDate);

        //taskオブジェクトが送られてくる想定
        Bundle bundle = getArguments();
        if(bundle != null){
            task = bundle.getParcelable("task");
            if(task != null){
                taskNameText.setText(task.getName());
                taskDescriptionText.setText(task.getDescription());
                if(task.getStartDate() != null){
                    startCalendarView.setDate(Converters.dateToTimestamp(task.getStartDate()));
                }
                if(task.getFinishDate() != null) {
                    finishCalendarView.setDate(Converters.dateToTimestamp(task.getFinishDate()));
                }
            }
        }

        Button confirmButton = view.findViewById(R.id.confirmTaskButton);
        confirmButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                dismiss();
            }
        });

        return view;
    }
}

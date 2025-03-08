package com.example.ver2.fragmentClass.viewModels;
import androidx.lifecycle.ViewModel;

import com.example.ver2.activityClass.confirmActivityClass.ConfirmBenchmarkingActivity;
import com.example.ver2.dataClass.goalManagement.Benchmarking;

public class ConfirmBenchmarkingViewModel extends ViewModel {
    private ConfirmBenchmarkingActivity activity;

    public void setActivity(ConfirmBenchmarkingActivity activity){ this.activity = activity;}

    public void callActivityMethod_updateTextView(Benchmarking benchmarking){
        activity.updateTextView(benchmarking);
    }
}

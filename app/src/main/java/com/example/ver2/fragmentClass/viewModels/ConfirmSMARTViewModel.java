package com.example.ver2.fragmentClass.viewModels;

import androidx.lifecycle.ViewModel;

import com.example.ver2.activityClass.confirmActivityClass.ConfirmBenchmarkingActivity;
import com.example.ver2.activityClass.confirmActivityClass.ConfirmSMARTActivity;
import com.example.ver2.dataClass.goalManagement.SMART;
import com.example.ver2.fragmentClass.confirmFragments.ConfirmSMARTEditFragment;

public class ConfirmSMARTViewModel extends ViewModel {
    private ConfirmSMARTActivity activity;

    public void setActivity(ConfirmSMARTActivity activity){ this.activity = activity; }

    public void callActivityMethod_updateTextView(SMART smart){
        activity.updateTextView(smart);
    }

}

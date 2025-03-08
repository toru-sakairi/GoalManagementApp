package com.example.ver2.fragmentClass.viewModels;

import androidx.lifecycle.ViewModel;

import com.example.ver2.activityClass.confirmActivityClass.ConfirmBenchmarkingActivity;
import com.example.ver2.activityClass.confirmActivityClass.ConfirmGoalActivity;
import com.example.ver2.dataClass.goalManagement.Goal;

public class ConfirmGoalViewModel extends ViewModel {
    private ConfirmGoalActivity activity;

    public void setActivity(ConfirmGoalActivity activity) {this.activity = activity;}

    public void callActivityMethod_updateLayoutComponent(Goal goal){
        activity.updateLayoutComponent(goal);
    }
}

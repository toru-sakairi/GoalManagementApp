package com.example.ver2.fragmentClass.viewModels;

import androidx.lifecycle.ViewModel;

import com.example.ver2.activityClass.confirmActivityClass.ConfirmMemoGoalActivity;
import com.example.ver2.dataClass.goalManagement.Memo_Goal;

public class ConfirmMemoGoalViewModel extends ViewModel {
    private ConfirmMemoGoalActivity activity;

    public void setActivity(ConfirmMemoGoalActivity activity){this.activity = activity;}

    public void callActivityMethod_updateTextView(Memo_Goal memo){
        activity.updateTextView(memo);
    }
}

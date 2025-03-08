package com.example.ver2.fragmentClass.viewModels;

import androidx.lifecycle.ViewModel;

import com.example.ver2.activityClass.confirmActivityClass.ConfirmWillCanMustActivity;
import com.example.ver2.dataClass.goalManagement.WillCanMust;

public class ConfirmWillCanMustViewModel extends ViewModel {
    private ConfirmWillCanMustActivity activity;

    public void setActivity(ConfirmWillCanMustActivity activity){this.activity = activity;}

    public void callActivityMethod_updateTextView(WillCanMust wcm){
        activity.updateTextView(wcm);
    }

}

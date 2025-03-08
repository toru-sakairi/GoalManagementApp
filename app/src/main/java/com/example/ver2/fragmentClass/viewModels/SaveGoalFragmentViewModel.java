package com.example.ver2.fragmentClass.viewModels;

import androidx.lifecycle.ViewModel;

import com.example.ver2.activityClass.createActivityClass.SaveGoalActivity;
import com.example.ver2.dataClass.Task;

public class SaveGoalFragmentViewModel extends ViewModel {
    private SaveGoalActivity activity;

    public void setActivity(SaveGoalActivity activity){
        this.activity = activity;
    }

    //returnないからここでエラーが出てもわからないから注意
    public void callActivityMethod_AddTask(Task task){
        if(activity != null){
            activity.addTask(task);
        }
    }
    public int callActivityMethod_getTaskNum() {
        if (activity != null) {
            return activity.getTaskNum();
        }
        return -1;
    }

    public void callActivityMethod_changeTask(Task task){
        if(activity != null){
            activity.changeTask(task);
        }
    }
}

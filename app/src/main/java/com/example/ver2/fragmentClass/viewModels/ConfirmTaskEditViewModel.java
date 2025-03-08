package com.example.ver2.fragmentClass.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ver2.dataClass.Task;
import com.example.ver2.fragmentClass.confirmFragments.ConfirmGoalEditFragment;

public class ConfirmTaskEditViewModel extends ViewModel {
    private ConfirmGoalEditFragment fragment;

    public void setFragment(ConfirmGoalEditFragment fragment){this.fragment = fragment;}

    public void callFragmentMethod_addTask(Task task){
        if(fragment != null)
            fragment.addTask(task);
    }

    public void callFragmentMethod_changeTask(Task task){
        if(fragment != null)
            fragment.changeTask(task);
    }

    public int callFragmentMethod_getTaskNum(){
        if(fragment != null){
            return fragment.getTaskNum();
        }
        else
            return -1;
    }

    public void callFragmentMethod_updateTaskList(){
        fragment.updateTaskList();
    }

}

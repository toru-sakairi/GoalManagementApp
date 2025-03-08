package com.example.ver2.dataClass.purposeManagement;

import androidx.room.Entity;

import com.example.ver2.dataClass.Task;
import java.util.*;

@Entity(tableName = "memo_purposes")
public class Memo_Purpose extends Purpose{
    private List<Task> tasks;
    private String memo; //いるかわからないけど一応

    public Memo_Purpose(int ID, String name, String description, Date createDate, Date startDate, Date finishDate, boolean state, List<Task> tasks, String purpose, List<String> goals, List<Chart> charts) {
        super(ID, name, description, createDate, startDate, finishDate, state, tasks, "Memo");
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        if (this.tasks == null) {
            this.tasks = new ArrayList<>();
        }
        this.tasks.add(task);
    }

    public void removeTask(Task task) {
        if (this.tasks != null) {
            this.tasks.remove(task);
        }
    }
    public String getMemo(){
        return memo;
    }
    public void setMemo(String memo){
        this.memo = memo;
    }
}

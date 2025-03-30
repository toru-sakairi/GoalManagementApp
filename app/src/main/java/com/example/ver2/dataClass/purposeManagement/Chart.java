package com.example.ver2.dataClass.purposeManagement;

import com.example.ver2.dataClass.Task;

import java.util.ArrayList;
import java.util.List;

public class Chart {
    private int ID;
    private String goal;
    private List<Task> tasks;
    private boolean state;

    public Chart(int ID, String goal, List<Task> tasks, boolean state) {
        this.ID = ID;
        this.goal = goal;
        this.tasks = tasks;
        this.state = state;
    }

    public int getID() {
        return ID;
    }

    public String getGoal() {
        return goal;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public boolean isState() {
        return state;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void setState(boolean state) {
        this.state = state;
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
}

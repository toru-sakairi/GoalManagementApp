package com.example.ver2.dataClass.purposeManagement;

import androidx.room.Entity;

import com.example.ver2.dataClass.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(tableName = "mandala_charts")
public class MandalaChart extends Purpose{
    private String purpose;
    //private List<String> goals; //chartと役割がかぶってる気がする
    private List<Chart> charts;

    public MandalaChart(int ID, String name, String description, Date createDate, Date startDate, Date finishDate, boolean state, List<Task> tasks, String purpose, List<Chart> charts) {
        super(ID, name, description, createDate, startDate, finishDate, state, tasks, "MandalaChart");
        this.purpose = purpose;
        //this.goals = goals;
        this.charts = charts;
    }


    public String getPurpose(){
        return purpose;
    }
    public Chart getChartbyID(int id){
        for(Chart chart:charts){
            if(chart.getID() == id){
                return chart;
            }
        }
        return null;
    }
    //public List<String> getGoals(){
    //    return goals;
    //}

    public List<Chart> getCharts(){
        return charts;
    }
    public void setPurpose(String purpose){
        this.purpose = purpose;
    }
    //public void setGoals(List<String> goals) {
    //    this.goals = goals;
    //}
    public void setCharts(List<Chart> charts) {
        this.charts = charts;
    }

    /*public void addGoal(String goal){
        if(goals == null){
            goals = new ArrayList<>();
        }
        goals.add(goal);
    }*/
    public void addChart(Chart chart){
        if(charts == null){
            charts = new ArrayList<>();
        }
        charts.add(chart);
    }

}

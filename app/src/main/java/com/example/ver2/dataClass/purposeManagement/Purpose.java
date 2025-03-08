package com.example.ver2.dataClass.purposeManagement;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.ver2.dataClass.Task;
import com.example.ver2.Converters;

import java.util.Date;  //Date型はroomに非対応のため、コンバーターが必要
import java.util.List;

@Entity(tableName = "purposes")
public class Purpose {
    @PrimaryKey(autoGenerate = true)
    private int ID;
    private String name;
    private String description;
    @TypeConverters(Converters.class)
    private Date createDate;
    @TypeConverters(Converters.class)
    private Date startDate;
    @TypeConverters(Converters.class)
    private Date finishDate;
    private boolean state;
    //private List<Task> tasks; //サブクラスのほうでタスクを設定するからここでやると分からなくなるかもだから、いったん消す
    private String type;    //MandalaChart or Memo

    public Purpose(int ID, String name, String description, Date createDate, Date startDate, Date finishDate, boolean state, List<Task> tasks, String type) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.createDate = createDate;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.state = state;
        //this.tasks = tasks;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Purpose{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createDate=" + createDate +
                ", startDate=" + startDate +
                ", finishDate=" + finishDate +
                ", state=" + state +
                //", tasks=" + tasks +
                ", type='" + type + '\'' +
                '}';
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public boolean getState() {
        return state;
    }

    //public List<Task> getTasks() {
    //    return tasks;
    //}

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    //public void setTasks(List<Task> tasks) {
    //    this.tasks = tasks;
    //}

    public void setType(String type) {
        this.type = type;
    }
}

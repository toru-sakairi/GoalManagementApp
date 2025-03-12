package com.example.ver2.dataClass;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.ver2.Converters;
import com.example.ver2.dataClass.goalManagement.Benchmarking;

import java.io.Serializable;
import java.util.Date;

public class Task implements Parcelable {
    private int ID;
    private String name;
    private String description;
    private Date createDate;
    private Date startDate;
    private Date finishDate;
    private boolean state;

    public Task(int ID, String name,String description,Date createDate,Date startDate,Date finishDate,boolean state)
    {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.createDate = createDate;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.state = state;
    }

    public int getID(){
        return ID;
    }
    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }
    public Date getCreateDate(){
        return createDate;
    }
    public Date getStartDate(){
        return startDate;
    }
    public Date getFinishDate(){
        return finishDate;
    }
    public boolean getState(){
        return state;
    }
    public void setID(int ID){
        this.ID = ID;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setCreateDate(Date createDate){
        this.createDate = createDate;
    }
    public void setStartDate(Date startDate){
        this.startDate = startDate;
    }
    public void setFinishDate(Date finishDate){
        this.finishDate = finishDate;
    }
    public void setState(boolean state){
        this.state = state;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeLong(Converters.dateToTimestamp(createDate));
        dest.writeLong(Converters.dateToTimestamp(startDate));
        dest.writeLong(Converters.dateToTimestamp(finishDate));
        dest.writeByte((byte) (state ? 1 : 0));

    }

    protected Task(Parcel in){
        ID = in.readInt();
        name = in.readString();
        description = in.readString();
        createDate = Converters.fromTimestamp(in.readLong());
        startDate = Converters.fromTimestamp(in.readLong());
        finishDate = Converters.fromTimestamp(in.readLong());
        state = in.readByte() != 0;
    }

    //Parcelableオブジェクトを生成するためのCreatorを定義
    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}

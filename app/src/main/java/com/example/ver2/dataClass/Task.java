/*
    GoalやPurposeに使用されるTaskクラス。これは単体ではデータベースに保存しない。
    Goalクラスなどに含まれて保存される
 */

package com.example.ver2.dataClass;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.ver2.Converters;

import java.util.Date;

public class Task implements Parcelable {
    //taskの順番ごとにIDをつけている
    private int ID;
    private String name;
    private String description;
    private Date createDate;
    private Date startDate;
    private Date finishDate;
    private boolean state;

    //コンストラクタ
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

    //ゲッター＆セッター
    public int getID(){
        return ID;
    }
    public void setID(int ID){
        this.ID = ID;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public Date getCreateDate(){
        return createDate;
    }
    public void setCreateDate(Date createDate){
        this.createDate = createDate;
    }
    public Date getStartDate(){
        return startDate;
    }
    public void setStartDate(Date startDate){
        this.startDate = startDate;
    }
    public Date getFinishDate(){
        return finishDate;
    }
    public void setFinishDate(Date finishDate){
        this.finishDate = finishDate;
    }
    public boolean getState(){
        return state;
    }
    public void setState(boolean state){
        this.state = state;
    }

    //Parcelableオブジェクトの管理
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(name);
        dest.writeString(description);
//Serializableだと互換性がなくバグの原因になるため。また、始めにGoalをインスタンス化する際はすべてnullだからnullの場合の初期値を設定する必要がある
        Long createDateTimestamp = Converters.dateToTimestamp(createDate);
        dest.writeLong(createDateTimestamp != null ? createDateTimestamp : 0L);

        Long startDateTimestamp = Converters.dateToTimestamp(startDate);
        dest.writeLong(startDateTimestamp != null ? startDateTimestamp : 0L);

        Long finishDateTimestamp = Converters.dateToTimestamp(finishDate);
        dest.writeLong(finishDateTimestamp != null ? finishDateTimestamp : 0L);

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

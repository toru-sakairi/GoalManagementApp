package com.example.ver2.dataClass.goalManagement;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.example.ver2.dataClass.Task;

@Entity(tableName = "SMART")
public class SMART extends Goal implements Parcelable {
    private String specific;
    private String measurable;
    private String achievable;
    private String relevant;
    private String timeBound;
    private String smartGoal;

    public SMART(String name, String description, Date createDate, java.util.Date startDate, Date finishDate, boolean state, List<Task> tasks, GoalType type , String specific, String measurable, String achievable, String relevant, String timeBound, String smartGoal){
        super(name, description, createDate, startDate, finishDate, state, tasks, type);
        this.specific = specific;
        this.measurable = measurable;
        this.achievable = achievable;
        this.relevant = relevant;
        this.timeBound = timeBound;
        this.smartGoal = smartGoal;
    }

    @Ignore
    public SMART(Goal goal, String s, String m,String a, String r, String t, String g){
        super(goal);
        this.specific = s;
        this.measurable = m;
        this.achievable = a;
        this.relevant = r;
        this.timeBound = t;
        this.smartGoal = g;
    }

    @Ignore
    public SMART(String s, String m, String a, String r, String t, String g){
        super(null,null,null,null,null,false,null, GoalType.SMART);
        this.specific = s;
        this.measurable = m;
        this.achievable = a;
        this.relevant = r;
        this.timeBound = t;
        this.smartGoal = g;
    }


    public String getSpecific(){
        return specific;
    }
    public String getMeasurable(){
        return measurable;
    }
    public String getAchievable(){
        return achievable;
    }
    public String getRelevant(){
        return relevant;
    }
    public String getTimeBound(){
        return timeBound;
    }
    public String getSmartGoal(){
        return smartGoal;
    }
    public void setSpecific(String specific){
        this.specific = specific;
    }
    public void setMeasurable(String measurable){
        this.measurable = measurable;
    }
    public void setAchievable(String achievable){
        this.achievable = achievable;
    }
    public void setRelevant(String relevant){
        this.relevant = relevant;
    }
    public void setTimeBound(String timeBound) {
        this.timeBound = timeBound;
    }
    public void setSmartGoal(String smartGoal){
        this.smartGoal = smartGoal;
    }

    // Parcelable の実装
    //オブジェクトがどのような種類のコンテンツを含んでいるか記述する。ほとんどの場合0を返す。
    @Override
    public int describeContents() {
        return 0;
    }

    //オブジェクトのフィールドの値をParcelに書き込むメソッド。
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //各フィールドの値を書き込んでいく　書き込み順と読み込み順は一致させる必要あり
        super.writeToParcel(dest, flags); // 親クラスの writeToParcel を呼び出す
        dest.writeString(specific);
        dest.writeString(measurable);
        dest.writeString(achievable);
        dest.writeString(relevant);
        dest.writeString(timeBound);
        dest.writeString(smartGoal);
    }

    protected SMART(Parcel in) {
        //書き込み順と必ず一致させる必要がある
        super(in); // 親クラスのコンストラクタを呼び出す
        specific= in.readString();
        measurable = in.readString();
        achievable = in.readString();
        relevant = in.readString();
        timeBound = in.readString();
        smartGoal = in.readString();
    }

    //Parcelableオブジェクトを生成するためのCreatorを定義
    public static final Creator<SMART> CREATOR = new Creator<SMART>() {
        @Override
        public SMART createFromParcel(Parcel in) {
            return new SMART(in);
        }

        @Override
        public SMART[] newArray(int size) {
            return new SMART[size];
        }
    };
}

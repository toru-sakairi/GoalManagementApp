package com.example.ver2.dataClass.goalManagement;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;

import com.example.ver2.dataClass.Task;
import com.example.ver2.dataClass.purposeManagement.Memo_Purpose;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(tableName = "memo_goals")
public class Memo_Goal extends Goal implements Parcelable {
    private String memo;

    public Memo_Goal(String name, String description, Date createDate, java.util.Date startDate, Date finishDate, boolean state, List<Task> tasks, GoalType type, String memo){
        super(name, description, createDate, startDate, finishDate, state, tasks, type);
        this.memo = memo;
    }

    @Ignore
    public Memo_Goal(Goal goal , String memo){
        super(goal);
        this.memo = memo;
    }

    @Ignore
    public Memo_Goal(String memo){
        super(null,null,null,null,null,false,null,GoalType.MEMO_GOAL);
        this.memo = memo;
    }

    public String getMemo(){
        return memo;
    }
    public void setMemo(String memo){
        this.memo = memo;
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
        dest.writeString(memo);
    }

    protected Memo_Goal(Parcel in) {
        //書き込み順と必ず一致させる必要がある
        super(in); // 親クラスのコンストラクタを呼び出す
        memo = in.readString();
        }

    //Parcelableオブジェクトを生成するためのCreatorを定義
    public static final Creator<Memo_Goal> CREATOR = new Creator<Memo_Goal>() {
        @Override
        public Memo_Goal createFromParcel(Parcel in) {
            return new Memo_Goal(in);
        }

        @Override
        public Memo_Goal[] newArray(int size) {
            return new Memo_Goal[size];
        }
    };
}

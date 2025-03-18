/*
    GoalクラスをスーパークラスとするWillCanMustクラス
    Will・Can・Mustフレームワークを用いた目標設定でのデータとして使用
 */

package com.example.ver2.dataClass.goalManagement;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;

import com.example.ver2.dataClass.Task;

import java.util.Date;
import java.util.List;

@Entity(tableName = "will_can_must")
public class WillCanMust extends Goal implements Parcelable {
    private String will;
    private String can;
    private String must;
    private String wcmGoal;

    //コンストラクタ：Goalに書かれている属性も含めたもの。データベースから取得するときなどに使用
    public WillCanMust(String name, String description, Date createDate, Date startDate, Date finishDate, boolean state, List<Task> tasks, GoalType type,String will,String can,String must, String wcmGoal){
        super(name, description, createDate, startDate, finishDate, state, tasks, type);
        this.will = will;
        this.can = can;
        this.must = must;
        this.wcmGoal = wcmGoal;
    }

    //コンストラクタ：Goalオブジェクトをそのまま入れることができるコンストラクタ
    @Ignore
    public WillCanMust(Goal goal, String will, String can, String must, String wcmGoal){
        super(goal);
        this.will = will;
        this.can = can;
        this.must = must;
        this.wcmGoal = wcmGoal;
    }

    //コンストラクタ：Goalオブジェクトをnullの状態で作るコンストラクタ（最初にWillCanMustクラスをインスタンス化するときなどに使用）
    @Ignore
    public WillCanMust(String will, String can, String must, String wcmGoal){
        super(null,null,null,null,null,false,null, GoalType.WILL_CAN_MUST);
        this.will = will;
        this.can = can;
        this.must = must;
        this.wcmGoal = wcmGoal;
    }

    //ゲッター＆セッター
    public String getWill(){ return will; }
    public void setWill(String will){ this.will = will; }
    public String getCan(){ return can; }
    public void setCan(String can){ this.can = can; }
    public String getMust(){ return must; }
    public void setMust(String must){ this.must = must; }
    public String getWcmGoal(){ return wcmGoal; }
    public void setWcmGoal(String wcmGoal){ this.wcmGoal = wcmGoal; }

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
        dest.writeString(will);
        dest.writeString(can);
        dest.writeString(must);
        dest.writeString(wcmGoal);
    }

    protected WillCanMust(Parcel in) {
        //書き込み順と必ず一致させる必要がある
        super(in); // 親クラスのコンストラクタを呼び出す
        will = in.readString();
        can = in.readString();
        must = in.readString();
        wcmGoal = in.readString();
    }

    //Parcelableオブジェクトを生成するためのCreatorを定義
    public static final Creator<WillCanMust> CREATOR = new Creator<WillCanMust>() {
        @Override
        public WillCanMust createFromParcel(Parcel in) { return new WillCanMust(in); }
        @Override
        public WillCanMust[] newArray(int size) { return new WillCanMust[size]; }
    };
}

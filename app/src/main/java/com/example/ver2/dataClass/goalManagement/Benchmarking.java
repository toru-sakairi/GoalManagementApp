/*
    GoalクラスをスーパークラスとするBenchmarkingクラス
    ベンチマーキングフレームワークを用いた目標設定でのデータとして使用
 */

package com.example.ver2.dataClass.goalManagement;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;

import com.example.ver2.dataClass.Task;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

@Entity(tableName = "Benchmarking")
public class Benchmarking extends Goal implements Parcelable {
    private String initialGoal;
    private String target;
    private String benchMark;
    private String comparison;

    //コンストラクタ：Goalに書かれている属性も含めたもの。データベースから取得するときなどに使用
    public Benchmarking(String name, String description, Date createDate, Date startDate, Date finishDate, boolean state, List<Task> tasks, String initialGoal, String target, String benchMark,String comparison, GoalType type){
        super(name, description, createDate, startDate, finishDate, state, tasks, type);
        this.initialGoal = initialGoal;
        this.target = target;
        this.benchMark = benchMark;
        this.comparison = comparison;
    }

    //コンストラクタ：Goalオブジェクトをそのまま入れることができるコンストラクタ
    @Ignore
    public Benchmarking(Goal goal, String initialGoal, String target, String benchMark, String comparison){
        super(goal);
        this.initialGoal = initialGoal;
        this.target = target;
        this.benchMark = benchMark;
        this.comparison = comparison;
    }

    //コンストラクタ：Goalオブジェクトをnullの状態で作るコンストラクタ（最初にBenchmarkingクラスをインスタンス化するときなどに使用）
    @Ignore
    public Benchmarking(String initialGoal, String target, String benchMark, String comparison){
        super(null,null,null,null,null,false,null, GoalType.BENCHMARKING);
        this.initialGoal = initialGoal;
        this.target = target;
        this.benchMark = benchMark;
        this.comparison = comparison;
    }

    //ゲッター＆セッター
    public String getInitialGoal(){ return initialGoal; }
    public void setInitialGoal(String initialGoal){ this.initialGoal = initialGoal; }
    public String getTarget(){ return target; }
    public void setTarget(String target){ this.target = target; }
    public String getBenchMark(){ return benchMark; }
    public void setBenchMark(String benchMark){ this.benchMark = benchMark; }
    public String getComparison() { return comparison; }
    public void setComparison(String comparison){ this.comparison = comparison; }

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
        dest.writeString(initialGoal);
        dest.writeString(target);
        dest.writeString(benchMark);
        dest.writeString(comparison);
    }

    protected Benchmarking(Parcel in) {
        //書き込み順と必ず一致させる必要がある
        super(in); // 親クラスのコンストラクタを呼び出す
        initialGoal = in.readString();
        target = in.readString();
        benchMark = in.readString();
        comparison = in.readString();
    }

    //Parcelableオブジェクトを生成するためのCreatorを定義
    public static final Creator<Benchmarking> CREATOR = new Creator<Benchmarking>() {
        @Override
        public Benchmarking createFromParcel(Parcel in) { return new Benchmarking(in); }
        @Override
        public Benchmarking[] newArray(int size) { return new Benchmarking[size]; }
    };
}

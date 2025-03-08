package com.example.ver2.dataClass.goalManagement;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.*;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.ver2.dataClass.Task;
import com.example.ver2.Converters;

@Entity(tableName = "goals")
@TypeConverters(Converters.class)
public class Goal implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int ID;     //これは自動生成されるからコンストラクタに入れる必要はない
    private String name;
    private String description;
    @TypeConverters(Converters.class)
    private Date createDate;
    @TypeConverters(Converters.class)
    private Date startDate;
    @TypeConverters(Converters.class)
    private Date finishDate;
    private boolean state;
    @TypeConverters(Converters.class)
    private List<Task> tasks;
    private GoalType type;

    // コンストラクタ
    public Goal(String name, String description, Date createDate, Date startDate, Date finishDate, boolean state, List<Task> tasks, GoalType type) {
        this.name = name;
        this.description = description;
        this.createDate = createDate;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.state = state;
        this.tasks = tasks;
        this.type = type;
    }

    @Ignore
    public Goal(Goal goal) {
        this.name = goal.getName();
        this.description = goal.getDescription();
        this.createDate = goal.getCreateDate();
        this.startDate = goal.getStartDate();
        this.finishDate = goal.getFinishDate();
        this.state = goal.isState();
        this.tasks = goal.getTasks();
        this.type = goal.getType();
    }

    public void setGoal(Goal goal) {
        this.name = goal.getName();
        this.description = goal.getDescription();
        this.createDate = goal.getCreateDate();
        this.startDate = goal.getStartDate();
        this.finishDate = goal.getFinishDate();
        this.state = goal.isState();
        this.tasks = goal.getTasks();
        this.type = goal.getType();
    }

    // ゲッター
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

    public boolean isState() {
        return state;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    // セッター
    //IDのセッターは必要ない可能性あり
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

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public GoalType getType() {
        if (type != null)
            return type;
        else
            return null;
    }

    public void setType(GoalType type) {
        this.type = type;
    }

    //state変数は無くてもいいから　今のところActivity遷移で使う
    public boolean isGoalExist() {
        //存在している場合true
        return name != null && description != null && createDate != null &&
                startDate != null && finishDate != null && tasks != null;
    }

    // タスク管理メソッド
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

    public void updateTask(Task task) {
        if (this.tasks != null) {
            for (int i = 0; i < this.tasks.size(); i++) {
                if (this.tasks.get(i).getID() == task.getID()) {
                    this.tasks.set(i, task);
                    break;
                }
            }
        }
    }

    // Parcelable の実装
    @Override
    public int describeContents() {
        return 0;
    }

    ///これ何に使われているか確認する必要あり
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeSerializable(createDate);
        dest.writeSerializable(startDate);
        dest.writeSerializable(finishDate);
        dest.writeByte((byte) (state ? 1 : 0));
        dest.writeList(tasks); // Task クラスも Parcelable であること
        //Intentで情報が送られない問題(ConfirmGoalActivityとConfirmBenchmarkingActivityの間の問題：EnumをIntで送る)
        dest.writeInt(type == null ? -1 : type.ordinal()); // enum の序数を書き込む (null の場合は -1)
    }

    protected Goal(Parcel in) {
        ID = in.readInt();
        name = in.readString();
        description = in.readString();
        createDate = (Date) in.readSerializable();
        startDate = (Date) in.readSerializable();
        finishDate = (Date) in.readSerializable();
        state = in.readByte() != 0;
        tasks = new ArrayList<>();
        in.readList(tasks, Task.class.getClassLoader()); // Task クラスも Parcelable であること
        //Intentで情報が送られない問題(ConfirmGoalActivityとConfirmBenchmarkingActivityの間の問題：EnumをIntでもらう)
        int typeOrdinal = in.readInt(); // Parcel から int 値を読み取り、typeOrdinal を宣言と同時に初期化
        type = typeOrdinal == -1 ? null : GoalType.values()[typeOrdinal]; // 序数から enum を復元 (null の場合は null)
    }

    public static final Creator<Goal> CREATOR = new Creator<Goal>() {
        @Override
        public Goal createFromParcel(Parcel in) {
            return new Goal(in);
        }

        @Override
        public Goal[] newArray(int size) {
            return new Goal[size];
        }
    };

}


package com.example.ver2.dataClass.goalManagement;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface MemoGoalDao {
    // 新しいMemo_Goalオブジェクトをデータベースに挿入
    @Insert
    void insert(Memo_Goal memoGoal);

    // 既存のMemo_Goalオブジェクトを更新
    @Update
    void update(Memo_Goal memoGoal);

    // 指定されたMemo_Goalオブジェクトをデータベースから削除
    @Delete
    void delete(Memo_Goal memoGoal);

    // データベース内のすべてのMemo_Goalオブジェクトを取得
    @Query("SELECT * FROM memo_goals")
    List<Memo_Goal> getAllMemoGoals();

    // 指定されたIDに対応するMemo_Goalオブジェクトを取得
    @Query("SELECT * FROM memo_goals WHERE ID = :id")
    LiveData<Memo_Goal> getMemoGoalById(int id);

    // 指定された状態に対応するMemo_Goalオブジェクトのリストを取得
    @Query("SELECT * FROM memo_goals WHERE state = :state")
    List<Memo_Goal> getMemoGoalsByState(boolean state);
}
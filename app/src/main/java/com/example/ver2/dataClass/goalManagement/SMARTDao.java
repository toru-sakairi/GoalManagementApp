package com.example.ver2.dataClass.goalManagement;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface SMARTDao {
    // 新しいSMARTオブジェクトをデータベースに挿入
    @Insert
    void insert(SMART smart);

    // 既存のSMARTオブジェクトを更新
    @Update
    void update(SMART smart);

    // 指定されたSMARTオブジェクトをデータベースから削除
    @Delete
    void delete(SMART smart);

    // データベース内のすべてのSMARTオブジェクトを取得
    @Query("SELECT * FROM SMART")
    List<SMART> getAllSMART();

    // 指定されたIDに対応するSMARTオブジェクトを取得
    @Query("SELECT * FROM SMART WHERE ID = :id")
    LiveData<SMART> getSMARTById(int id);

    // 指定された状態に対応するSMARTオブジェクトのリストを取得
    @Query("SELECT * FROM SMART WHERE state = :state")
    List<SMART> getSMARTByState(boolean state);
}
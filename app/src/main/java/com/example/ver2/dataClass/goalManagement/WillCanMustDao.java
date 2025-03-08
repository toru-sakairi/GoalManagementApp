package com.example.ver2.dataClass.goalManagement;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface WillCanMustDao {
    // 新しいWillCanMustオブジェクトをデータベースに挿入
    @Insert
    void insert(WillCanMust willCanMust);

    // 既存のWillCanMustオブジェクトを更新
    @Update
    void update(WillCanMust willCanMust);

    // 指定されたWillCanMustオブジェクトをデータベースから削除
    @Delete
    void delete(WillCanMust willCanMust);

    // データベース内のすべてのWillCanMustオブジェクトを取得
    @Query("SELECT * FROM will_can_must")
    List<WillCanMust> getAllWillCanMust();

    // 指定されたIDに対応するWillCanMustオブジェクトを取得
    @Query("SELECT * FROM will_can_must WHERE ID = :id")
    LiveData<WillCanMust> getWillCanMustById(int id);
}
package com.example.ver2.dataClass.goalManagement;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface BenchmarkingDao {
    // 新しいBenchmarkingオブジェクトをデータベースに挿入
    @Insert
    void insert(Benchmarking benchmarking);

    // 既存のBenchmarkingオブジェクトを更新
    @Update
    void update(Benchmarking benchmarking);

    // 指定されたBenchmarkingオブジェクトをデータベースから削除
    @Delete
    void delete(Benchmarking benchmarking);

    // データベース内のすべてのBenchmarkingオブジェクトを取得
    @Query("SELECT * FROM Benchmarking")
    List<Benchmarking> getAllBenchmarking();

    // 指定されたIDに対応するBenchmarkingオブジェクトを取得
    @Query("SELECT * FROM Benchmarking WHERE ID = :id")
    LiveData<Benchmarking> getBenchmarkingById(int id);

    // 指定された状態に対応するBenchmarkingオブジェクトのリストを取得
    @Query("SELECT * FROM Benchmarking WHERE state = :state")
    List<Benchmarking> getBenchmarkingByState(boolean state);
}
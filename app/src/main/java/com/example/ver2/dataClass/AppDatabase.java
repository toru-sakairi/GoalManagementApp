/*
    RoomDatabaseを継承した抽象クラス。データベースの定義とアクセスを提供

 */

package com.example.ver2.dataClass;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.ver2.Converters;
import com.example.ver2.dataClass.goalManagement.Benchmarking;
import com.example.ver2.dataClass.goalManagement.BenchmarkingDao;
import com.example.ver2.dataClass.goalManagement.Goal;
import com.example.ver2.dataClass.goalManagement.GoalDao;
import com.example.ver2.dataClass.goalManagement.MemoGoalDao;
import com.example.ver2.dataClass.goalManagement.Memo_Goal;
import com.example.ver2.dataClass.goalManagement.SMART;
import com.example.ver2.dataClass.goalManagement.SMARTDao;
import com.example.ver2.dataClass.goalManagement.WillCanMust;
import com.example.ver2.dataClass.goalManagement.WillCanMustDao;

@Database(entities = {Goal.class,WillCanMust.class, SMART.class, Memo_Goal.class, Benchmarking.class}, version = 5, exportSchema = false)
@TypeConverters({Converters.class}) // TypeConverter を指定
public abstract class AppDatabase extends RoomDatabase {
    public abstract GoalDao goalDao();
    public abstract WillCanMustDao wcmDao();
    public abstract SMARTDao smartDao();
    public abstract BenchmarkingDao benchmarkingDao();
    public abstract MemoGoalDao memoGoalDao();

    private static volatile AppDatabase INSTANCE;

    //シングルトンパターンでアプリ全体で共有する
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "app_database")
                            .fallbackToDestructiveMigration() // バージョン変更時にリセット（開発用）
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

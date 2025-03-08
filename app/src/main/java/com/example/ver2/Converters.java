package com.example.ver2;

import androidx.room.TypeConverter;

import com.example.ver2.dataClass.Task;
import com.example.ver2.dataClass.goalManagement.GoalType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Date;
import java.util.List;

public class Converters {

    private static final Gson gson = new Gson();
    @TypeConverter
    public static Date fromTimestamp(Long value){
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date){
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String fromTaskList(List<Task> tasks){
        return gson.toJson(tasks);
    }

    @TypeConverter
    public static List<Task> toTaskList(String value){
        return gson.fromJson(value, new TypeToken<List<Task>>() {}.getType());
    }

    @TypeConverter
    public static String fromGoalType(GoalType goalType) {
        return goalType == null ? null : goalType.name(); // Enumの名前を文字列として保存
    }

    @TypeConverter
    public static GoalType toGoalType(String value) {
        return value == null ? null : GoalType.valueOf(value); // 文字列からEnumに変換
    }

}

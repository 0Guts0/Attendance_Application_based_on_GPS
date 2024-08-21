package com.example.attendanceapplication.db.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.attendanceapplication.db.DbHelper;
import com.example.attendanceapplication.models.Tasks;

import java.util.ArrayList;
import java.util.List;

public class TasksDao {
    DbHelper dbHelper;
    public static String TAB_NAME = "tb_tasks";
    public static String CREATE = "CREATE TABLE " + TAB_NAME + " ("
            + "tasks_id" + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "course_id" + " INTEGER,"
            + "teacher_id" + " INTEGER,"
            + "location" + " TEXT NOT NULL,"
            + "limit_time" + " BIGINT,"
            + "end_time" + " BIGINT"
            + ");";

    public TasksDao(Context context) {
        dbHelper = new DbHelper(context);

    }

    public boolean create(Tasks tasks) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("course_id", tasks.getCourseId());
        values.put("teacher_id", tasks.getTeacherId());
        values.put("location", tasks.getLocation());
        values.put("limit_time", tasks.getLimitTime());
        values.put("end_time", tasks.getEndTime());
        long insert = db.insert(TAB_NAME, null, values);
        return insert > 0;
    }

    public boolean existsByCourseId(int courseId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {"tasks_id"};
        String selection = "course_id" + "=?";
        String[] selectionArgs = {String.valueOf(courseId)};
        Cursor cursor = db.query(TAB_NAME, columns, selection, selectionArgs, null, null, null);
        boolean exists = false;
        if (cursor != null) {
            exists = cursor.moveToFirst();
            cursor.close();
        }
        return exists;
    }
    public boolean existsByTaskId(int taskId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {"tasks_id"};
        String selection = "tasks_id" + "=?";
        String[] selectionArgs = {String.valueOf(taskId)};
        Cursor cursor = db.query(TAB_NAME, columns, selection, selectionArgs, null, null, null);
        boolean exists = false;
        if (cursor != null) {
            exists = cursor.moveToFirst();
            cursor.close();
        }
        return exists;
    }
    @SuppressLint("Range")
    public List<Tasks> queryListByTeacherId(Integer teacherId) {
        List<Tasks> tasksList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {
                "tasks_id",
                "course_id",
                "teacher_id",
                "location",
                "limit_time",
                "end_time"
        };
        String selection = "teacher_id" + "=?";
        String[] selectionArgs = {String.valueOf(teacherId)};
        Cursor cursor = db.query(TAB_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Tasks tasks = new Tasks();
                tasks.setCourseId(cursor.getInt(cursor.getColumnIndex("course_id")));
                tasks.setTeacherId(cursor.getInt(cursor.getColumnIndex("teacher_id")));
                tasks.setLocation(cursor.getString(cursor.getColumnIndex("location")));
                tasks.setLimitTime(cursor.getLong(cursor.getColumnIndex("limit_time")));
                tasks.setEndTime(cursor.getLong(cursor.getColumnIndex("end_time")));
                tasksList.add(tasks);
            }
            cursor.close();
            return tasksList;
        } else {
            cursor.close();
            return null;
        }
    }

    @SuppressLint("Range")
    public Tasks queryByCourseId(Integer courseId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {
                "tasks_id",
                "course_id",
                "teacher_id",
                "location",
                "limit_time",
                "end_time"
        };

        String selection = "course_id" + "=?";
        String[] selectionArgs = {String.valueOf(courseId)};
        Cursor cursor = db.query(TAB_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Tasks tasks = new Tasks();
            tasks.setTaskId(cursor.getInt(cursor.getColumnIndex("tasks_id")));
            tasks.setCourseId(cursor.getInt(cursor.getColumnIndex("course_id")));
            tasks.setTeacherId(cursor.getInt(cursor.getColumnIndex("teacher_id")));
            tasks.setLocation(cursor.getString(cursor.getColumnIndex("location")));
            tasks.setLimitTime(cursor.getLong(cursor.getColumnIndex("limit_time")));
            tasks.setEndTime(cursor.getLong(cursor.getColumnIndex("end_time")));
            cursor.close();
            return tasks;
        } else {
            cursor.close();
            return null;
        }
    }

    @SuppressLint("Range")
    public Tasks queryByTaskId(Integer taskId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {
                "tasks_id",
                "course_id",
                "teacher_id",
                "location",
                "limit_time",
                "end_time"
        };

        String selection = "tasks_id" + "=?";
        String[] selectionArgs = {String.valueOf(taskId)};
        Cursor cursor = db.query(TAB_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Tasks tasks = new Tasks();
            tasks.setTaskId(cursor.getInt(cursor.getColumnIndex("tasks_id")));
            tasks.setCourseId(cursor.getInt(cursor.getColumnIndex("course_id")));
            tasks.setTeacherId(cursor.getInt(cursor.getColumnIndex("teacher_id")));
            tasks.setLocation(cursor.getString(cursor.getColumnIndex("location")));
            tasks.setLimitTime(cursor.getLong(cursor.getColumnIndex("limit_time")));
            tasks.setEndTime(cursor.getLong(cursor.getColumnIndex("end_time")));
            cursor.close();
            return tasks;
        } else {
            cursor.close();
            return null;
        }
    }

    public boolean deleteById(Integer taskId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = "tasks_id = ?";
        String[] selectionArgs = {String.valueOf(taskId)};
        int rowsDeleted = db.delete(TAB_NAME, selection, selectionArgs);
        return rowsDeleted > 0;
    }
}

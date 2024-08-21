package com.example.attendanceapplication.db.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.attendanceapplication.db.DbHelper;
import com.example.attendanceapplication.models.Attendance;

import java.util.ArrayList;
import java.util.List;

public class AttendanceDao {
    DbHelper dbHelper;
    public static String TAB_NAME = "tb_attendance";
    public static String CREATE = "CREATE TABLE " + TAB_NAME + " ("
            + "attendance_id" + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "student_id" + " INTEGER,"
            + "task_id" + " INTEGER,"
            + "sign_time" + " BIGINT"
            + ");";

    public AttendanceDao(Context context) {
        dbHelper = new DbHelper(context);

    }

    public boolean create(Attendance attendance) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("student_id", attendance.getStudentId());
        values.put("task_id", attendance.getTaskId());
        values.put("sign_time", attendance.getSignTime());
        long insert = db.insert(TAB_NAME, null, values);
        return insert > 0;
    }

    public boolean existsByTaskIdAndStudentId(Integer taskId, Integer studentId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {"attendance_id"};
        String selection = "task_id" + "=?" + " AND " + "student_id" + "=?";
        String[] selectionArgs = {String.valueOf(taskId), String.valueOf(studentId)};
        Cursor cursor = db.query(TAB_NAME, columns, selection, selectionArgs, null, null, null);
        boolean exists = false;
        if (cursor != null) {
            exists = cursor.moveToFirst();
            cursor.close();
        }
        return exists;
    }

    @SuppressLint("Range")
    public List<Attendance> queryListByStudentId(Integer studentId) {
        List<Attendance> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {
                "attendance_id",
                "student_id",
                "task_id",
                "sign_time"
        };
        String selection = "student_id" + "=?";
        String[] selectionArgs = {String.valueOf(studentId)};
        Cursor cursor = db.query(TAB_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Attendance attendance = new Attendance();
                attendance.setAttendanceId(cursor.getInt(cursor.getColumnIndex("attendance_id")));
                attendance.setStudentId(cursor.getInt(cursor.getColumnIndex("student_id")));
                attendance.setTaskId(cursor.getInt(cursor.getColumnIndex("task_id")));
                attendance.setSignTime(cursor.getLong(cursor.getColumnIndex("sign_time")));
                list.add(attendance);
            }
            cursor.close();
            return list;
        } else {
            cursor.close();
            return null;
        }
    }

    @SuppressLint("Range")
    public List<Attendance> queryListByTaskId(Integer taskId) {
        List<Attendance> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {
                "attendance_id",
                "student_id",
                "task_id",
                "sign_time"
        };
        String selection = "task_id" + "=?";
        String[] selectionArgs = {String.valueOf(taskId)};
        Cursor cursor = db.query(TAB_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Attendance attendance = new Attendance();
                attendance.setAttendanceId(cursor.getInt(cursor.getColumnIndex("attendance_id")));
                attendance.setStudentId(cursor.getInt(cursor.getColumnIndex("student_id")));
                attendance.setTaskId(cursor.getInt(cursor.getColumnIndex("task_id")));
                attendance.setSignTime(cursor.getLong(cursor.getColumnIndex("sign_time")));
                list.add(attendance);
            }
            cursor.close();
            return list;
        } else {
            cursor.close();
            return null;
        }
    }

//    @SuppressLint("Range")
//    public Tasks queryByCourseId(Integer courseId) {
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String[] columns = {
//                "tasks_id",
//                "course_id",
//                "teacher_id",
//                "location",
//                "limit_time",
//                "end_time"
//        };
//
//        String selection = "course_id" + "=?";
//        String[] selectionArgs = {String.valueOf(courseId)};
//        Cursor cursor = db.query(TAB_NAME, columns, selection, selectionArgs, null, null, null);
//        if (cursor != null && cursor.moveToFirst()) {
//            Tasks tasks = new Tasks();
//            tasks.setTaskId(cursor.getInt(cursor.getColumnIndex("tasks_id")));
//            tasks.setCourseId(cursor.getInt(cursor.getColumnIndex("course_id")));
//            tasks.setTeacherId(cursor.getInt(cursor.getColumnIndex("teacher_id")));
//            tasks.setLocation(cursor.getString(cursor.getColumnIndex("location")));
//            tasks.setLimitTime(cursor.getLong(cursor.getColumnIndex("limit_time")));
//            tasks.setEndTime(cursor.getLong(cursor.getColumnIndex("end_time")));
//            cursor.close();
//            return tasks;
//        } else {
//            cursor.close();
//            return null;
//        }
//    }

    public boolean deleteById(Integer attendanceId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = "attendance_id = ?";
        String[] selectionArgs = {String.valueOf(attendanceId)};
        int rowsDeleted = db.delete(TAB_NAME, selection, selectionArgs);
        return rowsDeleted > 0;
    }
}

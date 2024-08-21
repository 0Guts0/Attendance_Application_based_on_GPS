package com.example.attendanceapplication.db.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.attendanceapplication.db.DbHelper;
import com.example.attendanceapplication.models.Timetable;

import java.util.ArrayList;
import java.util.List;

public class TimetableDao {
    DbHelper dbHelper;
    public static String TAB_NAME = "tb_timetable";
    public static String CREATE = "CREATE TABLE " + TAB_NAME + " ("
            + "timetable_id" + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "student_id" + " INTEGER,"
            + "course_id" + " INTEGER,"
            + "semester" + " TEXT NOT NULL"
            + ");";

    public TimetableDao(Context context) {
        dbHelper = new DbHelper(context);

    }

    public boolean create(Timetable timetable) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("student_id", timetable.getStudentId());
        values.put("course_id", timetable.getCourseId());
        values.put("semester", timetable.getSemester());
        long insert = db.insert(TAB_NAME, null, values);
        return insert > 0;
    }

    public boolean existsByCourseId(int courseId, int studentId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {"timetable_id"};
        String selection = "course_id" + "=?" + " AND " + "student_id" + "=?";
        String[] selectionArgs = {String.valueOf(courseId), String.valueOf(studentId)};
        Cursor cursor = db.query(TAB_NAME, columns, selection, selectionArgs, null, null, null);
        boolean exists = false;
        if (cursor != null) {
            exists = cursor.moveToFirst();
            cursor.close();
        }
        return exists;
    }

    @SuppressLint("Range")
    public List<Timetable> queryListByStudentId(Integer studentId) {
        List<Timetable> tasksList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {
                "timetable_id",
                "course_id",
                "student_id",
                "semester"
        };
        String selection = "student_id" + "=?";
        String[] selectionArgs = {String.valueOf(studentId)};
        Cursor cursor = db.query(TAB_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Timetable timetable = new Timetable();
                timetable.setTimetableId(cursor.getInt(cursor.getColumnIndex("timetable_id")));
                timetable.setCourseId(cursor.getInt(cursor.getColumnIndex("course_id")));
                timetable.setStudentId(cursor.getInt(cursor.getColumnIndex("student_id")));
                timetable.setSemester(cursor.getString(cursor.getColumnIndex("semester")));
                tasksList.add(timetable);
            }
            cursor.close();
            return tasksList;
        } else {
            cursor.close();
            return null;
        }
    }

    @SuppressLint("Range")
    public List<Timetable> queryByCourseId(Integer courseId) {
        List<Timetable> tasksList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {
                "timetable_id",
                "course_id",
                "student_id",
                "semester"
        };
        String selection = "course_id" + "=?";
        String[] selectionArgs = {String.valueOf(courseId)};
        Cursor cursor = db.query(TAB_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Timetable timetable = new Timetable();
                timetable.setTimetableId(cursor.getInt(cursor.getColumnIndex("timetable_id")));
                timetable.setCourseId(cursor.getInt(cursor.getColumnIndex("course_id")));
                timetable.setStudentId(cursor.getInt(cursor.getColumnIndex("student_id")));
                timetable.setSemester(cursor.getString(cursor.getColumnIndex("semester")));
                tasksList.add(timetable);
            }
            cursor.close();
            return tasksList;
        } else {
            cursor.close();
            return null;
        }
    }

    public boolean deleteById(Integer timetableId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = "timetable_id = ?";
        String[] selectionArgs = {String.valueOf(timetableId)};
        int rowsDeleted = db.delete(TAB_NAME, selection, selectionArgs);
        return rowsDeleted > 0;
    }
}

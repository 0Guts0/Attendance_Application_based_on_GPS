package com.example.attendanceapplication.db.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.attendanceapplication.db.DbHelper;
import com.example.attendanceapplication.models.Courses;

import java.util.ArrayList;
import java.util.List;

public class CoursesDao {
    DbHelper dbHelper;
    public static String TAB_NAME = "tb_courses";
    public static String CREATE = "CREATE TABLE " + TAB_NAME + " ("
            + "course_id" + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "course_name" + " TEXT NOT NULL,"
            + "teacher_id" + " INTEGER,"
            + "duration" + " INTEGER"
            + ");";

    public CoursesDao(Context context) {
        dbHelper = new DbHelper(context);

    }

    public boolean create(Courses courses) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("course_name", courses.getCourseName());
        values.put("duration", courses.getDuration());
        values.put("teacher_id", courses.getTeacherId());
        long insert = db.insert(TAB_NAME, null, values);
        return insert > 0;
    }

    @SuppressLint("Range")
    public List<Courses> queryListByTeacherId(Integer teacherId) {
        List<Courses> courseList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {
                "course_id",
                "course_name",
                "duration",
                "teacher_id"
        };
        String selection = "teacher_id" + "=?";
        String[] selectionArgs = {String.valueOf(teacherId)};
        Cursor cursor = db.query(TAB_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Courses courses = new Courses();
                courses.setCourseId(cursor.getInt(cursor.getColumnIndex("course_id")));
                courses.setTeacherId(cursor.getInt(cursor.getColumnIndex("teacher_id")));
                courses.setCourseName(cursor.getString(cursor.getColumnIndex("course_name")));
                courses.setDuration(cursor.getInt(cursor.getColumnIndex("duration")));

                courseList.add(courses);
            }
            cursor.close();
            return courseList;
        } else {
            cursor.close();
            return null;
        }
    }

    @SuppressLint("Range")
    public List<Courses> queryListAllCourses() {
        List<Courses> courseList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {
                "course_id",
                "course_name",
                "duration",
                "teacher_id"
        };
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = "course_id ASC";
        Cursor cursor = db.query(TAB_NAME, columns, selection, selectionArgs, null, null, sortOrder);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Courses courses = new Courses();
                courses.setCourseId(cursor.getInt(cursor.getColumnIndex("course_id")));
                courses.setCourseName(cursor.getString(cursor.getColumnIndex("course_name")));
                courses.setDuration(cursor.getInt(cursor.getColumnIndex("duration")));
                courses.setTeacherId(cursor.getInt(cursor.getColumnIndex("teacher_id")));

                courseList.add(courses);
            }
            cursor.close();
            return courseList;
        } else {
            cursor.close();
            return null;
        }
    }

    public boolean deleteById(Integer courseId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = "course_id = ?";
        String[] selectionArgs = {String.valueOf(courseId)};
        int rowsDeleted = db.delete(TAB_NAME, selection, selectionArgs);
        return rowsDeleted > 0;
    }
}

package com.example.attendanceapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.attendanceapplication.db.dao.AttendanceDao;
import com.example.attendanceapplication.db.dao.CoursesDao;
import com.example.attendanceapplication.db.dao.TasksDao;
import com.example.attendanceapplication.db.dao.TimetableDao;
import com.example.attendanceapplication.db.dao.UserDao;


public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "sign.db";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME,
                null,
                DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserDao.CREATE);
        db.execSQL(CoursesDao.CREATE);
        db.execSQL(TasksDao.CREATE);
        db.execSQL(TimetableDao.CREATE);
        db.execSQL(AttendanceDao.CREATE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserDao.TAB_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CoursesDao.TAB_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TasksDao.TAB_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TimetableDao.TAB_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + AttendanceDao.TAB_NAME);
        onCreate(db);
    }
}

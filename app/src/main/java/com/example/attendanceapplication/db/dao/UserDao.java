package com.example.attendanceapplication.db.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.attendanceapplication.db.DbHelper;
import com.example.attendanceapplication.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserDao {
    DbHelper dbHelper;
    public static String TAB_NAME = "tb_user";
    public static String CREATE = "CREATE TABLE " + TAB_NAME + " ("
            + "user_id" + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "user_name" + " TEXT NOT NULL,"
            + "email" + " TEXT NOT NULL UNIQUE,"
            + "role" + " TEXT NOT NULL,"
            + "major" + " TEXT NOT NULL,"
            + "current_location" + " TEXT NOT NULL,"
            + "password" + " TEXT NOT NULL"
            + ");";

    public UserDao(Context context) {
        dbHelper = new DbHelper(context);

    }

    public boolean register(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_name", user.getUserName());
        values.put("email", user.getEmail());
        values.put("role", user.getRole());
        values.put("major", user.getMajor());
        values.put("current_location", user.getCurrentLocation());
        values.put("password", user.getPassword());
        long insert = db.insert(TAB_NAME, null, values);
        return insert > 0;
    }

    @SuppressLint("Range")
    public User login(String email, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {
                "user_id",
                "user_name",
                "email",
                "role",
                "major",
                "current_location"
        };

        String selection = "email" + "=?" + " AND " + "password" + "=?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(TAB_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            User user = new User();
            user.setUserId(cursor.getInt(cursor.getColumnIndex("user_id")));
            user.setUserName(cursor.getString(cursor.getColumnIndex("user_name")));
            user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            user.setRole(cursor.getString(cursor.getColumnIndex("role")));
            user.setMajor(cursor.getString(cursor.getColumnIndex("major")));
            user.setCurrentLocation(cursor.getString(cursor.getColumnIndex("current_location")));
            cursor.close();
            return user;
        } else {
            cursor.close();
            return null;
        }
    }


    @SuppressLint("Range")
    public List<User> getUsersByIds(List<Integer> userIds) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {
                "user_id",
                "user_name",
                "email",
                "role",
                "major",
                "current_location"
        };

        StringBuilder selectionBuilder = new StringBuilder("user_id IN (");
        String[] selectionArgs = new String[userIds.size()];
        int i = 0;
        for (Integer userId : userIds) {
            selectionBuilder.append("?");
            selectionArgs[i++] = userId.toString();
            if (i < userIds.size()) {
                selectionBuilder.append(", ");
            }
        }
        selectionBuilder.append(")");

        String selection = selectionBuilder.toString();
        Cursor cursor = db.query(TAB_NAME, columns, selection, selectionArgs, null, null, null);

        List<User> users = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    User user = new User();
                    user.setUserId(cursor.getInt(cursor.getColumnIndex("user_id")));
                    user.setUserName(cursor.getString(cursor.getColumnIndex("user_name")));
                    user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                    user.setRole(cursor.getString(cursor.getColumnIndex("role")));
                    user.setMajor(cursor.getString(cursor.getColumnIndex("major")));
                    user.setCurrentLocation(cursor.getString(cursor.getColumnIndex("current_location")));
                    users.add(user);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return users;
    }
}

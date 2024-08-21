package com.example.attendanceapplication;

import android.app.Application;

import com.example.attendanceapplication.models.User;

public class App extends Application {
    public static double latitude;
    public static double longitude;
    public static String address;
    public static User user;


    @Override
    public void onCreate() {
        super.onCreate();
    }
}

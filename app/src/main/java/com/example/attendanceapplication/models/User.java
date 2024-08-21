package com.example.attendanceapplication.models;

public class User {

    private Integer userId;

    private String userName;
    private String currentLocation;
    private String role;
    private String email;
    private String major;
    private String password;
    private Long signTime=0L;

    private String semester;

    public User() {
    }

    public User(String userName, String currentLocation, String role, String email, String major, String password) {
        this.userName = userName;
        this.currentLocation = currentLocation;
        this.role = role;
        this.email = email;
        this.major = major;
        this.password = password;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Long getSignTime() {
        return signTime;
    }

    public void setSignTime(Long signTime) {
        this.signTime = signTime;
    }

    // Getters and Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", currentLocation='" + currentLocation + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", major='" + major + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

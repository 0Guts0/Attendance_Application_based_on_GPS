package com.example.attendanceapplication.models;

public class Tasks {
    private Integer taskId;

    private Integer courseId;
    private Integer teacherId;
    private String location;
    private Long limitTime;
    private Long endTime;

    public Tasks() {
    }

    public Tasks(Integer taskId, Integer courseId, Integer teacherId, String location, Long limitTime, Long endTime) {
        this.taskId = taskId;
        this.courseId = courseId;
        this.teacherId = teacherId;
        this.location = location;
        this.limitTime = limitTime;
        this.endTime = endTime;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(Long limitTime) {
        this.limitTime = limitTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Tasks{" +
                "taskId=" + taskId +
                ", courseId=" + courseId +
                ", teacherId=" + teacherId +
                ", location='" + location + '\'' +
                ", limitTime=" + limitTime +
                ", endTime=" + endTime +
                '}';
    }
}

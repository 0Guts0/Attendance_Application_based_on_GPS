package com.example.attendanceapplication.models;

import java.io.Serializable;

public class Courses implements Serializable {
    private Integer courseId;

    private String courseName;
    private Integer teacherId;

    private Integer duration;

    public Courses() {
    }

    public Courses(Integer courseId, String courseName, Integer teacherId, Integer duration) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.teacherId = teacherId;
        this.duration = duration;
    }

    public Courses(String courseName, Integer teacherId, Integer duration) {
        this.courseName = courseName;
        this.teacherId = teacherId;
        this.duration = duration;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public String toString() {
        return "Courses{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", teacherId='" + teacherId + '\'' +
                '}';
    }
}

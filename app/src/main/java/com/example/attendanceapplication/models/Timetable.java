package com.example.attendanceapplication.models;

public class Timetable {
    private Integer timetableId;
    private Integer studentId;
    private Integer courseId;
    private String semester;

    public Timetable(Integer timetableId, Integer studentId, Integer courseId, String semester) {
        this.timetableId = timetableId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.semester = semester;
    }

    public Timetable() {
    }

    public Integer getTimetableId() {
        return timetableId;
    }

    public void setTimetableId(Integer timetableId) {
        this.timetableId = timetableId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}

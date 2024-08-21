package com.example.attendanceapplication.models;

public class Attendance {
    private Integer attendanceId;
    private Integer taskId;
    private Integer studentId;
    private Long signTime;

    public Attendance(Integer attendanceId, Integer taskId, Integer studentId, Long signTime) {
        this.attendanceId = attendanceId;
        this.taskId = taskId;
        this.studentId = studentId;
        this.signTime = signTime;
    }

    public Attendance() {
    }

    public Integer getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Integer attendanceId) {
        this.attendanceId = attendanceId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Long getSignTime() {
        return signTime;
    }

    public void setSignTime(Long signTime) {
        this.signTime = signTime;
    }
}

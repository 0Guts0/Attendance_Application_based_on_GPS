package com.example.attendanceapplication.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.util.Date;

@Entity
public class AttendanceTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer taskId;

    @ManyToOne
    @JoinColumn(name = "class_id", referencedColumnName = "classId")
    private Class clazz;

    private Date time;
    private String location;
    private Integer totalStudentsNumber;
    private Integer attendedStudentNumber;

    // Getters and Setters
    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getTotalStudentsNumber() {
        return totalStudentsNumber;
    }

    public void setTotalStudentsNumber(Integer totalStudentsNumber) {
        this.totalStudentsNumber = totalStudentsNumber;
    }

    public Integer getAttendedStudentNumber() {
        return attendedStudentNumber;
    }

    public void setAttendedStudentNumber(Integer attendedStudentNumber) {
        this.attendedStudentNumber = attendedStudentNumber;
    }
}

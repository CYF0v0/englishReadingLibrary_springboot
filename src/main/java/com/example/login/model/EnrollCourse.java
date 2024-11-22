package com.example.login.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "enrollcourse")
public class EnrollCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "coursename", nullable = false)
    private String courseName;

    @Column(name = "studentid")
    private int studentId;

    @Column(name = "date", columnDefinition = "DATE DEFAULT CURRENT_DATE")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "status", nullable = false, columnDefinition = "TINYINT(1) default 0")
    private boolean status;

    @Column(name = "score", nullable = false, columnDefinition = "TINYINT(1) default 60")
    private int score;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "EnrollCourse{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", studentId=" + studentId +
                ", date=" + date +
                ", status=" + status +
                ", score=" + score +
                '}';
    }
}
package org.example.onlineexam.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "exams")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String uniqueLink;
    private Long teacherId;

    private String course;
    private String branch;
    private String year;
    private String semester;
    private String subject;

    // Naye fields Time aur Duration ke liye
    private String examDate;       // Format: "YYYY-MM-DD"
    private String startTime;      // Format: "HH:mm" (24-hour)
    private int durationMinutes;   // Duration in minutes (e.g., 30)

    public Exam() {}

    public Exam(String title, String uniqueLink, Long teacherId, String course, String branch, String year, String semester, String subject, String examDate, String startTime, int durationMinutes) {
        this.title = title;
        this.uniqueLink = uniqueLink;
        this.teacherId = teacherId;
        this.course = course;
        this.branch = branch;
        this.year = year;
        this.semester = semester;
        this.subject = subject;
        this.examDate = examDate;
        this.startTime = startTime;
        this.durationMinutes = durationMinutes;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getUniqueLink() { return uniqueLink; }
    public void setUniqueLink(String uniqueLink) { this.uniqueLink = uniqueLink; }

    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }

    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getExamDate() { return examDate; }
    public void setExamDate(String examDate) { this.examDate = examDate; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }
}
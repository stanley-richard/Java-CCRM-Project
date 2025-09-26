package edu.ccrm.domain;

import java.time.LocalDate;

/**
 * Enrollment class representing a student's enrollment in a course
 * Demonstrates composition and association relationships
 */
public class Enrollment {
    private Student student;
    private Course course;
    private Semester semester;
    private LocalDate enrollmentDate;
    private Grade grade;
    private double marks;
    private boolean active;
    
    public Enrollment(Student student, Course course, Semester semester, LocalDate enrollmentDate) {
        this.student = student;
        this.course = course;
        this.semester = semester;
        this.enrollmentDate = enrollmentDate;
        this.active = true;
    }
    
    // Getters and setters
    public Student getStudent() {
        return student;
    }
    
    public Course getCourse() {
        return course;
    }
    
    public Semester getSemester() {
        return semester;
    }
    
    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }
    
    public Grade getGrade() {
        return grade;
    }
    
    public void setGrade(Grade grade) {
        this.grade = grade;
    }
    
    public double getMarks() {
        return marks;
    }
    
    public void setMarks(double marks) {
        this.marks = marks;
        this.grade = Grade.fromMarks(marks);
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public boolean isCompleted() {
        return grade != null;
    }
    
    @Override
    public String toString() {
        String gradeStr = grade != null ? grade.toString() : "In Progress";
        return String.format("%s enrolled in %s (%s) - %s", 
            student.getName().getFullName(), 
            course.getCode(), 
            semester, 
            gradeStr);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Enrollment that = (Enrollment) obj;
        return student.equals(that.student) && 
               course.equals(that.course) && 
               semester == that.semester;
    }
    
    @Override
    public int hashCode() {
        return student.hashCode() + course.hashCode() + semester.hashCode();
    }
}
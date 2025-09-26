package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.*;

/**
 * Student class extending Person
 * Demonstrates inheritance, encapsulation, and nested classes
 */
public class Student extends Person {
    private String regNo;
    private Set<Course> enrolledCourses;
    private List<Enrollment> enrollmentHistory;
    private StudentStatus status;
    
    // Static nested class for student statistics
    public static class StudentStats {
        private final int totalEnrollments;
        private final double averageGPA;
        private final int completedCredits;
        
        public StudentStats(int totalEnrollments, double averageGPA, int completedCredits) {
            this.totalEnrollments = totalEnrollments;
            this.averageGPA = averageGPA;
            this.completedCredits = completedCredits;
        }
        
        public int getTotalEnrollments() { return totalEnrollments; }
        public double getAverageGPA() { return averageGPA; }
        public int getCompletedCredits() { return completedCredits; }
    }
    
    // Inner class for handling enrollment operations
    public class EnrollmentManager {
        public boolean canEnroll(Course course, Semester semester) {
            // Business rule: Max 20 credits per semester
            int currentCredits = getCurrentSemesterCredits(semester);
            return currentCredits + course.getCredits() <= 20;
        }
        
        public void enrollInCourse(Course course, Semester semester) {
            if (!canEnroll(course, semester)) {
                throw new IllegalStateException("Cannot enroll: Credit limit exceeded");
            }
            enrolledCourses.add(course);
            enrollmentHistory.add(new Enrollment(Student.this, course, semester, LocalDate.now()));
        }
        
        private int getCurrentSemesterCredits(Semester semester) {
            return enrollmentHistory.stream()
                .filter(e -> e.getSemester() == semester)
                .filter(e -> e.getGrade() == null) // Only active enrollments
                .mapToInt(e -> e.getCourse().getCredits())
                .sum();
        }
    }
    
    public enum StudentStatus {
        ACTIVE, INACTIVE, GRADUATED, SUSPENDED
    }
    
    // Constructor with super call
    public Student(String id, String regNo, Name name, String email) {
        super(id, name, email);
        this.regNo = regNo;
        this.enrolledCourses = new HashSet<>();
        this.enrollmentHistory = new ArrayList<>();
        this.status = StudentStatus.ACTIVE;
    }
    
    // Implementing abstract methods from Person
    @Override
    public String getDisplayTitle() {
        return "Student";
    }
    
    @Override
    public String getDetailedInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Student Details:\n");
        info.append("ID: ").append(id).append("\n");
        info.append("Registration No: ").append(regNo).append("\n");
        info.append("Name: ").append(name.getFullName()).append("\n");
        info.append("Email: ").append(email).append("\n");
        info.append("Status: ").append(status).append("\n");
        info.append("Enrolled Courses: ").append(enrolledCourses.size()).append("\n");
        info.append("Date Created: ").append(dateCreated).append("\n");
        return info.toString();
    }
    
    // Getters and setters
    public String getRegNo() {
        return regNo;
    }
    
    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }
    
    public Set<Course> getEnrolledCourses() {
        return new HashSet<>(enrolledCourses); // Defensive copy
    }
    
    public List<Enrollment> getEnrollmentHistory() {
        return new ArrayList<>(enrollmentHistory); // Defensive copy
    }
    
    public StudentStatus getStatus() {
        return status;
    }
    
    public void setStatus(StudentStatus status) {
        this.status = status;
    }
    
    public EnrollmentManager getEnrollmentManager() {
        return new EnrollmentManager();
    }
    
    // Calculate GPA using streams
    public double calculateGPA() {
        return enrollmentHistory.stream()
            .filter(e -> e.getGrade() != null)
            .mapToDouble(e -> e.getGrade().getGradePoints())
            .average()
            .orElse(0.0);
    }
    
    public StudentStats getStatistics() {
        double gpa = calculateGPA();
        int completedCredits = enrollmentHistory.stream()
            .filter(e -> e.getGrade() != null && e.getGrade() != Grade.F)
            .mapToInt(e -> e.getCourse().getCredits())
            .sum();
        
        return new StudentStats(enrollmentHistory.size(), gpa, completedCredits);
    }
    
    @Override
    public String toString() {
        return String.format("Student: %s [%s] - Reg: %s, GPA: %.2f", 
            name.getFullName(), id, regNo, calculateGPA());
    }
}
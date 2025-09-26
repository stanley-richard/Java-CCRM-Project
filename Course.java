package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.*;

/**
 * Course class with Builder pattern implementation
 * Demonstrates the Builder design pattern and method chaining
 */
public class Course {
    private CourseCode code;
    private String title;
    private int credits;
    private Instructor instructor;
    private Semester semester;
    private String department;
    private Set<String> prerequisites;
    private int maxEnrollment;
    private LocalDate dateCreated;
    private boolean active;
    
    // Private constructor for Builder pattern
    private Course(Builder builder) {
        this.code = builder.code;
        this.title = builder.title;
        this.credits = builder.credits;
        this.instructor = builder.instructor;
        this.semester = builder.semester;
        this.department = builder.department;
        this.prerequisites = new HashSet<>(builder.prerequisites);
        this.maxEnrollment = builder.maxEnrollment;
        this.dateCreated = LocalDate.now();
        this.active = true;
    }
    
    // Builder pattern implementation
    public static class Builder {
        private CourseCode code;
        private String title;
        private int credits;
        private Instructor instructor;
        private Semester semester;
        private String department;
        private Set<String> prerequisites = new HashSet<>();
        private int maxEnrollment = 50; // Default value
        
        public Builder(CourseCode code, String title, int credits) {
            this.code = code;
            this.title = title;
            this.credits = credits;
        }
        
        public Builder instructor(Instructor instructor) {
            this.instructor = instructor;
            return this;
        }
        
        public Builder semester(Semester semester) {
            this.semester = semester;
            return this;
        }
        
        public Builder department(String department) {
            this.department = department;
            return this;
        }
        
        public Builder prerequisite(String prerequisite) {
            this.prerequisites.add(prerequisite);
            return this;
        }
        
        public Builder prerequisites(Set<String> prerequisites) {
            this.prerequisites.addAll(prerequisites);
            return this;
        }
        
        public Builder maxEnrollment(int maxEnrollment) {
            this.maxEnrollment = maxEnrollment;
            return this;
        }
        
        public Course build() {
            // Validation
            if (credits < 1 || credits > 6) {
                throw new IllegalArgumentException("Credits must be between 1 and 6");
            }
            return new Course(this);
        }
    }
    
    // Getters
    public CourseCode getCode() {
        return code;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public int getCredits() {
        return credits;
    }
    
    public void setCredits(int credits) {
        this.credits = credits;
    }
    
    public Instructor getInstructor() {
        return instructor;
    }
    
    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }
    
    public Semester getSemester() {
        return semester;
    }
    
    public void setSemester(Semester semester) {
        this.semester = semester;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public Set<String> getPrerequisites() {
        return new HashSet<>(prerequisites);
    }
    
    public int getMaxEnrollment() {
        return maxEnrollment;
    }
    
    public void setMaxEnrollment(int maxEnrollment) {
        this.maxEnrollment = maxEnrollment;
    }
    
    public LocalDate getDateCreated() {
        return dateCreated;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    @Override
    public String toString() {
        String instructorName = instructor != null ? instructor.getName().getFullName() : "TBA";
        return String.format("%s: %s (%d credits) - %s [%s]", 
            code, title, credits, instructorName, semester);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Course course = (Course) obj;
        return code.equals(course.code);
    }
    
    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
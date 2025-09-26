package edu.ccrm.domain;

import java.util.*;

/**
 * Instructor class extending Person
 * Demonstrates inheritance and polymorphism
 */
public class Instructor extends Person {
    private String department;
    private String designation;
    private Set<Course> assignedCourses;
    
    public Instructor(String id, Name name, String email, String department, String designation) {
        super(id, name, email);
        this.department = department;
        this.designation = designation;
        this.assignedCourses = new HashSet<>();
    }
    
    @Override
    public String getDisplayTitle() {
        return designation + " (" + department + ")";
    }
    
    @Override
    public String getDetailedInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Instructor Details:\n");
        info.append("ID: ").append(id).append("\n");
        info.append("Name: ").append(name.getFullName()).append("\n");
        info.append("Email: ").append(email).append("\n");
        info.append("Department: ").append(department).append("\n");
        info.append("Designation: ").append(designation).append("\n");
        info.append("Assigned Courses: ").append(assignedCourses.size()).append("\n");
        info.append("Date Created: ").append(dateCreated).append("\n");
        return info.toString();
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public String getDesignation() {
        return designation;
    }
    
    public void setDesignation(String designation) {
        this.designation = designation;
    }
    
    public Set<Course> getAssignedCourses() {
        return new HashSet<>(assignedCourses);
    }
    
    public void assignCourse(Course course) {
        assignedCourses.add(course);
    }
    
    public void unassignCourse(Course course) {
        assignedCourses.remove(course);
    }
    
    @Override
    public String toString() {
        return String.format("Instructor: %s [%s] - %s, %s (%d courses)", 
            name.getFullName(), id, designation, department, assignedCourses.size());
    }
}
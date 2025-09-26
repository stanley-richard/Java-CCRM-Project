package edu.ccrm.service;

import edu.ccrm.domain.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Implementation of CourseService
 */
public class CourseServiceImpl implements CourseService {
    
    private final Map<CourseCode, Course> courses = new HashMap<>();
    
    @Override
    public Course create(Course course) {
        assert course != null : "Course cannot be null";
        assert course.getCode() != null : "Course code cannot be null";
        
        if (courses.containsKey(course.getCode())) {
            throw new IllegalArgumentException("Course with code " + course.getCode() + " already exists");
        }
        
        courses.put(course.getCode(), course);
        return course;
    }
    
    @Override
    public Course findById(CourseCode courseCode) {
        return courses.get(courseCode);
    }
    
    @Override
    public List<Course> findAll() {
        return new ArrayList<>(courses.values());
    }
    
    @Override
    public Course update(Course course) {
        if (!courses.containsKey(course.getCode())) {
            throw new IllegalArgumentException("Course not found: " + course.getCode());
        }
        
        courses.put(course.getCode(), course);
        return course;
    }
    
    @Override
    public boolean delete(CourseCode courseCode) {
        return courses.remove(courseCode) != null;
    }
    
    @Override
    public List<Course> findByInstructor(Instructor instructor) {
        return courses.values().stream()
            .filter(course -> instructor.equals(course.getInstructor()))
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Course> findByDepartment(String department) {
        return courses.values().stream()
            .filter(course -> department.equalsIgnoreCase(course.getDepartment()))
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Course> findBySemester(Semester semester) {
        return courses.values().stream()
            .filter(course -> semester == course.getSemester())
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Course> search(Predicate<Course> criteria) {
        return courses.values().stream()
            .filter(criteria)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Course> findActiveCourses() {
        return courses.values().stream()
            .filter(Course::isActive)
            .sorted(Comparator.comparing(course -> course.getCode().getCode()))
            .collect(Collectors.toList());
    }
    
    @Override
    public boolean assignInstructor(CourseCode courseCode, Instructor instructor) {
        Course course = courses.get(courseCode);
        if (course != null) {
            course.setInstructor(instructor);
            instructor.assignCourse(course);
            return true;
        }
        return false;
    }
    
    @Override
    public void generateCourseReport(CourseCode courseCode) {
        Course course = findById(courseCode);
        if (course == null) {
            System.out.println("Course not found: " + courseCode);
            return;
        }
        
        System.out.println("=".repeat(50));
        System.out.println("COURSE REPORT");
        System.out.println("=".repeat(50));
        System.out.println("Code: " + course.getCode());
        System.out.println("Title: " + course.getTitle());
        System.out.println("Credits: " + course.getCredits());
        System.out.println("Department: " + course.getDepartment());
        System.out.println("Semester: " + course.getSemester());
        System.out.println("Instructor: " + 
            (course.getInstructor() != null ? course.getInstructor().getName().getFullName() : "TBA"));
        System.out.println("Max Enrollment: " + course.getMaxEnrollment());
        System.out.println("Status: " + (course.isActive() ? "Active" : "Inactive"));
        System.out.println("=".repeat(50));
    }
}
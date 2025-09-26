package edu.ccrm.service;

import edu.ccrm.domain.*;
import java.util.List;
import java.util.function.Predicate;

/**
 * Interface for Course service operations
 */
public interface CourseService extends CRUDService<Course, CourseCode> {
    
    List<Course> findByInstructor(Instructor instructor);
    List<Course> findByDepartment(String department);
    List<Course> findBySemester(Semester semester);
    List<Course> search(Predicate<Course> criteria);
    List<Course> findActiveCourses();
    
    // Course-specific operations
    boolean assignInstructor(CourseCode courseCode, Instructor instructor);
    void generateCourseReport(CourseCode courseCode);
}
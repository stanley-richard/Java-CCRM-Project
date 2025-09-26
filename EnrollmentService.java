package edu.ccrm.service;

import edu.ccrm.domain.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Interface for Enrollment service operations
 */
public interface EnrollmentService {
    
    Enrollment enrollStudent(String studentId, CourseCode courseCode, Semester semester) 
        throws DuplicateEnrollmentException;
    
    boolean unenrollStudent(String studentId, CourseCode courseCode, Semester semester);
    
    List<Enrollment> getStudentEnrollments(String studentId);
    List<Enrollment> getCourseEnrollments(CourseCode courseCode);
    List<Enrollment> getEnrollmentsBySemester(Semester semester);
    
    boolean recordGrade(String studentId, CourseCode courseCode, Semester semester, double marks);
    
    // Business rule validation
    boolean canEnroll(String studentId, CourseCode courseCode, Semester semester);
    int getStudentCreditLoad(String studentId, Semester semester);
}
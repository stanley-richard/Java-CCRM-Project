package edu.ccrm.service;

import edu.ccrm.domain.Student;
import java.util.List;
import java.util.function.Predicate;

/**
 * Interface for Student service operations
 * Demonstrates functional interfaces and method references
 */
public interface StudentService extends CRUDService<Student, String> {
    
    // Additional methods specific to Student
    Student findByRegNo(String regNo);
    List<Student> findByStatus(Student.StudentStatus status);
    List<Student> findActiveStudents();
    List<Student> search(Predicate<Student> criteria);
    
    // Default method demonstrating diamond problem resolution
    @Override
    default boolean exists(String id) {
        Student student = findById(id);
        return student != null && student.isActive();
    }
    
    // Method for generating student reports
    void generateStudentReport(String studentId);
}
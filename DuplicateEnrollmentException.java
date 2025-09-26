package edu.ccrm.domain;

/**
 * Custom checked exception for duplicate enrollment scenarios
 * Demonstrates custom exception creation and checked exception handling
 */
public class DuplicateEnrollmentException extends Exception {
    private final String studentId;
    private final String courseCode;
    private final Semester semester;
    
    public DuplicateEnrollmentException(String studentId, String courseCode, Semester semester) {
        super(String.format("Student %s is already enrolled in course %s for %s semester", 
              studentId, courseCode, semester));
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.semester = semester;
    }
    
    public DuplicateEnrollmentException(String message, String studentId, String courseCode, Semester semester) {
        super(message);
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.semester = semester;
    }
    
    public String getStudentId() {
        return studentId;
    }
    
    public String getCourseCode() {
        return courseCode;
    }
    
    public Semester getSemester() {
        return semester;
    }
}
package edu.ccrm.service;

import edu.ccrm.domain.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of EnrollmentService with business rules
 */
public class EnrollmentServiceImpl implements EnrollmentService {
    
    private final StudentService studentService;
    private final CourseService courseService;
    private final List<Enrollment> enrollments = new ArrayList<>();
    
    public EnrollmentServiceImpl(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }
    
    @Override
    public Enrollment enrollStudent(String studentId, CourseCode courseCode, Semester semester) 
            throws DuplicateEnrollmentException {
        
        Student student = studentService.findById(studentId);
        if (student == null) {
            throw new IllegalArgumentException("Student not found: " + studentId);
        }
        
        Course course = courseService.findById(courseCode);
        if (course == null) {
            throw new IllegalArgumentException("Course not found: " + courseCode);
        }
        
        // Check for duplicate enrollment
        boolean alreadyEnrolled = enrollments.stream()
            .anyMatch(e -> e.getStudent().getId().equals(studentId) &&
                          e.getCourse().getCode().equals(courseCode) &&
                          e.getSemester() == semester &&
                          e.isActive());
        
        if (alreadyEnrolled) {
            throw new DuplicateEnrollmentException(studentId, courseCode.getCode(), semester);
        }
        
        // Check credit limit
        if (!canEnroll(studentId, courseCode, semester)) {
            int currentCredits = getStudentCreditLoad(studentId, semester);
            throw new MaxCreditLimitExceededException(studentId, currentCredits, 
                course.getCredits(), 20);
        }
        
        Enrollment enrollment = new Enrollment(student, course, semester, LocalDate.now());
        enrollments.add(enrollment);
        
        return enrollment;
    }
    
    @Override
    public boolean unenrollStudent(String studentId, CourseCode courseCode, Semester semester) {
        return enrollments.removeIf(e -> 
            e.getStudent().getId().equals(studentId) &&
            e.getCourse().getCode().equals(courseCode) &&
            e.getSemester() == semester &&
            e.isActive() &&
            !e.isCompleted()); // Can't unenroll from completed courses
    }
    
    @Override
    public List<Enrollment> getStudentEnrollments(String studentId) {
        return enrollments.stream()
            .filter(e -> e.getStudent().getId().equals(studentId))
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Enrollment> getCourseEnrollments(CourseCode courseCode) {
        return enrollments.stream()
            .filter(e -> e.getCourse().getCode().equals(courseCode))
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Enrollment> getEnrollmentsBySemester(Semester semester) {
        return enrollments.stream()
            .filter(e -> e.getSemester() == semester)
            .collect(Collectors.toList());
    }
    
    @Override
    public boolean recordGrade(String studentId, CourseCode courseCode, Semester semester, double marks) {
        Optional<Enrollment> enrollment = enrollments.stream()
            .filter(e -> e.getStudent().getId().equals(studentId) &&
                        e.getCourse().getCode().equals(courseCode) &&
                        e.getSemester() == semester &&
                        e.isActive())
            .findFirst();
        
        if (enrollment.isPresent()) {
            enrollment.get().setMarks(marks);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean canEnroll(String studentId, CourseCode courseCode, Semester semester) {
        Course course = courseService.findById(courseCode);
        if (course == null) return false;
        
        int currentCredits = getStudentCreditLoad(studentId, semester);
        return currentCredits + course.getCredits() <= 20; // Max 20 credits per semester
    }
    
    @Override
    public int getStudentCreditLoad(String studentId, Semester semester) {
        return enrollments.stream()
            .filter(e -> e.getStudent().getId().equals(studentId))
            .filter(e -> e.getSemester() == semester)
            .filter(e -> e.isActive())
            .filter(e -> !e.isCompleted())
            .mapToInt(e -> e.getCourse().getCredits())
            .sum();
    }
    
    // Additional utility methods
    public List<Enrollment> getAllEnrollments() {
        return new ArrayList<>(enrollments);
    }
    
    public void clearAllEnrollments() {
        enrollments.clear();
    }
}
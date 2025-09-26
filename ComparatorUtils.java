package edu.ccrm.util;

import edu.ccrm.domain.*;
import java.util.*;
import java.util.function.*;

/**
 * Utility class demonstrating various comparators and lambda expressions
 * Shows functional programming concepts and method references
 */
public class ComparatorUtils {
    
    // Private constructor to prevent instantiation
    private ComparatorUtils() {
        throw new AssertionError("Utility class should not be instantiated");
    }
    
    // Student comparators using lambda expressions
    public static final Comparator<Student> BY_NAME = 
        (s1, s2) -> s1.getName().getFullName().compareTo(s2.getName().getFullName());
    
    public static final Comparator<Student> BY_REG_NO = 
        Comparator.comparing(Student::getRegNo);
    
    public static final Comparator<Student> BY_GPA = 
        Comparator.comparing(Student::calculateGPA).reversed(); // Highest first
    
    public static final Comparator<Student> BY_ID = 
        Comparator.comparing(Student::getId);
    
    // Course comparators
    public static final Comparator<Course> BY_CODE = 
        Comparator.comparing(course -> course.getCode().getCode());
    
    public static final Comparator<Course> BY_TITLE = 
        Comparator.comparing(Course::getTitle);
    
    public static final Comparator<Course> BY_CREDITS = 
        Comparator.comparing(Course::getCredits).reversed(); // Highest first
    
    public static final Comparator<Course> BY_DEPARTMENT = 
        Comparator.comparing(Course::getDepartment, Comparator.nullsLast(String::compareTo));
    
    // Enrollment comparators
    public static final Comparator<Enrollment> BY_ENROLLMENT_DATE = 
        Comparator.comparing(Enrollment::getEnrollmentDate);
    
    public static final Comparator<Enrollment> BY_GRADE = 
        Comparator.comparing(Enrollment::getGrade, 
            Comparator.nullsLast(Comparator.comparing(Grade::getGradePoints).reversed()));
    
    // Complex comparators using method chaining
    public static final Comparator<Student> BY_STATUS_THEN_NAME = 
        Comparator.comparing(Student::getStatus)
                  .thenComparing(Student::getName, 
                      Comparator.comparing(Name::getFullName));
    
    public static final Comparator<Course> BY_SEMESTER_THEN_DEPARTMENT = 
        Comparator.comparing(Course::getSemester, Comparator.nullsLast(Comparator.naturalOrder()))
                  .thenComparing(Course::getDepartment, Comparator.nullsLast(String::compareTo));
    
    // Predicate factory methods for filtering
    public static Predicate<Student> hasMinimumGPA(double minGPA) {
        return student -> student.calculateGPA() >= minGPA;
    }
    
    public static Predicate<Student> isInStatus(Student.StudentStatus status) {
        return student -> student.getStatus() == status;
    }
    
    public static Predicate<Course> isInDepartment(String department) {
        return course -> department.equalsIgnoreCase(course.getDepartment());
    }
    
    public static Predicate<Course> hasCredits(int credits) {
        return course -> course.getCredits() == credits;
    }
    
    public static Predicate<Course> isInSemester(Semester semester) {
        return course -> course.getSemester() == semester;
    }
    
    // Function factory methods for mapping/transformation
    public static Function<Student, String> studentToDisplayString() {
        return student -> String.format("%s (%s) - GPA: %.2f", 
            student.getName().getFullName(), 
            student.getRegNo(), 
            student.calculateGPA());
    }
    
    public static Function<Course, String> courseToDisplayString() {
        return course -> String.format("%s: %s (%d credits)", 
            course.getCode(), 
            course.getTitle(), 
            course.getCredits());
    }
    
    // Consumer factory methods for actions
    public static Consumer<Student> printStudentSummary() {
        return student -> {
            System.out.println("Student: " + student.getName().getFullName());
            System.out.println("  ID: " + student.getId());
            System.out.println("  Reg No: " + student.getRegNo());
            System.out.println("  GPA: " + String.format("%.2f", student.calculateGPA()));
            System.out.println("  Status: " + student.getStatus());
        };
    }
    
    public static Consumer<Course> printCourseSummary() {
        return course -> {
            System.out.println("Course: " + course.getCode());
            System.out.println("  Title: " + course.getTitle());
            System.out.println("  Credits: " + course.getCredits());
            System.out.println("  Department: " + course.getDepartment());
            System.out.println("  Semester: " + course.getSemester());
        };
    }
    
    // BiFunction for more complex operations
    public static BiFunction<Student, List<Enrollment>, Double> calculateSemesterGPA(Semester semester) {
        return (student, enrollments) -> {
            OptionalDouble gpa = enrollments.stream()
                .filter(e -> e.getStudent().equals(student))
                .filter(e -> e.getSemester() == semester)
                .filter(e -> e.getGrade() != null)
                .mapToDouble(e -> e.getGrade().getGradePoints())
                .average();
            
            return gpa.orElse(0.0);
        };
    }
    
    // Method demonstrating advanced lambda usage with exception handling
    public static <T> Consumer<T> safeConsumer(Consumer<T> consumer) {
        return item -> {
            try {
                consumer.accept(item);
            } catch (Exception e) {
                System.err.println("Error processing item: " + item + " - " + e.getMessage());
            }
        };
    }
    
    // Method demonstrating currying (partial application)
    public static Function<Double, Predicate<Student>> gpaFilter() {
        return minGPA -> student -> student.calculateGPA() >= minGPA;
    }
    
    // Method demonstrating composition
    public static Function<Student, String> createStudentFormatter(String prefix, String suffix) {
        Function<Student, String> baseFormatter = studentToDisplayString();
        return baseFormatter.andThen(s -> prefix + s + suffix);
    }
}
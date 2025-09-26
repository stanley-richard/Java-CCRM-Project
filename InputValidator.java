package edu.ccrm.util;

import java.util.regex.Pattern;

/**
 * Utility class for input validation
 * Demonstrates utility methods and regular expressions
 */
public class InputValidator {
    
    // Email validation pattern
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    
    // Private constructor to prevent instantiation
    private InputValidator() {
        throw new AssertionError("Utility class should not be instantiated");
    }
    
    /**
     * Validate email format using regex
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    
    /**
     * Validate if string is not null or empty
     */
    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }
    
    /**
     * Validate if integer is within range
     */
    public static boolean isInRange(int value, int min, int max) {
        return value >= min && value <= max;
    }
    
    /**
     * Validate if double is within range
     */
    public static boolean isInRange(double value, double min, double max) {
        return value >= min && value <= max;
    }
    
    /**
     * Validate student ID format (should start with 'S' followed by digits)
     */
    public static boolean isValidStudentId(String studentId) {
        if (studentId == null) return false;
        return Pattern.matches("^S\\d{3,}$", studentId);
    }
    
    /**
     * Validate course code format (2-4 letters followed by 3 digits)
     */
    public static boolean isValidCourseCode(String courseCode) {
        if (courseCode == null) return false;
        return Pattern.matches("^[A-Z]{2,4}\\d{3}$", courseCode.toUpperCase());
    }
    
    /**
     * Validate marks range (0-100)
     */
    public static boolean isValidMarks(double marks) {
        return isInRange(marks, 0.0, 100.0);
    }
    
    /**
     * Validate credits range (1-6)
     */
    public static boolean isValidCredits(int credits) {
        return isInRange(credits, 1, 6);
    }
}
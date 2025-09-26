package edu.ccrm.domain;

/**
 * Custom unchecked exception for credit limit violations
 * Demonstrates runtime exception handling
 */
public class MaxCreditLimitExceededException extends RuntimeException {
    private final String studentId;
    private final int currentCredits;
    private final int attemptedCredits;
    private final int maxLimit;
    
    public MaxCreditLimitExceededException(String studentId, int currentCredits, 
                                         int attemptedCredits, int maxLimit) {
        super(String.format("Credit limit exceeded for student %s. Current: %d, Attempted: %d, Max: %d", 
              studentId, currentCredits, attemptedCredits, maxLimit));
        this.studentId = studentId;
        this.currentCredits = currentCredits;
        this.attemptedCredits = attemptedCredits;
        this.maxLimit = maxLimit;
    }
    
    public String getStudentId() {
        return studentId;
    }
    
    public int getCurrentCredits() {
        return currentCredits;
    }
    
    public int getAttemptedCredits() {
        return attemptedCredits;
    }
    
    public int getMaxLimit() {
        return maxLimit;
    }
}
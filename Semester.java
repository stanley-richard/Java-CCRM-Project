package edu.ccrm.domain;

/**
 * Enum representing academic semesters with constructors and fields
 */
public enum Semester {
    SPRING("Spring", 1),
    SUMMER("Summer", 2),
    FALL("Fall", 3);
    
    private final String displayName;
    private final int order;
    
    // Enum constructor
    Semester(String displayName, int order) {
        this.displayName = displayName;
        this.order = order;
    }
    
    // Getter methods
    public String getDisplayName() {
        return displayName;
    }
    
    public int getOrder() {
        return order;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}
package edu.ccrm.domain;

/**
 * Enum representing grades with grade points for GPA calculation
 */
public enum Grade {
    S("S", 10.0, "Outstanding"),
    A("A", 9.0, "Excellent"),
    B("B", 8.0, "Very Good"),
    C("C", 7.0, "Good"),
    D("D", 6.0, "Satisfactory"),
    E("E", 5.0, "Pass"),
    F("F", 0.0, "Fail");
    
    private final String letter;
    private final double gradePoints;
    private final String description;
    
    // Enum constructor
    Grade(String letter, double gradePoints, String description) {
        this.letter = letter;
        this.gradePoints = gradePoints;
        this.description = description;
    }
    
    // Getter methods
    public String getLetter() {
        return letter;
    }
    
    public double getGradePoints() {
        return gradePoints;
    }
    
    public String getDescription() {
        return description;
    }
    
    /**
     * Convert marks to grade using standard grading scale
     */
    public static Grade fromMarks(double marks) {
        if (marks >= 90) return S;
        else if (marks >= 80) return A;
        else if (marks >= 70) return B;
        else if (marks >= 60) return C;
        else if (marks >= 50) return D;
        else if (marks >= 40) return E;
        else return F;
    }
    
    @Override
    public String toString() {
        return letter + " (" + gradePoints + ")";
    }
}
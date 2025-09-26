package edu.ccrm.domain;

/**
 * Immutable value class representing a full name
 * Demonstrates immutability with final fields and defensive copying
 */
public final class Name {
    private final String firstName;
    private final String middleName;
    private final String lastName;
    
    // Constructor with defensive copying for immutability
    public Name(String firstName, String middleName, String lastName) {
        this.firstName = firstName != null ? firstName.trim() : "";
        this.middleName = middleName != null ? middleName.trim() : "";
        this.lastName = lastName != null ? lastName.trim() : "";
    }
    
    // Constructor overload
    public Name(String firstName, String lastName) {
        this(firstName, "", lastName);
    }
    
    // Getter methods (no setters for immutability)
    public String getFirstName() {
        return firstName;
    }
    
    public String getMiddleName() {
        return middleName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public String getFullName() {
        StringBuilder fullName = new StringBuilder(firstName);
        if (!middleName.isEmpty()) {
            fullName.append(" ").append(middleName);
        }
        fullName.append(" ").append(lastName);
        return fullName.toString();
    }
    
    @Override
    public String toString() {
        return getFullName();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Name name = (Name) obj;
        return firstName.equals(name.firstName) &&
               middleName.equals(name.middleName) &&
               lastName.equals(name.lastName);
    }
    
    @Override
    public int hashCode() {
        return firstName.hashCode() + middleName.hashCode() + lastName.hashCode();
    }
}
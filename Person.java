package edu.ccrm.domain;

import java.time.LocalDate;

/**
 * Abstract base class representing a person in the system
 * Demonstrates abstraction and inheritance
 */
public abstract class Person {
    protected String id;
    protected Name name;
    protected String email;
    protected LocalDate dateCreated;
    protected boolean active;
    
    // Constructor
    public Person(String id, Name name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dateCreated = LocalDate.now();
        this.active = true;
    }
    
    // Abstract methods that subclasses must implement
    public abstract String getDisplayTitle();
    public abstract String getDetailedInfo();
    
    // Concrete methods with common functionality
    public String getId() {
        return id;
    }
    
    public Name getName() {
        return name;
    }
    
    public void setName(Name name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public LocalDate getDateCreated() {
        return dateCreated;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    @Override
    public String toString() {
        return String.format("%s [%s] - %s", 
            name.getFullName(), id, active ? "Active" : "Inactive");
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Person person = (Person) obj;
        return id.equals(person.id);
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
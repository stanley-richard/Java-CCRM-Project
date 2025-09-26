package edu.ccrm.service;

import edu.ccrm.domain.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Implementation of StudentService
 * Demonstrates polymorphism, streams, and functional programming
 */
public class StudentServiceImpl implements StudentService {
    
    // In-memory storage (in real application, this would be a database)
    private final Map<String, Student> students = new HashMap<>();
    private final Map<String, Student> studentsByRegNo = new HashMap<>();
    
    @Override
    public Student create(Student student) {
        // Assertions for invariants (enable with -ea flag)
        assert student != null : "Student cannot be null";
        assert student.getId() != null : "Student ID cannot be null";
        assert student.getRegNo() != null : "Student registration number cannot be null";
        
        if (students.containsKey(student.getId())) {
            throw new IllegalArgumentException("Student with ID " + student.getId() + " already exists");
        }
        
        if (studentsByRegNo.containsKey(student.getRegNo())) {
            throw new IllegalArgumentException("Student with RegNo " + student.getRegNo() + " already exists");
        }
        
        students.put(student.getId(), student);
        studentsByRegNo.put(student.getRegNo(), student);
        return student;
    }
    
    @Override
    public Student findById(String id) {
        return students.get(id);
    }
    
    @Override
    public List<Student> findAll() {
        return new ArrayList<>(students.values());
    }
    
    @Override
    public Student update(Student student) {
        if (!students.containsKey(student.getId())) {
            throw new IllegalArgumentException("Student not found: " + student.getId());
        }
        
        students.put(student.getId(), student);
        studentsByRegNo.put(student.getRegNo(), student);
        return student;
    }
    
    @Override
    public boolean delete(String id) {
        Student student = students.remove(id);
        if (student != null) {
            studentsByRegNo.remove(student.getRegNo());
            return true;
        }
        return false;
    }
    
    @Override
    public Student findByRegNo(String regNo) {
        return studentsByRegNo.get(regNo);
    }
    
    @Override
    public List<Student> findByStatus(Student.StudentStatus status) {
        // Using streams and method references
        return students.values().stream()
            .filter(student -> student.getStatus() == status)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Student> findActiveStudents() {
        // Lambda expression demonstrating functional programming
        return students.values().stream()
            .filter(Student::isActive)
            .sorted((s1, s2) -> s1.getName().getFullName().compareTo(s2.getName().getFullName()))
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Student> search(Predicate<Student> criteria) {
        return students.values().stream()
            .filter(criteria)
            .collect(Collectors.toList());
    }
    
    @Override
    public void generateStudentReport(String studentId) {
        Student student = findById(studentId);
        if (student == null) {
            System.out.println("Student not found: " + studentId);
            return;
        }
        
        System.out.println("=".repeat(50));
        System.out.println("STUDENT REPORT");
        System.out.println("=".repeat(50));
        System.out.println(student.getDetailedInfo());
        
        Student.StudentStats stats = student.getStatistics();
        System.out.println("Academic Statistics:");
        System.out.println("Total Enrollments: " + stats.getTotalEnrollments());
        System.out.println("Completed Credits: " + stats.getCompletedCredits());
        System.out.println("Current GPA: " + String.format("%.2f", stats.getAverageGPA()));
        System.out.println("=".repeat(50));
    }
    
    // Method demonstrating multi-catch exception handling
    public void importStudentsFromData(List<String[]> studentData) {
        for (String[] data : studentData) {
            try {
                if (data.length < 4) {
                    throw new IllegalArgumentException("Insufficient data fields");
                }
                
                String id = data[0].trim();
                String regNo = data[1].trim();
                String fullName = data[2].trim();
                String email = data[3].trim();
                
                // Split name assuming "First Last" format
                String[] nameParts = fullName.split("\\s+", 2);
                Name name = nameParts.length == 2 ? 
                    new Name(nameParts[0], nameParts[1]) : 
                    new Name(nameParts[0], "");
                
                Student student = new Student(id, regNo, name, email);
                create(student);
                
            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                System.err.println("Error importing student data: " + e.getMessage());
                System.err.println("Data: " + Arrays.toString(data));
            }
        }
    }
}
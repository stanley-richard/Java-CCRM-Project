package edu.ccrm.cli;

import edu.ccrm.domain.*;
import edu.ccrm.service.*;
import edu.ccrm.util.InputValidator;
import java.util.Scanner;
import java.util.List;

/**
 * Main menu system for the CCRM application
 * Demonstrates switch statements, loops, and user interaction
 */
public class MainMenu {
    
    private final Scanner scanner;
    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private boolean running;
    
    public MainMenu() {
        this.scanner = new Scanner(System.in);
        this.studentService = new StudentServiceImpl();
        this.courseService = new CourseServiceImpl();
        this.enrollmentService = new EnrollmentServiceImpl(studentService, courseService);
        this.running = true;
        
        // Initialize with some sample data
        initializeSampleData();
    }
    
    public void start() {
        System.out.println("Welcome to Campus Course & Records Manager!");
        
        // Main menu loop demonstrating while loop and break/continue
        while (running) {
            try {
                displayMainMenu();
                int choice = getMenuChoice();
                
                // Enhanced switch statement (Java 14+ style)
                switch (choice) {
                    case 1 -> handleStudentManagement();
                    case 2 -> handleCourseManagement();
                    case 3 -> handleEnrollmentManagement();
                    case 4 -> handleGradeManagement();
                    case 5 -> handleReportsMenu();
                    case 6 -> handleFileOperations();
                    case 7 -> handleBackupOperations();
                    case 8 -> {
                        System.out.println("Thank you for using CCRM!");
                        running = false;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
                
                if (running) {
                    System.out.println("\\nPress Enter to continue...");
                    scanner.nextLine();
                }
                
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
                System.out.println("Please try again.");
            }
        }
        
        scanner.close();
    }
    
    private void displayMainMenu() {
        System.out.println("\\n" + "=".repeat(50));
        System.out.println("           MAIN MENU");
        System.out.println("=".repeat(50));
        System.out.println("1. Student Management");
        System.out.println("2. Course Management");
        System.out.println("3. Enrollment Management");
        System.out.println("4. Grade Management");
        System.out.println("5. Reports");
        System.out.println("6. File Operations (Import/Export)");
        System.out.println("7. Backup Operations");
        System.out.println("8. Exit");
        System.out.println("=".repeat(50));
        System.out.print("Enter your choice (1-8): ");
    }
    
    private int getMenuChoice() {
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            return choice;
        } catch (NumberFormatException e) {
            return -1; // Invalid choice
        }
    }
    
    private void handleStudentManagement() {
        boolean studentMenuRunning = true;
        
        // Labeled loop demonstrating labeled break/continue
        studentMenu: while (studentMenuRunning) {
            System.out.println("\\n--- Student Management ---");
            System.out.println("1. Add Student");
            System.out.println("2. List All Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Deactivate Student");
            System.out.println("6. Student Profile & Transcript");
            System.out.println("7. Back to Main Menu");
            System.out.print("Choice: ");
            
            int choice = getMenuChoice();
            
            // Classic switch demonstrating different syntax
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    listAllStudents();
                    break;
                case 3:
                    searchStudent();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    deactivateStudent();
                    break;
                case 6:
                    showStudentProfile();
                    break;
                case 7:
                    break studentMenu; // Labeled break
                default:
                    System.out.println("Invalid choice.");
                    continue studentMenu; // Labeled continue
            }
            
            System.out.println("\\nPress Enter to continue...");
            scanner.nextLine();
        }
    }
    
    private void addStudent() {
        System.out.println("\\n=== Add New Student ===");
        
        try {
            System.out.print("Student ID: ");
            String id = scanner.nextLine().trim();
            
            System.out.print("Registration Number: ");
            String regNo = scanner.nextLine().trim();
            
            System.out.print("First Name: ");
            String firstName = scanner.nextLine().trim();
            
            System.out.print("Last Name: ");
            String lastName = scanner.nextLine().trim();
            
            System.out.print("Email: ");
            String email = scanner.nextLine().trim();
            
            // Input validation
            if (!InputValidator.isValidEmail(email)) {
                System.out.println("Invalid email format!");
                return;
            }
            
            Name name = new Name(firstName, lastName);
            Student student = new Student(id, regNo, name, email);
            
            studentService.create(student);
            System.out.println("Student added successfully: " + student);
            
        } catch (Exception e) {
            System.err.println("Error adding student: " + e.getMessage());
        }
    }
    
    private void listAllStudents() {
        System.out.println("\\n=== All Students ===");
        
        List<Student> students = studentService.findActiveStudents();
        
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        
        // Enhanced for loop
        for (Student student : students) {
            System.out.println(student);
        }
        
        System.out.println("\\nTotal active students: " + students.size());
    }
    
    private void searchStudent() {
        System.out.println("\\n=== Search Student ===");
        System.out.print("Enter Student ID or Registration Number: ");
        String searchTerm = scanner.nextLine().trim();
        
        Student student = studentService.findById(searchTerm);
        if (student == null) {
            student = studentService.findByRegNo(searchTerm);
        }
        
        if (student != null) {
            System.out.println("Student found:");
            System.out.println(student.getDetailedInfo());
        } else {
            System.out.println("Student not found.");
        }
    }
    
    private void updateStudent() {
        System.out.println("\\n=== Update Student ===");
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine().trim();
        
        Student student = studentService.findById(id);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        
        System.out.println("Current details: " + student);
        System.out.print("New email (or press Enter to keep current): ");
        String newEmail = scanner.nextLine().trim();
        
        if (!newEmail.isEmpty() && InputValidator.isValidEmail(newEmail)) {
            student.setEmail(newEmail);
            studentService.update(student);
            System.out.println("Student updated successfully.");
        } else if (!newEmail.isEmpty()) {
            System.out.println("Invalid email format. Update cancelled.");
        }
    }
    
    private void deactivateStudent() {
        System.out.println("\\n=== Deactivate Student ===");
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine().trim();
        
        Student student = studentService.findById(id);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        
        student.setActive(false);
        studentService.update(student);
        System.out.println("Student deactivated: " + student);
    }
    
    private void showStudentProfile() {
        System.out.println("\\n=== Student Profile & Transcript ===");
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine().trim();
        
        studentService.generateStudentReport(id);
    }
    
    // Course Management methods
    private void handleCourseManagement() {
        System.out.println("\\n--- Course Management ---");
        System.out.println("1. Add Course");
        System.out.println("2. List All Courses");
        System.out.println("3. Search Courses");
        System.out.println("4. Assign Instructor");
        System.out.println("5. Back to Main Menu");
        System.out.print("Choice: ");
        
        int choice = getMenuChoice();
        
        switch (choice) {
            case 1 -> addCourse();
            case 2 -> listAllCourses();
            case 3 -> searchCourses();
            case 4 -> assignInstructor();
            case 5 -> { /* Return to main menu */ }
            default -> System.out.println("Invalid choice.");
        }
    }
    
    private void addCourse() {
        System.out.println("\\n=== Add New Course ===");
        
        try {
            System.out.print("Course Code: ");
            String codeStr = scanner.nextLine().trim().toUpperCase();
            CourseCode code = new CourseCode(codeStr);
            
            System.out.print("Course Title: ");
            String title = scanner.nextLine().trim();
            
            System.out.print("Credits (1-6): ");
            int credits = Integer.parseInt(scanner.nextLine().trim());
            
            System.out.print("Department: ");
            String department = scanner.nextLine().trim();
            
            System.out.println("Select Semester:");
            System.out.println("1. Spring");
            System.out.println("2. Summer");
            System.out.println("3. Fall");
            System.out.print("Choice: ");
            
            int semChoice = Integer.parseInt(scanner.nextLine().trim());
            Semester semester = switch (semChoice) {
                case 1 -> Semester.SPRING;
                case 2 -> Semester.SUMMER;
                case 3 -> Semester.FALL;
                default -> throw new IllegalArgumentException("Invalid semester choice");
            };
            
            // Using Builder pattern
            Course course = new Course.Builder(code, title, credits)
                .department(department)
                .semester(semester)
                .build();
            
            courseService.create(course);
            System.out.println("Course added successfully: " + course);
            
        } catch (Exception e) {
            System.err.println("Error adding course: " + e.getMessage());
        }
    }
    
    private void listAllCourses() {
        System.out.println("\\n=== All Courses ===");
        
        List<Course> courses = courseService.findActiveCourses();
        
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }
        
        courses.forEach(System.out::println); // Method reference
        System.out.println("\\nTotal active courses: " + courses.size());
    }
    
    private void searchCourses() {
        System.out.println("\\n=== Search Courses ===");
        System.out.println("1. By Department");
        System.out.println("2. By Semester");
        System.out.println("3. By Course Code");
        System.out.print("Choice: ");
        
        int choice = getMenuChoice();
        List<Course> results = null;
        
        switch (choice) {
            case 1:
                System.out.print("Enter department: ");
                String dept = scanner.nextLine().trim();
                results = courseService.findByDepartment(dept);
                break;
            case 2:
                System.out.println("Select Semester (1-SPRING, 2-SUMMER, 3-FALL): ");
                int semChoice = Integer.parseInt(scanner.nextLine().trim());
                Semester semester = Semester.values()[semChoice - 1];
                results = courseService.findBySemester(semester);
                break;
            case 3:
                System.out.print("Enter course code: ");
                String codeStr = scanner.nextLine().trim();
                Course course = courseService.findById(new CourseCode(codeStr));
                if (course != null) {
                    System.out.println("Course found: " + course);
                    courseService.generateCourseReport(course.getCode());
                } else {
                    System.out.println("Course not found.");
                }
                return;
        }
        
        if (results != null && !results.isEmpty()) {
            results.forEach(System.out::println);
        } else {
            System.out.println("No courses found.");
        }
    }
    
    private void assignInstructor() {
        System.out.println("\\n=== Assign Instructor (Not implemented in this demo) ===");
        System.out.println("This would require instructor management functionality.");
    }
    
    // Enrollment Management
    private void handleEnrollmentManagement() {
        System.out.println("\\n--- Enrollment Management ---");
        System.out.println("1. Enroll Student in Course");
        System.out.println("2. Unenroll Student from Course");
        System.out.println("3. View Student Enrollments");
        System.out.println("4. View Course Enrollments");
        System.out.println("5. Back to Main Menu");
        System.out.print("Choice: ");
        
        int choice = getMenuChoice();
        
        switch (choice) {
            case 1 -> enrollStudent();
            case 2 -> unenrollStudent();
            case 3 -> viewStudentEnrollments();
            case 4 -> viewCourseEnrollments();
            case 5 -> { /* Return to main menu */ }
            default -> System.out.println("Invalid choice.");
        }
    }
    
    private void enrollStudent() {
        System.out.println("\\n=== Enroll Student in Course ===");
        
        try {
            System.out.print("Student ID: ");
            String studentId = scanner.nextLine().trim();
            
            System.out.print("Course Code: ");
            String courseCodeStr = scanner.nextLine().trim();
            CourseCode courseCode = new CourseCode(courseCodeStr);
            
            System.out.println("Select Semester (1-SPRING, 2-SUMMER, 3-FALL): ");
            int semChoice = Integer.parseInt(scanner.nextLine().trim());
            Semester semester = Semester.values()[semChoice - 1];
            
            Enrollment enrollment = enrollmentService.enrollStudent(studentId, courseCode, semester);
            System.out.println("Student enrolled successfully: " + enrollment);
            
        } catch (DuplicateEnrollmentException e) {
            System.err.println("Enrollment failed: " + e.getMessage());
        } catch (MaxCreditLimitExceededException e) {
            System.err.println("Credit limit exceeded: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error during enrollment: " + e.getMessage());
        }
    }
    
    private void unenrollStudent() {
        System.out.println("\\n=== Unenroll Student from Course ===");
        
        try {
            System.out.print("Student ID: ");
            String studentId = scanner.nextLine().trim();
            
            System.out.print("Course Code: ");
            String courseCodeStr = scanner.nextLine().trim();
            CourseCode courseCode = new CourseCode(courseCodeStr);
            
            System.out.println("Select Semester (1-SPRING, 2-SUMMER, 3-FALL): ");
            int semChoice = Integer.parseInt(scanner.nextLine().trim());
            Semester semester = Semester.values()[semChoice - 1];
            
            boolean success = enrollmentService.unenrollStudent(studentId, courseCode, semester);
            
            if (success) {
                System.out.println("Student unenrolled successfully.");
            } else {
                System.out.println("Unenrollment failed. Check if the student is enrolled in this course.");
            }
            
        } catch (Exception e) {
            System.err.println("Error during unenrollment: " + e.getMessage());
        }
    }
    
    private void viewStudentEnrollments() {
        System.out.println("\\n=== View Student Enrollments ===");
        System.out.print("Student ID: ");
        String studentId = scanner.nextLine().trim();
        
        List<Enrollment> enrollments = enrollmentService.getStudentEnrollments(studentId);
        
        if (enrollments.isEmpty()) {
            System.out.println("No enrollments found for student: " + studentId);
            return;
        }
        
        System.out.println("\\nEnrollments for student " + studentId + ":");
        enrollments.forEach(System.out::println);
    }
    
    private void viewCourseEnrollments() {
        System.out.println("\\n=== View Course Enrollments ===");
        System.out.print("Course Code: ");
        String courseCodeStr = scanner.nextLine().trim();
        
        try {
            CourseCode courseCode = new CourseCode(courseCodeStr);
            List<Enrollment> enrollments = enrollmentService.getCourseEnrollments(courseCode);
            
            if (enrollments.isEmpty()) {
                System.out.println("No enrollments found for course: " + courseCode);
                return;
            }
            
            System.out.println("\\nEnrollments for course " + courseCode + ":");
            enrollments.forEach(System.out::println);
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    // Grade Management
    private void handleGradeManagement() {
        System.out.println("\\n--- Grade Management ---");
        System.out.println("1. Record Grade");
        System.out.println("2. View Student Grades");
        System.out.println("3. Back to Main Menu");
        System.out.print("Choice: ");
        
        int choice = getMenuChoice();
        
        switch (choice) {
            case 1 -> recordGrade();
            case 2 -> viewStudentGrades();
            case 3 -> { /* Return to main menu */ }
            default -> System.out.println("Invalid choice.");
        }
    }
    
    private void recordGrade() {
        System.out.println("\\n=== Record Grade ===");
        
        try {
            System.out.print("Student ID: ");
            String studentId = scanner.nextLine().trim();
            
            System.out.print("Course Code: ");
            String courseCodeStr = scanner.nextLine().trim();
            CourseCode courseCode = new CourseCode(courseCodeStr);
            
            System.out.println("Select Semester (1-SPRING, 2-SUMMER, 3-FALL): ");
            int semChoice = Integer.parseInt(scanner.nextLine().trim());
            Semester semester = Semester.values()[semChoice - 1];
            
            System.out.print("Enter marks (0-100): ");
            double marks = Double.parseDouble(scanner.nextLine().trim());
            
            boolean success = enrollmentService.recordGrade(studentId, courseCode, semester, marks);
            
            if (success) {
                Grade grade = Grade.fromMarks(marks);
                System.out.println("Grade recorded successfully: " + marks + " (" + grade + ")");
            } else {
                System.out.println("Failed to record grade. Check enrollment details.");
            }
            
        } catch (Exception e) {
            System.err.println("Error recording grade: " + e.getMessage());
        }
    }
    
    private void viewStudentGrades() {
        System.out.println("\\n=== View Student Grades ===");
        System.out.print("Student ID: ");
        String studentId = scanner.nextLine().trim();
        
        List<Enrollment> enrollments = enrollmentService.getStudentEnrollments(studentId);
        
        System.out.println("\\nGrades for student " + studentId + ":");
        enrollments.stream()
            .filter(e -> e.getGrade() != null)
            .forEach(e -> System.out.println(
                e.getCourse().getCode() + " (" + e.getSemester() + "): " + 
                e.getMarks() + " - " + e.getGrade()));
    }
    
    // Reports Menu
    private void handleReportsMenu() {
        System.out.println("\\n--- Reports ---");
        System.out.println("1. Student Statistics");
        System.out.println("2. Course Statistics");
        System.out.println("3. GPA Distribution");
        System.out.println("4. Back to Main Menu");
        System.out.print("Choice: ");
        
        int choice = getMenuChoice();
        
        switch (choice) {
            case 1 -> showStudentStatistics();
            case 2 -> showCourseStatistics();
            case 3 -> showGPADistribution();
            case 4 -> { /* Return to main menu */ }
            default -> System.out.println("Invalid choice.");
        }
    }
    
    private void showStudentStatistics() {
        System.out.println("\\n=== Student Statistics ===");
        List<Student> students = studentService.findActiveStudents();
        
        System.out.println("Total Active Students: " + students.size());
        
        // Using streams for statistics
        double avgGPA = students.stream()
            .mapToDouble(Student::calculateGPA)
            .filter(gpa -> gpa > 0)
            .average()
            .orElse(0.0);
        
        System.out.println("Average GPA: " + String.format("%.2f", avgGPA));
        
        long studentsWithGrades = students.stream()
            .filter(s -> s.calculateGPA() > 0)
            .count();
        
        System.out.println("Students with recorded grades: " + studentsWithGrades);
    }
    
    private void showCourseStatistics() {
        System.out.println("\\n=== Course Statistics ===");
        List<Course> courses = courseService.findActiveCourses();
        
        System.out.println("Total Active Courses: " + courses.size());
        
        // Group by department
        courses.stream()
            .collect(java.util.stream.Collectors.groupingBy(
                Course::getDepartment,
                java.util.stream.Collectors.counting()))
            .forEach((dept, count) -> 
                System.out.println(dept + ": " + count + " courses"));
    }
    
    private void showGPADistribution() {
        System.out.println("\\n=== GPA Distribution ===");
        List<Student> students = studentService.findActiveStudents();
        
        long excellent = students.stream().filter(s -> s.calculateGPA() >= 9.0).count();
        long veryGood = students.stream().filter(s -> s.calculateGPA() >= 8.0 && s.calculateGPA() < 9.0).count();
        long good = students.stream().filter(s -> s.calculateGPA() >= 7.0 && s.calculateGPA() < 8.0).count();
        long satisfactory = students.stream().filter(s -> s.calculateGPA() >= 6.0 && s.calculateGPA() < 7.0).count();
        long poor = students.stream().filter(s -> s.calculateGPA() > 0 && s.calculateGPA() < 6.0).count();
        
        System.out.println("Excellent (9.0+): " + excellent);
        System.out.println("Very Good (8.0-8.9): " + veryGood);
        System.out.println("Good (7.0-7.9): " + good);
        System.out.println("Satisfactory (6.0-6.9): " + satisfactory);
        System.out.println("Poor (<6.0): " + poor);
    }
    
    // File Operations and Backup (placeholder methods)
    private void handleFileOperations() {
        System.out.println("\\n--- File Operations ---");
        System.out.println("File I/O operations will be implemented in the next phase.");
        System.out.println("This would include CSV import/export functionality.");
    }
    
    private void handleBackupOperations() {
        System.out.println("\\n--- Backup Operations ---");
        System.out.println("Backup operations will be implemented in the next phase.");
        System.out.println("This would include timestamped backups and recursive utilities.");
    }
    
    // Initialize sample data for demonstration
    private void initializeSampleData() {
        try {
            // Sample students
            Student student1 = new Student("S001", "2023CS001", 
                new Name("John", "Doe"), "john.doe@university.edu");
            Student student2 = new Student("S002", "2023CS002", 
                new Name("Jane", "Smith"), "jane.smith@university.edu");
            Student student3 = new Student("S003", "2023IT001", 
                new Name("Alice", "Johnson"), "alice.johnson@university.edu");
            
            studentService.create(student1);
            studentService.create(student2);
            studentService.create(student3);
            
            // Sample courses
            Course course1 = new Course.Builder(new CourseCode("CS101"), "Introduction to Programming", 3)
                .department("Computer Science")
                .semester(Semester.FALL)
                .build();
            
            Course course2 = new Course.Builder(new CourseCode("CS201"), "Data Structures", 4)
                .department("Computer Science")
                .semester(Semester.SPRING)
                .build();
            
            Course course3 = new Course.Builder(new CourseCode("IT301"), "Database Systems", 3)
                .department("Information Technology")
                .semester(Semester.FALL)
                .build();
            
            courseService.create(course1);
            courseService.create(course2);
            courseService.create(course3);
            
            // Sample enrollments
            enrollmentService.enrollStudent("S001", new CourseCode("CS101"), Semester.FALL);
            enrollmentService.enrollStudent("S001", new CourseCode("IT301"), Semester.FALL);
            enrollmentService.enrollStudent("S002", new CourseCode("CS101"), Semester.FALL);
            enrollmentService.enrollStudent("S003", new CourseCode("IT301"), Semester.FALL);
            
            // Sample grades
            enrollmentService.recordGrade("S001", new CourseCode("CS101"), Semester.FALL, 85.0);
            enrollmentService.recordGrade("S002", new CourseCode("CS101"), Semester.FALL, 92.0);
            
            System.out.println("Sample data initialized successfully.");
            
        } catch (Exception e) {
            System.err.println("Error initializing sample data: " + e.getMessage());
        }
    }
}
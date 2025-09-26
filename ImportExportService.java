package edu.ccrm.io;

import edu.ccrm.domain.*;
import edu.ccrm.service.*;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service for importing and exporting data using NIO.2 APIs
 * Demonstrates file I/O, streams, and NIO.2 features
 */
public class ImportExportService {
    
    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final Path exportPath;
    
    public ImportExportService(StudentService studentService, CourseService courseService, 
                              EnrollmentService enrollmentService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
        this.exportPath = Paths.get("exports");
        
        // Create exports directory if it doesn't exist
        try {
            Files.createDirectories(exportPath);
        } catch (IOException e) {
            System.err.println("Failed to create exports directory: " + e.getMessage());
        }
    }
    
    /**
     * Import students from CSV file using NIO.2 and Streams
     */
    public void importStudentsFromCSV(String fileName) throws IOException {
        Path filePath = Paths.get(fileName);
        
        // Check if file exists
        if (!Files.exists(filePath)) {
            throw new IOException("File not found: " + fileName);
        }
        
        try (Stream<String> lines = Files.lines(filePath)) {
            List<String[]> studentData = lines
                .skip(1) // Skip header line
                .filter(line -> !line.trim().isEmpty())
                .map(line -> line.split(","))
                .collect(Collectors.toList());
            
            System.out.println("Importing " + studentData.size() + " students...");
            
            int successCount = 0;
            int errorCount = 0;
            
            for (String[] data : studentData) {
                try {
                    if (data.length >= 4) {
                        String id = data[0].trim();
                        String regNo = data[1].trim();
                        String fullName = data[2].trim();
                        String email = data[3].trim();
                        
                        // Parse name
                        String[] nameParts = fullName.split("\\s+", 2);
                        Name name = nameParts.length == 2 ? 
                            new Name(nameParts[0], nameParts[1]) : 
                            new Name(nameParts[0], "");
                        
                        Student student = new Student(id, regNo, name, email);
                        studentService.create(student);
                        successCount++;
                    }
                } catch (Exception e) {
                    System.err.println("Error importing student: " + Arrays.toString(data) + 
                                     " - " + e.getMessage());
                    errorCount++;
                }
            }
            
            System.out.println("Import completed. Success: " + successCount + ", Errors: " + errorCount);
        }
    }
    
    /**
     * Import courses from CSV file
     */
    public void importCoursesFromCSV(String fileName) throws IOException {
        Path filePath = Paths.get(fileName);
        
        if (!Files.exists(filePath)) {
            throw new IOException("File not found: " + fileName);
        }
        
        try (Stream<String> lines = Files.lines(filePath)) {
            List<String[]> courseData = lines
                .skip(1) // Skip header
                .filter(line -> !line.trim().isEmpty())
                .map(line -> line.split(","))
                .collect(Collectors.toList());
            
            System.out.println("Importing " + courseData.size() + " courses...");
            
            int successCount = 0;
            int errorCount = 0;
            
            for (String[] data : courseData) {
                try {
                    if (data.length >= 5) {
                        CourseCode code = new CourseCode(data[0].trim());
                        String title = data[1].trim();
                        int credits = Integer.parseInt(data[2].trim());
                        String department = data[3].trim();
                        Semester semester = Semester.valueOf(data[4].trim().toUpperCase());
                        
                        Course course = new Course.Builder(code, title, credits)
                            .department(department)
                            .semester(semester)
                            .build();
                        
                        courseService.create(course);
                        successCount++;
                    }
                } catch (Exception e) {
                    System.err.println("Error importing course: " + Arrays.toString(data) + 
                                     " - " + e.getMessage());
                    errorCount++;
                }
            }
            
            System.out.println("Import completed. Success: " + successCount + ", Errors: " + errorCount);
        }
    }
    
    /**
     * Export students to CSV using NIO.2
     */
    public Path exportStudentsToCSV() throws IOException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path outputFile = exportPath.resolve("students_" + timestamp + ".csv");
        
        List<Student> students = studentService.findAll();
        
        List<String> lines = new ArrayList<>();
        lines.add("ID,RegNo,Name,Email,Status,DateCreated,Active");
        
        // Using streams to convert students to CSV lines
        List<String> studentLines = students.stream()
            .map(student -> String.join(",",
                student.getId(),
                student.getRegNo(),
                "\"" + student.getName().getFullName() + "\"",
                student.getEmail(),
                student.getStatus().toString(),
                student.getDateCreated().toString(),
                String.valueOf(student.isActive())
            ))
            .collect(Collectors.toList());
        
        lines.addAll(studentLines);
        
        // Write to file using NIO.2
        Files.write(outputFile, lines, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        
        System.out.println("Exported " + students.size() + " students to: " + outputFile);
        return outputFile;
    }
    
    /**
     * Export courses to CSV
     */
    public Path exportCoursesToCSV() throws IOException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path outputFile = exportPath.resolve("courses_" + timestamp + ".csv");
        
        List<Course> courses = courseService.findAll();
        
        List<String> lines = new ArrayList<>();
        lines.add("Code,Title,Credits,Department,Semester,Instructor,Active");
        
        List<String> courseLines = courses.stream()
            .map(course -> String.join(",",
                course.getCode().getCode(),
                "\"" + course.getTitle() + "\"",
                String.valueOf(course.getCredits()),
                course.getDepartment() != null ? course.getDepartment() : "",
                course.getSemester() != null ? course.getSemester().toString() : "",
                course.getInstructor() != null ? "\"" + course.getInstructor().getName().getFullName() + "\"" : "",
                String.valueOf(course.isActive())
            ))
            .collect(Collectors.toList());
        
        lines.addAll(courseLines);
        
        Files.write(outputFile, lines, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        
        System.out.println("Exported " + courses.size() + " courses to: " + outputFile);
        return outputFile;
    }
    
    /**
     * Export enrollments to CSV
     */
    public Path exportEnrollmentsToCSV() throws IOException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path outputFile = exportPath.resolve("enrollments_" + timestamp + ".csv");
        
        if (enrollmentService instanceof EnrollmentServiceImpl) {
            EnrollmentServiceImpl impl = (EnrollmentServiceImpl) enrollmentService;
            List<Enrollment> enrollments = impl.getAllEnrollments();
            
            List<String> lines = new ArrayList<>();
            lines.add("StudentID,CourseCode,Semester,EnrollmentDate,Grade,Marks,Active");
            
            List<String> enrollmentLines = enrollments.stream()
                .map(enrollment -> String.join(",",
                    enrollment.getStudent().getId(),
                    enrollment.getCourse().getCode().getCode(),
                    enrollment.getSemester().toString(),
                    enrollment.getEnrollmentDate().toString(),
                    enrollment.getGrade() != null ? enrollment.getGrade().getLetter() : "",
                    enrollment.getGrade() != null ? String.valueOf(enrollment.getMarks()) : "",
                    String.valueOf(enrollment.isActive())
                ))
                .collect(Collectors.toList());
            
            lines.addAll(enrollmentLines);
            
            Files.write(outputFile, lines, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            
            System.out.println("Exported " + enrollments.size() + " enrollments to: " + outputFile);
            return outputFile;
        }
        
        throw new UnsupportedOperationException("Enrollment export not supported for this implementation");
    }
    
    /**
     * Export all data (students, courses, enrollments)
     */
    public List<Path> exportAllData() throws IOException {
        List<Path> exportedFiles = new ArrayList<>();
        
        System.out.println("Starting full data export...");
        
        try {
            exportedFiles.add(exportStudentsToCSV());
            exportedFiles.add(exportCoursesToCSV());
            exportedFiles.add(exportEnrollmentsToCSV());
            
            System.out.println("Full data export completed successfully.");
            
        } catch (IOException e) {
            System.err.println("Error during data export: " + e.getMessage());
            throw e;
        }
        
        return exportedFiles;
    }
    
    /**
     * Generate a summary report of current data
     */
    public Path generateSummaryReport() throws IOException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path reportFile = exportPath.resolve("summary_report_" + timestamp + ".txt");
        
        List<String> reportLines = new ArrayList<>();
        reportLines.add("=".repeat(60));
        reportLines.add("CCRM DATA SUMMARY REPORT");
        reportLines.add("Generated: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        reportLines.add("=".repeat(60));
        reportLines.add("");
        
        // Student statistics
        List<Student> students = studentService.findAll();
        reportLines.add("STUDENT STATISTICS:");
        reportLines.add("Total Students: " + students.size());
        reportLines.add("Active Students: " + students.stream().filter(Student::isActive).count());
        
        // Calculate average GPA using streams
        OptionalDouble avgGPA = students.stream()
            .mapToDouble(Student::calculateGPA)
            .filter(gpa -> gpa > 0)
            .average();
        
        if (avgGPA.isPresent()) {
            reportLines.add("Average GPA: " + String.format("%.2f", avgGPA.getAsDouble()));
        }
        reportLines.add("");
        
        // Course statistics
        List<Course> courses = courseService.findAll();
        reportLines.add("COURSE STATISTICS:");
        reportLines.add("Total Courses: " + courses.size());
        reportLines.add("Active Courses: " + courses.stream().filter(Course::isActive).count());
        
        // Group courses by department
        Map<String, Long> coursesByDept = courses.stream()
            .filter(c -> c.getDepartment() != null)
            .collect(Collectors.groupingBy(Course::getDepartment, Collectors.counting()));
        
        reportLines.add("Courses by Department:");
        coursesByDept.forEach((dept, count) -> 
            reportLines.add("  " + dept + ": " + count));
        reportLines.add("");
        
        // Enrollment statistics
        if (enrollmentService instanceof EnrollmentServiceImpl) {
            EnrollmentServiceImpl impl = (EnrollmentServiceImpl) enrollmentService;
            List<Enrollment> enrollments = impl.getAllEnrollments();
            
            reportLines.add("ENROLLMENT STATISTICS:");
            reportLines.add("Total Enrollments: " + enrollments.size());
            reportLines.add("Active Enrollments: " + enrollments.stream().filter(Enrollment::isActive).count());
            reportLines.add("Completed Enrollments: " + enrollments.stream().filter(Enrollment::isCompleted).count());
        }
        
        reportLines.add("");
        reportLines.add("=".repeat(60));
        reportLines.add("End of Report");
        
        Files.write(reportFile, reportLines, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        
        System.out.println("Summary report generated: " + reportFile);
        return reportFile;
    }
}
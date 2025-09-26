# CCRM Usage Guide

## Quick Start Guide

This document provides detailed usage examples for the Campus Course & Records Manager (CCRM) application.

## Starting the Application

### Method 1: Command Line
```bash
cd CCRM
javac -d bin -cp src src/edu/ccrm/CCRMApp.java src/edu/ccrm/*/*.java
java -ea -cp bin edu.ccrm.CCRMApp
```

### Method 2: Eclipse IDE
1. Import project into Eclipse
2. Right-click on `CCRMApp.java` → Run As → Java Application

## Menu Navigation

The application uses a hierarchical menu system:

```
MAIN MENU
├── 1. Student Management
├── 2. Course Management  
├── 3. Enrollment Management
├── 4. Grade Management
├── 5. Reports
├── 6. File Operations (Import/Export)
├── 7. Backup Operations
└── 8. Exit
```

## Feature Walkthrough

### 1. Student Management

#### Adding a Student
```
Main Menu → 1 (Student Management) → 1 (Add Student)

Sample Input:
Student ID: S011
Registration Number: 2023CS005
First Name: Michael
Last Name: Johnson
Email: michael.johnson@university.edu
```

#### Listing Students
```
Main Menu → 1 (Student Management) → 2 (List All Students)

Expected Output:
Student: John Doe [S001] - Reg: 2023CS001, GPA: 8.50
Student: Jane Smith [S002] - Reg: 2023CS002, GPA: 9.20
...
Total active students: 10
```

#### Searching for a Student
```
Main Menu → 1 (Student Management) → 3 (Search Student)

Enter Student ID or Registration Number: S001
or
Enter Student ID or Registration Number: 2023CS001

Output:
Student Details:
ID: S001
Registration No: 2023CS001
Name: John Doe
Email: john.doe@university.edu
Status: ACTIVE
Enrolled Courses: 2
Date Created: 2025-09-26
```

#### Viewing Student Profile & Transcript
```
Main Menu → 1 (Student Management) → 6 (Student Profile & Transcript)

Enter Student ID: S001

Output:
==================================================
STUDENT REPORT
==================================================
[Detailed student information including grades and GPA]
```

### 2. Course Management

#### Adding a Course
```
Main Menu → 2 (Course Management) → 1 (Add Course)

Sample Input:
Course Code: CS401
Course Title: Machine Learning
Credits (1-6): 4
Department: Computer Science
Select Semester:
1. Spring
2. Summer  
3. Fall
Choice: 3
```

#### Listing Courses
```
Main Menu → 2 (Course Management) → 2 (List All Courses)

Expected Output:
CS101: Introduction to Programming (3 credits) - TBA [Fall]
CS201: Data Structures (4 credits) - TBA [Spring]
...
Total active courses: 15
```

#### Searching Courses
```
Main Menu → 2 (Course Management) → 3 (Search Courses)

Options:
1. By Department
2. By Semester
3. By Course Code

Example - Search by Department:
Enter department: Computer Science

Output:
CS101: Introduction to Programming (3 credits) - TBA [Fall]
CS201: Data Structures (4 credits) - TBA [Spring]
CS301: Database Systems (3 credits) - TBA [Fall]
```

### 3. Enrollment Management

#### Enrolling a Student
```
Main Menu → 3 (Enrollment Management) → 1 (Enroll Student in Course)

Sample Input:
Student ID: S001
Course Code: CS201
Select Semester (1-SPRING, 2-SUMMER, 3-FALL): 2

Output:
Student enrolled successfully: John Doe enrolled in CS201 (Summer) - In Progress
```

#### Viewing Enrollments
```
Main Menu → 3 (Enrollment Management) → 3 (View Student Enrollments)

Enter Student ID: S001

Output:
Enrollments for student S001:
John Doe enrolled in CS101 (Fall) - A (9.0)
John Doe enrolled in CS201 (Summer) - In Progress
```

### 4. Grade Management

#### Recording a Grade
```
Main Menu → 4 (Grade Management) → 1 (Record Grade)

Sample Input:
Student ID: S001
Course Code: CS101
Select Semester (1-SPRING, 2-SUMMER, 3-FALL): 3
Enter marks (0-100): 85

Output:
Grade recorded successfully: 85.0 (A (9.0))
```

#### Viewing Student Grades
```
Main Menu → 4 (Grade Management) → 2 (View Student Grades)

Enter Student ID: S001

Output:
Grades for student S001:
CS101 (Fall): 85.0 - A (9.0)
CS201 (Spring): 92.0 - S (10.0)
```

### 5. Reports

#### Student Statistics
```
Main Menu → 5 (Reports) → 1 (Student Statistics)

Output:
=== Student Statistics ===
Total Active Students: 10
Average GPA: 8.45
Students with recorded grades: 8
```

#### Course Statistics  
```
Main Menu → 5 (Reports) → 2 (Course Statistics)

Output:
=== Course Statistics ===
Total Active Courses: 15
Computer Science: 6 courses
Information Technology: 4 courses
Electrical Engineering: 3 courses
Mechanical Engineering: 2 courses
```

#### GPA Distribution
```
Main Menu → 5 (Reports) → 3 (GPA Distribution)

Output:
=== GPA Distribution ===
Excellent (9.0+): 3
Very Good (8.0-8.9): 4
Good (7.0-7.9): 1
Satisfactory (6.0-6.9): 0
Poor (<6.0): 0
```

### 6. File Operations

#### Importing Data
To import students and courses from CSV files:

1. Place CSV files in the project directory
2. Use the file import menu options
3. Follow the CSV format shown in `test-data/` folder

Sample CSV Format for Students:
```csv
ID,RegNo,Name,Email
S001,2023CS001,John Doe,john.doe@university.edu
```

Sample CSV Format for Courses:
```csv
Code,Title,Credits,Department,Semester
CS101,Introduction to Programming,3,Computer Science,FALL
```

#### Exporting Data
The application can export current data to timestamped CSV files:
- Students export: `exports/students_20250926_143022.csv`
- Courses export: `exports/courses_20250926_143022.csv`
- Enrollments export: `exports/enrollments_20250926_143022.csv`

### 7. Backup Operations

#### Creating Backups
Backups create timestamped copies of all exported data:
- Backup location: `backups/backup_20250926_143022/`
- Includes all CSV files and reports
- Shows total backup size

#### Listing Backups
Shows all available backups with sizes:
```
=== Available Backups ===
backup_20250926_143022 - Size: 2.5 KB
backup_20250926_150315 - Size: 3.1 KB
```

## Sample Test Data

The `test-data/` directory contains sample CSV files:

### students.csv
- 10 sample students from different departments
- Includes Computer Science, IT, Electrical, and Mechanical Engineering students
- Proper email format validation

### courses.csv  
- 15 sample courses across multiple departments
- Different credit hours (3-4 credits)
- Distributed across Fall and Spring semesters

## Advanced Features

### Lambda Expressions in Action
The application uses lambda expressions for:
- Sorting students by GPA: `students.sort((s1, s2) -> Double.compare(s2.calculateGPA(), s1.calculateGPA()))`
- Filtering active students: `students.stream().filter(Student::isActive)`
- Custom comparisons and transformations

### Stream API Examples
```java
// Find top students
students.stream()
    .filter(s -> s.calculateGPA() > 8.0)
    .sorted(Comparator.comparing(Student::calculateGPA).reversed())
    .limit(5)
    .forEach(System.out::println);
```

### Exception Handling
The application demonstrates:
- **Checked Exceptions**: `DuplicateEnrollmentException` for enrollment conflicts
- **Unchecked Exceptions**: `MaxCreditLimitExceededException` for credit limits
- **Multi-catch**: Handling multiple exception types
- **Try-with-resources**: Automatic resource management

### Design Patterns
- **Singleton**: `AppConfig` for global configuration
- **Builder**: `Course.Builder` for flexible object creation
- **Factory Methods**: Various utility method factories

## Troubleshooting

### Common Issues

1. **"Student already exists" Error**
   - Solution: Use unique student IDs and registration numbers

2. **"Course not found" Error**  
   - Solution: Ensure course code is entered correctly (case sensitive)

3. **"Credit limit exceeded" Error**
   - Solution: Check current semester credit load (max 20 credits)

4. **File Import Errors**
   - Solution: Verify CSV format matches expected structure
   - Check file permissions and path accessibility

5. **Assertion Errors**
   - Solution: Run with `-ea` flag to enable assertions
   - Check input validation requirements

### Debug Mode
To run in debug mode with detailed output:
```bash
java -ea -verbose:gc -XX:+PrintGCDetails -cp bin edu.ccrm.CCRMApp
```

### Memory Issues
If encountering memory issues with large datasets:
```bash
java -ea -Xmx512m -cp bin edu.ccrm.CCRMApp
```

## Performance Tips

1. **Batch Operations**: Import multiple students/courses at once using CSV
2. **Memory Management**: Clear unused data periodically
3. **Stream Processing**: Use parallel streams for large datasets
4. **File Operations**: Use NIO.2 for better performance

## Best Practices

1. **Data Validation**: Always validate input before processing
2. **Exception Handling**: Use appropriate exception types
3. **Resource Management**: Use try-with-resources for file operations
4. **Immutability**: Prefer immutable objects where possible
5. **Functional Programming**: Use streams and lambdas for data processing

## Sample Session

Here's a complete sample session demonstrating key features:

```
1. Start application
2. Add a few students (S001, S002, S003)
3. Add some courses (CS101, CS201, IT301)
4. Enroll students in courses
5. Record grades for completed courses
6. Generate student reports
7. Export data to CSV
8. Create backup
9. View statistics and reports
10. Exit application
```

This workflow demonstrates all major features and Java concepts implemented in the application.

## Additional Resources

- **Java Documentation**: https://docs.oracle.com/en/java/
- **Stream API Guide**: Focus on data processing examples
- **NIO.2 Tutorial**: File I/O operations
- **Lambda Expressions**: Functional programming concepts

---

For questions or issues, refer to the comprehensive README.md file or examine the source code comments for detailed explanations of each feature.
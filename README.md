# Campus Course & Records Manager (CCRM)

## Project Overview

The Campus Course & Records Manager (CCRM) is a comprehensive console-based Java SE application designed to manage institute operations including student management, course administration, enrollment handling, and academic record keeping. This project demonstrates advanced Java programming concepts including Object-Oriented Programming, design patterns, modern Java features, and comprehensive exception handling.

## How to Run

### Prerequisites
- Java Development Kit (JDK) 11 or higher
- Any IDE (Eclipse, IntelliJ IDEA, or VS Code) or command line

### Running the Application

#### Command Line:
```bash
# Navigate to the project directory
cd CCRM

# Compile all Java files
javac -d bin -cp src src/edu/ccrm/CCRMApp.java src/edu/ccrm/*/*.java

# Run the application
java -cp bin edu.ccrm.CCRMApp

# To enable assertions (recommended for development)
java -ea -cp bin edu.ccrm.CCRMApp
```

#### Using Eclipse IDE:
1. File → New → Java Project
2. Import the source files into the project
3. Right-click on `CCRMApp.java` → Run As → Java Application
4. To enable assertions: Run Configurations → Arguments → VM Arguments: `-ea`

## Evolution of Java

### Java Timeline (Key Milestones)

- **1995**: Java 1.0 - Initial release by Sun Microsystems
- **1997**: Java 1.1 - Inner classes, JDBC, RMI
- **1998**: Java 1.2 (J2SE) - Collections Framework, Swing
- **2000**: Java 1.3 - HotSpot JVM, JNDI
- **2002**: Java 1.4 - Assertions, regex, NIO
- **2004**: Java 5 (1.5) - Generics, annotations, enums, autoboxing
- **2006**: Java 6 - Scripting support, compiler API
- **2011**: Java 7 - Try-with-resources, diamond operator, strings in switch
- **2014**: Java 8 - Lambda expressions, Stream API, default methods
- **2017**: Java 9 - Module system, private interface methods
- **2018**: Java 10 - Local variable type inference (var)
- **2018**: Java 11 LTS - HTTP Client, string methods
- **2019**: Java 12 - Switch expressions (preview)
- **2019**: Java 13 - Text blocks (preview)
- **2020**: Java 14 - Pattern matching (preview), records (preview)
- **2020**: Java 15 - Sealed classes (preview)
- **2021**: Java 17 LTS - Sealed classes, pattern matching for switch
- **2022**: Java 18 - UTF-8 by default
- **2022**: Java 19 - Virtual threads (preview)
- **2023**: Java 20 - Scoped values (preview)
- **2023**: Java 21 LTS - Virtual threads, pattern matching

## Java Editions Comparison

| Feature | Java ME (Micro Edition) | Java SE (Standard Edition) | Java EE (Enterprise Edition) |
|---------|------------------------|----------------------------|------------------------------|
| **Target Platform** | Mobile devices, embedded systems | Desktop applications, servers | Large-scale enterprise applications |
| **Memory Footprint** | Very small (KBs) | Medium (MBs) | Large (hundreds of MBs) |
| **Core Libraries** | Minimal (CLDC, MIDP) | Full Java API | Java SE + Enterprise APIs |
| **Application Types** | Mobile apps, IoT devices | Desktop apps, command-line tools | Web applications, microservices |
| **Key Technologies** | MIDP, CLDC | Collections, I/O, Concurrency | Servlets, JSP, EJB, JPA |
| **Deployment** | JAR/JAD files | JAR files, installers | WAR/EAR files, application servers |
| **Examples** | Feature phones, smart cards | IntelliJ IDEA, NetBeans | Banking systems, e-commerce |

## Java Architecture: JDK, JRE, JVM

### JVM (Java Virtual Machine)
- **Purpose**: Runtime environment that executes Java bytecode
- **Components**: Class loader, execution engine, memory management
- **Platform-specific**: Different implementations for Windows, Linux, macOS
- **Responsibilities**: Memory management, garbage collection, security

### JRE (Java Runtime Environment)
- **Purpose**: Provides runtime environment for Java applications
- **Components**: JVM + Core libraries + Supporting files
- **Contents**: rt.jar, charsets.jar, and other runtime libraries
- **For**: End users who need to run Java applications

### JDK (Java Development Kit)
- **Purpose**: Complete development environment for Java
- **Components**: JRE + Development tools (javac, javadoc, jar, etc.)
- **Contents**: Compiler, debugger, documentation generator
- **For**: Developers who create Java applications

```
┌─────────────────────────────────────┐
│                JDK                  │
│  ┌─────────────────────────────┐    │
│  │             JRE             │    │
│  │  ┌─────────────────────┐    │    │
│  │  │        JVM          │    │    │
│  │  │                     │    │    │
│  │  └─────────────────────┘    │    │
│  │  + Core Libraries           │    │
│  └─────────────────────────────┘    │
│  + Development Tools                │
│  (javac, javadoc, jar, etc.)       │
└─────────────────────────────────────┘
```

## Windows Java Installation Steps

### Step 1: Download JDK
1. Visit [Oracle JDK Downloads](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)
2. Download the Windows x64 installer (e.g., `jdk-17_windows-x64_bin.exe`)

### Step 2: Install JDK
1. Run the downloaded installer as Administrator
2. Follow installation wizard (default location: `C:\Program Files\Java\jdk-17`)
3. Complete installation

### Step 3: Set Environment Variables
1. Open System Properties → Advanced → Environment Variables
2. Add/Edit System Variables:
   - **JAVA_HOME**: `C:\Program Files\Java\jdk-17`
   - **PATH**: Add `%JAVA_HOME%\bin` to existing PATH

### Step 4: Verify Installation
```cmd
# Open Command Prompt and run:
java -version
javac -version
```

**Expected Output:**
```
java version "17.0.x" 2023-xx-xx LTS
Java(TM) SE Runtime Environment (build 17.0.x+xx-LTS-xxx)
Java HotSpot(TM) 64-Bit Server VM (build 17.0.x+xx-LTS-xxx, mixed mode, sharing)
```

### Screenshot Locations
- JDK installation verification: `screenshots/java_version.png`
- Environment variables setup: `screenshots/env_variables.png`

## Eclipse IDE Setup Steps

### Step 1: Download Eclipse
1. Visit [Eclipse Downloads](https://www.eclipse.org/downloads/)
2. Download "Eclipse IDE for Java Developers"

### Step 2: Install and Configure
1. Extract Eclipse to desired location
2. Launch Eclipse and select workspace
3. Configure JDK: Window → Preferences → Java → Installed JREs

### Step 3: Create CCRM Project
1. File → New → Java Project
2. Project name: "CCRM"
3. Use default JRE version
4. Create project structure following package hierarchy

### Step 4: Import Source Files
1. Copy source files to `src` folder maintaining package structure
2. Refresh project (F5)
3. Resolve any compilation errors

### Step 5: Run Configuration
1. Right-click on `CCRMApp.java` → Run As → Java Application
2. For assertions: Run Configurations → Arguments → VM arguments: `-ea`

**Screenshot Locations:**
- Eclipse project creation: `screenshots/eclipse_project_setup.png`
- Run configuration: `screenshots/eclipse_run_config.png`
- Program execution: `screenshots/program_running.png`

## Technical Features Mapping

| Syllabus Topic | Implementation Location | Description |
|---------------|------------------------|-------------|
| **Java Syntax & Structure** | `CCRMApp.java` | Main class with proper package structure |
| **Packages** | `edu.ccrm.*` | Organized package hierarchy |
| **Primitive Variables** | Throughout classes | int, double, boolean usage |
| **Objects & Classes** | `domain/` package | Student, Course, Instructor classes |
| **Operators** | `CCRMApp.java` comments | Arithmetic, relational, logical examples |
| **Decision Structures** | `cli/MainMenu.java` | if-else, nested if, switch statements |
| **Loops** | `cli/MainMenu.java` | while, do-while, for, enhanced-for |
| **Jump Controls** | `cli/MainMenu.java` | break, continue, labeled jumps |
| **Arrays** | `util/ArrayUtils.java` | Array operations, Arrays class usage |
| **Strings** | Throughout | String methods, manipulation |
| **Encapsulation** | All domain classes | Private fields, getters/setters |
| **Inheritance** | `Person` → `Student`/`Instructor` | Abstract class inheritance |
| **Abstraction** | `Person.java` | Abstract class with abstract methods |
| **Polymorphism** | Service interfaces | Virtual method invocation |
| **Access Modifiers** | Throughout | private, protected, public, default |
| **Constructors** | All classes | Constructor chaining, super() calls |
| **Immutability** | `Name.java`, `CourseCode.java` | Final fields, defensive copying |
| **Nested Classes** | `Student.java` | Static nested and inner classes |
| **Interfaces** | `service/` package | Interface definition and implementation |
| **Default Methods** | `CRUDService.java` | Interface evolution |
| **Diamond Problem** | `StudentService.java` | Multiple inheritance resolution |
| **Functional Interfaces** | `util/ComparatorUtils.java` | Lambda expressions, method references |
| **Anonymous Classes** | Various locations | Anonymous inner class usage |
| **Enums** | `Grade.java`, `Semester.java` | Enums with constructors and methods |
| **Upcast/Downcast** | Service implementations | Type casting with instanceof |
| **Method Overriding** | `toString()` methods | Method overriding in domain classes |
| **Method Overloading** | Constructor overloading | Multiple constructor variants |
| **Singleton Pattern** | `config/AppConfig.java` | Enum-based singleton |
| **Builder Pattern** | `Course.java` | Builder pattern implementation |
| **Exception Handling** | Throughout | try-catch-finally, custom exceptions |
| **Custom Exceptions** | `domain/` package | Checked and unchecked exceptions |
| **Assertions** | Service classes | Invariant checking |
| **File I/O (NIO.2)** | `io/` package | Path, Files API usage |
| **Streams API** | Service implementations | Stream operations and pipelines |
| **Date/Time API** | Domain classes | LocalDate, LocalDateTime usage |
| **Recursion** | `io/BackupService.java` | Recursive directory operations |

## Enabling Assertions

Assertions are used throughout the codebase to check invariants and preconditions. To enable them:

### Command Line:
```bash
java -ea -cp bin edu.ccrm.CCRMApp
```

### Eclipse IDE:
1. Run → Run Configurations
2. Select your configuration
3. Arguments tab → VM arguments: `-ea`

### Example Assertions in Code:
```java
// In StudentServiceImpl.java
assert student != null : "Student cannot be null";
assert student.getId() != null : "Student ID cannot be null";

// In CourseServiceImpl.java  
assert course != null : "Course cannot be null";
assert course.getCode() != null : "Course code cannot be null";
```

## Sample Commands and Usage

### Basic Operations:
1. **Add Student**: Use menu option 1 → 1
2. **Add Course**: Use menu option 2 → 1  
3. **Enroll Student**: Use menu option 3 → 1
4. **Record Grade**: Use menu option 4 → 1
5. **Generate Reports**: Use menu option 5

### File Operations:
- Import data from CSV files in `test-data/` directory
- Export current data to timestamped files
- Create backups with recursive directory copying

### Advanced Features:
- Stream-based filtering and reporting
- Lambda expressions for custom sorting
- Functional interfaces for data processing
- Recursive file operations

## Project Structure

```
CCRM/
├── src/
│   └── edu/
│       └── ccrm/
│           ├── CCRMApp.java          # Main application entry point
│           ├── cli/                  # Command-line interface
│           │   └── MainMenu.java
│           ├── config/               # Configuration management
│           │   └── AppConfig.java    # Singleton pattern
│           ├── domain/               # Domain model classes
│           │   ├── Person.java       # Abstract base class
│           │   ├── Student.java      # Student entity with nested classes
│           │   ├── Instructor.java   # Instructor entity
│           │   ├── Course.java       # Course entity with Builder pattern
│           │   ├── Enrollment.java   # Enrollment relationship
│           │   ├── Grade.java        # Grade enum
│           │   ├── Semester.java     # Semester enum
│           │   ├── Name.java         # Immutable value object
│           │   ├── CourseCode.java   # Immutable value object
│           │   ├── DuplicateEnrollmentException.java
│           │   └── MaxCreditLimitExceededException.java
│           ├── service/              # Business logic layer
│           │   ├── CRUDService.java  # Generic interface
│           │   ├── StudentService.java
│           │   ├── StudentServiceImpl.java
│           │   ├── CourseService.java
│           │   ├── CourseServiceImpl.java
│           │   ├── EnrollmentService.java
│           │   └── EnrollmentServiceImpl.java
│           ├── io/                   # File I/O operations
│           │   ├── ImportExportService.java  # NIO.2 based I/O
│           │   └── BackupService.java        # Recursive file operations
│           └── util/                 # Utility classes
│               ├── InputValidator.java       # Validation utilities
│               ├── ComparatorUtils.java     # Functional programming
│               └── ArrayUtils.java          # Array operations
├── test-data/                        # Sample CSV files
├── screenshots/                      # Documentation screenshots
├── README.md                         # This file
└── USAGE.md                         # Usage examples
```

## Design Patterns Used

### 1. Singleton Pattern
- **Class**: `AppConfig`
- **Implementation**: Enum-based thread-safe singleton
- **Purpose**: Global application configuration

### 2. Builder Pattern  
- **Class**: `Course.Builder`
- **Purpose**: Flexible object construction with optional parameters
- **Benefits**: Readable code, immutable objects

### 3. Factory Methods
- **Location**: Various utility classes
- **Purpose**: Create commonly used objects and functions

## Advanced Java Features Demonstrated

### 1. Lambda Expressions & Functional Interfaces
```java
// Method references
students.forEach(System.out::println);

// Lambda expressions
students.stream().filter(s -> s.isActive()).collect(toList());

// Custom functional interfaces
Function<Student, String> formatter = student -> student.getName().getFullName();
```

### 2. Stream API Operations
```java
// Complex stream pipeline
students.stream()
    .filter(Student::isActive)
    .sorted(Comparator.comparing(Student::calculateGPA).reversed())
    .limit(10)
    .map(Student::getName)
    .forEach(System.out::println);
```

### 3. NIO.2 File Operations
```java
// Modern file I/O with streams
try (Stream<String> lines = Files.lines(filePath)) {
    return lines.skip(1)
                .filter(line -> !line.trim().isEmpty())
                .map(line -> line.split(","))
                .collect(Collectors.toList());
}
```

### 4. Date/Time API
```java
// Modern date handling
LocalDate enrollmentDate = LocalDate.now();
String timestamp = LocalDateTime.now()
    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
```

## Error Handling Strategy

### Errors vs Exceptions
- **Errors**: Serious system problems (OutOfMemoryError, StackOverflowError)
  - Generally unrecoverable
  - Should not be caught in normal application code
  
- **Exceptions**: Recoverable conditions that can be handled
  - **Checked**: Must be declared or handled (IOException, ClassNotFoundException)
  - **Unchecked**: Runtime exceptions (IllegalArgumentException, NullPointerException)

### Exception Hierarchy in CCRM
```
Exception (Checked)
├── DuplicateEnrollmentException (Custom checked)
└── IOException (File operations)

RuntimeException (Unchecked)  
├── MaxCreditLimitExceededException (Custom unchecked)
├── IllegalArgumentException (Validation failures)
└── NullPointerException (Null reference errors)
```

### Multi-catch Examples
```java
try {
    // File operations that might fail
    importStudentsFromCSV(filename);
} catch (IOException | SecurityException e) {
    System.err.println("File access error: " + e.getMessage());
} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
    System.err.println("Data format error: " + e.getMessage());
}
```

## Performance Considerations

### Stream vs Loop Performance
- Streams: Better for complex transformations, parallel processing
- Traditional loops: Better for simple iterations, primitive operations

### Memory Management
- Defensive copying for immutable objects
- Proper resource management with try-with-resources
- Avoiding memory leaks in collection references

## Testing and Quality Assurance

### Assertion Usage
```java
// Precondition checking
assert student != null : "Student cannot be null";
assert credits >= 1 && credits <= 6 : "Credits must be between 1 and 6";

// Postcondition checking  
assert result.size() >= 0 : "Result size cannot be negative";
```

### Input Validation
- Email format validation using regex
- Range checking for grades and credits
- Null and empty string validation

## Future Enhancements

### Planned Features
1. **Database Integration**: Replace in-memory storage with database
2. **GUI Interface**: JavaFX or Swing-based user interface
3. **Multi-threading**: Concurrent operations for better performance
4. **Security**: User authentication and authorization
5. **Web Interface**: REST API with Spring Boot
6. **Reporting**: PDF generation for transcripts and reports

### Architectural Improvements
1. **Dependency Injection**: IoC container integration
2. **Configuration Management**: External properties files
3. **Logging**: Structured logging with logback/log4j
4. **Unit Testing**: JUnit and Mockito integration
5. **Build Management**: Maven/Gradle build system

## Troubleshooting

### Common Issues

1. **ClassNotFoundException**: Ensure all packages are properly structured
2. **Assertion Errors**: Run with `-ea` flag to enable assertions
3. **File Access Errors**: Check file permissions and paths
4. **Memory Issues**: Increase heap size with `-Xmx` parameter

### Debug Commands
```bash
# Enable verbose garbage collection
java -verbose:gc -XX:+PrintGCDetails -cp bin edu.ccrm.CCRMApp

# Enable detailed class loading
java -verbose:class -cp bin edu.ccrm.CCRMApp

# Remote debugging
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -cp bin edu.ccrm.CCRMApp
```

## Contributing

This is an academic project demonstrating Java SE concepts. For educational purposes:

1. Each feature demonstrates specific Java concepts
2. Code includes extensive comments explaining design decisions
3. Emphasis on best practices and clean code principles
4. Comprehensive error handling and validation

## Acknowledgments

- Oracle Java Documentation
- Effective Java by Joshua Bloch
- Java: The Complete Reference by Herbert Schildt
- Modern Java in Action by Raoul-Gabriel Urma

## License

This project is created for educational purposes. Feel free to use and modify for learning Java programming concepts.

---

**Author**: [Your Name]  
**Course**: Java Programming  
**Date**: September 2025  
**Version**: 1.0
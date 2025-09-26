package edu.ccrm.util;

import java.util.Arrays;

/**
 * Utility class demonstrating array operations and algorithms
 * Shows classic array manipulation and searching/sorting
 */
public class ArrayUtils {
    
    // Private constructor to prevent instantiation
    private ArrayUtils() {
        throw new AssertionError("Utility class should not be instantiated");
    }
    
    /**
     * Demonstrate various array operations and Arrays utility class usage
     */
    public static void demonstrateArrayOperations() {
        System.out.println("\\n=== Array Operations Demonstration ===");
        
        // Array creation and initialization
        String[] courseNames = {"Computer Science", "Mathematics", "Physics", "Chemistry", "Biology"};
        int[] credits = {3, 4, 3, 3, 2};
        double[] gpas = {8.5, 9.2, 7.8, 8.9, 9.5, 6.7, 7.3};
        
        System.out.println("Original course names: " + Arrays.toString(courseNames));
        System.out.println("Credits array: " + Arrays.toString(credits));
        System.out.println("GPAs array: " + Arrays.toString(gpas));
        
        // Sorting arrays
        String[] sortedCourses = Arrays.copyOf(courseNames, courseNames.length);
        Arrays.sort(sortedCourses);
        System.out.println("Sorted courses: " + Arrays.toString(sortedCourses));
        
        double[] sortedGPAs = Arrays.copyOf(gpas, gpas.length);
        Arrays.sort(sortedGPAs);
        System.out.println("Sorted GPAs: " + Arrays.toString(sortedGPAs));
        
        // Binary search (array must be sorted first)
        int index = Arrays.binarySearch(sortedCourses, "Mathematics");
        System.out.println("Index of 'Mathematics' in sorted array: " + index);
        
        // Fill array with default value
        int[] grades = new int[5];
        Arrays.fill(grades, -1); // -1 indicates no grade assigned
        System.out.println("Initialized grades array: " + Arrays.toString(grades));
        
        // Compare arrays
        int[] credits2 = {3, 4, 3, 3, 2};
        boolean areEqual = Arrays.equals(credits, credits2);
        System.out.println("Are credit arrays equal? " + areEqual);
        
        // Multi-dimensional array operations
        demonstrateMultiDimensionalArrays();
        
        // Array copying
        demonstrateArrayCopying();
        
        // Custom array operations
        demonstrateCustomArrayOperations(gpas);
    }
    
    /**
     * Demonstrate multi-dimensional array operations
     */
    private static void demonstrateMultiDimensionalArrays() {
        System.out.println("\\n--- Multi-dimensional Arrays ---");
        
        // 2D array representing student grades (students x courses)
        double[][] studentGrades = {
            {85.5, 92.0, 78.5}, // Student 1 grades
            {91.0, 87.5, 95.0}, // Student 2 grades
            {76.5, 83.0, 88.5}  // Student 3 grades
        };
        
        System.out.println("Student grades matrix:");
        for (int i = 0; i < studentGrades.length; i++) {
            System.out.println("Student " + (i + 1) + ": " + Arrays.toString(studentGrades[i]));
        }
        
        // Calculate average grade for each student
        System.out.println("\\nAverage grades:");
        for (int i = 0; i < studentGrades.length; i++) {
            double sum = 0;
            for (int j = 0; j < studentGrades[i].length; j++) {
                sum += studentGrades[i][j];
            }
            double average = sum / studentGrades[i].length;
            System.out.println("Student " + (i + 1) + " average: " + String.format("%.2f", average));
        }
        
        // Deep copy demonstration
        double[][] copyOfGrades = new double[studentGrades.length][];
        for (int i = 0; i < studentGrades.length; i++) {
            copyOfGrades[i] = Arrays.copyOf(studentGrades[i], studentGrades[i].length);
        }
        
        System.out.println("Deep copy created successfully");
        System.out.println("Arrays are equal? " + Arrays.deepEquals(studentGrades, copyOfGrades));
    }
    
    /**
     * Demonstrate array copying techniques
     */
    private static void demonstrateArrayCopying() {
        System.out.println("\\n--- Array Copying ---");
        
        int[] original = {1, 2, 3, 4, 5};
        
        // Method 1: Arrays.copyOf
        int[] copy1 = Arrays.copyOf(original, original.length);
        System.out.println("Copy using Arrays.copyOf: " + Arrays.toString(copy1));
        
        // Method 2: Arrays.copyOfRange
        int[] copy2 = Arrays.copyOfRange(original, 1, 4); // Elements 1-3
        System.out.println("Copy using Arrays.copyOfRange(1,4): " + Arrays.toString(copy2));
        
        // Method 3: System.arraycopy
        int[] copy3 = new int[original.length];
        System.arraycopy(original, 0, copy3, 0, original.length);
        System.out.println("Copy using System.arraycopy: " + Arrays.toString(copy3));
        
        // Method 4: Clone
        int[] copy4 = original.clone();
        System.out.println("Copy using clone: " + Arrays.toString(copy4));
        
        // Demonstrate shallow vs deep copy issues (reference types)
        String[] names = {"Alice", "Bob", "Charlie"};
        String[] namesCopy = names.clone(); // Shallow copy
        namesCopy[0] = "ALICE"; // This doesn't affect original because strings are immutable
        
        System.out.println("Original names: " + Arrays.toString(names));
        System.out.println("Modified copy: " + Arrays.toString(namesCopy));
    }
    
    /**
     * Demonstrate custom array operations
     */
    private static void demonstrateCustomArrayOperations(double[] gpas) {
        System.out.println("\\n--- Custom Array Operations ---");
        
        // Find minimum and maximum
        double min = findMin(gpas);
        double max = findMax(gpas);
        System.out.println("Min GPA: " + min + ", Max GPA: " + max);
        
        // Calculate average
        double average = calculateAverage(gpas);
        System.out.println("Average GPA: " + String.format("%.2f", average));
        
        // Find elements above threshold
        double threshold = 8.0;
        double[] aboveThreshold = findElementsAboveThreshold(gpas, threshold);
        System.out.println("GPAs above " + threshold + ": " + Arrays.toString(aboveThreshold));
        
        // Reverse array
        double[] reversed = reverseArray(gpas);
        System.out.println("Reversed GPA array: " + Arrays.toString(reversed));
        
        // Custom sorting demonstration
        String[] studentIds = {"S003", "S001", "S002", "S005", "S004"};
        System.out.println("Student IDs before sorting: " + Arrays.toString(studentIds));
        
        // Sort by numeric part of ID
        Arrays.sort(studentIds, (id1, id2) -> {
            int num1 = Integer.parseInt(id1.substring(1));
            int num2 = Integer.parseInt(id2.substring(1));
            return Integer.compare(num1, num2);
        });
        
        System.out.println("Student IDs after custom sorting: " + Arrays.toString(studentIds));
    }
    
    // Helper methods for custom array operations
    
    /**
     * Find minimum value in array
     */
    public static double findMin(double[] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty");
        }
        
        double min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
        }
        return min;
    }
    
    /**
     * Find maximum value in array
     */
    public static double findMax(double[] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty");
        }
        
        double max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }
    
    /**
     * Calculate average of array elements
     */
    public static double calculateAverage(double[] array) {
        if (array.length == 0) {
            return 0.0;
        }
        
        double sum = 0;
        for (double value : array) {
            sum += value;
        }
        return sum / array.length;
    }
    
    /**
     * Find elements above threshold
     */
    public static double[] findElementsAboveThreshold(double[] array, double threshold) {
        // First pass: count elements above threshold
        int count = 0;
        for (double value : array) {
            if (value > threshold) {
                count++;
            }
        }
        
        // Second pass: collect elements
        double[] result = new double[count];
        int index = 0;
        for (double value : array) {
            if (value > threshold) {
                result[index++] = value;
            }
        }
        
        return result;
    }
    
    /**
     * Reverse array elements
     */
    public static double[] reverseArray(double[] array) {
        double[] reversed = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            reversed[i] = array[array.length - 1 - i];
        }
        return reversed;
    }
    
    /**
     * Linear search in array
     */
    public static int linearSearch(String[] array, String target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(target)) {
                return i;
            }
        }
        return -1; // Not found
    }
    
    /**
     * Check if array is sorted (ascending)
     */
    public static boolean isSorted(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) {
                return false;
            }
        }
        return true;
    }
}
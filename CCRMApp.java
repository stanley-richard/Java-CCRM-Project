package edu.ccrm;

import edu.ccrm.cli.MainMenu;
import edu.ccrm.config.AppConfig;

/**
 * Campus Course & Records Manager (CCRM) - Main Application Entry Point
 * 
 * This is a comprehensive Java SE application demonstrating:
 * - Object-Oriented Programming (Encapsulation, Inheritance, Abstraction, Polymorphism)
 * - Advanced Java features (Streams, NIO.2, Date/Time API, Lambdas, etc.)
 * - Design Patterns (Singleton, Builder)
 * - Exception Handling and File I/O
 * 
 * @author Your Name
 * @version 1.0
 */
public class CCRMApp {
    
    public static void main(String[] args) {
        // Demonstrate operator precedence in comments
        /* 
         * Operator Precedence Example:
         * int result = 5 + 3 * 2; // equals 11, not 16 (multiplication has higher precedence)
         * int result2 = (5 + 3) * 2; // equals 16 (parentheses override precedence)
         */
        
        System.out.println("=".repeat(60));
        System.out.println("    Campus Course & Records Manager (CCRM)");
        System.out.println("=".repeat(60));
        
        // Print Java platform information
        printPlatformInfo();
        
        try {
            // Initialize application configuration (Singleton pattern)
            AppConfig config = AppConfig.getInstance();
            
            // Start the CLI menu system
            MainMenu menu = new MainMenu();
            menu.start();
            
        } catch (Exception e) {
            System.err.println("Fatal error starting CCRM: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        
        System.out.println("\nThank you for using CCRM!");
    }
    
    /**
     * Print platform information demonstrating Java SE vs ME vs EE knowledge
     */
    private static void printPlatformInfo() {
        System.out.println("\n--- Java Platform Information ---");
        System.out.println("Java Version: " + System.getProperty("java.version"));
        System.out.println("Java Vendor: " + System.getProperty("java.vendor"));
        System.out.println("OS: " + System.getProperty("os.name"));
        
        System.out.println("\nThis application runs on Java SE (Standard Edition):");
        System.out.println("• Java SE: Desktop/server applications with full JDK features");
        System.out.println("• Java ME: Mobile/embedded devices with limited resources");
        System.out.println("• Java EE: Enterprise web applications with server-side components");
        System.out.println();
    }
}
package edu.ccrm.config;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Application configuration using Singleton pattern
 * Demonstrates thread-safe singleton implementation
 */
public class AppConfig {
    
    // Thread-safe singleton using enum
    private static enum SingletonHelper {
        INSTANCE;
        
        private final AppConfig appConfig = new AppConfig();
    }
    
    // Configuration properties
    private String applicationName = "Campus Course & Records Manager";
    private String version = "1.0";
    private Path dataDirectory = Paths.get("data");
    private Path backupDirectory = Paths.get("backups");
    private Path exportDirectory = Paths.get("exports");
    private int maxCreditsPerSemester = 20;
    private String dateFormat = "yyyy-MM-dd";
    
    // Private constructor for singleton
    private AppConfig() {
        // Initialize configuration
        loadConfiguration();
    }
    
    // Public method to get singleton instance
    public static AppConfig getInstance() {
        return SingletonHelper.INSTANCE.appConfig;
    }
    
    private void loadConfiguration() {
        // In a real application, this would load from properties file
        // For now, using default values
        System.out.println("Loading application configuration...");
    }
    
    // Getters and setters
    public String getApplicationName() {
        return applicationName;
    }
    
    public String getVersion() {
        return version;
    }
    
    public Path getDataDirectory() {
        return dataDirectory;
    }
    
    public void setDataDirectory(Path dataDirectory) {
        this.dataDirectory = dataDirectory;
    }
    
    public Path getBackupDirectory() {
        return backupDirectory;
    }
    
    public void setBackupDirectory(Path backupDirectory) {
        this.backupDirectory = backupDirectory;
    }
    
    public Path getExportDirectory() {
        return exportDirectory;
    }
    
    public void setExportDirectory(Path exportDirectory) {
        this.exportDirectory = exportDirectory;
    }
    
    public int getMaxCreditsPerSemester() {
        return maxCreditsPerSemester;
    }
    
    public void setMaxCreditsPerSemester(int maxCreditsPerSemester) {
        this.maxCreditsPerSemester = maxCreditsPerSemester;
    }
    
    public String getDateFormat() {
        return dateFormat;
    }
    
    public void printConfiguration() {
        System.out.println("=== Application Configuration ===");
        System.out.println("Application: " + applicationName);
        System.out.println("Version: " + version);
        System.out.println("Data Directory: " + dataDirectory);
        System.out.println("Backup Directory: " + backupDirectory);
        System.out.println("Export Directory: " + exportDirectory);
        System.out.println("Max Credits/Semester: " + maxCreditsPerSemester);
        System.out.println("Date Format: " + dateFormat);
        System.out.println("=".repeat(35));
    }
}
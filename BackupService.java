package edu.ccrm.io;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

/**
 * Service for backup operations using NIO.2 and recursive utilities
 * Demonstrates file operations, recursion, and directory traversal
 */
public class BackupService {
    
    private final Path backupRootPath;
    private final Path dataPath;
    
    public BackupService() {
        this.backupRootPath = Paths.get("backups");
        this.dataPath = Paths.get("exports");
        
        try {
            Files.createDirectories(backupRootPath);
            Files.createDirectories(dataPath);
        } catch (IOException e) {
            System.err.println("Failed to create backup directories: " + e.getMessage());
        }
    }
    
    /**
     * Create a timestamped backup of all export files
     */
    public Path createBackup() throws IOException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path backupDir = backupRootPath.resolve("backup_" + timestamp);
        
        // Create backup directory
        Files.createDirectories(backupDir);
        
        // Copy all files from exports directory to backup directory
        if (Files.exists(dataPath)) {
            copyDirectoryRecursively(dataPath, backupDir);
        }
        
        System.out.println("Backup created successfully: " + backupDir);
        System.out.println("Backup size: " + calculateDirectorySizeRecursively(backupDir) + " bytes");
        
        return backupDir;
    }
    
    /**
     * Recursively copy directory contents
     * Demonstrates recursive file operations and NIO.2 copy methods
     */
    private void copyDirectoryRecursively(Path source, Path target) throws IOException {
        try (Stream<Path> stream = Files.walk(source)) {
            stream.forEach(sourcePath -> {
                try {
                    Path targetPath = target.resolve(source.relativize(sourcePath));
                    
                    if (Files.isDirectory(sourcePath)) {
                        Files.createDirectories(targetPath);
                    } else {
                        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    System.err.println("Error copying file: " + sourcePath + " - " + e.getMessage());
                }
            });
        }
    }
    
    /**
     * Calculate total size of directory recursively
     * Demonstrates recursion and stream operations
     */
    public long calculateDirectorySizeRecursively(Path directory) {
        if (!Files.exists(directory) || !Files.isDirectory(directory)) {
            return 0;
        }
        
        try (Stream<Path> stream = Files.walk(directory)) {
            return stream
                .filter(Files::isRegularFile)
                .mapToLong(path -> {
                    try {
                        return Files.size(path);
                    } catch (IOException e) {
                        System.err.println("Error getting file size: " + path + " - " + e.getMessage());
                        return 0;
                    }
                })
                .sum();
        } catch (IOException e) {
            System.err.println("Error calculating directory size: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * List all backup directories
     */
    public void listBackups() throws IOException {
        if (!Files.exists(backupRootPath)) {
            System.out.println("No backups directory found.");
            return;
        }
        
        try (Stream<Path> stream = Files.list(backupRootPath)) {
            System.out.println("\\n=== Available Backups ===");
            
            stream.filter(Files::isDirectory)
                  .sorted()
                  .forEach(backup -> {
                      try {
                          long size = calculateDirectorySizeRecursively(backup);
                          String sizeStr = formatFileSize(size);
                          System.out.println(backup.getFileName() + " - Size: " + sizeStr);
                      } catch (Exception e) {
                          System.out.println(backup.getFileName() + " - Size: Unknown");
                      }
                  });
        }
    }
    
    /**
     * Recursively list files in a directory with depth indication
     * Demonstrates recursive directory traversal
     */
    public void listFilesRecursively(Path directory, int maxDepth) throws IOException {
        if (!Files.exists(directory)) {
            System.out.println("Directory not found: " + directory);
            return;
        }
        
        System.out.println("\\nFiles in " + directory + ":");
        listFilesRecursivelyHelper(directory, 0, maxDepth);
    }
    
    /**
     * Recursive helper method for listing files
     */
    private void listFilesRecursivelyHelper(Path directory, int currentDepth, int maxDepth) {
        if (currentDepth > maxDepth) {
            return;
        }
        
        try (Stream<Path> stream = Files.list(directory)) {
            stream.sorted().forEach(path -> {
                String indent = "  ".repeat(currentDepth);
                
                if (Files.isDirectory(path)) {
                    System.out.println(indent + "[DIR]  " + path.getFileName());
                    try {
                        listFilesRecursivelyHelper(path, currentDepth + 1, maxDepth);
                    } catch (Exception e) {
                        System.out.println(indent + "  [ERROR accessing directory]");
                    }
                } else {
                    try {
                        long size = Files.size(path);
                        System.out.println(indent + "[FILE] " + path.getFileName() + 
                                         " (" + formatFileSize(size) + ")");
                    } catch (IOException e) {
                        System.out.println(indent + "[FILE] " + path.getFileName() + " (size unknown)");
                    }
                }
            });
        } catch (IOException e) {
            System.err.println("Error listing directory: " + directory + " - " + e.getMessage());
        }
    }
    
    /**
     * Delete old backups (keep only the most recent N backups)
     */
    public void cleanupOldBackups(int keepCount) throws IOException {
        if (!Files.exists(backupRootPath)) {
            return;
        }
        
        try (Stream<Path> stream = Files.list(backupRootPath)) {
            // Get all backup directories sorted by name (which includes timestamp)
            Path[] backups = stream
                .filter(Files::isDirectory)
                .filter(path -> path.getFileName().toString().startsWith("backup_"))
                .sorted()
                .toArray(Path[]::new);
            
            if (backups.length <= keepCount) {
                System.out.println("No old backups to clean up.");
                return;
            }
            
            // Delete oldest backups
            int deleteCount = backups.length - keepCount;
            for (int i = 0; i < deleteCount; i++) {
                deleteDirectoryRecursively(backups[i]);
                System.out.println("Deleted old backup: " + backups[i].getFileName());
            }
            
            System.out.println("Cleanup completed. Deleted " + deleteCount + " old backups.");
        }
    }
    
    /**
     * Recursively delete a directory and its contents
     */
    private void deleteDirectoryRecursively(Path directory) throws IOException {
        try (Stream<Path> stream = Files.walk(directory)) {
            stream.sorted((path1, path2) -> path2.compareTo(path1)) // Delete deepest first
                  .forEach(path -> {
                      try {
                          Files.delete(path);
                      } catch (IOException e) {
                          System.err.println("Error deleting: " + path + " - " + e.getMessage());
                      }
                  });
        }
    }
    
    /**
     * Format file size in human-readable format
     */
    private String formatFileSize(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        } else if (bytes < 1024 * 1024) {
            return String.format("%.1f KB", bytes / 1024.0);
        } else if (bytes < 1024 * 1024 * 1024) {
            return String.format("%.1f MB", bytes / (1024.0 * 1024.0));
        } else {
            return String.format("%.1f GB", bytes / (1024.0 * 1024.0 * 1024.0));
        }
    }
    
    /**
     * Get backup statistics
     */
    public void printBackupStatistics() throws IOException {
        if (!Files.exists(backupRootPath)) {
            System.out.println("No backup directory found.");
            return;
        }
        
        try (Stream<Path> stream = Files.list(backupRootPath)) {
            long backupCount = stream.filter(Files::isDirectory).count();
            long totalSize = calculateDirectorySizeRecursively(backupRootPath);
            
            System.out.println("\\n=== Backup Statistics ===");
            System.out.println("Total Backups: " + backupCount);
            System.out.println("Total Size: " + formatFileSize(totalSize));
            System.out.println("Backup Location: " + backupRootPath.toAbsolutePath());
        }
    }
}
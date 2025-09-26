#!/bin/bash
# CCRM Compilation and Execution Script for Linux/Mac
# This script compiles and runs the Campus Course & Records Manager application

echo "==============================================="
echo "Campus Course & Records Manager (CCRM)"
echo "Compilation and Execution Script"
echo "==============================================="

# Check if Java is installed
echo "Checking Java installation..."
if ! command -v java &> /dev/null; then
    echo "ERROR: Java is not installed or not in PATH"
    echo "Please install JDK and set JAVA_HOME environment variable"
    exit 1
fi

if ! command -v javac &> /dev/null; then
    echo "ERROR: Java compiler (javac) is not found"
    echo "Please install JDK (not just JRE)"
    exit 1
fi

echo "Java installation verified successfully!"
echo ""

# Create bin directory if it doesn't exist
if [ ! -d "bin" ]; then
    echo "Creating bin directory..."
    mkdir -p bin
fi

# Create output directories
mkdir -p exports backups

echo "Compiling Java source files..."
echo ""

# Find and compile all Java files
find src -name "*.java" -print0 | xargs -0 javac -d bin -cp src

if [ $? -ne 0 ]; then
    echo "ERROR: Compilation failed!"
    echo "Please check the source code for syntax errors."
    exit 1
fi

echo "Compilation successful!"
echo ""

echo "Starting CCRM application with assertions enabled..."
echo ""
echo "==============================================="
echo ""

# Run the application with assertions enabled
java -ea -cp bin edu.ccrm.CCRMApp

echo ""
echo "==============================================="
echo "CCRM application has terminated."
echo "Thank you for using Campus Course & Records Manager!"
echo "==============================================="
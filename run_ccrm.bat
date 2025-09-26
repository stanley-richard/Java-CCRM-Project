@echo off
REM CCRM Compilation and Execution Script for Windows
REM This script compiles and runs the Campus Course & Records Manager application

echo ===============================================
echo Campus Course ^& Records Manager (CCRM)
echo Compilation and Execution Script
echo ===============================================

REM Check if Java is installed
echo Checking Java installation...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install JDK and set JAVA_HOME environment variable
    pause
    exit /b 1
)

javac -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java compiler (javac) is not found
    echo Please install JDK (not just JRE)
    pause
    exit /b 1
)

echo Java installation verified successfully!
echo.

REM Create bin directory if it doesn't exist
if not exist "bin" (
    echo Creating bin directory...
    mkdir bin
)

REM Create output directories
if not exist "exports" mkdir exports
if not exist "backups" mkdir backups

echo Compiling Java source files...
echo.

REM Compile all Java files
javac -d bin -cp src src\edu\ccrm\CCRMApp.java src\edu\ccrm\cli\*.java src\edu\ccrm\config\*.java src\edu\ccrm\domain\*.java src\edu\ccrm\service\*.java src\edu\ccrm\io\*.java src\edu\ccrm\util\*.java

if %errorlevel% neq 0 (
    echo ERROR: Compilation failed!
    echo Please check the source code for syntax errors.
    pause
    exit /b 1
)

echo Compilation successful!
echo.

echo Starting CCRM application with assertions enabled...
echo.
echo ===============================================
echo

REM Run the application with assertions enabled
java -ea -cp bin edu.ccrm.CCRMApp

echo.
echo ===============================================
echo CCRM application has terminated.
echo Thank you for using Campus Course ^& Records Manager!
echo ===============================================
pause
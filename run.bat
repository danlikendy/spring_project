@echo off
setlocal enabledelayedexpansion

echo Searching for Java installation...

if defined JAVA_HOME (
    echo Using JAVA_HOME: %JAVA_HOME%
    goto run_maven
)

where java >nul 2>&1
if %errorlevel% equ 0 (
    for /f "tokens=*" %%i in ('where java') do (
        set JAVA_PATH=%%i
        goto found_java
    )
)

where javac >nul 2>&1
if %errorlevel% equ 0 (
    for /f "tokens=*" %%i in ('where javac') do (
        set JAVA_PATH=%%i
        goto found_java
    )
)

echo Java not found in PATH. Searching common locations...

if exist "C:\Program Files\Microsoft\jdk-17.0.17.10-hotspot\bin\java.exe" (
    set JAVA_HOME=C:\Program Files\Microsoft\jdk-17.0.17.10-hotspot
    goto found_java
)

if exist "C:\Program Files\Java\jdk-17\bin\java.exe" (
    set JAVA_HOME=C:\Program Files\Java\jdk-17
    goto found_java
)

if exist "C:\Program Files\Java\jdk-21\bin\java.exe" (
    set JAVA_HOME=C:\Program Files\Java\jdk-21
    goto found_java
)

if exist "C:\Program Files\Eclipse Adoptium\jdk-17.0.0.37-hotspot\bin\java.exe" (
    for /d %%d in ("C:\Program Files\Eclipse Adoptium\jdk-17*") do (
        set JAVA_HOME=%%d
        goto found_java
    )
)

if exist "C:\Program Files\Eclipse Adoptium\jdk-21.0.0.37-hotspot\bin\java.exe" (
    for /d %%d in ("C:\Program Files\Eclipse Adoptium\jdk-21*") do (
        set JAVA_HOME=%%d
        goto found_java
    )
)

echo ERROR: Java not found. Please install Java 17 or higher.
echo Download from: https://adoptium.net/
pause
exit /b 1

:found_java
if not defined JAVA_HOME (
    for %%F in ("%JAVA_PATH%") do (
        set "JAVA_DIR=%%~dpF"
        set "JAVA_DIR=!JAVA_DIR:~0,-1!"
        for %%P in ("!JAVA_DIR!") do set JAVA_HOME=%%~dpP
        set "JAVA_HOME=!JAVA_HOME:~0,-1!"
    )
)
echo Found Java at: %JAVA_HOME%
echo.

:run_maven
echo Starting Spring Boot application...
echo.

if exist "maven\apache-maven-3.9.5\bin\mvn.cmd" (
    call maven\apache-maven-3.9.5\bin\mvn.cmd spring-boot:run
) else (
    mvn spring-boot:run
)

if %errorlevel% neq 0 (
    echo.
    echo ERROR: Failed to start application.
    echo Make sure Maven is installed and in PATH.
    pause
)

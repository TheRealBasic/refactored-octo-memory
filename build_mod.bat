@echo off
setlocal

set SCRIPT_DIR=%~dp0
set GRADLEW=%SCRIPT_DIR%gradlew.bat
set WRAPPER_JAR=%SCRIPT_DIR%gradle\wrapper\gradle-wrapper.jar

echo [INFO] Checking for Java 21 (required for Minecraft 1.21.11)...
java -version >NUL 2>&1
if errorlevel 1 (
    echo [ERROR] Java runtime not found. Please install Java 21 or newer and try again.
    exit /b 1
)

if not exist "%GRADLEW%" (
    echo [ERROR] gradlew.bat not found. Please ensure the repository contains the Gradle wrapper.
    exit /b 1
)

if not exist "%WRAPPER_JAR%" (
    echo [INFO] gradle-wrapper.jar not found. Attempting to regenerate with a locally installed Gradle...
    where gradle >NUL 2>&1
    if errorlevel 1 (
        echo [ERROR] Gradle executable not found. Install Gradle 8.12.1 or manually restore gradle-wrapper.jar.
        exit /b 1
    )
    gradle wrapper --gradle-version 8.12.1 --distribution-type bin
    if errorlevel 1 (
        echo [ERROR] Failed to regenerate gradle-wrapper.jar. Please run \"gradle wrapper --gradle-version 8.12.1\" manually.
        exit /b 1
    )
)

pushd "%SCRIPT_DIR%"
echo [INFO] Building mod for Minecraft 1.21.11 / Forge 61.0.3...
"%GRADLEW%" --no-daemon --refresh-dependencies build
set EXITCODE=%ERRORLEVEL%
if %EXITCODE% neq 0 (
    echo [ERROR] Build failed with exit code %EXITCODE%.
) else (
    echo [SUCCESS] Build complete. JAR should be in build\libs.
)
popd
exit /b %EXITCODE%

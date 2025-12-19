@echo off
setlocal enabledelayedexpansion
set SCRIPT_DIR=%~dp0
set TOOLS_DIR=%SCRIPT_DIR%tools
set GRADLE_VERSION=2.3
set GRADLE_DIR=%TOOLS_DIR%\gradle-%GRADLE_VERSION%
set GRADLE_ZIP=%TOOLS_DIR%\gradle-%GRADLE_VERSION%-bin.zip
set GRADLE_URL=https://services.gradle.org/distributions/gradle-%GRADLE_VERSION%-bin.zip
set GRADLE_CMD=

if not defined JAVA_HOME (
    echo [INFO] JAVA_HOME is not set. Trying system java...
) else (
    echo [INFO] JAVA_HOME is set to %JAVA_HOME%
)
java -version >NUL 2>&1
if errorlevel 1 (
    echo [ERROR] Java runtime not found. Please install Java 8 (required for Forge 1.7.10) and re-run this script.
    exit /b 1
)

if exist "%GRADLE_DIR%\bin\gradle.bat" (
    set GRADLE_CMD=%GRADLE_DIR%\bin\gradle.bat
)

if not defined GRADLE_CMD (
    where gradle >NUL 2>&1
    if not errorlevel 1 (
        for /f "usebackq delims=" %%g in (`where gradle`) do (
            set GRADLE_CMD=%%g
            goto :foundGradle
        )
    )
)

:foundGradle
if defined GRADLE_CMD (
    echo [INFO] Using existing Gradle: %GRADLE_CMD%
    goto :runGradle
)

echo [INFO] Gradle not found. Downloading portable Gradle %GRADLE_VERSION%...
if not exist "%TOOLS_DIR%" mkdir "%TOOLS_DIR%"
if exist "%GRADLE_ZIP%" del /f /q "%GRADLE_ZIP%"

powershell -NoProfile -Command "\
    [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; \
    $ErrorActionPreference = 'Stop'; \
    Write-Host 'Downloading %GRADLE_URL%'; \
    Invoke-WebRequest -Uri '%GRADLE_URL%' -OutFile '%GRADLE_ZIP%'; \
    Write-Host 'Extracting...'; \
    Expand-Archive -Path '%GRADLE_ZIP%' -DestinationPath '%TOOLS_DIR%' -Force; \
" || (
    echo [ERROR] Failed to download or extract Gradle. Please install Gradle manually or check your network connection.
    exit /b 1
)
set GRADLE_CMD=%GRADLE_DIR%\bin\gradle.bat

:runGradle
pushd "%SCRIPT_DIR%"
echo [INFO] Running setupDecompWorkspace (first-time setup may take a while)...
"%GRADLE_CMD%" setupDecompWorkspace --refresh-dependencies
if errorlevel 1 (
    echo [ERROR] setupDecompWorkspace failed.
    popd
    exit /b 1
)

echo [INFO] Building mod...
"%GRADLE_CMD%" build
set EXITCODE=%ERRORLEVEL%
if %EXITCODE% neq 0 (
    echo [ERROR] Build failed with exit code %EXITCODE%.
) else (
    echo [SUCCESS] Build complete. JAR should be in build\libs.
)
popd
exit /b %EXITCODE%

# Creative Test Mod

This project now targets **Minecraft 1.21.11** with **Forge 61.0.3**. It keeps the original creative showcase vibe—a tiny mod shell ready for adding the glowing apple rewards and other playful touches.

## Getting started

1. Install **JDK 21** (Minecraft 1.21.x ships Java 21 to end users).
2. From the repository root, let the Gradle wrapper download dependencies and build the mod:
   ```bash
   ./gradlew build
   ```
   The resulting JAR will be under `build/libs`.
   - If `gradle/wrapper/gradle-wrapper.jar` is missing (kept out of version control), regenerate it with a locally installed Gradle: `gradle wrapper --gradle-version 8.12.1 --distribution-type bin`.
3. Optional development runs:
   - Client: `./gradlew runClient`
   - Dedicated server: `./gradlew runServer`
   - Data generation: `./gradlew runData`

Gradle caches are local to your machine; the first build will take longer while Forge and mappings download.

## Windows quick build

Use the bundled wrapper via the helper script:

```bat
build_mod.bat
```

The script checks for Java, then runs `gradlew.bat --no-daemon --refresh-dependencies build`, placing the built JAR in `build\libs`.

## Project notes

- Build configuration uses ForgeGradle 6 and official 1.21.11 mappings.
- Mod metadata lives in `src/main/resources/META-INF/mods.toml`.
- The mod entry point is `com.example.creativetestmod.CreativeTestMod` (see `src/main/java`).

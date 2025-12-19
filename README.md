# Creative Test Mod

A tiny Minecraft Forge 1.7.10 mod that adds a glowing apple and a couple of playful rewards:

- Craft the **Glowing Apple** with an apple and glowstone dust for night vision.
- Eating the apple grants temporary speed and jump boosts and a cheerful chime.
- Carrying one while fighting occasionally yields extra glowstone dust from defeated foes.

## Getting started

This project targets Forge **10.13.4.1614** for Minecraft **1.7.10** and uses the ForgeGradle 1.x plugin.

1. Install **JDK 8** (Forge 1.7.10 requires Java 8).
2. Install **Gradle 2.x** or newer locally (the Gradle wrapper JAR is not bundled because the environment is offline).
3. Run the usual ForgeGradle setup tasks from the repository root:
   ```bash
   gradle setupDecompWorkspace
   gradle eclipse   # or gradle idea
   gradle build
   ```
4. Drop the built JAR from `build/libs/creative-test-mod-1.0.0.jar` into your mods folder.

Feel free to tweak the item stats, drop chances, or add textures under `src/main/resources/assets/creative_test_mod/`.

## Windows quick build

Run `build_mod.bat` from a Developer Command Prompt (or PowerShell) to handle setup and build in one step:

```bat
build_mod.bat
```

The script will:
- verify Java is available (Forge 1.7.10 needs Java 8)
- use an existing Gradle installation if present, or download a portable Gradle 2.3 locally under `tools/`
- run `setupDecompWorkspace` (first run only) and then `build`
- place the resulting JAR in `build\libs`

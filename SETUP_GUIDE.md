# ABG Insights - Complete Setup Guide

## 📋 Table of Contents
1. [Prerequisites](#prerequisites)
2. [Environment Setup](#environment-setup)
3. [Gemini API Configuration](#gemini-api-configuration)
4. [Building the Application](#building-the-application)
5. [Testing](#testing)
6. [Deployment](#deployment)
7. [Troubleshooting](#troubleshooting)

---

## Prerequisites

### Required Software

1. **Android Studio**
   - Version: Hedgehog (2023.1.1) or later
   - Download: https://developer.android.com/studio
   - Components needed:
     - Android SDK Platform 34
     - Android SDK Build-Tools 34
     - Android Emulator (for testing)

2. **JDK (Java Development Kit)**
   - Version: JDK 17 or later
   - Usually bundled with Android Studio
   - Verify: `java -version` in terminal

3. **Git** (optional, for version control)
   - Download: https://git-scm.com/
   - For cloning and version management

### Hardware Requirements

- **For Development**:
  - 8 GB RAM minimum (16 GB recommended)
  - 8 GB free disk space
  - Intel i5/Ryzen 5 or better

- **For Testing**:
  - Android device with Android 7.0+ OR
  - Android emulator (requires hardware virtualization)

---

## Environment Setup

### Step 1: Install Android Studio

1. Download Android Studio from official website
2. Run the installer
3. Follow setup wizard:
   - Choose "Standard" installation
   - Accept license agreements
   - Wait for component downloads

### Step 2: Configure Android SDK

1. Open Android Studio
2. Go to: **Tools > SDK Manager**
3. In "SDK Platforms" tab, ensure these are checked:
   - ✅ Android 14.0 (API 34) - Target SDK
   - ✅ Android 7.0 (API 24) - Minimum SDK
4. In "SDK Tools" tab, ensure these are checked:
   - ✅ Android SDK Build-Tools 34
   - ✅ Android SDK Platform-Tools
   - ✅ Android Emulator
   - ✅ Google Play services
5. Click "Apply" and wait for downloads

### Step 3: Set Up Emulator (Optional)

1. Go to: **Tools > Device Manager**
2. Click "Create Device"
3. Select device (e.g., Pixel 6)
4. Select system image: **Android 14 (API 34)**
5. Download system image if needed
6. Finish setup
7. Test by clicking "Play" button

---

## Gemini API Configuration

### Step 1: Get Your FREE API Key

1. **Visit Google AI Studio**
   - URL: https://makersuite.google.com/app/apikey
   - OR: https://aistudio.google.com/app/apikey

2. **Sign In**
   - Use your Google account
   - No payment method required for free tier

3. **Create API Key**
   - Click "Create API Key" button
   - Choose "Create API key in new project" (recommended)
   - Copy the generated key (starts with "AI...")
   - **IMPORTANT**: Save this key securely!

### Step 2: Add API Key to Application

1. **Open Project in Android Studio**
   - File > Open
   - Navigate to ABG-android-app folder
   - Click OK

2. **Locate GeminiService.kt**
   ```
   app/src/main/java/com/medical/abginsights/ai/GeminiService.kt
   ```

3. **Replace API Key**
   - Find line 18:
   ```kotlin
   private const val API_KEY = "YOUR_API_KEY_HERE"
   ```
   - Replace with your actual key:
   ```kotlin
   private const val API_KEY = "AIza...your-actual-key-here"
   ```

4. **Save the File** (Ctrl+S / Cmd+S)

### Step 3: Verify API Key Works

1. Build and run the application
2. Enter test ABG values
3. Click "Analyze ABG"
4. If you see results, API key is working! ✅
5. If you see errors, check:
   - API key copied correctly
   - No extra spaces
   - Internet connection available

### Alternative: Use BuildConfig (Recommended for Production)

For better security, store API key in local.properties:

1. **Create/Edit local.properties** (in project root):
   ```properties
   GEMINI_API_KEY=AIza...your-actual-key-here
   ```

2. **Update app/build.gradle.kts**:
   ```kotlin
   android {
       defaultConfig {
           // Load API key from local.properties
           val properties = Properties()
           properties.load(project.rootProject.file("local.properties").inputStream())
           buildConfigField("String", "GEMINI_API_KEY", "\"${properties.getProperty("GEMINI_API_KEY")}\"")
       }
       buildFeatures {
           buildConfig = true
       }
   }
   ```

3. **Update GeminiService.kt**:
   ```kotlin
   private const val API_KEY = BuildConfig.GEMINI_API_KEY
   ```

4. **Add to .gitignore**:
   ```
   local.properties
   ```

---

## Building the Application

### Step 1: Open Project

1. Launch Android Studio
2. Click "Open"
3. Navigate to project folder
4. Wait for Gradle sync to complete

### Step 2: Sync Dependencies

1. **Automatic Sync**:
   - Android Studio usually syncs automatically
   - Wait for "Sync successful" message

2. **Manual Sync** (if needed):
   - File > Sync Project with Gradle Files
   - OR click sync icon in toolbar

### Step 3: Build APK

#### For Testing:

1. **Build Debug APK**:
   - Build > Build Bundle(s) / APK(s) > Build APK(s)
   - Wait for build to complete
   - Click "locate" to find APK file
   - APK location: `app/build/outputs/apk/debug/app-debug.apk`

#### For Release:

1. **Generate Signing Key**:
   - Build > Generate Signed Bundle / APK
   - Select "APK"
   - Click "Create new..."
   - Fill in details:
     - Key store path: Choose location
     - Password: Create strong password
     - Alias: abg-insights-key
     - Validity: 25 years
     - Certificate info: Your details
   - Click OK

2. **Build Signed APK**:
   - Select your keystore file
   - Enter passwords
   - Choose "release" build variant
   - Click Finish
   - APK location: `app/release/app-release.apk`

### Step 4: Install on Device

#### Via USB:

1. Enable Developer Options on Android device:
   - Settings > About Phone
   - Tap "Build Number" 7 times
2. Enable USB Debugging:
   - Settings > Developer Options
   - Toggle "USB Debugging" ON
3. Connect device via USB
4. In Android Studio:
   - Click Run (▶️) button
   - Select your device
   - Click OK

#### Via APK File:

1. Copy APK to device
2. Open file on device
3. Allow installation from unknown sources if prompted
4. Install

---

## Testing

### Unit Tests (Optional)

Run existing tests:
```bash
./gradlew test
```

### Manual Testing Checklist

- [ ] **Home Screen**
  - [ ] App launches without crashes
  - [ ] UI displays correctly
  - [ ] Buttons are clickable

- [ ] **Analysis Screen**
  - [ ] All input fields accept numbers
  - [ ] Validation works for invalid inputs
  - [ ] "Analyze" button triggers analysis
  - [ ] Loading indicator shows during analysis

- [ ] **Results Screen**
  - [ ] Interpretation displays correctly
  - [ ] Suggested conditions appear
  - [ ] Treatment recommendations show
  - [ ] "New Analysis" button works

- [ ] **History Screen**
  - [ ] Previous analyses are listed
  - [ ] Tapping analysis shows details
  - [ ] Delete function works
  - [ ] Empty state shows when no history

### Test ABG Values

Use these values for testing:

**Normal ABG**:
- pH: 7.40
- pCO₂: 40.0
- HCO₃: 24.0
- PaO₂: 95.0
- BE: 0.0

**Metabolic Acidosis**:
- pH: 7.30
- pCO₂: 38.0
- HCO₃: 16.0
- PaO₂: 92.0
- BE: -8.0

**Respiratory Acidosis**:
- pH: 7.32
- pCO₂: 52.0
- HCO₃: 26.0
- PaO₂: 78.0
- BE: 1.0

---

## Deployment

### Google Play Store (Optional)

1. **Prepare Release**:
   - Ensure all features work
   - Test on multiple devices
   - Verify API key is secure
   - Update version code/name in build.gradle.kts

2. **Create Play Store Account**:
   - Visit: https://play.google.com/console
   - Pay one-time $25 registration fee

3. **Create App Listing**:
   - Upload signed APK or AAB
   - Add screenshots (required)
   - Write description
   - Set category: Medical
   - Add privacy policy (required for medical apps)

4. **Medical App Compliance**:
   - Include medical disclaimer
   - Specify it's for medical professionals
   - May require additional verification
   - Check local regulations (FDA, etc.)

### Internal Distribution

#### For Hospital/Clinic Internal Use:

1. **Build Signed APK** (see above)
2. **Distribute via**:
   - Email
   - Internal file server
   - MDM (Mobile Device Management) system
   - Firebase App Distribution (free)

#### Firebase App Distribution:

1. Add Firebase to project
2. Install Firebase CLI
3. Run:
   ```bash
   firebase appdistribution:distribute app-release.apk \
     --app YOUR_APP_ID \
     --groups testers
   ```

---

## Troubleshooting

### Common Build Issues

#### Issue: "Gradle sync failed"
**Solution**:
1. Check internet connection
2. File > Invalidate Caches > Invalidate and Restart
3. Delete `.gradle` folder in project root
4. Sync again

#### Issue: "SDK not found"
**Solution**:
1. Tools > SDK Manager
2. Ensure SDK Platform 34 is installed
3. Update `local.properties` if needed:
   ```
   sdk.dir=/path/to/Android/sdk
   ```

#### Issue: "Kotlin version mismatch"
**Solution**:
1. Check `build.gradle.kts` versions match
2. Update to latest stable versions:
   - Kotlin: 1.9.20+
   - Compose: 1.5.4+

### Runtime Issues

#### Issue: "API key invalid"
**Solution**:
1. Verify key copied correctly from Google AI Studio
2. No extra spaces or quotes
3. Key starts with "AIza"
4. Try generating new key if problem persists

#### Issue: "Network error during analysis"
**Solution**:
1. Check device internet connection
2. Try on WiFi instead of mobile data
3. Check firewall settings
4. Verify Gemini API is not blocked in your region

#### Issue: "App crashes on startup"
**Solution**:
1. Check Logcat in Android Studio
2. Look for specific error message
3. Common causes:
   - Missing permissions in manifest
   - Incorrect dependencies
   - Memory issues
4. Clean and rebuild project

### Performance Issues

#### Issue: "Slow analysis"
**Solution**:
- Normal for first analysis (model initialization)
- Subsequent analyses should be faster
- Check internet speed
- Consider using gemini-1.5-flash (faster than pro)

#### Issue: "App uses too much data"
**Solution**:
- Each analysis uses ~5-10 KB
- Responses are cached by Gemini SDK
- Implement local caching if needed

---

## Additional Resources

### Documentation
- **Android Developers**: https://developer.android.com/
- **Jetpack Compose**: https://developer.android.com/jetpack/compose
- **Gemini API**: https://ai.google.dev/docs
- **Kotlin**: https://kotlinlang.org/docs/home.html

### Support
- **Android Studio Issues**: https://issuetracker.google.com/issues?q=componentid:192708
- **Gemini API Forum**: https://discuss.ai.google.dev/
- **Stack Overflow**: https://stackoverflow.com/questions/tagged/android

### Version Information
- **Created**: February 2026
- **Android Studio**: Hedgehog 2023.1.1+
- **Gradle**: 8.2+
- **Kotlin**: 1.9.20+
- **Compose**: 1.5.4+
- **Target SDK**: 34 (Android 14)
- **Min SDK**: 24 (Android 7.0)

---

## Next Steps

After successful setup:

1. ✅ Test with sample ABG values
2. ✅ Customize color scheme if desired
3. ✅ Add app icon (optional)
4. ✅ Test on multiple devices
5. ✅ Share with colleagues for feedback
6. ✅ Consider adding features from TODO list

**Happy Building! 🚀**

For questions or issues not covered here, please review the main README.md file or check the inline code comments.

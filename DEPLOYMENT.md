# ABG Insights - Deployment Guide

Complete guide for deploying ABG Insights to different environments.

## 📋 Table of Contents

1. [Pre-Deployment Checklist](#pre-deployment-checklist)
2. [Building Release APK](#building-release-apk)
3. [Google Play Store Deployment](#google-play-store-deployment)
4. [Internal/Enterprise Distribution](#internalenterprise-distribution)
5. [Firebase App Distribution](#firebase-app-distribution)
6. [Medical Compliance](#medical-compliance)

---

## Pre-Deployment Checklist

Before deploying, ensure:

### Code Quality
- [ ] All features tested and working
- [ ] No hardcoded API keys in repository
- [ ] Proper error handling implemented
- [ ] Loading states display correctly
- [ ] Medical disclaimer displayed prominently
- [ ] No debug logs in production code

### Security
- [ ] API key stored securely (BuildConfig or backend)
- [ ] No sensitive data in logs
- [ ] HTTPS used for all network calls
- [ ] Input validation implemented
- [ ] ProGuard rules configured

### Testing
- [ ] Tested on multiple devices (phones, tablets)
- [ ] Tested on different Android versions (7.0+)
- [ ] Tested with various ABG values
- [ ] Tested network error scenarios
- [ ] Tested offline behavior
- [ ] Verified history functionality

### Legal & Compliance
- [ ] Medical disclaimer included
- [ ] Privacy policy created
- [ ] Terms of service prepared
- [ ] HIPAA considerations documented
- [ ] Local medical app regulations checked

### Version Management
- [ ] Version code incremented in build.gradle.kts
- [ ] Version name updated (e.g., 1.0.0 → 1.0.1)
- [ ] CHANGELOG.md updated (if applicable)
- [ ] Git tag created for release

---

## Building Release APK

### Step 1: Generate Keystore

First time only - create a signing key:

```bash
keytool -genkey -v -keystore abg-insights-release.keystore \
  -alias abg-insights-key \
  -keyalg RSA -keysize 2048 -validity 10000
```

Answer the prompts:
- **Password**: Create a strong password (save it!)
- **Name**: Your name
- **Organizational Unit**: Your department
- **Organization**: Your organization/hospital
- **City**: Your city
- **State**: Your state
- **Country Code**: Your country (e.g., US)

**IMPORTANT**: Keep keystore file and passwords extremely secure!

### Step 2: Configure Signing

#### Option A: Using Android Studio GUI

1. **Build > Generate Signed Bundle / APK**
2. Select **APK**
3. Click **Next**
4. **Key store path**: Browse to your `.keystore` file
5. **Key store password**: Enter keystore password
6. **Key alias**: `abg-insights-key`
7. **Key password**: Enter key password
8. Click **Next**
9. Select **release** build variant
10. Check both signature versions (V1 and V2)
11. Click **Finish**

#### Option B: Using Command Line

1. Create `keystore.properties` in project root:
   ```properties
   storePassword=your-keystore-password
   keyPassword=your-key-password
   keyAlias=abg-insights-key
   storeFile=/path/to/abg-insights-release.keystore
   ```

2. Update `app/build.gradle.kts`:
   ```kotlin
   android {
       signingConfigs {
           create("release") {
               val keystorePropertiesFile = rootProject.file("keystore.properties")
               val keystoreProperties = Properties()
               keystoreProperties.load(FileInputStream(keystorePropertiesFile))
               
               storeFile = file(keystoreProperties["storeFile"] as String)
               storePassword = keystoreProperties["storePassword"] as String
               keyAlias = keystoreProperties["keyAlias"] as String
               keyPassword = keystoreProperties["keyPassword"] as String
           }
       }
       
       buildTypes {
           release {
               signingConfig = signingConfigs.getByName("release")
               isMinifyEnabled = true
               proguardFiles(
                   getDefaultProguardFile("proguard-android-optimize.txt"),
                   "proguard-rules.pro"
               )
           }
       }
   }
   ```

3. Build:
   ```bash
   ./gradlew assembleRelease
   ```

4. Find APK at: `app/build/outputs/apk/release/app-release.apk`

### Step 3: Verify APK

Test the release APK before distribution:

```bash
# Install on connected device
adb install app/build/outputs/apk/release/app-release.apk

# Check APK info
aapt dump badging app-release.apk | grep version

# Verify signing
jarsigner -verify -verbose -certs app-release.apk
```

---

## Google Play Store Deployment

### Prerequisites

1. **Google Play Console Account**
   - Cost: $25 one-time registration fee
   - URL: https://play.google.com/console

2. **Required Assets**
   - App icon (512x512 PNG)
   - Feature graphic (1024x500 PNG)
   - Screenshots (minimum 2)
   - Privacy policy URL
   - App description

### Step-by-Step Process

#### 1. Create App Listing

1. Log into Google Play Console
2. Click **"Create App"**
3. Fill in details:
   - **App name**: ABG Insights
   - **Default language**: English (US)
   - **App or game**: App
   - **Free or paid**: Free
4. Accept declarations
5. Click **"Create app"**

#### 2. Set Up Store Listing

Navigate to **Store presence > Main store listing**:

**App details**:
- **App name**: ABG Insights
- **Short description** (80 chars):
  ```
  AI-powered ABG analysis for medical professionals. Instant interpretation.
  ```
- **Full description** (4000 chars):
  ```
  ABG Insights provides AI-powered interpretation of Arterial Blood Gas values
  for medical professionals.
  
  FEATURES:
  • Instant ABG interpretation using advanced AI
  • Comprehensive acid-base analysis
  • Diagnostic suggestions
  • Treatment recommendations
  • Analysis history tracking
  • Clean, professional interface
  
  DESIGNED FOR:
  • Physicians
  • Nurse Practitioners
  • Physician Assistants
  • Medical Students
  • Respiratory Therapists
  
  IMPORTANT:
  This app provides clinical decision support and does not replace professional
  medical judgment. Always consider the full clinical context.
  
  Powered by Google Gemini AI for accurate, evidence-based interpretations.
  ```

**Graphics**:
- Upload app icon (512x512)
- Upload feature graphic (1024x500)
- Upload at least 2 screenshots from your app

**Categorization**:
- **App category**: Medical
- **Tags**: ABG, Medical, Healthcare, Clinical

**Contact details**:
- Email: your-email@example.com
- Website (optional): your-website.com

#### 3. Content Rating

1. Go to **Policy > App content > Content rating**
2. Click **"Start questionnaire"**
3. Select **Utility, Productivity, Reference**
4. Answer questions honestly:
   - No user-generated content
   - No social features
   - Medical information only
5. Submit for rating
6. Will likely receive: **Everyone, ESRB: Everyone**

#### 4. Privacy Policy

**REQUIRED for medical apps**

Create a privacy policy that covers:
- What data is collected (ABG values, analysis history)
- How data is stored (locally on device)
- Data sharing (none / only with Gemini API for analysis)
- User rights
- Contact information

Host it on:
- Your website
- GitHub Pages (free)
- Google Sites (free)

Add URL in: **Policy > App content > Privacy policy**

#### 5. Target Audience & Content

1. **Policy > App content > Target audience**
2. Select: **18 and over** (medical professionals)
3. Complete declarations

#### 6. Medical App Declaration

**CRITICAL for medical apps**:

1. **Policy > App content > Health**
2. Declare if app is for medical purposes: **YES**
3. Specify:
   - Clinical decision support tool
   - For licensed medical professionals
   - Not FDA approved (if applicable)
   - Not a replacement for medical judgment

#### 7. Upload APK/AAB

1. **Release > Production**
2. Click **"Create new release"**
3. Upload your signed APK or AAB:
   ```
   app/build/outputs/apk/release/app-release.apk
   ```
4. Add release notes:
   ```
   Initial release of ABG Insights
   - AI-powered ABG interpretation
   - Comprehensive clinical analysis
   - Treatment recommendations
   - Analysis history tracking
   ```
5. **Save** and proceed

#### 8. Review & Publish

1. Complete all required sections (green checkmarks)
2. Click **"Send for review"**
3. Review typically takes 1-7 days
4. You'll receive email when approved/rejected
5. Once approved, click **"Publish"** to go live

### App Updates

For future updates:

1. Increment version in `build.gradle.kts`:
   ```kotlin
   versionCode = 2  // increment
   versionName = "1.0.1"  // update
   ```
2. Build new signed APK
3. **Release > Production > Create new release**
4. Upload new APK
5. Add release notes describing changes
6. Submit for review

---

## Internal/Enterprise Distribution

For hospitals, clinics, or medical schools:

### Method 1: Direct APK Distribution

1. **Build signed APK** (see above)
2. **Distribute via**:
   - Email
   - Internal file server
   - Google Drive / Dropbox
   - MDM (Mobile Device Management) system

3. **Installation instructions** for users:
   ```
   1. Download APK file
   2. Settings > Security > Enable "Install from Unknown Sources"
   3. Open downloaded APK
   4. Tap "Install"
   5. Tap "Open" when complete
   ```

### Method 2: Google Play Private/Internal Track

1. Upload APK to Play Console
2. **Release > Testing > Internal testing**
3. Create list of tester emails
4. Share testing link with your team
5. Testers install via Play Store (no "Unknown Sources" needed)

### Method 3: Enterprise MDM

Most hospitals use MDM systems (Intune, MobileIron, etc.):

1. Upload signed APK to MDM
2. Create deployment policy
3. Push to designated devices
4. Users receive automatic installation

---

## Firebase App Distribution

**Free, easy testing distribution from Google**

### Setup

1. **Add Firebase to project**:
   - Visit: https://console.firebase.google.com
   - Create new project
   - Download `google-services.json`
   - Place in `app/` directory

2. **Install Firebase CLI**:
   ```bash
   npm install -g firebase-tools
   firebase login
   ```

3. **Distribute**:
   ```bash
   firebase appdistribution:distribute app-release.apk \
     --app 1:1234567890:android:abc123def456 \
     --groups testers \
     --release-notes "Initial beta release"
   ```

4. **Invite testers**:
   - Go to Firebase Console
   - App Distribution section
   - Add tester emails
   - They receive invitation email
   - Install via Firebase App Distribution app

---

## Medical Compliance

### FDA Regulations (United States)

**Is ABG Insights a medical device?**

Likely **NO**, because:
- It's a clinical decision support tool
- Doesn't directly diagnose
- Requires professional interpretation
- Not patient-facing

However:
- Include clear disclaimers
- State it's for medical professionals only
- Not a replacement for clinical judgment

### HIPAA Compliance

**Does ABG Insights need to be HIPAA compliant?**

Depends on usage:

**NOT HIPAA-covered if**:
- Users enter only ABG values (no patient identifiers)
- No patient names, DOB, MRN entered
- Local storage only

**WOULD BE HIPAA-covered if**:
- Patient data stored/transmitted
- Cloud backup enabled
- Analysis shared with identifiers

**Recommendations**:
- Don't collect patient identifiers
- Store data locally only
- Include privacy notice
- Train users on proper usage

### European Union (CE Marking)

If distributing in EU, may need:
- CE marking (Medical Device Regulation)
- Clinical evaluation
- Technical documentation
- Regulatory consultation recommended

### Other Regions

Check local regulations:
- **Canada**: Health Canada medical device rules
- **Australia**: TGA medical device requirements
- **UK**: MHRA regulations

---

## Post-Deployment

### Monitoring

1. **Play Console Analytics**:
   - Downloads
   - Active users
   - Crash reports
   - ANRs (App Not Responding)

2. **User Feedback**:
   - Monitor Play Store reviews
   - Respond to user questions
   - Track feature requests

3. **Error Tracking** (Optional):
   - Add Firebase Crashlytics
   - Monitor API errors
   - Track usage patterns

### Maintenance

Regular tasks:
- [ ] Update dependencies monthly
- [ ] Test with new Android versions
- [ ] Monitor Gemini API changes
- [ ] Review and update medical information
- [ ] Respond to user feedback
- [ ] Release updates as needed

### Support

Provide users with:
- In-app help/FAQ
- Support email
- User manual
- Video tutorials (optional)

---

## Checklist Before Launch

Final verification:

- [ ] All features working correctly
- [ ] Medical disclaimer prominent
- [ ] Privacy policy published
- [ ] Content rating obtained
- [ ] Screenshots uploaded
- [ ] Store listing complete
- [ ] APK signed and tested
- [ ] Version numbers correct
- [ ] No debug code in production
- [ ] API quotas sufficient
- [ ] Support channels ready
- [ ] Compliance requirements met

---

## Resources

- **Play Console**: https://play.google.com/console
- **Firebase Console**: https://console.firebase.google.com
- **FDA Guidance**: https://www.fda.gov/medical-devices/digital-health
- **HIPAA Info**: https://www.hhs.gov/hipaa/
- **Play Store Policies**: https://play.google.com/about/developer-content-policy/

---

**Version**: 1.0.0  
**Last Updated**: February 2026

For deployment assistance, consult with:
- Android development team
- Legal/compliance team
- Your institution's IT department
- Medical device regulatory consultant (if needed)

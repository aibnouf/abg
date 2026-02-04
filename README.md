# ABG Insights - Android Application

**AI-Powered Arterial Blood Gas Analysis for Medical Professionals**

## 🏥 Overview

ABG Insights is a professional Android application that provides AI-assisted interpretation of Arterial Blood Gas (ABG) values using Google's Gemini AI. The application is designed to support medical professionals in clinical decision-making by providing:

- Automated ABG interpretation
- Diagnostic suggestions based on ABG patterns
- Treatment recommendations
- Analysis history tracking
- Clean, professional medical interface

## ✨ Key Features

### 📊 Core Functionality
- **ABG Value Input**: Enter pH, pCO₂, HCO₃, PaO₂, and Base Excess values
- **AI-Powered Analysis**: Uses Google Gemini 1.5 Flash for intelligent interpretation
- **Comprehensive Results**: 
  - Primary acid-base disorder identification
  - Compensation status analysis
  - Oxygenation assessment
  - Suggested underlying conditions
  - Treatment recommendations
- **Analysis History**: Track and review previous analyses
- **Offline-Capable**: Local data storage for analysis history

### 🎨 Design Features
- Material Design 3 (Material You)
- Custom color scheme based on medical application standards:
  - Primary: Calm Blue (#64B5F6) - Trust and reliability
  - Secondary: Muted Green (#81C784) - Positive outcomes
  - Background: Light Grey (#F0F4F8) - Clean and professional
- Dark mode support
- Intuitive navigation
- Loading states and error handling

## 💰 Cost Structure - COMPLETELY FREE

### ✅ Zero Operational Costs
This application is designed to be **100% free for future use** with NO ongoing costs:

1. **Google Gemini AI SDK**: 
   - Uses FREE Google AI SDK (not Vertex AI)
   - Free tier includes generous quota
   - No billing required for moderate usage
   - Get free API key at: https://makersuite.google.com/app/apikey

2. **No Backend Server Costs**:
   - All AI processing happens directly on-device via Gemini SDK
   - No cloud functions or server hosting needed
   - No Firebase costs (authentication optional, storage optional)

3. **Local Storage**:
   - Analysis history stored locally on device
   - No database costs
   - No cloud storage costs

### 📊 Gemini API Free Tier Limits
- 60 requests per minute
- 1,500 requests per day
- Perfect for individual medical professional use
- Can be upgraded to paid tier if needed for high-volume usage

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or later
- Android SDK 34 (Android 14)
- Minimum Android SDK 24 (Android 7.0)
- Kotlin 1.9.20 or later

### Installation Steps

1. **Clone or Download** this project

2. **Open in Android Studio**
   ```
   File > Open > Select project folder
   ```

3. **Configure Gemini API Key**
   
   Open `app/src/main/java/com/medical/abginsights/ai/GeminiService.kt`
   
   Replace the API key:
   ```kotlin
   private const val API_KEY = "YOUR_API_KEY_HERE"
   ```
   
   **How to get a FREE API key:**
   - Visit: https://makersuite.google.com/app/apikey
   - Sign in with Google account
   - Click "Create API Key"
   - Copy the key and paste it in GeminiService.kt

4. **Sync Gradle**
   ```
   File > Sync Project with Gradle Files
   ```

5. **Run the Application**
   - Connect an Android device or start an emulator
   - Click Run (▶️) button
   - Select your device
   - Wait for build and installation

## 📱 Application Structure

```
ABG Insights
├── app/
│   ├── src/main/
│   │   ├── java/com/medical/abginsights/
│   │   │   ├── MainActivity.kt                    # Main entry point
│   │   │   ├── ai/
│   │   │   │   └── GeminiService.kt              # Gemini AI integration
│   │   │   ├── data/model/
│   │   │   │   └── AbgData.kt                    # Data models
│   │   │   ├── viewmodel/
│   │   │   │   └── AbgViewModel.kt               # Business logic
│   │   │   └── ui/
│   │   │       ├── navigation/
│   │   │       │   └── AppNavigation.kt          # Navigation graph
│   │   │       ├── screens/
│   │   │       │   ├── HomeScreen.kt             # Home screen
│   │   │       │   ├── AnalysisScreen.kt         # ABG input screen
│   │   │       │   ├── ResultsScreen.kt          # Results display
│   │   │       │   └── HistoryScreen.kt          # History view
│   │   │       └── theme/
│   │   │           ├── Theme.kt                  # App theme
│   │   │           └── Type.kt                   # Typography
│   │   ├── res/
│   │   │   ├── values/
│   │   │   │   ├── strings.xml                   # String resources
│   │   │   │   └── themes.xml                    # Theme resources
│   │   │   └── xml/
│   │   │       ├── backup_rules.xml
│   │   │       └── data_extraction_rules.xml
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts                          # App-level Gradle
├── build.gradle.kts                              # Project-level Gradle
├── settings.gradle.kts                           # Gradle settings
└── README.md                                     # This file
```

## 🔧 Technology Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose (Material 3)
- **Architecture**: MVVM (Model-View-ViewModel)
- **AI Engine**: Google Gemini 1.5 Flash
- **Navigation**: Navigation Compose
- **Async**: Kotlin Coroutines + Flow
- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)

## 📖 Usage Guide

### For Medical Professionals

1. **Starting an Analysis**
   - Launch the app
   - Tap "Start New Analysis"
   - Enter ABG values from patient's blood gas analysis
   - Values include: pH, pCO₂, HCO₃, PaO₂, Base Excess
   - Tap "Analyze ABG"

2. **Reviewing Results**
   - View comprehensive interpretation
   - Review suggested conditions
   - Read treatment recommendations
   - Always consider full clinical context

3. **Accessing History**
   - Tap "View History" from home screen
   - Browse previous analyses
   - Tap any analysis to view details
   - Swipe or tap delete to remove entries

### Important Clinical Notes

⚠️ **Medical Disclaimer**: This application provides AI-assisted analysis to support clinical decision-making. It does NOT constitute medical advice and should ALWAYS be used in conjunction with:
- Professional clinical evaluation
- Patient history and physical examination
- Additional diagnostic tests
- Current medical guidelines
- Clinical judgment

## 🔒 Privacy & Security

- **Local Storage**: All analysis data stored locally on device
- **No Cloud Sync**: Analysis history never leaves the device
- **No User Tracking**: No analytics or tracking implemented
- **HIPAA Considerations**: 
  - Do not enter patient identifiable information
  - Use for clinical decision support only
  - Follow your institution's policies on mobile medical apps

## 🛠️ Customization Options

### Changing AI Model
Edit `GeminiService.kt`:
```kotlin
private const val MODEL_NAME = "gemini-1.5-flash" // or "gemini-1.5-pro"
```

### Adding Firebase Authentication (Optional)
1. Add `google-services.json` to `app/` directory
2. Implement authentication in MainActivity
3. Link analysis history to user accounts

### Modifying Color Scheme
Edit `Theme.kt`:
```kotlin
private val CalmBlue = Color(0xFF64B5F6)        // Change primary color
private val MutedGreen = Color(0xFF81C784)      // Change secondary color
private val LightGrey = Color(0xFFF0F4F8)       // Change background color
```

## 🐛 Troubleshooting

### Common Issues

**Issue**: "API Key not valid"
- **Solution**: Verify you've replaced `YOUR_API_KEY_HERE` with actual API key from Google AI Studio

**Issue**: "Network error"
- **Solution**: Check internet connection. Gemini API requires internet access.

**Issue**: "Build failed"
- **Solution**: 
  - Sync Gradle files
  - Clean and rebuild project
  - Check Android SDK is properly installed

**Issue**: App crashes on analysis
- **Solution**: 
  - Verify API key is correct
  - Check Gemini SDK dependency version
  - Review Logcat for error details

## 📝 Future Enhancements (Optional)

Potential features that could be added:
- [ ] Image OCR for ABG report scanning
- [ ] Export analysis as PDF
- [ ] Multiple patient profiles
- [ ] Graphical ABG trends
- [ ] Offline AI model (TensorFlow Lite)
- [ ] Multi-language support
- [ ] Voice input for ABG values
- [ ] Integration with EMR systems

## 🤝 Contributing

This is a medical application. Any modifications should be:
1. Thoroughly tested
2. Reviewed by medical professionals
3. Compliant with medical software regulations
4. Well-documented

## 📄 License

This application is provided as-is for educational and clinical support purposes. 

**Important**: 
- Not FDA approved
- Not intended as sole diagnostic tool
- Users assume all responsibility for clinical decisions
- Always verify AI interpretations with clinical judgment

## 📞 Support

For issues or questions:
1. Check this README
2. Review code comments
3. Check Google Gemini API documentation: https://ai.google.dev/docs
4. Review Jetpack Compose documentation: https://developer.android.com/jetpack/compose

## 🎓 Educational Purpose

This application demonstrates:
- Integration of Google Gemini AI in Android
- Modern Android development with Jetpack Compose
- MVVM architecture pattern
- Material Design 3 implementation
- Medical app UI/UX best practices
- Cost-free AI implementation for mobile apps

## ⚕️ Medical Accuracy

The AI interpretations are based on:
- Standard ABG interpretation algorithms
- Medical literature and guidelines
- Gemini AI's trained medical knowledge

However, always:
- Verify with clinical findings
- Consider patient history
- Follow current medical guidelines
- Use as decision support, not replacement

## 🔄 Updates & Maintenance

To keep the app updated:
1. Monitor Gemini API changes: https://ai.google.dev/docs
2. Update dependencies regularly
3. Test with latest Android versions
4. Review medical guideline updates

---

**Version**: 1.0.0  
**Last Updated**: February 2026  
**Minimum Requirements**: Android 7.0 (API 24)  
**Recommended**: Android 12+ for best performance

**Built with ❤️ for medical professionals**

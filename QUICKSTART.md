# ABG Insights - Quick Start Guide

Get your ABG Analyzer running in **5 minutes**! ⚡

## 🎯 Prerequisites Checklist

Before you start, ensure you have:
- [ ] Android Studio installed
- [ ] Internet connection
- [ ] Google account (for free API key)

## 🚀 5-Minute Setup

### Step 1: Get Your Free API Key (2 minutes)

1. Go to: **https://makersuite.google.com/app/apikey**
2. Sign in with your Google account
3. Click **"Create API Key"**
4. **Copy** the key (starts with "AIza...")

### Step 2: Open Project (1 minute)

1. Open **Android Studio**
2. Click **File > Open**
3. Select the **abg-android-app** folder
4. Wait for Gradle sync (shows in bottom status bar)

### Step 3: Add Your API Key (1 minute)

1. In Android Studio, navigate to:
   ```
   app/src/main/java/com/medical/abginsights/ai/GeminiService.kt
   ```

2. Find line 18 that says:
   ```kotlin
   private const val API_KEY = "YOUR_API_KEY_HERE"
   ```

3. Replace `YOUR_API_KEY_HERE` with your actual key:
   ```kotlin
   private const val API_KEY = "AIzaSyC...your-key-here"
   ```

4. Save the file (Ctrl+S or Cmd+S)

### Step 4: Run the App (1 minute)

**Option A: Using Android Device**
1. Enable USB Debugging on your phone
2. Connect phone via USB
3. Click the green **Run ▶️** button in Android Studio
4. Select your device
5. Done! 🎉

**Option B: Using Emulator**
1. Click **Device Manager** in Android Studio
2. Click **"Create Device"** if you don't have one
3. Select **Pixel 6** and **API 34**
4. Click green **Run ▶️** button
5. Select the emulator
6. Done! 🎉

## ✅ Test Your Setup

1. App should launch and show the home screen
2. Tap **"Start New Analysis"**
3. Enter these test values:
   - pH: **7.40**
   - pCO₂: **40.0**
   - HCO₃: **24.0**
   - PaO₂: **95.0**
   - BE: **0.0**
4. Tap **"Analyze ABG"**
5. Wait 5-10 seconds
6. You should see analysis results! ✅

If you see results, **congratulations!** Your app is working perfectly! 🎊

## 🆘 Quick Troubleshooting

### Problem: "Gradle sync failed"
**Fix**: 
1. Check internet connection
2. In Android Studio: File > Invalidate Caches > Invalidate and Restart

### Problem: "API key invalid" error
**Fix**: 
1. Double-check you copied the entire key
2. Make sure there are no extra spaces
3. Verify the key in GeminiService.kt is inside quotes

### Problem: Can't find GeminiService.kt
**Fix**: 
1. In Android Studio, switch to "Project" view (dropdown at top-left)
2. Navigate through folders: app > src > main > java > com > medical > abginsights > ai
3. Open GeminiService.kt

### Problem: App crashes immediately
**Fix**: 
1. Check the "Run" tab at bottom of Android Studio
2. Look for red error messages
3. Most common: API key not set correctly

## 📚 What's Next?

Now that your app is running:

- **Read the full [README.md](README.md)** for detailed information
- **Check [SETUP_GUIDE.md](SETUP_GUIDE.md)** for advanced configuration
- **Try different ABG values** to test various conditions
- **Customize the app** colors, icons, features

## 🎓 Sample ABG Values for Testing

**Normal Values** (should show normal interpretation):
```
pH: 7.40 | pCO₂: 40.0 | HCO₃: 24.0 | PaO₂: 95.0 | BE: 0.0
```

**Metabolic Acidosis** (diabetic ketoacidosis):
```
pH: 7.25 | pCO₂: 30.0 | HCO₃: 14.0 | PaO₂: 98.0 | BE: -12.0
```

**Respiratory Acidosis** (COPD exacerbation):
```
pH: 7.32 | pCO₂: 52.0 | HCO₃: 26.0 | PaO₂: 78.0 | BE: 1.0
```

**Metabolic Alkalosis** (persistent vomiting):
```
pH: 7.50 | pCO₂: 45.0 | HCO₃: 34.0 | PaO₂: 92.0 | BE: 10.0
```

**Mixed Disorder**:
```
pH: 7.38 | pCO₂: 52.0 | HCO₃: 30.0 | PaO₂: 72.0 | BE: 5.0
```

## 💡 Pro Tips

1. **Keep your API key secret** - Don't share it publicly
2. **Test on real device** for best performance
3. **Save frequently** used ABG patterns in history
4. **Always verify** AI results with clinical judgment
5. **Check Gemini API quotas** at https://aistudio.google.com

## 📞 Need More Help?

- **Detailed setup**: See [SETUP_GUIDE.md](SETUP_GUIDE.md)
- **Full documentation**: See [README.md](README.md)
- **Android Studio help**: https://developer.android.com/studio/intro
- **Gemini API docs**: https://ai.google.dev/docs

---

**That's it! You're ready to use ABG Insights! 🏥✨**

Remember: This app is for clinical decision support only, not a replacement for professional medical judgment.

**Version**: 1.0.0  
**Last Updated**: February 2026

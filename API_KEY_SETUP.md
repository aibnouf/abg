# Google Gemini API Key Setup Guide

## 🔑 Getting Your FREE Gemini API Key

Google's Gemini AI provides a generous **FREE tier** that's perfect for this medical application. No credit card required!

## Step-by-Step Instructions

### 1. Access Google AI Studio

Choose one of these URLs (they both work):
- **Option A**: https://makersuite.google.com/app/apikey
- **Option B**: https://aistudio.google.com/app/apikey

### 2. Sign In

- Use any Google account (Gmail, Google Workspace, etc.)
- If you don't have a Google account, create one at https://accounts.google.com

### 3. Create API Key

1. Once signed in, you'll see the API Key page
2. Click the blue **"Create API Key"** button
3. You have two options:
   - **"Create API key in new project"** (Recommended for new users)
   - **"Create API key in existing project"** (If you already have Google Cloud projects)
4. Click your preferred option
5. Wait a few seconds for the key to be generated

### 4. Copy Your API Key

1. Your API key will appear (format: `AIzaSy...`)
2. Click the **Copy** icon or manually select and copy the entire key
3. ⚠️ **IMPORTANT**: Save this key somewhere safe! You'll need it in the next step.

### 5. Add Key to Application

#### Method 1: Direct (Simple, Quick)

1. Open the project in Android Studio
2. Navigate to: `app/src/main/java/com/medical/abginsights/ai/GeminiService.kt`
3. Find line 18:
   ```kotlin
   private const val API_KEY = "YOUR_API_KEY_HERE"
   ```
4. Replace `YOUR_API_KEY_HERE` with your actual key:
   ```kotlin
   private const val API_KEY = "AIzaSyC-xxxxxxxxxxxxxxxxxxxxxxxxxxx"
   ```
5. Save the file (Ctrl+S / Cmd+S)

#### Method 2: BuildConfig (More Secure, Recommended for Teams)

1. Create or edit `local.properties` file in project root:
   ```properties
   GEMINI_API_KEY=AIzaSyC-xxxxxxxxxxxxxxxxxxxxxxxxxxx
   ```

2. Update `app/build.gradle.kts`:
   ```kotlin
   android {
       defaultConfig {
           val properties = Properties()
           properties.load(project.rootProject.file("local.properties").inputStream())
           buildConfigField("String", "GEMINI_API_KEY", 
               "\"${properties.getProperty("GEMINI_API_KEY")}\"")
       }
       buildFeatures {
           buildConfig = true
       }
   }
   ```

3. Update `GeminiService.kt`:
   ```kotlin
   private const val API_KEY = BuildConfig.GEMINI_API_KEY
   ```

4. Add to `.gitignore`:
   ```
   local.properties
   ```

## 📊 Understanding the Free Tier

### What's Included (100% FREE):

- **60 requests per minute** - More than enough for individual use
- **1,500 requests per day** - Covers dozens of analyses
- **No credit card required** - Completely free to start
- **No expiration** - Key works indefinitely (within quotas)

### Typical Usage:

- **1 ABG analysis = 1 API request**
- Average medical professional: 10-30 analyses per day
- Well within free tier limits!

### When You Might Need Paid Tier:

- High-volume clinics (100+ analyses per day)
- Multiple users sharing same API key
- Automated batch processing

## 🔐 Security Best Practices

### ✅ DO:

- Keep your API key private
- Use BuildConfig method for team projects
- Add `local.properties` to `.gitignore`
- Rotate keys periodically (every 3-6 months)
- Use different keys for development and production

### ❌ DON'T:

- Share your API key publicly
- Commit API keys to Git/GitHub
- Use the same key across multiple apps
- Hardcode keys in production apps
- Share screenshots containing your API key

## 🔄 Managing Your API Keys

### View All Your Keys:

1. Go to: https://aistudio.google.com/app/apikey
2. See list of all your API keys
3. View usage statistics
4. Create additional keys if needed

### Delete/Revoke a Key:

1. Go to API key management page
2. Find the key you want to delete
3. Click the trash/delete icon
4. Confirm deletion
5. Update your app with a new key

### Create Multiple Keys:

You can create multiple API keys for:
- **Development**: Testing and development
- **Production**: Released app
- **Backup**: Emergency fallback

## 📈 Monitoring Usage

### Check Your Usage:

1. Visit: https://aistudio.google.com/
2. Click on your project
3. View usage dashboard:
   - Requests per day
   - Requests per minute
   - Success/error rates
   - Quota remaining

### Usage Alerts:

- Google will email you at 80% quota usage
- Monitor regularly if multiple users share the key
- Consider upgrading if consistently hitting limits

## 🆘 Troubleshooting

### "API Key Not Valid" Error

**Possible causes:**
1. Key not copied completely
2. Extra spaces before/after key
3. Key was deleted/revoked in Google console
4. Typo in the key

**Solution:**
1. Generate a new API key
2. Copy the entire key (starts with "AIza")
3. Paste carefully without extra spaces
4. Rebuild and run the app

### "Quota Exceeded" Error

**Meaning**: You've hit the free tier limits

**Solutions:**
1. Wait for quota to reset (daily reset at midnight Pacific Time)
2. Create a new API key (separate quota)
3. Upgrade to paid tier if needed

### "API Key Restricted" Error

**Meaning**: API key has restrictions enabled

**Solution:**
1. Go to Google Cloud Console
2. Find your API key
3. Check restrictions settings
4. Either remove restrictions or add "Generative Language API" to allowed APIs

## 💰 Upgrading to Paid Tier (Optional)

If you need more than the free tier:

### Pricing (as of 2026):

- **Gemini 1.5 Flash**: ~$0.35 per 1M tokens
- **Typical ABG analysis**: ~1,000 tokens
- **Cost per analysis**: ~$0.00035 (less than $0.01)

### How to Upgrade:

1. Visit: https://console.cloud.google.com/
2. Select your project
3. Enable billing
4. Add payment method
5. Same API key continues to work with higher quotas

## 🔗 Useful Links

- **Get API Key**: https://makersuite.google.com/app/apikey
- **API Documentation**: https://ai.google.dev/docs
- **Usage Dashboard**: https://aistudio.google.com/
- **Google Cloud Console**: https://console.cloud.google.com/
- **Pricing Information**: https://ai.google.dev/pricing

## 📞 Support

If you encounter issues:

1. **Check API Key**: Verify it's copied correctly
2. **Check Quotas**: Ensure you haven't exceeded limits
3. **Test Key**: Try making a simple request via Google AI Studio
4. **Documentation**: Review official Gemini API docs
5. **Community**: Ask on Google AI Developer Forum

---

**Important Notes:**

- Free tier is perfect for individual medical professionals
- No credit card needed to start
- Key works across all your apps using Gemini
- Keep your key secure and private
- Monitor usage to avoid unexpected quota issues

**Last Updated**: February 2026  
**Gemini API Version**: 1.5 Flash

For questions about API keys, visit: https://ai.google.dev/docs

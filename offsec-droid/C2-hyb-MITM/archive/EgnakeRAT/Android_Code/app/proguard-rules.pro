# EgnakeRAT v2.0 ProGuard Rules
# Obfuscate everything except what absolutely must stay

# Keep the config class intact (build system writes to it)
-keep class com.egnakerat.system.config { *; }

# Keep JSON parsing (uses reflection)
-keepclassmembers class * {
    @org.json.JSONObject *;
}

# Keep service declarations for manifest
-keep class com.egnakerat.system.RATService
-keep class com.egnakerat.system.mainService
-keep class com.egnakerat.system.broadcastReciever
-keep class com.egnakerat.system.keypadListner
-keep class com.egnakerat.system.jobScheduler
-keep class com.egnakerat.system.Payloads.audioManager
-keep class com.egnakerat.system.Payloads.videoRecorder

# Aggressively obfuscate everything else
-repackageclasses ''
-allowaccessmodification
-overloadaggressively

# Remove logging in release
-assumenosideeffects class android.util.Log {
    public static int v(...);
    public static int d(...);
    public static int i(...);
    public static int w(...);
}

# Optimize
-optimizationpasses 5
-dontusemixedcaseclassnames
-verbose

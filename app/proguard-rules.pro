# Zerokosh R8 rules (release). No Retrofit/Room/Gson rules — those libraries
# are prohibited by spec R0.2/R0.7 and must never appear here.

# --- Lazysodium + JNA (reflection over native structs) ---
-keep class com.goterl.lazysodium.** { *; }
-keep class com.sun.jna.** { *; }
-keepclassmembers class * extends com.sun.jna.Structure { public *; }
-dontwarn java.awt.**
-dontwarn com.sun.jna.**

# --- kotlinx.serialization: keep generated serializers for our models ---
-keepattributes RuntimeVisibleAnnotations,AnnotationDefault
-keepclassmembers class org.zerokosh.core.** {
    *** Companion;
}
-keepclasseswithmembers class org.zerokosh.core.** {
    kotlinx.serialization.KSerializer serializer(...);
}
-keepclassmembers class org.zerokosh.app.data.** {
    *** Companion;
}
-keepclasseswithmembers class org.zerokosh.app.data.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Secrets hygiene (§3.5): strip verbose/debug logging in release
-assumenosideeffects class android.util.Log {
    public static int v(...);
    public static int d(...);
}

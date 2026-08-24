# Zerokosh R8 rules (release). No Retrofit/Gson rules — those libraries are
# prohibited by spec R0.2/R0.7 and must never appear here. Room does appear
# below, but not as a dependency of ours: WorkManager embeds it.

# --- Room, reached through WorkManager (reminders, clipboard clear) ---
# Room loads its generated implementation by name — Class.forName(db + "_Impl").
# R8 cannot see a reflective load, so it deleted WorkDatabase_Impl and the app
# died inside androidx.startup before MainActivity ever ran. The unminified
# build was fine, which is the whole reason the releaseCheck variant exists.
-keep class * extends androidx.room.RoomDatabase { <init>(); }
-dontwarn androidx.room.paging.**

# Workers are instantiated from a class name stored in that database, so the
# same blindness applies to them.
-keep class * extends androidx.work.ListenableWorker { <init>(...); }

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

-dontoptimize
-dontshrink
-dontwarn

-keepclassmembers class org.breakthebot.models.** {
    public *;
}

-keepclassmembers class org.breakthebot.api.** {
    public *;
}
-keepclassmembers class * {
    public *;
}

-keepnames class org.breakthebot.models.** { *; }
-keepnames class org.breakthebot.api.** { *; }

-keepattributes *Annotation*, Signature, InnerClasses, EnclosingMethod

-keep class kotlin.Metadata { *; }


-dontshrink
-dontwarn
-keep class org.breakthebot.objects.** { *; }

# Keep models for reflection
-keep class org.breakthebot.models.** { *; }

# Keep reflection + generics info
-keepattributes *Annotation*, Signature, InnerClasses, EnclosingMethod

# Kotlin support
-keep class kotlin.Metadata { *; }

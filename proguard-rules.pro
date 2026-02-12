-dontoptimize
-dontshrink
-dontwarn
-keep class org.breakthebot.objects.** { *; }

-keep public class * {
    public *;
}

-keepattributes *Annotation*, Signature, InnerClasses, EnclosingMethod
-keep class kotlin.Metadata { *; }
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\sdk\android-sdk-windows/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-dontshrink
-dontoptimize

-keepattributes Signature
-keep class sun.misc.Unsafe { *; }


-keepattributes SourceFile,LineNumberTable
-keep class com.parse.*{ *; }
-dontwarn com.parse.**
-dontwarn com.squareup.picasso.**
-keepclasseswithmembernames class * {
    native <methods>;
}




-dontskipnonpubliclibraryclassmembers

-keepattributes *Annotation*,EnclosingMethod

-keepnames class org.codehaus.jackson.** { *; }

-dontwarn javax.xml.**
-dontwarn javax.xml.stream.events.**
-dontwarn com.fasterxml.jackson.databind.**
-dontwarn retrofit.**
-dontwarn okhttp3.internal.platform.*
# Application classes that will be serialized/deserialized over Gson
-keep class com.kelltontech.volley.ext.** { *; }

-keep class com.bluestar.models.** {*;}

-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
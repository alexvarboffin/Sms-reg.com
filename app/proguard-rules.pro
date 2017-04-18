# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\android\sdk/tools/proguard/proguard-android.txt
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

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-optimizationpasses 5

-obfuscationdictionary obfuscationdictionary.txt
-classobfuscationdictionary obfuscationdictionary.txt
-packageobfuscationdictionary package_dictionary.txt

-dontskipnonpubliclibraryclasses

-overloadaggressively
-useuniqueclassmembernames

#-repackageclasses

-dump class_files.txt
-printseeds seeds.txt
-printusage unused.txt
-printmapping mapping.txt


-dontwarn okio.**
-dontwarn retrofit2.Platform$Java8
-dontwarn retrofit.Platform$Java8
-dontwarn retrofit2.Platform$Java8
-dontwarn rx.internal.util.**

#--------------------------------------------------------
# Retrofit
#--------------------------------------------------------
#-dontwarn retrofit2.**
#-dontwarn org.codehaus.mojo.**
#-keep class retrofit2.** { *; }
#-keepattributes Exceptions
#-keepattributes *Annotation*

#-keepattributes RuntimeVisibleAnnotations
#-keepattributes RuntimeInvisibleAnnotations
#-keepattributes RuntimeVisibleParameterAnnotations
#-keepattributes RuntimeInvisibleParameterAnnotations

#-keepattributes EnclosingMethod



#-keepclasseswithmembers interface * {
#    @retrofit2.* <methods>;
#}


# Retrofit 2.X
## https://square.github.io/retrofit/ ##

-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

-dontwarn okhttp3.**



-dontwarn sun.misc.Unsafe
-dontwarn org.w3c.dom.bootstrap.DOMImplementationRegistry


#Firebase SDK 2.0.0:

 -keep class com.firebase.** { *; }
 -keep class org.apache.** { *; }
 -keepnames class com.fasterxml.jackson.** { *; }
 -keepnames class javax.servlet.** { *; }
 -keepnames class org.ietf.jgss.** { *; }
 -dontwarn org.w3c.dom.**
 -dontwarn org.joda.time.**
 -dontwarn org.shaded.apache.**
 -dontwarn org.ietf.jgss.**

 # Only necessary if you downloaded the SDK jar directly instead of from maven.
 -keep class com.shaded.fasterxml.jackson.** { *; }

 #Last resort:

 -keep class !com.psyberia.** { *; }

 -keep public class com.google.firebase.** { public *; }




 # Add this global rule
-keepattributes Signature

 # This rule will properly ProGuard all the model classes in
 # the package com.yourcompany.models. Modify to fit the structure
 # of your app.
 -keepclassmembers class com.psyberia.sms_regcom.rest.beans.** {
   *;
 }



 #
 -keep public class * extends android.app.Activity
 -keep public class * extends android.app.Application
 -keep public class * extends android.app.Service
 -keep public class * extends android.app.Fragment
 -keep public class * extends android.support.v4.app.Fragment
 -keep public class * extends android.content.BroadcastReceiver
 -keep public class * extends android.content.ContentProvider
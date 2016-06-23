# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\android-sdk/tools/proguard/proguard-android.txt
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

-libraryjars 'D:\Program Files (x86)\Java\jdk1.8.0_51\lib\rt.jar'

-libraryjars 'D:\android-sdk\platforms\android-23\android.jar'

-dontpreverify

-dontwarn

#-keep class com.cool.makejarlib.utils.* {
#
#public <fields>;
#
#public <methods>;
#
#}
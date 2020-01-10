# RecyclerCalendarAndroid

[![](https://jitpack.io/v/tejpratap46/RecyclerCalendarAndroid.svg)](https://jitpack.io/#tejpratap46/RecyclerCalendarAndroid)

A DIY calendar generator library for android written in Kotlin.

Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

Step 2. Add the dependency
```gradle
dependencies {
        implementation 'com.github.tejpratap46:RecyclerCalendarAndroid:LATEST_RELEASE_TAG'
}
```
Here are sample calenders you can create with this library:
### 1. Week Calender: -> Code At: [/app/src/main/java/com/tejpratapsingh/recyclercalendaractivity/horizontal](https://github.com/tejpratap46/RecyclerCalendarAndroid/tree/master/app/src/main/java/com/tejpratapsingh/recyclercalendaractivity/horizontal)
![Week Calender.gif](https://raw.githubusercontent.com/tejpratap46/RecyclerCalendarAndroid/master/sample_images/week_example.gif)

### 2. Month With Events: -> Code At: [/app/src/main/java/com/tejpratapsingh/recyclercalendaractivity/vertical](https://github.com/tejpratap46/RecyclerCalendarAndroid/tree/master/app/src/main/java/com/tejpratapsingh/recyclercalendaractivity/vertical)
![Month With Events.gif](https://raw.githubusercontent.com/tejpratap46/RecyclerCalendarAndroid/master/sample_images/month_vertical.gif)

### 2. Month With Swipe Pages And Event With Progress: -> Code At: [/app/src/main/java/com/tejpratapsingh/recyclercalendaractivity/viewpager](https://github.com/tejpratap46/RecyclerCalendarAndroid/tree/master/app/src/main/java/com/tejpratapsingh/recyclercalendaractivity/viewpager)
![Month With Events.gif](https://raw.githubusercontent.com/tejpratap46/RecyclerCalendarAndroid/master/sample_images/progress_sample.gif)

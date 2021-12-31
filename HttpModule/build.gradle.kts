apply plugin: 'com.android.library'
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-android-extensions'

android {
    compileSdkVersion compile_sdk_version
    buildToolsVersion build_tools_version

    defaultConfig {
        minSdkVersion min_sdk_version
        targetSdkVersion target_sdk_version
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
    lintOptions {
        abortOnError false
    }
}

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation "androidx.core:core-ktx:$kotlin_core"
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    implementation "androidx.appcompat:appcompat:$appcompat_version"
    implementation 'com.google.code.gson:gson:2.8.2'
    //kotlin
    api "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.1"
    api "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.1"
    //lifecycle
    api 'androidx.lifecycle:lifecycle-runtime:2.4.0-alpha01'
    api 'androidx.lifecycle:lifecycle-runtime-ktx:2.4.0-alpha01'
    api 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0'
    api "androidx.lifecycle:lifecycle-extensions:2.2.0"
    // liveData
    api "androidx.lifecycle:lifecycle-livedata-ktx:2.2.0"
    // viewModel
    api "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
    api "androidx.fragment:fragment-ktx:1.2.5"
    // okHttp
    api "com.squareup.okhttp3:okhttp:4.2.0"
    api "com.squareup.retrofit2:retrofit:2.6.1"
    api "com.squareup.okhttp3:logging-interceptor:4.2.0"
    api "com.squareup.retrofit2:converter-gson:2.6.1"
}

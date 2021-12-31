plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(ProjectVersions.compileSdkVersion)
    buildToolsVersion(ProjectVersions.buildToolsVersion)
    defaultConfig {
        minSdkVersion(ProjectVersions.minSdkVersion)
        targetSdkVersion(ProjectVersions.targetSdkVersion)
        applicationId = ProjectVersions.applicationId
        versionCode = ProjectVersions.versionCode
        versionName = ProjectVersions.versionName
        multiDexEnabled = true
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    sourceSets {
        getByName("main") {
            java.srcDirs("src/main/java","src/main/aidl")
        }
    }
    lintOptions {
        isAbortOnError = false
    }
    //指定jdk版本
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    packagingOptions {
        exclude("META-INF/proguard/coroutines.pro")
    }
}
repositories {
    flatDir {
        dirs("libs")
    }
    google()
}

dependencies {

    implementation(Libs.kotlinCore)
    implementation(Libs.kotlinVersion)

    implementation(Libs.junit)
    implementation(Libs.glide)
    implementation(Libs.appcompat)
    implementation(Libs.recyclerview)
    implementation(Libs.material)
    implementation(Libs.constraintlayout)

    implementation(Libs.retrofit)
    implementation(Libs.retrofitConverterGson)
    implementation(Libs.okhttp)

    implementation(Libs.rxandroid)
    implementation(Libs.rxbinding)
    implementation(Libs.rxjava)
    implementation(Libs.retrofitRxAdapter)
    implementation(Libs.rxlifecycle)
    implementation(Libs.rxlifecycleAndroid)
    implementation(Libs.rxlifecycleComponents)

    implementation(Libs.navigation)
    implementation(Libs.navigationUiKtx)
    implementation(Libs.navigationFeatures)

    implementation(Libs.paging)
    implementation(Libs.lifecycleExt)
    implementation(Libs.viewmodelKtx)
    implementation(Libs.fragmentKtx)

    implementation(Libs.loadsir)

    implementation{ project(":HttpModule") }
    implementation{ project(":UtilsLibrary") }
    implementation{ project(":QrLibrary") }
}

plugins {
    kotlin("jvm")
    kotlin("android")
    kotlin("android.extensions")
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
    lintOptions {
        isAbortOnError = false
    }
}

dependencies {
    implementation(Libs.kotlinCore)
    implementation(Libs.kotlinVersion)

    implementation(Libs.glide)
    implementation(Libs.appcompat)
    implementation(Libs.recyclerview)
    implementation(Libs.material)
    implementation(Libs.constraintlayout)
    implementation(Libs.gson)
    implementation(Libs.refreshLayout)
}

plugins {
    id("com.android.library")
    id("kotlin-android")
}

android(Action {
    compileSdkVersion(ProjectVersions.compileSdkVersion)
    defaultConfig(Action {
        minSdkVersion(ProjectVersions.minSdkVersion)
        targetSdkVersion(ProjectVersions.targetSdkVersion)
        versionCode = ProjectVersions.versionCode
        versionName = ProjectVersions.versionName
        multiDexEnabled = true
    })
    buildTypes(Action {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    })
    lintOptions {
        isAbortOnError = false
    }
})

dependencies {
    implementation(Libs.kotlinCore)
//    implementation(Libs.kotlinVersion)

    implementation(Libs.appcompat)
    implementation(Libs.gson)

    implementation(Libs.lifecycleRuntime)
    implementation(Libs.lifecycleRuntimeKtx)
    implementation(Libs.viewmodelKtx)
    implementation(Libs.liveData)

    implementation(Libs.okhttp)
    implementation(Libs.retrofit)
    implementation(Libs.retrofitConverterGson)
    implementation(Libs.okhttpLogging)
}

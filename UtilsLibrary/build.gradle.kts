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

    lintOptions(Action {
        isAbortOnError = false
    })
})

dependencies {
    implementation(Libs.kotlinCore)
//    implementation(Libs.kotlinVersion)

    implementation(Libs.glide)
    implementation(Libs.appcompat)
    implementation(Libs.recyclerview)
    implementation(Libs.material)
    implementation(Libs.constraintlayout)
    implementation(Libs.gson)
    implementation(Libs.refreshLayout)
}

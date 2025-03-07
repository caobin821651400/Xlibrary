plugins {
    id("com.android.application")
    id("com.google.dagger.hilt.android")
    kotlin("android")
    kotlin("kapt")
}

android(Action {
    compileSdkVersion(ProjectVersions.compileSdkVersion)
    defaultConfig(Action {
        minSdkVersion(ProjectVersions.minSdkVersion)
        targetSdkVersion(ProjectVersions.targetSdkVersion)
        applicationId = ProjectVersions.applicationId
        versionCode = ProjectVersions.versionCode
        versionName = ProjectVersions.versionName
        multiDexEnabled = true
    })

    signingConfigs {
        create("release") {
            storeFile = file("../jxGlobal.jks")
            storePassword = "juxue123321"
            keyAlias = "tvbrowser"
            keyPassword = "juxue123321"
        }
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("release")
        }
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    kotlinOptions(Action { jvmTarget = "1.8" })


    sourceSets {
        getByName("main") {
            java.srcDirs("src/main/java", "src/main/aidl")
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
        exclude("META-INF/gradle/incremental.annotation.processors")
    }
})
kapt {
    correctErrorTypes = true
}
dependencies {
    implementation(Libs.kotlinCore)
//    implementation(Libs.kotlinVersion)

    implementation(Libs.junit)
    implementation(Libs.glide)
    implementation(Libs.appcompat)
    implementation(Libs.recyclerview)
    implementation(Libs.material)
    implementation(Libs.constraintlayout)
    implementation(Libs.refreshLayout)

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

    implementation(Libs.koin)
    implementation(Libs.paging)
    implementation(Libs.viewmodelKtx)
    implementation(Libs.fragmentKtx)

    implementation(Libs.loadsir)
    implementation(Libs.hilt)
    kapt(Libs.hiltCompiler)

    implementation(project(":HttpModule"))
    implementation(project(":UtilsLibrary"))
//    implementation(project(":ComposeModule"))
//    implementation(project(":QrLibrary"))
}

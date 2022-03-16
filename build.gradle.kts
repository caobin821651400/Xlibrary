// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://androidx.dev/snapshots/builds/6543454/artifacts/repository/")
        maven("https://maven.aliyun.com/repository/google/")
        maven("https://maven.aliyun.com/repository/public/")
        maven("https://maven.aliyun.com/repository/gradle-plugin/")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.2.1")
//        classpath("com.hujiang.aspectjx:gradle-android-plugin-aspectjx:2.0.10")
        classpath(kotlin("gradle-plugin", version = Versions.kotlinVersion))
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://androidx.dev/snapshots/builds/6543454/artifacts/repository/")
        maven("https://maven.aliyun.com/repository/google/")
        maven("https://maven.aliyun.com/repository/public/")
        maven("https://maven.aliyun.com/repository/gradle-plugin/")
    }
}

tasks.register("clean", Delete::class, Action {
    delete(rootProject.buildDir)
})

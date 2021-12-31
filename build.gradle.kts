// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        maven  ("https://androidx.dev/snapshots/builds/6543454/artifacts/repository/")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.2.1")
        classpath(kotlin("gradle-plugin", version = Versions.kotlinVersion))
    }
}

allprojects {
    repositories {
        google()
        maven  ("https://jitpack.io")
        maven  ("https://androidx.dev/snapshots/builds/6543454/artifacts/repository/")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

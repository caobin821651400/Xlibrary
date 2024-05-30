//rootProject.buildFileName = "build.gradle.kts"
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.aliyun.com/repository/google/")
        maven("https://maven.aliyun.com/repository/public/")
        maven("https://maven.aliyun.com/repository/gradle-plugin/")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://maven.aliyun.com/repository/google/")
        maven("https://maven.aliyun.com/repository/public/")
        maven("https://maven.aliyun.com/repository/gradle-plugin/")
//        maven {
//            isAllowInsecureProtocol = true
//            setUrl("http://nexus.tvplus.club:11574/repository/maven-dianshijia/")
//        }
    }
}


rootProject.name = "Test"
//业务层
include(":javalibrary")
include(":HttpModule")
include(":app")
include(":UtilsLibrary")
//include(":ComposeModule")
//include(":DesignMode")

//buildscript {
//    repositories {
//        google()
//        mavenCentral()
//        maven("https://androidx.dev/snapshots/builds/6543454/artifacts/repository/")
//        maven("https://maven.aliyun.com/repository/google/")
//        maven("https://maven.aliyun.com/repository/public/")
//        maven("https://maven.aliyun.com/repository/gradle-plugin/")
//    }
//    dependencies {
//        classpath("com.android.tools.build:gradle:4.2.1")
////        classpath("com.hujiang.aspectjx:gradle-android-plugin-aspectjx:2.0.10")
//        classpath(kotlin("gradle-plugin", version = Versions.kotlinVersion))
//    }
//}
//
//allprojects {
//    repositories {
//        google()
//        mavenCentral()
//        maven("https://jitpack.io")
//        maven("https://androidx.dev/snapshots/builds/6543454/artifacts/repository/")
//        maven("https://maven.aliyun.com/repository/google/")
//        maven("https://maven.aliyun.com/repository/public/")
//        maven("https://maven.aliyun.com/repository/gradle-plugin/")
//    }
//}
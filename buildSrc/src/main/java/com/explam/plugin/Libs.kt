/**
 * @author: bincao2
 * @date: 2021/12/31 11:19
 * @desc: 描述
 * @updateUser: 更新者
 * @updateDate: 2021/12/31 11:19
 * @updateRemark: 更新说明
 */
object Libs {

    //Kotlin
    const val kotlinCore = "androidx.core:core-ktx:${Versions.kotlinCore}"
//    const val kotlinVersion = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinVersion}"

    //Common
    const val junit = "junit:junit:${Versions.junitVersion}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glideVersion}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompatVersion}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.rvVersion}"
    const val material = "com.google.android.material:material:${Versions.materialVersion}"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayoutVersion}"
    const val refreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.refreshLayoutVersion}"
    const val gson = "com.google.code.gson:gson:${Versions.gsonVersion}"

    //Internet
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    const val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofitConverterGsonVersion}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttpVersion}"
    const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpVersion}"

    //Rx
    const val rxbinding = "com.jakewharton.rxbinding3:rxbinding:${Versions.rxbindingVersion}"
    const val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rxjavaVersion}"
    const val rxandroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxandroidVersion}"
    const val retrofitRxAdapter =
        "com.jakewharton.retrofit:retrofit2-rxjava2-adapter:${Versions.retrofitRxAdapterVersion}"
    const val rxlifecycle = "com.trello.rxlifecycle3:rxlifecycle:${Versions.rxLifeVersion}"
    const val rxlifecycleAndroid = "com.trello.rxlifecycle3:rxlifecycle-android:${Versions.rxLifeVersion}"
    const val rxlifecycleComponents = "com.trello.rxlifecycle3:rxlifecycle-components:${Versions.rxLifeVersion}"

    //Navigation
    const val navigation = "androidx.navigation:navigation-fragment-ktx:${Versions.navigationVersion}"
    const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigationVersion}"
    const val navigationFeatures = "androidx.navigation:navigation-dynamic-features-fragment:${Versions.navigationVersion}"

    //Jetpack
    const val paging = "androidx.paging:paging-runtime:${Versions.pagingVersion}"
    //     const val navigation ="androidx.work:work-runtime-ktx:${Versions.workVersion}"
    const val viewmodelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewmodelKtxVersion}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtxVersion}"
    const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeVersion}"
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime:${Versions.lifecycleRuntimeVersion}"
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.liveDataVersion}"


    //Other
    const val loadsir = "com.kingja.loadsir:loadsir:${Versions.loadsirVersion}"
    const val hilt = "com.google.dagger:hilt-android:${Versions.hiltVersion}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hiltVersion}"
}
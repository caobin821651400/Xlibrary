package com.example.cb.test.jetpack.hilt.ok

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * @author: cb
 * @date: 2024/5/30 18:10
 * @desc: 描述
 */
@Module
@InstallIn(ActivityComponent::class)
class OkHttpModule {


    @Provides
    fun providerOkHttp(
        intercept: OkHttpIntercept
    ): OkHttpClient2 {
        val client = OkHttpClient2()
        client.addIntercept(intercept)
        return client
    }
}
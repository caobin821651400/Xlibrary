package com.example.cb.test.jetpack.hilt.ok

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Named

/**
 * @author: cb
 * @date: 2024/5/30 18:10
 * @desc: 描述
 */
@Module
@InstallIn(ActivityComponent::class)
class OkHttpModule {

    companion object {
        const val NAME_SPACE_OKHTTP_NO_INTERCEPT = "name_space_okhttp_no_intercept"
    }

    /**
     * 有拦截器的
     * @param intercept OkHttpIntercept
     * @return OkHttpClient2
     */
    @Provides
    fun providerOkHttp(
        intercept: OkHttpIntercept
    ): OkHttpClient2 {
        val client = OkHttpClient2()
        client.addIntercept(intercept)
        return client
    }


    /**
     * 无拦截器的,区分不同的provider
     * @return OkHttpClient2
     */
    @Named(NAME_SPACE_OKHTTP_NO_INTERCEPT)
    @Provides
    fun providerOkHttpNoIntercept(): OkHttpClient2 {
        return OkHttpClient2()
    }
}
package com.example.cb.test.jetpack.hilt.single

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author: cb
 * @date: 2024/5/31 10:29
 * @desc: 提供单例,作用于是Application,生命周期也是Application
 */
@Module
@InstallIn(SingletonComponent::class)
class SingleScopedModule {

    @Provides
    @Singleton
    fun providerSingleA(): SingleA {
        return SingleA()
    }
}
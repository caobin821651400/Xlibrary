package com.example.cb.test.jetpack.hilt.single

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Singleton

/**
 * @author: cb
 * @date: 2024/5/31 10:29
 * @desc: 作用域在activity生命周期内
 */
@Module
@InstallIn(ActivityRetainedComponent::class)
class ActivityScopedModule {

    @Provides
    @ActivityRetainedScoped
    fun providerSingleA(): SingleB {
        return SingleB()
    }
}
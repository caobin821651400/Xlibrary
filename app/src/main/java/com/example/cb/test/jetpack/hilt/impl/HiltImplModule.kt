package com.example.cb.test.jetpack.hilt.impl

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * @author: cb
 * @date: 2024/5/30 17:45
 * @desc: 描述
 */
@Module
@InstallIn(ActivityComponent::class)
abstract class HiltImplModule {

    @Binds
    abstract fun bindHiltInterface(impl: HiltImpl): HiltInterface
}
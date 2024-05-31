package com.example.cb.test.jetpack.hilt.impl

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Qualifier

/**
 * @author: cb
 * @date: 2024/5/30 17:45
 * @desc: 描述
 */
@Module
@InstallIn(ActivityComponent::class)
abstract class HiltImplModule {

    @Binds
    @Type1
    abstract fun bindHiltInterface(impl: HiltImpl): HiltInterface

    @Binds
    @Type2
    abstract fun bindHiltInterface2(impl: HiltImpl2): HiltInterface


    /**
     * 不同实现类
     */
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class Type1

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class Type2
}
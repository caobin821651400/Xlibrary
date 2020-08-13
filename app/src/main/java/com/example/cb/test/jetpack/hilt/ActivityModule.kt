package com.example.cb.test.jetpack.hilt

import com.example.cb.test.jetpack.hilt.other.HiltObject
import com.example.cb.test.jetpack.hilt.other.HiltParamsObject
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewComponent
import javax.inject.Qualifier

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/11 14:59
 * @Desc :
 * ====================================================
 */
@Module
@InstallIn(ActivityComponent::class, ViewComponent::class)
class ActivityModule {

    @Type1
    @Provides
    fun providesString(): String {
        return "ActivityModule String"
    }

    @Type2
    @Provides
    fun providesString2(): String {
        return "ActivityModule String2"
    }

    @Provides
    fun providesBean(): HiltBean {
        return HiltBean("name", "曹斌")
    }

    @Provides
    fun providersHiltTextView(): HiltObject {
        return HiltObject()
    }


    /**
     * 如果返回类型相同值不同需要定义两个自定义注解来区分
     */
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    internal annotation class Type1


    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    internal annotation class Type2
}
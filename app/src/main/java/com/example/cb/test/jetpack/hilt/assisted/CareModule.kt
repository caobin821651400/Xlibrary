package com.example.cb.test.jetpack.hilt.assisted

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

/**
 * @author: cb
 * @date: 2024/5/31 11:07
 * @desc: 描述
 */
@Module
@InstallIn(ActivityRetainedComponent::class)
class CareModule {

    @ActivityRetainedScoped
    @Provides
    fun providerXiaoMiWheel(): Wheel {
        return Wheel("米其林")
    }

    @ActivityRetainedScoped
    @Provides
    fun providerXiaoMiEngine(): Engine {
        return Engine("永磁同步电机")
    }

//    @ActivityRetainedScoped
//    @Provides
//    fun providerXiaoMiCar(
//        wheel: Wheel,
//        engine: Engine
//    ) {
//        return Car(
//            wheel = wheel,
//            engine = engine,
//            //👇🏻出问题的部分，我们无法明确这个编号
//            number = ???
//        )
//    }
}
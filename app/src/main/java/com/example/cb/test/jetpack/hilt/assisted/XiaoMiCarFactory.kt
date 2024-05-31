package com.example.cb.test.jetpack.hilt.assisted

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

/**
 * @author: cb
 * @date: 2024/5/31 11:13
 * @desc: 描述
 */
@AssistedFactory
interface XiaoMiCarFactory {


    fun createCar(
        @Assisted("number") num: Int,
        @Assisted("price") price: Int = 0
    ): Car
}
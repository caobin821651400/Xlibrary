package com.example.cb.test.jetpack.hilt.assisted

import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

/**
 * @author: cb
 * @date: 2024/5/31 11:06
 * @desc: 描述
 */
class Car @AssistedInject constructor(
    val wheel: Wheel,
    val engine: Engine,
    @Assisted("number")
    val number: Int,
    @Assisted("price")
    val price: Int
) {

}
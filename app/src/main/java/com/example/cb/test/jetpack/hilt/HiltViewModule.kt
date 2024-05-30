package com.example.cb.test.jetpack.hilt


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/11 15:43
 * @Desc :
 * ====================================================
 */
class HiltViewModule /*@ViewModelInject*/ constructor(
        private var s: String,
        /*@Assisted*/ private val savedState: SavedStateHandle
) : ViewModel() {


}

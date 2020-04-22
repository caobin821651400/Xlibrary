package com.example.cb.test.jetpack.livedata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/4/22 12:46
 * @Desc :
 * ====================================================
 */
class NameViewModule : ViewModel() {
    var currentName: MutableLiveData<String>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
            }
            return field
        }
}
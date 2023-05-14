package com.example.cb.test.kotlin.flow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * @author: bincao2
 * @date: 2021/12/10 16:48
 * @desc: 描述
 * @updateUser: 更新者
 * @updateDate: 2021/12/10 16:48
 * @updateRemark: 更新说明
 */
class FlowHotClodViewModel : ViewModel() {


    val liveData=MutableLiveData<String>()

    sealed class NavigationState(var state: String) {
        data class EmptyNavigation(var value: String) : NavigationState(value)
        data class ViewNavigation(var value: String) : NavigationState(value)
    }

    sealed class NavigationTarget {
        data class Go2Main(var value: String) : NavigationTarget()
        data class Go2User(var value: String) : NavigationTarget()
    }

    //SharedFlow 没有默认值
    //replay 重新发送订阅者之前以发送出的值 [粘性事件]
    val intentEvent = MutableSharedFlow<NavigationTarget>(replay = 1)

    //stateFlow必须要有默认值
    val uiNavigation = MutableStateFlow<NavigationState>(NavigationState.EmptyNavigation("EmptyNavigation"))


    /**
     * 模拟请求数据
     * @param value Int
     * @return String
     */
    suspend fun getData(value: Int): String {
        delay(1000)
        return "数据：$value"
    }
}
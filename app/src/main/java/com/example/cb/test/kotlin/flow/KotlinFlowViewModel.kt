package com.example.cb.test.kotlin.flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * @author: bincao2
 * @date: 2021/12/10 16:48
 * @desc: 描述
 * @updateUser: 更新者
 * @updateDate: 2021/12/10 16:48
 * @updateRemark: 更新说明
 */
class KotlinFlowViewModel : ViewModel() {

    private val effect: Channel<Effect> = Channel()

    val mEffect = effect.receiveAsFlow()


    private fun setEffect(block: () -> Effect) {
        viewModelScope.launch {
            effect.send(block())
        }
    }

    fun showToast(text: String) {
        setEffect {
            Effect.ShowToast(text)
        }
    }

    sealed class Effect {
        data class ShowToast(val text: String) : Effect()
    }
}
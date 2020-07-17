package com.example.cb.test.jetpack.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/6/23 14:52
 * @Desc :给予LiveData实现的事件总线
 * ====================================================
 */
    object LiveDataBus {

    private val map = HashMap<String, EventLiveData<*>>()


    @Suppress("UNCHECKED_CAST")
    fun <T> with(key: String, type: Class<T>? = null): EventLiveData<T> {
        if (!map.containsKey(key)) {
            map[key] = EventLiveData<T>()
        }
        return map[key] as? EventLiveData<T>
                ?: throw RuntimeException("cannot cast EventLiveData of " + type.toString())
    }

    /**
     * 记录版本的 MutableLiveData。
     */
    class EventLiveData<T> : MutableLiveData<T>() {
        var version: Int = 0
            private set

        /**
         * postValue 最终也会调 setValue ，所以只需要在这里统计 version。
         */
        override fun setValue(value: T) {
            version++
            super.setValue(value)
        }

        /**
         * 订阅事件。绑定生命周期。返回 Observer 方便主动移除观察。
         */
        fun observeEvent(owner: LifecycleOwner, onEvent: (T) -> Unit): Observer<T> {
            return EventObserver(onEvent).apply { super.observe(owner, this) }
        }

        /**
         * 订阅事件。永久有效。返回 Observer 方便主动移除观察。
        */
        fun observeForeverEvent(onEvent: (T) -> Unit): Observer<T> {
            return EventObserver(onEvent).apply { super.observeForever(this) }
        }

        /**
         * 不接收创建之前的消息。
         */
        inner class EventObserver<T>(val onEvent: (T) -> Unit) : Observer<T> {
            private var observerVersion = this@EventLiveData.version
            override fun onChanged(t: T) {
                if (observerVersion < this@EventLiveData.version) {
                    observerVersion = this@EventLiveData.version
                    onEvent(t)
                }
            }
        }
    }
}
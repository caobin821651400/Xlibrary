package cn.sccl.http.upload

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import java.util.concurrent.ConcurrentHashMap

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/9/8 10:33
 * @Desc :
 * ====================================================
 */
object UpLoadPool {

    private val scopeMap = ConcurrentHashMap<String, CoroutineScope>()

    /**
     * 加入
     */
    fun add(
            key: String,
            job: CoroutineScope
    ) {
        scopeMap[key] = job
    }


    /**
     * 移除
     */
    fun remove(key: String) {
        val scope = scopeMap[key]
        scope?.apply {
            if (isActive) cancel()
        }
        scopeMap.remove(key)
    }

    /**
     * 移除全部
     */
    fun removeAll() {
        scopeMap.forEach {
            if (it.value.isActive) {
                it.value.cancel()
            }
        }
        scopeMap.clear()
    }
}
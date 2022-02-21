package cn.sccl.http.cache

import kotlinx.coroutines.async
import kotlinx.coroutines.flow.channelFlow

/**
 * @author: bincao2
 * @date: 2022/2/21 9:53
 * @desc: 描述
 * @updateUser: 更新者
 * @updateDate: 2022/2/21 9:53
 * @updateRemark: 更新说明
 */
abstract class CacheRepository<T> {


    fun fetchData() = channelFlow<CacheResult<T>> {
        val local = async {
            fetchDataFromLocal().also {
                if (it is CacheResult.Success) {
                    send(it)
                }
            }
        }

        val network = async {
            fetchDataFromNetWork().also {
                if (it is CacheResult.Success) {
                    send(it)
                    local.cancel()
                }
            }
        }

        //本地数据和网络数据，都加载失败的情况
        val localData = local.await()
        val networkData = network.await()
        if (localData is CacheResult.Error && networkData is CacheResult.Error) {
            send(CacheResult.Error(Throwable("load data error")))
        }
    }


    protected abstract suspend fun fetchDataFromLocal(): CacheResult<T>

    protected abstract suspend fun fetchDataFromNetWork(): CacheResult<T>


    sealed class CacheResult<out R> {
        data class Success<out T>(val data: T) : CacheResult<T>()
        data class Error(val throwable: Throwable) : CacheResult<Nothing>()
    }
}
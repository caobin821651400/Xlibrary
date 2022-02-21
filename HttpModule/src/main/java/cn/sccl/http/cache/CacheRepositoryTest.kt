package cn.sccl.http.cache

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * @author: bincao2
 * @date: 2022/2/21 10:12
 * @desc: 描述
 * @updateUser: 更新者
 * @updateDate: 2022/2/21 10:12
 * @updateRemark: 更新说明
 */
class CacheRepositoryTest : AppCompatActivity() {

    companion object {
        const val TAG = "CacheRepositoryTest"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    private fun test() {
        lifecycleScope.launch {
            TestRepository().fetchData()
                .onStart { Log.d(TAG, "TestRepository: onStart") }
                .onCompletion { Log.d(TAG, "TestRepository: onCompletion") }
                .catch { Log.d(TAG, "collect: $it") }
        }
    }


    class TestRepository : CacheRepository<String>() {

        override suspend fun fetchDataFromLocal(): CacheResult<String> {
            delay(1000)
            return CacheResult.Success("fetchDataFromLocal 成功")
        }

        override suspend fun fetchDataFromNetWork(): CacheResult<String> {
            delay(2000)
            return CacheResult.Success("fetchDataFromNetWork 成功")
        }
    }
}
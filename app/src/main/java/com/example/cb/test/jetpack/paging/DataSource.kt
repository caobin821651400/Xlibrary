package com.example.cb.test.jetpack.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import cn.sccl.http.XHttp
import cn.sccl.xlibrary.utils.XLogUtils
import com.example.cb.test.kotlin.coroutines.net.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DataSource() : PagingSource<Int, RepoResponse.Repo>() {

    override fun getRefreshKey(state: PagingState<Int, RepoResponse.Repo>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepoResponse.Repo> {

        return try {
            //默认从第一页开始
            val page = params.key ?: 1
            val pageSize = params.loadSize
            //retrofit 请求
            val response = withContext(Dispatchers.IO) {
                XLogUtils.d("线程 " + Thread.currentThread().name)
                XHttp.getService(ApiService::class.java).getGitHubData(page, pageSize)
            }
            val currItem = response.items
            //判断是否有上下一页
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (currItem.isNotEmpty()) page + 1 else null
            LoadResult.Page(currItem, prevKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }


//        PagedList.Config.Builder()
//            .setEnablePlaceholders(false)
//            .setInitialLoadSizeHint(20)
//            .setPageSize(1).build()
    }


}
package com.example.cb.test.jetpack.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import cn.sccl.http.XHttp
import com.example.cb.test.kotlin.coroutines.PageListDataUiState
import com.example.cb.test.kotlin.coroutines.net.ApiService
import com.example.cb.test.kotlin.coroutines.net.WanAndroidBean

class DataSource() {/*: PagingSource<Int, PageListDataUiState<WanAndroidBean>>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PageListDataUiState<WanAndroidBean>> {
        return kotlin.runCatching {
            val page = params.key ?: 1
            val pageSize = params.loadSize
//           val response=viewModule.getData(false)
            val repoResponse = XHttp.getService(ApiService::class.java)
                    .getGitHubData(page, pageSize)
            val repoItems = repoResponse.items
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (repoItems.isNotEmpty()) page + 1 else null
            LoadResult.Page(repoItems, prevKey, nextKey)
        }.onFailure {
            LoadResult.Error(it.cause)
        }

        try {

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PageListDataUiState<WanAndroidBean>>): Int? {
        return null
    }*/
}
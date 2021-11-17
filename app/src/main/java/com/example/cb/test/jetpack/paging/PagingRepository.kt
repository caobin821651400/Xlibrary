package com.example.cb.test.jetpack.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

/**
 * @author: bincao2
 * @date: 2021/8/13 16:35
 * @desc: 描述
 * @updateUser: 更新者
 * @updateDate: 2021/8/13 16:35
 * @updateRemark: 更新说明
 */
object PagingRepository {

    private const val PAGE_SIZE = 20

    fun getData(): Flow<PagingData<RepoResponse.Repo>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE,enablePlaceholders=true),
            pagingSourceFactory = { DataSource() }
        ).flow
    }

}
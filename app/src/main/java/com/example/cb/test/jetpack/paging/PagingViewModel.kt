package com.example.cb.test.jetpack.paging

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import cn.sccl.http.core.BaseViewModule
import kotlinx.coroutines.flow.Flow

/**
 * @author: bincao2
 * @date: 2021/8/13 16:44
 * @desc: 描述
 * @updateUser: 更新者
 * @updateDate: 2021/8/13 16:44
 * @updateRemark: 更新说明
 */
class PagingViewModel : BaseViewModule() {

    /**
     * 通过Paging请求数据
     */
    fun getPagingData(): Flow<PagingData<RepoResponse.Repo>> {
        return PagingRepository.getData().cachedIn(viewModelScope)
    }

}
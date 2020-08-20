package com.example.cb.test.kotlin.coroutines

import android.view.View
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cn.sccl.xlibrary.adapter.XRecyclerViewAdapter
import com.example.cb.test.R
import com.example.cb.test.weight.loadCallBack.EmptyCallback
import com.example.cb.test.weight.loadCallBack.ErrorCallback
import com.example.cb.test.weight.loadCallBack.LoadingCallback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir

///数据分页拓展函数


/**
 * 替换出错时的文字
 */
fun LoadService<*>.setErrorText(message: String) {
    if (message.isNotEmpty()) {
        this.setCallBack(ErrorCallback::class.java) { _, view ->
            view.findViewById<TextView>(R.id.error_text).text = message
        }
    }
}

/**
 * 设置错误布局
 * @param message 错误布局显示的提示内容
 */
fun LoadService<*>.showError(message: String = "") {
    this.setErrorText(message)
    this.showCallback(ErrorCallback::class.java)
}

/**
 * 设置空布局
 */
fun LoadService<*>.showEmpty() {
    this.showCallback(EmptyCallback::class.java)
}

/**
 * 设置加载中
 */
fun LoadService<*>.showLoading() {
    this.showCallback(LoadingCallback::class.java)
}

/**
 * 界面状态加载
 * @param view 需要在view下面嵌套
 * @param callBack 重试回调
 */
fun loadServiceInit(view: View, callBack: () -> Unit): LoadService<Any> {
    val loadSir = LoadSir.getDefault().register(view) {
        callBack.invoke()
    }
    loadSir.showSuccess()
    return loadSir
}

/**
 * 分页数据加载
 */
fun <T> loadListData(
        data: XListPageDataUi<T>,
        adapter: XRecyclerViewAdapter<T>,
        loadService: LoadService<*>,
        swipeRefreshLayout: SwipeRefreshLayout
) {
    swipeRefreshLayout.isRefreshing = false
    if (data.isSuccess) {
        when {
            data.isFirstEmpty -> {
                loadService.showEmpty()
            }
            data.isRefresh -> {
                //第一页
                adapter.dataLists = data.listData
                loadService.showSuccess()
            }
            else -> {
                adapter.addAll(data.listData)
                loadService.showSuccess()
            }
        }
        //分页
        if (data.isHasMore) {
            adapter.isLoadMore(true)
        } else {
            adapter.showLoadComplete()
        }
    } else {
        if (data.isRefresh) {
            loadService.showError(data.errMsg)
        } else {
            adapter.showLoadError()
        }
    }


}
package com.example.cb.test.download

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.sccl.http.exception.NetException
import cn.sccl.xlibrary.adapter.XRecyclerViewAdapter
import cn.sccl.xlibrary.adapter.XViewHolder
import com.example.cb.test.R
import com.example.cb.test.download.DownLoadActivity.DownLoadInfo

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/9/1 21:49
 * @Desc :
 * ====================================================
 */
class DownLoadAdapter(private val rv: RecyclerView) :
        XRecyclerViewAdapter<DownLoadInfo>(rv, R.layout.item_download) {

    private var onStart: ((info: DownLoadInfo) -> Unit)? = null
    private var onPause: ((info: DownLoadInfo) -> Unit)? = null

    fun setListener(
            startClick: ((info: DownLoadInfo) -> Unit),
            pauseClick: ((info: DownLoadInfo) -> Unit)
    ) {
        onStart = startClick
        onPause = pauseClick
    }

    override fun bindData(holder: XViewHolder, data: DownLoadInfo, position: Int) {
        val tvWaiting = holder.getView<TextView>(R.id.tv_waiting);
        val progressBar = holder.getView<ProgressBar>(R.id.progress_bar);
        val tvProgress = holder.getView<TextView>(R.id.tv_progress);
        val tvSize = holder.getView<TextView>(R.id.tv_size);
        val btPause = holder.getView<Button>(R.id.bt_pause);

        btPause.setOnClickListener {
            if (btPause.text == "开始")
                onStart?.invoke(data)
            else
                onPause?.invoke(data)
        }
    }

    fun setDownLoadError(key: String, throwable: NetException) {
        getChildAt(key)?.findViewById<TextView>(R.id.tv_waiting)?.text = "下载失败"
        getChildAt(key)?.findViewById<TextView>(R.id.bt_pause)?.text = "开始"
    }


    private fun getChildAt(key: String): View? {
        getDataLists().forEachIndexed { index, downLoadInfo ->
            if (downLoadInfo.taskId == key) {
                return rv.getChildAt(index)
            }
        }
        return null
    }

    fun setDownLoadSuccess(key: String, path: String) {
        getChildAt(key)?.findViewById<TextView>(R.id.tv_waiting)?.text = "下载成功"
        getChildAt(key)?.findViewById<TextView>(R.id.bt_pause)?.text = "开始"
    }

    fun setDownLoadProgress(
            key: String,
            progress: Int,
            read: String,
            count: String,
            done: Boolean
    ) {
        getChildAt(key)?.findViewById<TextView>(R.id.bt_pause)?.text = "暂停"
        getChildAt(key)?.findViewById<TextView>(R.id.tv_size)?.text = "$read/$count"
        getChildAt(key)?.findViewById<TextView>(R.id.tv_waiting)?.text = "正在下载"
        getChildAt(key)?.findViewById<TextView>(R.id.tv_progress)?.text = "$progress%"
        getChildAt(key)?.findViewById<ProgressBar>(R.id.progress_bar)?.progress = progress

    }

    fun setDownLoadPause(key: String) {
        getChildAt(key)?.findViewById<TextView>(R.id.tv_waiting)?.text = "已暂停"
        getChildAt(key)?.findViewById<TextView>(R.id.bt_pause)?.text = "开始"
    }

    fun setDownLoadCancel(key: String) {
        getChildAt(key)?.findViewById<TextView>(R.id.tv_waiting)?.text = "已取消"
        getChildAt(key)?.findViewById<TextView>(R.id.bt_pause)?.text = "开始"
    }

    fun setDownLoadPrepare(key: String) {
        getChildAt(key)?.findViewById<TextView>(R.id.tv_waiting)?.text = "等待中..."
        getChildAt(key)?.findViewById<TextView>(R.id.bt_pause)?.text = "暂停"
    }
}
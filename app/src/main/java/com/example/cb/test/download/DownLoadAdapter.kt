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

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/9/1 21:49
 * @Desc :
 * ====================================================
 */
class DownLoadAdapter(private val rv: RecyclerView) :
        XRecyclerViewAdapter<DownloadTask>(rv, R.layout.item_download) {

    private var onStart: ((info: DownloadTask) -> Unit)? = null
    private var onPause: ((info: DownloadTask) -> Unit)? = null

    fun setListener(
            startClick: ((info: DownloadTask) -> Unit),
            pauseClick: ((info: DownloadTask) -> Unit)
    ) {
        onStart = startClick
        onPause = pauseClick
    }

    override fun bindData(holder: XViewHolder, data: DownloadTask, position: Int) {
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
}
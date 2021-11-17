package com.example.cb.test.jetpack.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cb.test.R

/**
 * @author: bincao2
 * @date: 2021/8/13 16:54
 * @desc: 描述
 * @updateUser: 更新者
 * @updateDate: 2021/8/13 16:54
 * @updateRemark: 更新说明
 */
class PagingAdapter() : PagingDataAdapter<RepoResponse.Repo, PagingAdapter.ViewHolder>(COMPARATOR) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<RepoResponse.Repo>() {
            override fun areItemsTheSame(
                oldItem: RepoResponse.Repo,
                newItem: RepoResponse.Repo
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: RepoResponse.Repo,
                newItem: RepoResponse.Repo
            ): Boolean = oldItem == newItem

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_paging_demo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = getItem(position)
        if (repo != null) {
            holder.name.text = repo.name
            holder.description.text = repo.description
            holder.starCount.text = repo.starCount.toString()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name_text)
        val description: TextView = itemView.findViewById(R.id.description_text)
        val starCount: TextView = itemView.findViewById(R.id.star_count_text)
    }
}
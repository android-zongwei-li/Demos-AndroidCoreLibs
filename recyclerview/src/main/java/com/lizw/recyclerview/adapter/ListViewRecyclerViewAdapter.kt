package com.lizw.recyclerview.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lizw.recyclerview.R
import com.lizw.recyclerview.beans.ItemBean

/**
 * 用于
 *
 * author: zongwei.li created on: 2022/6/6
 */
class ListViewRecyclerViewAdapter(data: List<ItemBean>?) :
    BaseRecyclerViewAdapter(data) {
    var mOnRefreshListener: OnRefreshListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = getSubView(parent, viewType)
        return if (viewType == TYPE_NORMAL) {
            InnerViewHolder(view)
        } else {
            val loaderMoreHolder = LoaderMoreHolder(view)
            loaderMoreHolder.update(LOADER_STATE_LOADING)
            loaderMoreHolder
        }
    }

    override fun getSubView(parent: ViewGroup?, viewType: Int): View {
        var view: View? = null
        if (parent != null) {
            view = if (viewType == TYPE_NORMAL) {
                View.inflate(parent.context, R.layout.item_list_view, null)
            } else {
                View.inflate(parent.context, R.layout.item_load_more, null)
            }
        }
        return view!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_NORMAL && holder is InnerViewHolder) {
            holder.setData(mData!![position], position)
        } else {
            (holder as LoaderMoreHolder).update(LOADER_STATE_LOADING)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) {
            TYPE_LOADER_MORE
        } else TYPE_NORMAL
    }

    fun setOnRefreshListener(listener: OnRefreshListener?) {
        mOnRefreshListener = listener
    }

    interface OnRefreshListener {
        fun onUpPullRefresh(loaderMoreHolder: LoaderMoreHolder?)
    }

    inner class LoaderMoreHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var llLoading: LinearLayout = itemView.findViewById(R.id.ll_loading)
        private var tvReload: TextView = itemView.findViewById(R.id.tv_reload)

        fun update(state: Int) {
            //重置控件状态
            llLoading.visibility = View.GONE
            tvReload.visibility = View.GONE
            when (state) {
                LOADER_STATE_LOADING -> {
                    llLoading.visibility = View.VISIBLE
                    //触发加载数据
                    startLoadMore()
                }
                LOADER_STATE_RELOAD -> tvReload.visibility =
                    View.VISIBLE
                LOADER_STATE_NORMAL -> {
                    llLoading.visibility = View.GONE
                    tvReload.visibility = View.GONE
                }
                else -> {}
            }
        }

        private fun startLoadMore() {
            if (mOnRefreshListener != null) {
                mOnRefreshListener!!.onUpPullRefresh(this)
            }
        }

        init {
            tvReload.setOnClickListener { update(LOADER_STATE_LOADING) }
        }
    }

    companion object {
        const val TYPE_NORMAL = 0
        const val TYPE_LOADER_MORE = 1

        const val LOADER_STATE_LOADING = 0
        const val LOADER_STATE_RELOAD = 1
        const val LOADER_STATE_NORMAL = 2
    }
}

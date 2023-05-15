package com.lizw.ui_demos.recyclerview.adapter

import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.lizw.ui_demos.R
import com.lizw.ui_demos.recyclerview.Model
import com.lizw.ui_demos.recyclerview.base.BaseRecyclerViewAdapter
import com.lizw.ui_demos.recyclerview.base.BaseViewHolder
import com.lizw.ui_demos.recyclerview.beans.ItemBean
import com.lizw.ui_demos.recyclerview.viewholder.AViewHolder
import java.util.*

/**
 * 用于
 *
 * author: zongwei.li created on: 2022/6/6
 */
class ListViewRecyclerViewAdapter(data: List<ItemBean>) : BaseRecyclerViewAdapter<ItemBean>(data) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ItemBean> {
        return if (viewType == TYPE_NORMAL) {
            AViewHolder(View.inflate(parent.context, R.layout.item_list_view, null))
        } else {
            val loaderMoreHolder = LoaderMoreHolder(View.inflate(parent.context, R.layout.item_load_more, null))
            loaderMoreHolder.update(LOADER_STATE_LOADING)
            loaderMoreHolder
        }
    }
    
    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) {
            TYPE_LOADER_MORE
        } else TYPE_NORMAL
    }
    
    inner class LoaderMoreHolder(itemView: View) : BaseViewHolder<ItemBean>(itemView) {
        private var llLoading: LinearLayout = itemView.findViewById(R.id.ll_loading)
        private var tvReload: TextView = itemView.findViewById(R.id.tv_reload)
        
        override fun onBindViewHolder(position: Int, itemData: ItemBean) {
            update(LOADER_STATE_LOADING)
        }
        
        fun update(state: Int) {
            //重置控件状态
            llLoading.visibility = View.GONE
            
            tvReload.setOnClickListener { update(LOADER_STATE_LOADING) }
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
            // 上拉加载
            Handler(Looper.getMainLooper()).postDelayed({ //刷新停止，更新列表
                val random = Random()
                if (random.nextInt() % 2 == 0) {
                    Model.loadNewDataEnd()
                    
                    notifyItemChanged(Model.itemData.size - 1)
                    update(LOADER_STATE_NORMAL)
                } else {
                    update(LOADER_STATE_RELOAD)
                }
            }, 1000L)
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

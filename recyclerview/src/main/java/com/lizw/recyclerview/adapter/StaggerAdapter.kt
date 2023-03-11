package com.lizw.recyclerview.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lizw.recyclerview.R
import com.lizw.recyclerview.base.BaseRecyclerViewAdapter
import com.lizw.recyclerview.base.BaseViewHolder
import com.lizw.recyclerview.beans.ItemBean
import com.lizw.recyclerview.viewholder.AViewHolder

/**
 * 用于
 *
 * author: zongwei.li created on: 2022/6/6
 */
class StaggerAdapter(data: List<ItemBean>) : BaseRecyclerViewAdapter<ItemBean>(data) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ItemBean> {
        val view = View.inflate(parent.context, R.layout.item_stagger_view, null)
        return AViewHolder(view)
    }
}
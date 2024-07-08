package com.lizw.ui_demos.recyclerview.adapter

import android.view.View
import android.view.ViewGroup
import com.lizw.ui_demos.R
import com.lizw.ui_demos.recyclerview.base.BaseRecyclerViewAdapter
import com.lizw.ui_demos.recyclerview.base.BaseViewHolder
import com.lizw.ui_demos.recyclerview.beans.ItemBean
import com.lizw.ui_demos.recyclerview.viewholder.AViewHolder

/**
 * 用于
 */
class GridViewRecyclerViewAdapter(val data: List<ItemBean>) : BaseRecyclerViewAdapter<ItemBean>(data) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ItemBean> {
        val view = View.inflate(parent.context, R.layout.item_grid_view, null)
        return AViewHolder(view)
    }
}
package com.lizw.recyclerview.adapter

import android.view.View
import android.view.ViewGroup
import com.lizw.recyclerview.R
import com.lizw.recyclerview.beans.ItemBean

/**
 * 用于
 *
 * author: zongwei.li created on: 2022/6/6
 */
class StaggerAdapter(data: List<ItemBean>?) :
    BaseRecyclerViewAdapter(data) {

    override fun getSubView(parent: ViewGroup?, viewType: Int): View {
        return View.inflate(parent?.context, R.layout.item_stagger_view, null)
    }
}
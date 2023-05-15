package com.lizw.ui_demos.recyclerview.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.lizw.ui_demos.R
import com.lizw.ui_demos.recyclerview.base.BaseRecyclerViewAdapter
import com.lizw.ui_demos.recyclerview.base.BaseViewHolder
import com.lizw.ui_demos.recyclerview.beans.MultiTypeBean

/**
 * 用于
 *
 * author: zongwei.li created on: 2022/6/6
 */
class MultiTypeAdapter(private val data: List<MultiTypeBean>) : BaseRecyclerViewAdapter<MultiTypeBean>(data) {
    companion object {
        const val FULL_IMAGE = 0
        const val THREE_IMAGES = 1
        const val RIGHT_IMAGE = 2
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MultiTypeBean> {
        val view: View
        return when (viewType) {
            FULL_IMAGE -> {
                view = View.inflate(parent.context, R.layout.item_grid_view, null)
                FullImageHolder(view)
            }
            THREE_IMAGES -> {
                view = View.inflate(parent.context, R.layout.item_list_view, null)
                ThreeImagesHolder(view)
            }
            else -> {
                view = View.inflate(parent.context, R.layout.item_stagger_view, null)
                RightImageHolder(view)
            }
        }
    }
    
    override fun getItemViewType(position: Int): Int {
        val typeBean = data[position]
        return typeBean.type
    }
    
    class FullImageHolder(itemView: View) : BaseViewHolder<MultiTypeBean>(itemView) {
        private val ivIcon: ImageView = itemView.findViewById(R.id.icon)
        
        override fun onBindViewHolder(position: Int, itemData: MultiTypeBean) {
            ivIcon.setImageResource(itemData.pic)
        }
    }
    
    class ThreeImagesHolder(itemView: View) : BaseViewHolder<MultiTypeBean>(itemView) {
        private val ivIcon: ImageView = itemView.findViewById(R.id.icon)
        
        override fun onBindViewHolder(position: Int, itemData: MultiTypeBean) {
            ivIcon.setImageResource(itemData.pic)
        }
    }
    
    class RightImageHolder(itemView: View) : BaseViewHolder<MultiTypeBean>(itemView) {
        private val ivIcon: ImageView = itemView.findViewById(R.id.icon)
        
        override fun onBindViewHolder(position: Int, itemData: MultiTypeBean) {
            ivIcon.setImageResource(itemData.pic)
        }
    }
}
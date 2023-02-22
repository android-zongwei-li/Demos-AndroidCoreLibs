package com.lizw.recyclerview.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.lizw.recyclerview.R
import com.lizw.recyclerview.beans.MultiTypeBean

/**
 * 用于
 *
 * author: zongwei.li created on: 2022/6/6
 */
class MultiTypeAdapter(private val data: List<MultiTypeBean>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val FULL_IMAGE = 0
        const val THREE_IMAGES = 1
        const val RIGHT_IMAGE = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            FULL_IMAGE -> {
                (holder as FullImageHolder).setData(data!![position])
            }
            THREE_IMAGES -> {
                (holder as ThreeImagesHolder).setData(data!![position])
            }
            else -> {
                (holder as RightImageHolder).setData(data!![position])
            }
        }
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        val typeBean = data!![position]
        return typeBean.type
    }

    private inner class FullImageHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val ivIcon: ImageView = itemView.findViewById(R.id.icon)

        fun setData(multiTypeBean: MultiTypeBean) {
            ivIcon.setImageResource(multiTypeBean.pic)
        }
    }

    private inner class ThreeImagesHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val ivIcon: ImageView = itemView.findViewById(R.id.icon)
        fun setData(multiTypeBean: MultiTypeBean) {
            ivIcon.setImageResource(multiTypeBean.pic)
        }
    }

    private inner class RightImageHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val ivIcon: ImageView = itemView.findViewById(R.id.icon)
        fun setData(multiTypeBean: MultiTypeBean) {
            ivIcon.setImageResource(multiTypeBean.pic)
        }
    }
}
package com.lizw.recyclerview.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.lizw.recyclerview.R
import com.lizw.recyclerview.base.BaseViewHolder
import com.lizw.recyclerview.beans.ItemBean

class AViewHolder(itemView: View) : BaseViewHolder<ItemBean>(itemView) {
    private val ivIcon: ImageView = itemView.findViewById(R.id.icon)
    private val tvTitle: TextView = itemView.findViewById(R.id.title)
    
    /**
     * 给item设置数据
     *
     * @param itemBean
     */
    private fun setData(itemBean: ItemBean, position: Int) {
        ivIcon.setImageResource(itemBean.icon)
        tvTitle.text = itemBean.text
        
        //找到条目的控件
        itemView.setOnClickListener {
            Toast.makeText(
                    itemView.context,
                    "点击了第" + position + "个",
                    Toast.LENGTH_SHORT
            ).show()
        }
    }
    
    override fun onBindViewHolder(position: Int, itemData: ItemBean) {
        setData(itemData, position)
    }
}
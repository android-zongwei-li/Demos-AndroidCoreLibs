package com.lizw.ui_demos.customview

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lizw.ui_demos.R

/**
 * 作用：
 * 技术栈：
 */
class CustomViewRVAdapter(val data: List<CustomViewItemBean>) : RecyclerView.Adapter<CustomViewRVAdapter.CustomViewItemViewHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_custom_view, parent, false)
        return CustomViewItemViewHolder(itemView)
    }
    
    override fun onBindViewHolder(holder: CustomViewItemViewHolder, position: Int) {
        holder.onBind(data[position])
    }
    
    override fun getItemCount(): Int {
        return data.size
    }
    
    class CustomViewItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val btnViewName: Button = itemView.findViewById(R.id.btn_view_name)
        private val tvViewInfo: TextView = itemView.findViewById(R.id.tv_view_info)
        private val tvViewTechs: TextView = itemView.findViewById(R.id.tv_view_techs)
        
        @SuppressLint("SetTextI18n")
        fun onBind(customViewItemBean: CustomViewItemBean) {
            btnViewName.text = customViewItemBean.name
            btnViewName.setOnClickListener {
                if (customViewItemBean.targetClass != null) {
                    itemView.context.startActivity(Intent(itemView.context, customViewItemBean.targetClass))
                } else {
                    PreviewActivity.startSelf(itemView.context, customViewItemBean.id)
                }
            }
            
            tvViewInfo.text = "简介：${customViewItemBean.info}"
            tvViewTechs.text = "技术点：${customViewItemBean.techs}"
        }
    }
}
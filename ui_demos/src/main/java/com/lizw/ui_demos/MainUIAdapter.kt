package com.lizw.ui_demos

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * author: zongwei.li created on: 2022/9/3
 */
class MainUIAdapter(val data: HashMap<Class<*>, String>) :
    RecyclerView.Adapter<MainUIAdapter.MainUIViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainUIViewHolder {
        val itemView = View.inflate(parent.context, R.layout.item_main_ui, null)
        return MainUIViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MainUIViewHolder, position: Int) {
        holder.initView(holder.itemView.context, data, position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class MainUIViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var btnStartActivity: Button = itemView.findViewById(R.id.btn_start_activity)

        fun initView(context: Context, data: HashMap<Class<*>, String>, position: Int) {
            val keys = data.keys.toMutableList()
            val keyClass = keys[position]
            btnStartActivity.text = data[keyClass]
            btnStartActivity.setOnClickListener {
                if (context is Activity) {
                    context.startActivity(Intent(context, keyClass))
                }
            }
        }
    }
}
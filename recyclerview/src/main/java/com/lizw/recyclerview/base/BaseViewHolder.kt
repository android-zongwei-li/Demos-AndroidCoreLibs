package com.lizw.recyclerview.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * 通过在ViewHolder中添加 onBindViewHolder 方法，
 * adapter将不在关心具体的数据处理，只需要将数据传递给 ViewHolder，处理逻辑在 ViewHolder 中实现。
 * 这样做的好处是，如果adapter中对应着多个 ViewHolder ，不会随之臃肿、复杂化。
 */
abstract class BaseViewHolder<Data>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    
    abstract fun onBindViewHolder(position: Int, itemData: Data)
}
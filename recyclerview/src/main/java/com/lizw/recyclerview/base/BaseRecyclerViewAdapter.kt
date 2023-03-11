package com.lizw.recyclerview.base

import androidx.recyclerview.widget.RecyclerView
import com.lizw.recyclerview.Data

/**
 * 通过构造方法传入数据
 * 思考：
 * 1、数据直接在adapter内部初始化怎么样？
 * 1）不好。数据放里面，适配器就定死了。不够灵活。
 * 2）从设计模式的角度思考，适配器的职责是建立数据和视图的关系。而数据从何而来，如何产生，是不应该关注的。
 * author: zongwei.li created on: 2020/11/25
 */
abstract class BaseRecyclerViewAdapter<Data>(private val mData: List<Data>) : RecyclerView.Adapter<BaseViewHolder<Data>>() {
    
    override fun onBindViewHolder(holder: BaseViewHolder<Data>, position: Int) {
        holder.onBindViewHolder(position, mData[position])
    }
    
    /**
     * 获取返回条目的个数
     *
     * @return 返回条目的个数
     */
    override fun getItemCount(): Int {
        return mData.size
    }
}
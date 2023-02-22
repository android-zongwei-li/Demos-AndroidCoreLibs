package com.lizw.recyclerview.adapter

import com.lizw.recyclerview.beans.ItemBean

/**
 * 提供 RecyclerViewAdapter。
 * author: zongwei.li created on: 2022/7/8
 */
@Deprecated("尝试工厂方法优化，效果不太好")
class RecyclerViewAdapterFactory {
    enum class RecyclerViewAdapterType {
        LIST,       // 列表
        GRID,       // 表格
        STAGGER     // 瀑布流
    }

    fun getAdapter(
        data: List<ItemBean>,
        type: RecyclerViewAdapterType
    ): BaseRecyclerViewAdapter {
        return when (type) {
            RecyclerViewAdapterType.LIST -> ListViewRecyclerViewAdapter(data)
            RecyclerViewAdapterType.GRID -> GridViewRecyclerViewAdapter(data)
            RecyclerViewAdapterType.STAGGER -> StaggerAdapter(data)
        }
    }
}
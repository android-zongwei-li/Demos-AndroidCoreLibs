package com.lizw.recyclerview

import com.lizw.recyclerview.beans.ItemBean
import com.lizw.recyclerview.beans.MultiTypeBean
import java.util.*

/**
 * 负责数据的加载。并对外提供数据。
 * 思考：如果数据量很大，应该考虑异步加载。
 *
 * author: zongwei.li created on: 2022/7/7
 */
object Model {
    
    lateinit var itemData: ArrayList<ItemBean>
    lateinit var multiTypeData: ArrayList<MultiTypeBean>
    
    fun loadData(): Model {
        val data: ArrayList<ItemBean> = ArrayList(Data.icons.size)
        for (i in Data.icons.indices) {
            data.add(ItemBean(Data.icons[i], "第$i 条数据"))
        }
        itemData = data
        
        return this
    }
    
    fun loadMultiData(): Model {
        val data: ArrayList<MultiTypeBean> = ArrayList(Data.icons.size)
        val random = Random()
        for (i in Data.icons.indices) {
            val item = MultiTypeBean(type = random.nextInt(3), pic = Data.icons[i])
            data.add(item)
        }
        multiTypeData = data
        
        return this
    }
    
    /**
     * 真实业务中，此方法可能是异步执行的
     *
     * @return true：数据加载成功
     */
    fun loadNewData(): Boolean {
        val itemBean = ItemBean(R.mipmap.p1, "新添加的")
        itemData.add(0, itemBean)
        return true
    }
    
    fun loadNewDataEnd(): Boolean {
        val itemBean = ItemBean(R.mipmap.p1, "新添加的")
        itemData.add(itemBean)
        return true
    }
}
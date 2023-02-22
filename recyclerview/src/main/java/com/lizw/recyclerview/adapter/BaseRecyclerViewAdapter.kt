package com.lizw.recyclerview.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lizw.recyclerview.R
import com.lizw.recyclerview.beans.ItemBean

/**
 * 通过构造方法传入数据
 * 思考：
 * 1、数据直接在adapter内部初始化怎么样？
 * 1）不好。数据放里面，适配器就定死了。不够灵活。
 * 2）从设计模式的角度思考，适配器的职责是建立数据和视图的关系。而数据从何而来，如何产生，是不应该关注的。
 * author: zongwei.li created on: 2020/11/25
 */
abstract class BaseRecyclerViewAdapter(val mData: List<ItemBean>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null

    /**
     * 创建条目View
     *
     * @param parent
     * @param viewType
     * @return
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = getSubView(parent, viewType)
        return InnerViewHolder(view)
    }

    /**
     * 子类传入View
     *
     * @param parent
     * @param viewType
     * @return
     */
    protected abstract fun getSubView(parent: ViewGroup?, viewType: Int): View

    /**
     * 用于绑定holder，一般用来设置数据
     *
     * @param holder
     * @param position
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as InnerViewHolder).setData(mData!![position], position)
    }

    /**
     * 获取返回条目的个数
     *
     * @return 返回条目的个数
     */
    override fun getItemCount(): Int {
        return mData?.size ?: 0
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        // 设置一个监听，即设置一个回调接口
        mOnItemClickListener = listener
    }

    /**
     * 编写回调的步骤
     * 1、创建接口
     * 2、定义接口内部方法
     * 3、提供设置接口的方法（外部实现接口）
     * 4、接口方法调用
     */
    interface OnItemClickListener {
        /**
         * 处理item点击事件
         *
         * @param position
         */
        fun onItemClick(position: Int)
    }

    /**
     * 思考：
     * 1、RecycleView的自定义视频器中使用的自定义内部ViewHolder是否要设为static？why？
     */
    inner class InnerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivIcon: ImageView = itemView.findViewById(R.id.icon)
        private val tvTitle: TextView = itemView.findViewById(R.id.title)
        private var mPosition = 0

        /**
         * 给item设置数据
         *
         * @param itemBean
         */
        fun setData(itemBean: ItemBean, position: Int) {
            this.mPosition = position
            ivIcon.setImageResource(itemBean.icon)
            tvTitle.text = itemBean.text
        }

        init {
            //找到条目的控件
            itemView.setOnClickListener {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener!!.onItemClick(mPosition)
                }
            }
        }
    }
}
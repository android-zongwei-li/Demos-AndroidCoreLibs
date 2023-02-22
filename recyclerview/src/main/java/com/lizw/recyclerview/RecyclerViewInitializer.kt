package com.lizw.recyclerview

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.lizw.recyclerview.adapter.BaseRecyclerViewAdapter
import com.lizw.recyclerview.adapter.GridViewRecyclerViewAdapter
import com.lizw.recyclerview.adapter.ListViewRecyclerViewAdapter
import com.lizw.recyclerview.adapter.StaggerAdapter
import com.lizw.recyclerview.beans.ItemBean
import java.util.*

/**
 *
 * author: zongwei.li created on: 2022/7/8
 */
class RecyclerViewInitializer(private val rv: RecyclerView, private val itemData: List<ItemBean>) {
    private var mAdapter: BaseRecyclerViewAdapter? = null
    
    /**
     * 显示ListView的效果
     */
    fun showList(vertical: Boolean = true, reverse: Boolean = false) {
        mAdapter = ListViewRecyclerViewAdapter(itemData)
        rv.adapter = mAdapter
        initListener()
        
        val layoutManager = LinearLayoutManager(rv.context).apply {
            orientation =
                if (vertical) LinearLayoutManager.VERTICAL else LinearLayoutManager.HORIZONTAL
            reverseLayout = reverse
        }
        rv.layoutManager = layoutManager
    }
    
    /**
     * 显示GridView的效果
     *
     */
    fun showGrid(spanCount: Int, vertical: Boolean = true, reverse: Boolean = false) {
        mAdapter = GridViewRecyclerViewAdapter(itemData)
        rv.adapter = mAdapter
        initListener()
        
        val gridLayoutManager = GridLayoutManager(rv.context, spanCount).apply {
            orientation =
                if (vertical) LinearLayoutManager.VERTICAL else LinearLayoutManager.HORIZONTAL
            reverseLayout = reverse
        }
        rv.layoutManager = gridLayoutManager
    }
    
    /**
     * 显示瀑布流效果
     *
     */
    fun showStagger(spanCount: Int, vertical: Boolean = true, reverse: Boolean = false) {
        mAdapter = StaggerAdapter(itemData)
        rv.adapter = mAdapter
        initListener()
        
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(
                spanCount,
                if (vertical) StaggeredGridLayoutManager.VERTICAL else StaggeredGridLayoutManager.HORIZONTAL
        ).apply {
            reverseLayout = reverse
        }
        rv.layoutManager = staggeredGridLayoutManager
    }
    
    private fun initListener() {
        mAdapter?.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                Toast.makeText(
                        rv.context,
                        "点击了第" + position + "个",
                        Toast.LENGTH_SHORT
                ).show()
            }
        })
        
        //处理上拉加载更多
        if (mAdapter is ListViewRecyclerViewAdapter) {
            (mAdapter as ListViewRecyclerViewAdapter).setOnRefreshListener(object :
                    ListViewRecyclerViewAdapter.OnRefreshListener {
                override fun onUpPullRefresh(loaderMoreHolder: ListViewRecyclerViewAdapter.LoaderMoreHolder?) {
                    Handler(Looper.getMainLooper()).postDelayed({ //刷新停止，更新列表
                        val random = Random()
                        if (random.nextInt() % 2 == 0) {
                            Model.loadNewDataEnd()
                            
                            (mAdapter as ListViewRecyclerViewAdapter).notifyItemChanged(Model.itemData.size - 1)
                            loaderMoreHolder?.update(ListViewRecyclerViewAdapter.LOADER_STATE_NORMAL)
                        } else {
                            loaderMoreHolder?.update(ListViewRecyclerViewAdapter.LOADER_STATE_RELOAD)
                        }
                    }, 2000L)
                }
            })
        }
    }
    
    fun initHandlerDownPullUpdate(swipeRefreshLayout: SwipeRefreshLayout) {
        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorAccent,
                R.color.colorPrimary,
                R.color.colorPrimaryDark
        )
        swipeRefreshLayout.isEnabled = true
        swipeRefreshLayout.setOnRefreshListener {
            // 在这里执行刷新数据的操作
            // 下拉时触发，此方法在主线程中执行
            
            Model.loadNewData()
            
            Handler(Looper.getMainLooper()).postDelayed({ //刷新停止，更新列表
                rv.adapter?.notifyDataSetChanged()
                swipeRefreshLayout.isRefreshing = false
            }, 2000L)
        }
    }
}
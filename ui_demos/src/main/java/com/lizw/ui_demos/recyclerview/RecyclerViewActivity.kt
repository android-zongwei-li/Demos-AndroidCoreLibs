package com.lizw.ui_demos.recyclerview

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.lizw.ui_demos.R

class RecyclerViewActivity : AppCompatActivity() {
    private lateinit var rvInitializer: RecyclerViewInitializer
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        
        rvInitializer = RecyclerViewInitializer(findViewById(R.id.recycler_view), Model.loadData().itemData)
        rvInitializer.showList()
        
        rvInitializer.initHandlerDownPullUpdate(findViewById(R.id.swipe_refresh_layout))
    }
    
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.list_view_vertical_stander ->
                rvInitializer.showList(vertical = true, reverse = false)
            R.id.list_view_vertical_reverse ->
                rvInitializer.showList(vertical = true, reverse = true)
            R.id.list_view_horizontal_stander ->
                rvInitializer.showList(vertical = false, reverse = false)
            R.id.list_view_horizontal_reverse ->
                rvInitializer.showList(vertical = false, reverse = true)
            R.id.grid_view_vertical_stander ->
                rvInitializer.showGrid(2, vertical = true, reverse = false)
            R.id.grid_view_vertical_reverse ->
                rvInitializer.showGrid(2, vertical = true, reverse = true)
            R.id.grid_view_horizontal_stander ->
                rvInitializer.showGrid(2, vertical = false, reverse = false)
            R.id.grid_view_horizontal_reverse ->
                rvInitializer.showGrid(2, vertical = false, reverse = true)
            R.id.stagger_view_vertical_stander ->
                rvInitializer.showStagger(2, vertical = true, reverse = false)
            R.id.stagger_view_vertical_reverse ->
                rvInitializer.showStagger(3, vertical = true, reverse = true)
            R.id.stagger_view_horizontal_stander ->
                rvInitializer.showStagger(3, vertical = false, reverse = false)
            R.id.stagger_view_horizontal_reverse ->
                rvInitializer.showStagger(3, vertical = false, reverse = true)
            R.id.multi_type -> {
                val intent = Intent(this, MultiTypeActivity::class.java)
                startActivity(intent)
            }
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }
}
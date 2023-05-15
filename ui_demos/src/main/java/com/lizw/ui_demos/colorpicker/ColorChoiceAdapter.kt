package com.lizw.ui_demos.colorpicker

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lizw.ui_demos.R

class ColorChoiceAdapter(private val colorArray: IntArray) : RecyclerView.Adapter<ColorChoiceAdapter.CircleColorViewHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CircleColorViewHolder {
        val view = View.inflate(parent.context, R.layout.item_circle_shape, null)
        
        return CircleColorViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: CircleColorViewHolder, position: Int) {
        holder.onBind(colorArray[position])
    }
    
    override fun getItemCount(): Int {
        return colorArray.size
    }
    
    class CircleColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivColor: ImageViewWithBounds = itemView.findViewById(R.id.iv_color)
        
        fun onBind(color: Int) {
            // val item_container = itemView.findViewById<LinearLayoutCompat>(R.id.item_container)
            // item_container.layoutParams = ViewGroup.LayoutParams(40, 40)
            //
            // val colorDrawable = ColorDrawable(color).apply {
            // }
            // ivColor.setImageDrawable(colorDrawable)
            //
            // ivColor.outlineProvider = object : ViewOutlineProvider() {
            //     override fun getOutline(view: View, outline: Outline) {
            //         outline.setOval(0, 0, view.width, view.height)
            //     }
            // }
            // ivColor.clipToOutline = true
            ivColor.setColor(color)
            ivColor.setOnClickListener {
            
            }
            // ivColor.background = colorDrawable
            // ivColor.setBackgroundResource(R.drawable.bg_shape_circle)
        }
    }
}

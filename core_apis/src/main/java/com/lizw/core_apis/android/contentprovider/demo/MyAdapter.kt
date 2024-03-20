package com.lizw.core_apis.android.contentprovider.demo

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.lizw.core_apis.R
import java.io.File

class MyAdapter(var data: ArrayList<Picture>, var context: Context) : BaseAdapter() {
    var inflater: LayoutInflater
    
    init {
        inflater = LayoutInflater.from(context)
    }
    
    override fun getCount(): Int {
        return data.size
    }
    
    override fun getItem(position: Int): Any {
        return data[position]
    }
    
    override fun getItemId(position: Int): Long {
        return 0
    }
    
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        // TODO Auto-generated method stub
        var convertView = convertView
        var holder: ViewHolder? = null
        if (convertView == null) {
            holder = ViewHolder()
            convertView = inflater.inflate(R.layout.list_item, null)
            holder.tv_1 = convertView.findViewById<View>(R.id.tv) as TextView
            holder.iv = convertView.findViewById<View>(R.id.img) as ImageView
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        val picture = data[position]
        val file = File(picture.path)
        if (file.exists()) {
            holder.tv_1!!.text = picture.name
            val bitmap = BitmapFactory.decodeFile(picture.path)
            holder.iv!!.setImageBitmap(bitmap)
        } else {
            Toast.makeText(context, picture.name + "������˵����⣬ͼƬ�Ƿ���ɾ����",
                    Toast.LENGTH_SHORT).show()
            val helper = MySqliteHelper(context)
            val db = helper.writableDatabase
            val sql = "DELETE FROM img WHERE path = '" + picture.path + "'"
            db.execSQL(sql)
        }
        return convertView
    }
    
    internal inner class ViewHolder {
        var tv_1: TextView? = null
        var iv: ImageView? = null
    }
}

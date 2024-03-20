package com.lizw.core_apis.android.contentprovider.demo

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class MySqliteHelper(context: Context?) : SQLiteOpenHelper(context, "picture.db", null, 1) {
    /**
     * column name 		    图片名称，带后缀
     * column downloadUrl	下载地址
     * column savePath		应用内，保存的地址
     * column picGroup	    每张图片，可能会被归类到某个图片组中，是一系列图片中的一张
     * column uri		    用来匹配对应的图片
     *
     * @param db The database.
     */
    override fun onCreate(db: SQLiteDatabase) {
        val sql = "create table img(" +
                "_id integer primary key autoincrement," +
                "name text ," +
                "downloadUrl text," +
                "savePath text," +
                "picGroup text," +
                "uri text" +
                ")"
        db.execSQL(sql)
        Log.d("onCreateDataBase", "helper onCreate create table img")
    }
    
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
}

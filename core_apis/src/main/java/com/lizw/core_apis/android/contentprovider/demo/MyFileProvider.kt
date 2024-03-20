package com.lizw.core_apis.android.contentprovider.demo

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.pm.ProviderInfo
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File

private const val TAG = "MyFileProvider"

class MyFileProvider : ContentProvider() {
    override fun attachInfo(context: Context, info: ProviderInfo) {
        super.attachInfo(context, info)
    }
    
    override fun onCreate(): Boolean {
        return true
    }
    
    /**
     * 1、获取图片列表
     * 2、封装到 Cursor 中
     */
    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        // var projection = projection
        // logI("走到query方法")
        // val file: File = mStrategy.getFileForUri(uri)
        // logI("file:$file")
        // if (!file.exists()) {
        //     return null
        // }
        // val directory = file.isDirectory
        // return if (directory) {
        //     logI("文件夹")
        //     val files = file.listFiles()
        //     for (childFile in files) {
        //         if (childFile.isFile) {
        //             val name = childFile.name
        //             val path = childFile.path
        //             val size = childFile.length()
        //             val uriForFile: Uri = mStrategy.getUriForFile(childFile)
        //             logI("name:$name path:$path size: $size uriForFile:$uriForFile")
        //         }
        //     }
        //     //自己遍历封装Cursor实现
        //     null
        // } else {
        //     logI("文件")
        //     val
        //     val cursor = MatrixCursor(cols, 1)
        //     cursor.addRow(values)
        //     cursor
        // }
        return null
    }
    
    override fun getType(uri: Uri): String? {
        return null
    }
    
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }
    
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }
    
    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }
    
    private fun logI(msg: String) {
        Log.i(TAG, msg)
    }
}
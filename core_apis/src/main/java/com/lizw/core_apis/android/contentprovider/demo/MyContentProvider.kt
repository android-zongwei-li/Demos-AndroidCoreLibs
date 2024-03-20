package com.lizw.core_apis.android.contentprovider.demo

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.util.Log
import java.io.File
import java.io.FileNotFoundException

class MyContentProvider : ContentProvider() {
    var helper: MySqliteHelper? = null
    override fun onCreate(): Boolean {
        helper = MySqliteHelper(context)
        return true
    }
    
    override fun query(
            uri: Uri, projection: Array<String>?, selection: String?,
            selectionArgs: Array<String>?, sortOrder: String?,
    ): Cursor? {
        Log.e("query", "query " + uri.authority)
        val db = helper!!.writableDatabase
        val cursor = db.query("img", projection, selection, selectionArgs,
                null, null, sortOrder)
        cursor.setNotificationUri(context!!.contentResolver, uri)
        return cursor
    }
    
    override fun getType(uri: Uri): String? {
        return "image/png"
    }
    
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Log.e("insert", " " + uri.authority)
        val db = helper!!.writableDatabase
        val id = db.insert("img", null, values)
        db.close()
        context!!.contentResolver.notifyChange(uri, null)
        return ContentUris.withAppendedId(uri, id)
    }
    
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        Log.e("delete", "delete")
        val db = helper!!.writableDatabase
        val count = db.delete("img", selection, selectionArgs)
        context!!.contentResolver.notifyChange(uri, null)
        return count
    }
    
    override fun update(
            uri: Uri, values: ContentValues?, selection: String?,
            selectionArgs: Array<String>?,
    ): Int {
        Log.e("update", "update$selection")
        val db = helper!!.writableDatabase
        val count = db.update("img", values, selection, selectionArgs)
        context!!.contentResolver.notifyChange(uri, null)
        return count
    }
    
    var imgSavePath = "/storage/emulated/0/Android/data/com.lizw.demos_androidcorelibs/files/image/"
    
    @Throws(FileNotFoundException::class)
    override fun openFile(uri: Uri, mode: String): ParcelFileDescriptor? {
        Log.i(TAG, "openFile: $uri")
        if (context != null) {
            val path = uri.path
            imgSavePath = "${context!!.getExternalFilesDir(null)}/files/image/"
            val file = File(imgSavePath + path)
            // ParcelFileDescriptor.MODE_READ_ONLY：只可读
            // ParcelFileDescriptor.MODE_WRITE_ONLY：只可写
            // ParcelFileDescriptor.MODE_READ_WRITE：可读可写
            return ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
        }
        return super.openFile(uri, mode)
    }
    
    companion object {
        private const val TAG = "MyContentProvider"
    }
}

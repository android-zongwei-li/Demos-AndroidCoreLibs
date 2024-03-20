package com.lizw.clientapp

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File

private const val TAG = "MainActivity"

/**
 * 存储相关 demo
 * （1）从其他应用的 CP 中加载图片
 * （2）读取SD卡上的其他应用的隐藏文件
 */
class StorageDemoActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        imageView = findViewById(R.id.imageView)
        
        loadImgFromContentProvider()
        
        findViewById<Button>(R.id.btn_load_img).setOnClickListener {
            checkAndRequestPermission()
        }
    }
    
    private fun loadImgFromContentProvider() {
        val cursor =
            contentResolver.query(Uri.parse("content://com.picture.provider"),
                    null, null, null, null)
        Log.i(TAG, "onCreate: ${cursor?.count}")
        Log.i(TAG, "onCreate: ${cursor?.columnNames}")
        cursor?.apply {
            cursor.columnNames.onEach {
                Log.i(TAG, "columnName = $it")
            }
            while (moveToNext()) {
                val uri = cursor.getString(cursor.getColumnIndexOrThrow("uri"))
                Log.i(TAG, "onCreate: uri =  $uri")
                // val uri = cursor.getString(4)
                contentResolver.openInputStream(Uri.parse(uri)).apply {
                    val bitmap = BitmapFactory.decodeStream(this)
                    imageView.setImageBitmap(bitmap)
                    
                    this?.close()
                }
            }
            cursor.close()
        }
    }
    
    // 权限请求码
    private val REQUEST_CODE_PERMISSION = 1
    
    // 检查权限并请求
    private fun checkAndRequestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_CODE_PERMISSION)
        } else {
            Log.i(TAG, "checkAndRequestPermission: has permission!")
            // 权限已经被授予，可以读取文件
            readHiddenFileFromSdCard()
        }
    }
    
    // 权限请求结果处理
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 权限被授予，可以读取文件
                readHiddenFileFromSdCard()
            } else {
                // 权限被拒绝，需要提示用户或者采取其他方式
                Toast.makeText(this, "需要读取存储权限", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    // 读取SD卡上的其他应用的隐藏文件
    private fun readHiddenFileFromSdCard() {
        // 读取其他应用保存在 sdcard 下的隐藏文件，读取不到，会报错，得申请 MANAGE_EXTERNAL_STORAGE 权限
        val imgPath = Environment.getExternalStorageDirectory().path + "/otherAppPkg/.486.png"
        Log.i(TAG, "imgPath = $imgPath")
        val hiddenFile = File(imgPath)
        Log.i(TAG, "readHiddenFileFromSdCard: exists = ${hiddenFile.exists()} , hidden = ${hiddenFile.isHidden}")
        Log.i(TAG, "readHiddenFileFromSdCard: ${hiddenFile.absoluteFile}")
        hiddenFile.setReadable(true)
        Log.i(TAG, "readHiddenFileFromSdCard: canRead = ${hiddenFile.canRead()} , canWrite = ${hiddenFile.canWrite()}")
        
        // val bitmap = BitmapFactory.decodeFile(imgPath)
        val bitmap = BitmapFactory.decodeStream(hiddenFile.inputStream())
        
        imageView.setImageBitmap(bitmap)
    }
}
package com.lizw.core_apis.android.file

import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.lizw.core_apis.R
import java.io.File

private const val TAG = "FileDemoActivity"

class FileDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_demo)

        // internal storage
        // getFilesDir()
        // /data/user/0/com.lizw.demos_androidcorelibs/files
        val filesDir = filesDir
        Log.i(TAG, "file_dir = $filesDir")

        // external storage

        // /storage/emulated/0/Android/data/com.lizw.demos_androidcorelibs/files
        val externalFilesDir = getExternalFilesDir(null)
        Log.i(TAG, "externalFileDir = $externalFilesDir")

        // /storage/emulated/0/Android/data/com.lizw.demos_androidcorelibs/files/Caches
        val externalFilesDir1 = getExternalFilesDir("Caches")
        Log.i(TAG, "externalFileDir1 = $externalFilesDir1")

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // SD卡已装入
            Log.i(TAG, "mounted")
        }




        // 非应用专属
        // /storage/emulated/0
        val sdCard = Environment.getExternalStorageDirectory()
        Log.i(TAG, "sdCard = $sdCard")

        // /storage/emulated/0/Pictures
        val directory_pictures = File(sdCard, "Pictures")
        Log.i(TAG, "directory_pictures = $directory_pictures")

        // /storage/emulated/0/Pictures
        val publicDirectory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        Log.e(TAG, "publicDirectory = $publicDirectory")
    }
}
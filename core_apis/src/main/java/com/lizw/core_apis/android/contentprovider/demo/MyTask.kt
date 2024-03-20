package com.lizw.core_apis.android.contentprovider.demo

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.Random

class MyTask(val context: Context, var cb: CallBack?) : AsyncTask<String?, Int?, String?>() {
    interface CallBack {
        fun start()
        fun updataProgress(progress: Int)
        fun finish(imgPath: String?)
    }
    
    override fun doInBackground(vararg params: String?): String? {
        // 1.HttpURLConnection
        var conn: HttpURLConnection? = null
        var imgPath: String? = null
        // 2.URL
        try {
            val url = URL(params[0])
            // 3.url.openConnection
            conn = url.openConnection() as HttpURLConnection
            // 4.InputStream
            val `in` = conn.inputStream
            // ��ȡ���ļ����ܳ���
            val total = conn.contentLength
            // String path_sdcard = Environment.getExternalStorageDirectory()
            //         .getAbsolutePath() + "/image";
            val groupId = Random().nextInt(2)
            // val path_sdcard = "/storage/emulated/0/Android/data/com.lizw.demos_androidcorelibs/files/image/$groupId"
            val path_sdcard1 = "${context.filesDir}/files/image/$groupId"
            Log.i("filesDir", path_sdcard1)
            
            val path_sdcard = "${context.getExternalFilesDir(null)}/files/image/$groupId"
            Log.i("getExternalFilesDir", path_sdcard)
            val fileParent = File(path_sdcard)
            if (!fileParent.exists()) {
                fileParent.mkdirs()
            }
            // String arr[] = params[0].split("/");
            // String filenameString = arr[arr.length - 1];
            val filenameString = System.currentTimeMillis().toString() + ".png"
            val file = File(path_sdcard, filenameString)
            if (file.exists()) {
                return file.absolutePath
            }
            imgPath = file.absolutePath
            val out: OutputStream = FileOutputStream(file)
            val buffer = ByteArray(4096)
            var sum = 0
            var len = 0
            while (`in`.read(buffer).also { len = it } != -1) {
                out.write(buffer, 0, len)
                sum = sum + len
                val per = (sum * 100f / total).toInt()
                publishProgress(per)
            }
            out.flush()
            out.close()
            `in`.close()
            return imgPath
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } finally {
            conn?.disconnect()
        }
        return null
    }
    
    override fun onPreExecute() {
        super.onPreExecute()
        if (cb != null) {
            cb!!.start()
        }
    }
    
    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        if (cb != null) {
            values[0]?.let { cb!!.updataProgress(it) }
        }
    }
    
    override fun onPostExecute(result: String?) {
        // TODO Auto-generated method stub
        super.onPostExecute(result)
        if (cb != null) {
            cb!!.finish(result)
        }
    }
}

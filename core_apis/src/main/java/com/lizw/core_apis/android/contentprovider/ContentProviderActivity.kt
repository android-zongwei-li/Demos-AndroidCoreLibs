package com.lizw.core_apis.android.contentprovider

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lizw.core_apis.R

class ContentProviderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_provider)
        /**
         * 对user表进行操作
         */
        // 设置URI
        val uri_user: Uri = Uri.parse("content://com.lizw.myprovider/user")

        // 插入表中数据
        val values = ContentValues()
        values.put("_id", 3)
        values.put("name", "Iverson")

        // 获取ContentResolver
        val resolver = contentResolver
        // 通过ContentResolver 根据URI 向ContentProvider中插入数据
        resolver.insert(uri_user, values)

        // 通过ContentResolver 向ContentProvider中查询数据
        resolver.query(uri_user, arrayOf("_id", "name"), null, null, null)?.apply {
            while (moveToNext()) {
                // 将表中数据全部输出
                println("query book:" + getInt(0).toString() + " " + getString(1))
            }
            close()
        }

        /**
         * 对job表进行操作
         */
        // 和上述类似,只是URI需要更改,从而匹配不同的URI CODE,从而找到不同的数据资源
        val uri_job: Uri = Uri.parse("content://com.lizw.myprovider/job")

        // 插入表中数据
        val values2 = ContentValues()
        values2.put("_id", 3)
        values2.put("job", "NBA Player")

        val resolver2 = contentResolver
        resolver2.insert(uri_job, values2)

        resolver2.query(uri_job, arrayOf("_id", "job"), null, null, null)?.apply {
            while (moveToNext()) {
                println("query job:" + getInt(0).toString() + " " + getString(1))
            }
            close()
        }
    }
}
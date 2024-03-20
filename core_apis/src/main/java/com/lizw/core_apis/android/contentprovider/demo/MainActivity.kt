package com.lizw.core_apis.android.contentprovider.demo

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.lizw.core_apis.R
import com.lizw.core_apis.android.contentprovider.demo.MyTask.CallBack
import java.io.File

private const val TAG = "MainActivity"

class MainActivity : Activity() {
    var tv_isFist: TextView? = null
    var lv: ListView? = null
    var pd: ProgressDialog? = null
    var helper: MySqliteHelper? = null
    var db: SQLiteDatabase? = null
    var adapter: MyAdapter? = null
    var data: ArrayList<Picture>? = null
    var position = -1
    var url: String? = null
    var name: String? = null
    var path: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main_2)
        lv = findViewById<View>(R.id.lv) as ListView
        tv_isFist = findViewById<View>(R.id.tv_isFirst) as TextView
        readShared()
        initData()
        setListViewListen()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1000)
        }
        Log.i(TAG, getExternalFilesDir(null)!!.path)
    }
    
    private fun setListViewListen() {
        lv!!.onItemClickListener = AdapterView.OnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long -> this@MainActivity.position = position }
        lv!!.onItemLongClickListener = AdapterView.OnItemLongClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            val builder = AlertDialog.Builder(
                    this@MainActivity)
            builder.setTitle(data!![position].name).setMessage("")
            builder.setPositiveButton("Positive"
            ) { dialog: DialogInterface?, which: Int ->
                // 1
                val uri = Uri
                        .parse("content://com.picture.provider")
                // 2. ContentResolver
                val cr = contentResolver
                // 3.
                cr.delete(uri, "url='" + data!![position].url
                        + "'", null)
                val file = File(data!![position].path)
                if (file.exists()) {
                    file.delete()
                }
                data = readDataBase()
                this@MainActivity.position = -1
                adapter!!.notifyDataSetChanged()
            }
            builder.setNegativeButton("Negative"
            ) { dialog: DialogInterface?, which: Int -> }
            builder.show()
            false
        }
    }
    
    private fun initData() {
        helper = MySqliteHelper(this)
        db = helper!!.writableDatabase
        data = ArrayList()
        data = readDataBase()
        adapter = MyAdapter(data!!, this)
        lv!!.adapter = adapter
    }
    
    private fun readShared() {
        val shared = getSharedPreferences("firstRun",
                MODE_PRIVATE)
        val isFirst = shared.getBoolean("isFirst", true)
        if (isFirst) {
            tv_isFist!!.text = "tv_isFirst"
            val editor = shared.edit()
            editor.putBoolean("isFirst", false)
            editor.commit()
        } else {
            tv_isFist!!.visibility = View.GONE
            lv!!.visibility = View.VISIBLE
        }
    }
    
    var dialog: AlertDialog? = null
    fun add(view: View?) {
        tv_isFist!!.visibility = View.GONE
        lv!!.visibility = View.VISIBLE
        val builder = AlertDialog.Builder(this)
        val dView = LayoutInflater.from(this).inflate(R.layout.downloadlayout,
                null)
        builder.setTitle("title").setView(dView)
        val et1 = dView.findViewById<View>(R.id.et_1) as EditText
        // et1.setText("https://img2.baidu.com/it/u=1501942214,2694155220&fm=253&fmt=auto&app=138&f=PNG?w=427&h=470");
        et1.setText("https://img3.downza.cn/xueyuan/202012/54dac48cd512a8fab83320b66d174d7a.png")
        et1.setTextColor(Color.BLACK)
        val et2 = dView.findViewById<View>(R.id.et_2) as EditText
        val btn1 = dView.findViewById<View>(R.id.btn1) as Button
        val btn2 = dView.findViewById<View>(R.id.btn2) as Button
        btn1.setOnClickListener { v: View? ->
            url = et1.text.toString()
            name = et2.text.toString()
            data = readDataBase()
            for (i in data!!.indices) {
                Log.e(TAG, "url = $url")
                if (url == data!![i].url) {
                    flag = false
                    val builder1 = AlertDialog.Builder(
                            this@MainActivity)
                    builder1.setTitle(data!![i].name + " name " + name + "?")
                    
                    val ok = "确定"
                    val okSpanStr = SpannableString(ok).apply {
                        setSpan(ForegroundColorSpan(Color.BLACK), 0, ok.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
                    }
                    builder1.setPositiveButton(
                            okSpanStr,
                    ) { dialog: DialogInterface?, which: Int ->
                        
                        /*
                                 * String sql = " update img set name='"
                                 * + name + "' where url='" + url + "'";
                                 * db.execSQL(sql); String sql2 =
                                 * "select * from img"; // ִ�У�����Cursor
                                 * Cursor cs = db.rawQuery(sql2, null);
                                 */
                        val uri = Uri
                                .parse("content://com.picture.provider")
                        val cr = contentResolver
                        val values = ContentValues()
                        values.put("name", name)
                        cr.update(uri, values, "downloadUrl='$url'", null)
                        data = readDataBase()
                        adapter!!.notifyDataSetChanged()
                    }
                    val cancel = "取消"
                    val cancelSpanStr = SpannableString(cancel).apply {
                        setSpan(ForegroundColorSpan(Color.BLACK), 0, cancel.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                    builder1.setNegativeButton(
                            cancelSpanStr,
                    ) { dialog: DialogInterface?, which: Int -> }
                    builder1.show()
                    break
                } else {
                    flag = true
                }
            }
            if (flag) {
                val task = MyTask(this, object : CallBack {
                    override fun updataProgress(progress: Int) {
                        pd!!.progress = progress // ������
                    }
                    
                    override fun start() {
                        pd = ProgressDialog(this@MainActivity)
                        pd!!.setTitle("进度条")
                        pd!!.setMessage("进度...")
                        pd!!.setCancelable(false)
                        pd!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
                        pd!!.show()
                    }
                    
                    override fun finish(imagePath: String?) {
                        pd!!.dismiss()
                        path = imagePath
                        if (path != null) {
                            /*
                             * String sql =
                             * "insert into img(name,url,path) values('" +
                             * name// д�����ݿ� + "','" + url + "','" + path +
                             * "')"; db.execSQL(sql);
                             */
                            val uri = Uri.parse("content://com.picture.provider")
                            val cr = contentResolver
                            val values = ContentValues()
                            values.put("name", name)
                            values.put("downloadUrl", url)
                            values.put("savePath", path)
                            // imagePath = .../.../picGroup/xxx.png
                            // picDir = .../.../picGroup
                            val picName = path!!.substring(imagePath!!.lastIndexOf("/") + 1)
                            val picDir = path!!.substring(0, imagePath.lastIndexOf("/"))
                            val picGroup = picDir.substring(picDir.lastIndexOf("/") + 1)
                            values.put("picGroup", picGroup)
                            // String uriStr = String.valueOf(FileProvider.getUriForFile(MainActivity.this,
                            //         "com.picture.provider", new File(path)));
                            val uriStr = Uri.withAppendedPath(uri, picGroup + File.separator + picName).toString()
                            // String uriStr = Uri.withAppendedPath(uri, picName).toString();
                            values.put("uri", uriStr)
                            Log.i(TAG, "finish: uriStr $uriStr")
                            cr.insert(uri, values)
                            data = readDataBase()
                            adapter!!.notifyDataSetChanged()
                        } else {
                            Toast.makeText(this@MainActivity, "Image path is null", Toast.LENGTH_LONG).show()
                        }
                    }
                })
                task.execute(url)
            }
            dialog!!.dismiss()
        }
        btn2.setOnClickListener { v: View? ->
            dialog!!.dismiss()
            et2.setText("")
            name = null
            Log.e(TAG, "et2 $name")
        }
        dialog = builder.create()
        dialog?.show()
    }
    
    fun del(view: View?) {
        if (position != -1) {
            val builder = AlertDialog.Builder(
                    this@MainActivity)
            builder.setTitle("title" + data!![position].name).setMessage(
                    "msg")
            builder.setPositiveButton("pos"
            ) { dialog: DialogInterface?, which: Int ->
                /*
                         * String sql = "delete from img where url='" +
                         * data.get(position).url + "'"; db.execSQL(sql);
                         */
                val uri = Uri
                        .parse("content://com.picture.provider")
                val cr = contentResolver
                cr.delete(uri, "downloadUrl='" + data!![position].url
                        + "'", null)
                val file = File(data!![position].path)
                if (file.exists()) {
                    file.delete()
                }
                data = readDataBase()
                if (data!!.size == 0) {
                    flag = true
                    position = -1
                }
                adapter!!.notifyDataSetChanged()
            }
            builder.setNegativeButton("N"
            ) { dialog: DialogInterface?, which: Int -> }
            builder.show()
        } else {
            Toast.makeText(this, "未选中", Toast.LENGTH_LONG).show()
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        db!!.close()
    }
    
    @SuppressLint("Range")
    fun readDataBase(): ArrayList<Picture>? {
        data!!.clear()
        val sql = "select * from img"
        val cs = db!!.rawQuery(sql, null)
        while (cs.moveToNext()) {
            val picture = Picture()
            picture.name = cs.getString(cs.getColumnIndex("name"))
            picture.url = cs.getString(cs.getColumnIndex("downloadUrl"))
            picture.path = cs.getString(cs.getColumnIndex("savePath"))
            data!!.add(picture)
        }
        cs.close()
        if (data!!.size == 0) {
            flag = true
        }
        return data
    }
    
    companion object {
        var flag = false
    }
}

package com.lizw.aidlclient.aidl

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lizw.aidlclient.R
import com.lizw.aidlserver.BookInfo
import com.lizw.aidlserver.IBookService
import main.aidl.IRemoteService

class AidlClientActivity : AppCompatActivity() {

    private var remoteService: IRemoteService? = null

    private val mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            remoteService = IRemoteService.Stub.asInterface(service)
            remoteService?.apply {
                Toast.makeText(this@AidlClientActivity, "server pid = $pid", Toast.LENGTH_LONG).show()
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.e("ServiceConnection", "Service has unexpectedly disconnected")
            remoteService = null
        }
    }

    // BookService
    private var bookService: IBookService? = null

    private val bookServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            bookService = IBookService.Stub.asInterface(service)
            bookService?.apply {
                Log.i("bookServiceConnection", "${bookInfo.name},${bookInfo.price}")
                Toast.makeText(
                    this@AidlClientActivity,
                    "${bookInfo.name},${bookInfo.price}",
                    Toast.LENGTH_LONG
                ).show()
                val book = BookInfo().apply {
                    this.name = "老人与海"
                    this.price = 22
                    this.type = 3
                }
                Log.i("bookServiceConnection", isExist(book).toString())
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.e("ServiceConnection", "Service has unexpectedly disconnected")
            bookService = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aidl_client)

        findViewById<Button>(R.id.btn_bind_remote_service).setOnClickListener {
            val intent = Intent().apply {
                `package` = "com.lizw.aidlserver"
                action = "com.lizw.aidlserver.RemoteService.action"
            }

            bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
        }

        findViewById<Button>(R.id.btn_bind_book_service).setOnClickListener {
            val intent = Intent().apply {
                `package` = "com.lizw.aidlserver"
                action = "com.lizw.aidlserver.BookService.action"
            }

            bindService(intent, bookServiceConnection, Context.BIND_AUTO_CREATE)
        }
    }
}
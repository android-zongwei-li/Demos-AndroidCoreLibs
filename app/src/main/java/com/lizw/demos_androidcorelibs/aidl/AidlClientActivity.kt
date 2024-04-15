package com.lizw.demos_androidcorelibs.aidl

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lizw.demos_androidcorelibs.databinding.ActivityAidlClientBinding

class AidlClientActivity : AppCompatActivity() {
    // RemoteService
    private var remoteService: com.lizw.aidlserver.aidlsdk.IRemoteService? = null
    
    private val remoteServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            remoteService = com.lizw.aidlserver.aidlsdk.IRemoteService.Stub.asInterface(service)
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
    private var bookService: com.lizw.aidlserver.aidlsdk.book.IBookService? = null
    
    private val bookServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            bookService = com.lizw.aidlserver.aidlsdk.book.IBookService.Stub.asInterface(service)
            bookService?.apply {
                Log.i("bookServiceConnection", "${bookInfo.name},${bookInfo.price}")
                Toast.makeText(
                        this@AidlClientActivity,
                        "${bookInfo.name},${bookInfo.price}",
                        Toast.LENGTH_LONG
                ).show()
                val book = com.lizw.aidlserver.aidlsdk.book.BookInfo().apply {
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
    
    // 通过aar依赖的方式，相较于在服务端、客户端分别创建文件夹、文件的方式，更便于接入，并且不需要build后才生成文件
    private var personManagerService: com.lizw.aidlserver.aidlsdk.person.IPersonInfoManager? = null
    private val personManagerServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            personManagerService = com.lizw.aidlserver.aidlsdk.person.IPersonInfoManager.Stub.asInterface(service)
            Log.i("PersonManager", "connected")
            
            personManagerService?.howManyPersons()
        }
        
        override fun onServiceDisconnected(name: ComponentName?) {
            Toast.makeText(this@AidlClientActivity, "PersonManagerService Disconnected!", Toast.LENGTH_LONG).show()
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val viewBinding = ActivityAidlClientBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        
        viewBinding.btnBindRemoteService.setOnClickListener {
            val intent = Intent().apply {
                `package` = "com.lizw.aidlserver"
                action = "com.lizw.aidlserver.RemoteService.action"
            }
            
            bindService(intent, remoteServiceConnection, BIND_AUTO_CREATE)
        }
        
        viewBinding.btnBindBookService.setOnClickListener {
            val intent = Intent().apply {
                `package` = "com.lizw.aidlserver"
                action = "com.lizw.aidlserver.BookService.action"
            }
            
            bindService(intent, bookServiceConnection, BIND_AUTO_CREATE)
        }
        
        fun initPersonBusiness() {
            viewBinding.btnBindPersonManagerService.setOnClickListener {
                val intent = Intent().apply {
                    `package` = "com.lizw.aidlserver"
                    action = "com.lizw.aidlserver.action.PersonManagerService"
                }
                
                bindService(intent, personManagerServiceConnection, BIND_AUTO_CREATE)
            }
            
            viewBinding.btnAddPerson.setOnClickListener {
                val person = com.lizw.aidlserver.aidlsdk.person.Person().apply {
                    name = "Leo"
                    age = 19
                }
                personManagerService?.addPerson(person)
            }
            
            viewBinding.btnAddPersonChangeListener.setOnClickListener {
                personManagerService?.addOnPersonChangeListener(object : com.lizw.aidlserver.aidlsdk.person.IOnPersonChangeListener.Stub() {
                    override fun onChange(p0: Int) {
                        // p0 是人员总数
                        Toast.makeText(this@AidlClientActivity, "PersonNum =  $p0", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
        initPersonBusiness()
    }
}
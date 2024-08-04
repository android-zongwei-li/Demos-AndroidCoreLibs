package com.lizw.aidlserver.aidlsdk.person

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Bitmap
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.ParcelFileDescriptor
import android.os.RemoteException
import android.util.Log
import java.io.FileDescriptor

/**
 *
 */
object PersonManager {
    private const val TAG = "PersonManager"

    private lateinit var application: Application

    // 通过aar依赖的方式，相较于在服务端、客户端分别创建文件夹、文件的方式，更便于接入，并且不需要build后才生成文件
    private var personManagerService: IPersonInfoManager? = null
    private val personManagerServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            personManagerService = IPersonInfoManager.Stub.asInterface(service)

            // linkToDeath方法调用在onServiceDisconnected之前
            service?.linkToDeath({
                Log.i(TAG, "binderDied:")
                release()
            }, 0)
            Log.i("PersonManager", "connected")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.i(TAG, "PersonManagerService Disconnected!")
            release()
            reconnect()
        }
    }

    private fun release() {
        Log.i(TAG, "release")
        personManagerService = null
    }

    private fun bindToService(): Boolean {
        if (personManagerService != null && personManagerService!!.asBinder().isBinderAlive) {
            return true
        }
        val intent = Intent().apply {
            `package` = "com.lizw.aidlserver"
            action = "com.lizw.aidlserver.action.PersonManagerService"
        }
        // bindService 的 ServiceConnection 回调是在主线程中
        application.bindService(intent, personManagerServiceConnection, Context.BIND_AUTO_CREATE)
        return personManagerService != null
    }

    fun unBind() {
        application.unbindService(personManagerServiceConnection)
    }

    private fun isServiceConnected(): Boolean {
        return bindToService()
    }

    private fun reconnect() {
        Log.i(TAG, "reconnect")
        Handler(Looper.getMainLooper()).postDelayed({
            bindToService()
        }, 3 * 1000)
    }

    fun init(application: Application) {
        this.application = application
        bindToService()
    }

    fun addPerson(person: Person) {
        try {
            personManagerService?.addPerson(person)
        } catch (e: RemoteException) {
            // 客户端通常都是关心执行结果的，仅做 catch 处理应该还不够
            Log.e(TAG, "addPerson: $e")
        }
    }

    fun addOnPersonChangeListener(onPersonChangeListener: IOnPersonChangeListener) {
        try {
            personManagerService?.addOnPersonChangeListener(onPersonChangeListener)
        } catch (e: RemoteException) {
            Log.e(TAG, "addOnPersonChangeListener: $e")
        }
    }

    fun sendData(data: ByteArray) {
        try {
            personManagerService?.sendData(data)
        } catch (e: RemoteException) {
            Log.e(TAG, "sendData: $e")
        }
    }

    fun getIcon(): Bitmap? {
        if (personManagerService != null) {
            return personManagerService?.icon
        }
        return null
    }

    fun getPersonsList(): List<Person> {
        if (personManagerService == null) {
            return ArrayList()
        }
        return personManagerService!!.getPersonsList(18)
    }

    fun getPersonsMap(): Map<String, Person> {
        if (personManagerService == null) {
            return HashMap()
        }
        return personManagerService!!.personsMap
    }

    fun getFileDescriptor(): ParcelFileDescriptor? {
        return personManagerService!!.fileDescriptor
    }
}
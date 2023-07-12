package com.lizw.aidlserver

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Process
import android.util.Log
import main.aidl.IRemoteService

class RemoteService : Service() {
    companion object {
        private const val TAG = "DefaultRemoteService"
    }
    
    private val binderDefaultRemoteService = DefaultRemoteService()
    
    override fun onBind(intent: Intent): IBinder {
        return binderDefaultRemoteService
    }
    
    private class DefaultRemoteService : IRemoteService.Stub() {
        override fun getPid(): Int {
            return Process.myPid()
        }
        
        override fun basicTypes(
                anInt: Int,
                aLong: Long,
                aBoolean: Boolean,
                aFloat: Float,
                aDouble: Double,
                aString: String?,
        ) {
            Log.i(TAG, "basicTypes $anInt,$aLong,$aBoolean,$aFloat,$aDouble,$aString")
        }
    }
}
package com.lizw.aidlserver.personmanager

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteCallbackList
import android.os.RemoteException
import android.util.Log
import com.lizw.aidlserver.aidlsdk.ICommonDoBusinessCallback
import com.lizw.aidlserver.aidlsdk.person.IOnPersonChangeListener
import com.lizw.aidlserver.aidlsdk.person.IPersonInfoManager
import com.lizw.aidlserver.aidlsdk.person.Person

class PersonManagerService : Service() {
    
    override fun onBind(intent: Intent): IBinder {
        return PersonManager()
    }
    
    private class PersonManager : IPersonInfoManager.Stub() {
        private val TAG = "PersonManagerService"
        
        private val onPersonChangeListeners = ArrayList<IOnPersonChangeListener>()
        
        /**
         * 通过 RemoteCallbackList ，可以在客户端断开时，自动清理掉回调
         */
        private val remoteCallbackList = RemoteCallbackList<IOnPersonChangeListener>()
        
        override fun addPerson(p0: Person?): Boolean {
            Log.i(TAG, "addPerson = $p0")
            
            onPersonChangeListeners.forEach {
                it.onChange(howManyPersons())
            }
            
            return true
        }
        
        override fun howManyPersons(): Int {
            return 10
        }
        
        override fun doBusiness(p0: Bundle?, p1: Intent?, p2: ICommonDoBusinessCallback?): Int {
            return -1
        }
        
        override fun addOnPersonChangeListener(p0: IOnPersonChangeListener?) {
            p0?.apply {
                onPersonChangeListeners.add(p0)
                
                val binder = asBinder()
                val deathRecipient = IBinder.DeathRecipient {
                    onPersonChangeListeners.remove(p0)
                }
                binder.linkToDeath(deathRecipient, 0)
            }
            
            remoteCallbackList.register(p0)
        }
        
        override fun removeOnPersonChangeListener(p0: IOnPersonChangeListener?) {
            remoteCallbackList.unregister(p0)
        }
        
        // 向客户端发起回调
        @Synchronized
        private fun notifyToClient() {
            Log.i(TAG, "notifyToClient")
            val n: Int = remoteCallbackList.beginBroadcast()
            for (i in 0 until n) {
                try {
                    remoteCallbackList.getBroadcastItem(i).onChange(i)
                } catch (e: RemoteException) {
                    e.printStackTrace()
                }
            }
            remoteCallbackList.finishBroadcast()
        }
    }
}
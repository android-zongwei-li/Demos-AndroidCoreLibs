package com.lizw.aidlserver.personmanager

import android.app.Application
import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.IBinder
import android.os.ParcelFileDescriptor
import android.os.Process
import android.os.RemoteCallbackList
import android.os.RemoteException
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.lizw.aidlserver.R
import com.lizw.aidlserver.aidlsdk.ICommonDoBusinessCallback
import com.lizw.aidlserver.aidlsdk.person.IOnPersonChangeListener
import com.lizw.aidlserver.aidlsdk.person.IPersonInfoManager
import com.lizw.aidlserver.aidlsdk.person.Person
import java.io.File

class PersonManagerService : Service() {
//    private val personManager = PersonManager()

    override fun onBind(intent: Intent): IBinder {
        // 这两种返回方式如何选择，分别有什么影响
//        return personManager
        return PersonManager(application)
    }

    private class PersonManager(val application: Application) : IPersonInfoManager.Stub() {
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

        override fun getPerson(): Person {
            return Person().apply {
                this.name = "test"
                age = 18
            }
        }

        override fun isExist(person: Person?): Boolean {
            if (person == null) {
                return false
            }
            val demo = Person().apply {
                name = "demo"
            }

            return demo.name == person.name
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

        override fun getPersonsList(age: Int): MutableList<Person> {
            val arrayList = ArrayList<Person>()
            arrayList.add(Person().apply {
                name = "t1"
                this.age = 18
            })
            arrayList.add(Person().apply {
                name = "t2"
                this.age = 18
            })
            return arrayList
        }

        override fun getPersonsMap(): MutableMap<String, Person> {
            val hashMap = HashMap<String, Person>()
            hashMap.put("p1", Person().apply {
                name = "t3"
                age = 19
            })
            hashMap.put("p2", Person().apply {
                name = "t4"
                age = 20
            })
            return hashMap
        }

        override fun sendData(data: ByteArray?) {
            Log.i(TAG, "data size : ${data?.size}")
        }

        override fun getIcon(): Bitmap {
            return ResourcesCompat.getDrawable(
                application.resources,
                R.drawable.img1,
                null
            )!!.toBitmap()
        }

        override fun getFileDescriptor(): ParcelFileDescriptor? {
            try {
                // test.mp4 是要传输的文件，位于app的缓存目录下，约256M
                return ParcelFileDescriptor.open(
                    File(application.cacheDir, "test.mp4"), ParcelFileDescriptor.MODE_READ_ONLY
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
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
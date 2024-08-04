package com.lizw.demos_androidcorelibs.aidl

import android.os.Binder
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.lizw.aidlserver.aidlsdk.person.IOnPersonChangeListener
import com.lizw.aidlserver.aidlsdk.person.PersonManager
import com.lizw.demos_androidcorelibs.databinding.ActivityAidlClientBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class AidlClientActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "AidlClientActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityAidlClientBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.btnBindPersonManagerService.setOnClickListener {
            PersonManager.init(application)
        }

        viewBinding.btnAddPerson.setOnClickListener {
            val person = com.lizw.aidlserver.aidlsdk.person.Person().apply {
                name = "Leo"
                age = 19
            }
            PersonManager.addPerson(person)
        }

        viewBinding.btnAddPersonChangeListener.setOnClickListener {
            PersonManager.addOnPersonChangeListener(object : IOnPersonChangeListener.Stub() {
                override fun onChange(p0: Int) {
                    // p0 是人员总数
                    Log.i(TAG, "onChange: PersonNum =  $p0")
                }
            })
        }

        viewBinding.btnSendData.setOnClickListener {
            // 1024 * 1024 ： 将会传输失败，但不会报错
            PersonManager.sendData(ByteArray(1024 * 1024))
        }

        viewBinding.btnGetBitmap.setOnClickListener {
            val bitmap = PersonManager.getIcon() ?: return@setOnClickListener
            Log.i(
                TAG,
                "getBitmap allocationByteCount : ${bitmap.allocationByteCount / 1024 / 1024} M"
            )
            viewBinding.ivIcon.setImageBitmap(bitmap)
        }

        viewBinding.btnGetList.setOnClickListener {
            PersonManager.getPersonsList().apply {
                Log.i(TAG, "getPersonsList: ${this.size}")
            }.onEach {
                Log.i(TAG, "getPersonsList each: ${it.name} ${it.age}")
            }
        }

        viewBinding.btnGetMap.setOnClickListener {
            PersonManager.getPersonsMap().apply {
                Log.i(TAG, "getPersonsList: ${this.size}")
            }.onEach {
                Log.i(TAG, "getPersonsList each: ${it.key} ${it.value.name} ${it.value.age}")
            }
        }

        viewBinding.btnGetBigFile.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                Log.i(TAG, "btnGetBigFile start")
                val pfd = PersonManager.getFileDescriptor()
                Log.i(TAG, "btnGetBigFile : ${pfd.statSize}")
                val file = File(cacheDir, "testFromServer.mp4")
                try {
                    val inputStream = ParcelFileDescriptor.AutoCloseInputStream(pfd)
                    file.delete()
                    file.createNewFile()
                    val stream = FileOutputStream(file)
                    val buffer = ByteArray(1024)
                    // 将inputStream中的数据写入到file中
                    while (true) {
                        // 读取数据到缓冲区
                        val len = inputStream.read(buffer)
                        if (len == -1) {
                            // 如果到达输入流的末尾，则退出循环
                            break
                        }
                        stream.write(buffer, 0, len) // 将缓冲区中的数据写入输出流
                    }
                    stream.close()
                    pfd.close()
                    Log.i(TAG, "btnGetBigFile end")
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
}
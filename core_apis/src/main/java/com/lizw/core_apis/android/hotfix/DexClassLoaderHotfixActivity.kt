package com.lizw.core_apis.android.hotfix

import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lizw.core_apis.databinding.ActivityDexClassLoaderHotfixBinding
import dalvik.system.DexClassLoader
import java.io.File

class DexClassLoaderHotfixActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "DexClassLoaderHotfixAct"
    }

    var mSay: ISay? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDexClassLoaderHotfixBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sayException: ISay = SayException()
//        binding.btnSay.setOnClickListener {
//            Log.i(TAG, sayException.saySomething())
//            Toast.makeText(this, sayException.saySomething(), Toast.LENGTH_LONG).show()
//        }


        val jarFile = File(cacheDir.absolutePath + "/hotfix.jar")
        jarFile.setReadable(true)
        assets.open("SayHotFix.jar").copyTo(jarFile.outputStream())
        binding.btnSay.setOnClickListener {
            // 获取hotfix的jar包文件
//            val jarFile = File(
//                Environment.getExternalStorageDirectory().path
//                        + File.separator + "SayHotFix.jar"
//            )

            if (!jarFile.exists()) {
                mSay = SayException()
                Toast.makeText(this, mSay!!.saySomething(), Toast.LENGTH_LONG).show()
            } else {
                //只要有读写权限的路径均可
                val dexClassLoader = DexClassLoader(
                    jarFile.absolutePath,
                    cacheDir.absolutePath, null, classLoader
                )
                try {
                    val clazz = dexClassLoader.loadClass("com.lzw.dexclassloaderhotfix.SayHotFix")
                    // 强转成 ISay 注意 ISay 的包名需要和hotfix jar包中的一致
                    val iSay = clazz.newInstance() as ISay
                    Toast.makeText(this, iSay.saySomething(), Toast.LENGTH_LONG).show()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
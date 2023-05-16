package com.lizw.core_apis.android.bitmap

import android.os.Bundle
import com.lizw.core_apis.R
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException

class BigViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bigview)
        val myBigView = findViewById<MyBigView>(R.id.mbv_1)
        try {
            val `is` = assets.open("big_image2.jpg")
            myBigView.setImage(`is`)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
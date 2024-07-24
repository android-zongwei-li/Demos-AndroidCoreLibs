package com.lizw.core_apis.jetpack.livedata_viewmodel.sample_count

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.lizw.core_apis.R

class SecondActivity : AppCompatActivity() {
    @VMScope("count")
    lateinit var viewModel: CountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        injectViewModel()
        initEvent()
    }

    private fun initEvent() {
        val countRandom: Button = findViewById(R.id.count_random)
        val back: Button = findViewById(R.id.back)
        val countSecondTv: TextView = findViewById(R.id.count_second_tv)
        countRandom.setOnClickListener {
            viewModel.random()
        }
        back.setOnClickListener {
            finish()
        }
        /**
         * mycount是一个LiveData类型 可以观察
         * */
        viewModel.myCount.observe(this@SecondActivity) {
            countSecondTv.text = viewModel.myCount.value.toString()
        }
    }
}
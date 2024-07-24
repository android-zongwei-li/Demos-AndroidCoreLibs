package com.lizw.core_apis.thirdpartlibs.livedata_viewmodel.sample_count

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.lizw.core_apis.R
import kotlinx.coroutines.flow.flow
import kotlin.concurrent.thread

class CountActivity : AppCompatActivity() {
    @VMScope("count")
    lateinit var viewModel: CountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count)
        injectViewModel()
        initEvent()
    }

    private fun initEvent() {
        val cardReduce: CardView = findViewById(R.id.card_reduce)
        val cardAdd: CardView = findViewById(R.id.card_add)
        val sendMessage: Button = findViewById(R.id.send_message)
        val countTv: TextView = findViewById(R.id.count_tv)
        val clearMessage: TextView = findViewById(R.id.clear_message)
        cardReduce.setOnClickListener {
            viewModel.reduce()
        }
        cardAdd.setOnClickListener {
            viewModel.add()
        }
        sendMessage.setOnClickListener {
            val intent = Intent(this@CountActivity, SecondActivity::class.java)
            startActivity(intent)
        }

        clearMessage.setOnClickListener {
            viewModel.clear()
        }
        /**
         *  订阅 ViewModel,mycount是一个LiveData类型 可以观察
         * */
        viewModel.myCount.observe(this@CountActivity) {
            countTv.text = viewModel.myCount.value.toString()
        }

// LiveData onchange会自动感应生命周期 不需要手动
//        viewModel.mycount.observe(this, object : Observer<Int> {
//            override fun onChanged(t: Int?) {
//
//            }
//        })
    }
}
package com.lizw.core_apis.jetpack.livedata_viewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.lizw.core_apis.R

class LiveDataViewModelDemoActivity : AppCompatActivity() {
    private lateinit var viewModel: DemoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_data_view_model_demo)
        val countTv: TextView = findViewById(R.id.count_tv)

        /** 记住绝对不可以直接去创建ViewModel实例
        一定要通过ViewModelProvider(ViewModelStoreOwner)构造函数来获取。
        因为每次旋转屏幕都会重新调用onCreate()方法，如果每次都创建新的实例的话就无法保存数据了。
        用上述方法后，onCreate方法被再次调用,
        它会返回一个与MainActivity相关联的预先存在的ViewModel，这就是保存数据的原因。
         */
        viewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get(DemoViewModel::class.java)

        /**
         *  订阅 ViewModel,mycount是一个LiveData类型 可以观察
         * */
        viewModel.mycount.observe(this) {
            countTv.text = viewModel.mycount.value.toString()
        }
    }

}
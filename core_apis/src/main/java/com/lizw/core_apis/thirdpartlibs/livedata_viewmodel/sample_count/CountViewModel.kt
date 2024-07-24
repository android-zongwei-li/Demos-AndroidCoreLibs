package com.lizw.core_apis.thirdpartlibs.livedata_viewmodel.sample_count

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CountViewModel : ViewModel() {
    private var _myCount: MutableLiveData<Int> = MutableLiveData()

    val myCount: LiveData<Int> get() = _myCount

    init {
        _myCount.value = 0
    }

    /**
     * mycount.value若为空就赋值为0，不为空则加一
     * */
    fun add() {
        _myCount.value = _myCount.value?.plus(1)
    }

    /**
     * mycount.value若为空就赋值为0，不为空则减一，可以为负数
     * */
    fun reduce() {
        _myCount.value = _myCount.value?.minus(1)
    }

    /**
     * 随机参数
     * */
    fun random() {
        val random = (0..100).random()
        _myCount.value = random
    }

    /**
     * 清除数据
     * */
    fun clear() {
        _myCount.value = 0
    }
}
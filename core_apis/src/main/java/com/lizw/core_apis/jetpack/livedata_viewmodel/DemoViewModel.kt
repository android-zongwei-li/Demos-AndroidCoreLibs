package com.lizw.core_apis.jetpack.livedata_viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

/**
 *
 */
class DemoViewModel : ViewModel() {
    var mycount: MutableLiveData<Int> = MutableLiveData()
}
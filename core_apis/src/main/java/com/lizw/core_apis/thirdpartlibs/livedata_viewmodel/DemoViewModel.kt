package com.lizw.core_apis.thirdpartlibs.livedata_viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 *
 */
class DemoViewModel : ViewModel() {
    var mycount: MutableLiveData<Int> = MutableLiveData()
}
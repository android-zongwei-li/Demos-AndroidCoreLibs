package com.lizw.common

import android.content.Context
import android.widget.Toast

class ToastUtils {
    companion object {
        fun showShortToast(context: Context, msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
        
        fun showLongToast(context: Context, msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        }
    }
}
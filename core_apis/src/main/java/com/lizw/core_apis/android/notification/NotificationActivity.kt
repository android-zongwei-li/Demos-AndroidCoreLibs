package com.lizw.core_apis.android.notification

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lizw.core_apis.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val notificationHelper = NotificationHelper(this)
        viewBinding.btnSendNotification.setOnClickListener {
            notificationHelper.sendTestNotification()
        }

        viewBinding.btnCancelNotification.setOnClickListener {
            notificationHelper.cancelTestNotification()
        }
    }
}
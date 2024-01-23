package com.lizw.core_apis.android.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.lizw.common.interfaces.ITechKeywords
import com.lizw.core_apis.R


/**
 *
 * 技术栈：消息通知
 * author: zongwei.li created on: 2023/8/5
 */
class NotificationHelper(val context: Context) : ITechKeywords {
    companion object {
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "notification_demo"
    }

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun sendTestNotification() {
        /**
         * 必要属性有三项
         * 小图标，通过 setSmallIcon() 方法设置
         * 标题，通过 setContentTitle() 方法设置
         * 内容，通过 setContentText() 方法设置
         */
        val notification = NotificationCompat.Builder(context, CHANNEL_ID).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel =
                    NotificationChannel(CHANNEL_ID,
                        "my_channel",
                        NotificationManager.IMPORTANCE_DEFAULT)
                channel.enableLights(true) //是否在桌面icon右上角展示小红点
                channel.lightColor = Color.GREEN //小红点颜色
                channel.setShowBadge(true) //是否在久按桌面图标时显示此渠道的通知
                notificationManager.createNotificationChannel(channel)

                setChannelId(CHANNEL_ID)
            }

            setSubText("这是Subtext")
            // TODO：图标没有显示
            setSmallIcon(R.mipmap.ic_launcher)
            setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
            setContentTitle("我的测试通知") //设置通知标题
            setContentText("有点饿了") //设置通知内容
            setTicker("滚动消息......")

            /**
             * 点击通知内容后跳转到发送通知的页面
             */
            fun setOnContentClick() {
                val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    PendingIntent.getActivity(
                        context,
                        0,
                        Intent(context, NotificationActivity::class.java),
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )
                } else {
                    PendingIntent.getActivity(
                        context,
                        0,
                        Intent(context, NotificationActivity::class.java),
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )
                }
                setContentIntent(pendingIntent)
            }
            setOnContentClick()

            // 设置自定义View，需要注意，设置过以后呢，就会使用自定义布局，上面的设置就无效了
            val remoteView = RemoteViews(context.packageName, R.layout.notification_demo_view)
            setCustomContentView(remoteView)
            // 这行不设置好像能显示
            setCustomBigContentView(remoteView)

            // 振动需要权限，android.permission.VIBRATE
            // DEFAULT_VIBRATE，振动； DEFAULT_LIGHTS，呼吸灯
            setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE or Notification.DEFAULT_LIGHTS)
            setWhen(System.currentTimeMillis()) //设置通知时间，默认为系统发出通知的时间，通常不用设置
        }.build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    /**
     * 清楚指定Id的通知
     */
    fun cancelTestNotification() {
        notificationManager.cancel(NOTIFICATION_ID)
    }

    override fun getTechKeywords(): Array<String> {
        return arrayOf("消息通知，点击消息打开Activity，消息通知-声音/振动/呼吸灯，消息通知-自定义View")
    }

}
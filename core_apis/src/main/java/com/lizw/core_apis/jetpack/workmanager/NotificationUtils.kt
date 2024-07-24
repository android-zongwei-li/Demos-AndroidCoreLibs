package com.lizw.core_apis.jetpack.workmanager

/**
 * 作用：
 * 技术栈：
 */
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.lizw.core_apis.jetpack.workmanager.Constants.CHANNEL_NAME

private lateinit var mNotificationManager: NotificationManager

fun notifyNotification(
    context: Context,
    notificationId: Int,
    block: NotificationCompat.Builder.() -> Unit
) {
    val notification = createNotification(context, block)
    val manager = getNotificationManager(context)
    manager.notify(notificationId, notification)
}

fun notifyNotification(context: Context, notificationId: Int, notification: Notification) {
    val manager = getNotificationManager(context)
    manager.notify(notificationId, notification)
}

fun createNotification(
    context: Context,
    block: NotificationCompat.Builder.() -> Unit
): Notification {
    val builder = NotificationCompat.Builder(context, Constants.CHANNEL_ID)
    block.invoke(builder)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        createNotificationChannel(context)
    }
    return builder.build()
}

@RequiresApi(Build.VERSION_CODES.O)
private fun createNotificationChannel(context: Context): NotificationChannel {
    val notificationManager = getNotificationManager(context)
    val channel =
        NotificationChannel(Constants.CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW)
    notificationManager.createNotificationChannel(channel)
    return channel
}

private fun getNotificationManager(context: Context): NotificationManager {
    if (!::mNotificationManager.isInitialized) {
        mNotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
    return mNotificationManager
}

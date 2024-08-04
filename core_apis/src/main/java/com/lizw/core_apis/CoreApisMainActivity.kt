package com.lizw.core_apis

import com.lizw.common.ext.startActivityDefaultIntent
import com.lizw.core_apis.android.AndroidDemoActivity
import com.lizw.core_apis.android.activity.LifecycleTestActivity
import com.lizw.core_apis.android.contentprovider.ContentProviderActivity
import com.lizw.core_apis.android.file.FileDemoActivity
import com.lizw.core_apis.android.notification.NotificationActivity
import com.lizw.core_apis.java.thread.ThreadDemoActivity
import com.lizw.core_apis.kotlin.coroutines.CoroutinesDemoActivity
import com.lizw.core_apis.kotlin.flow.FlowDemoActivity
import com.lizw.core_apis.navigation.NavigationDemoActivity
import com.lizw.core_apis.thirdpartlibs.retrofit.RetrofitActivity

class CoreApisMainActivity : BaseListOfBtnsV2Activity() {
    override fun getItems(): Map<String, () -> Unit> {
        return mapOf(
            "导航" to { startActivityDefaultIntent(NavigationDemoActivity::class.java) },
            "Retrofit" to { startActivityDefaultIntent(RetrofitActivity::class.java) },
            "线程示例" to { startActivityDefaultIntent(ThreadDemoActivity::class.java) },
            "ContentProvider" to { startActivityDefaultIntent(ContentProviderActivity::class.java) },
            "ActivityLifecycle" to { startActivityDefaultIntent(LifecycleTestActivity::class.java) },
            "消息通知" to { startActivityDefaultIntent(NotificationActivity::class.java) },
            "协程" to { startActivityDefaultIntent(CoroutinesDemoActivity::class.java) },
            "Flow" to { startActivityDefaultIntent(FlowDemoActivity::class.java) },
            "文件" to { startActivityDefaultIntent(FileDemoActivity::class.java) },
            "Android" to { startActivityDefaultIntent(AndroidDemoActivity::class.java) },
        )
    }
}
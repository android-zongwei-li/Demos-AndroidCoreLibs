package com.lizw.core_apis.kotlin.coroutines

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.lizw.core_apis.BaseListOfBtnsActivity
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.system.measureTimeMillis

class CoroutinesDemoActivity : BaseListOfBtnsActivity(),
    // 通过委托模式让 Activity 实现 CoroutineScope 接口，
    // 从而可以在 Activity 内直接启动协程而不必显示地指定它们的上下文，
    // 并且在 `onDestroy()`中自动取消所有协程
    CoroutineScope by CoroutineScope(Dispatchers.Default) {
//    private val binding by lazy {
//        ActivityCoroutinesDemoBinding.inflate(layoutInflater)
//    }

    private fun log(tag: String, msg: Any) =
        Log.i(tag, "[${Thread.currentThread().name}] $msg")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(binding.root)


        // 测试 CoroutineScope 对协程生命周期的管理
        // tag：协程1
        fun testCoroutineLifecycle() {
            launch {
                repeat(5) {
                    delay(500L * it)
                    log("test_lifecycle", it)
                }
            }
            log("test_lifecycle", "Activity Created")
        }
        // testCoroutineLifecycle()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun test_CoroutineStart(tag: String = "test_CoroutineStart") {
        lifecycleScope.launch {
            // main

            launch {
                // main
                log(tag, "CoroutineStart1")
            }

            launch(Dispatchers.Default) {
                // default
                log(tag, "CoroutineStart2")
            }
            launch(Dispatchers.Default, start = CoroutineStart.ATOMIC) {
                // main
                log(tag, "CoroutineStart.ATOMIC")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        // cancel 后，在 Activity 中启动的协程（[onCreate方法中-tag：协程1]）就不再会执行了
        cancel()
        log("test_lifecycle", "Activity Destroyed")
    }

    fun basicUse(tag: String = "basicUse") {
        GlobalScope.launch(context = Dispatchers.IO) {
            log(tag, "launch start")
            // 延时一秒
            delay(1000)
            log(tag, "launch")
        }
        val job = GlobalScope.launch(context = Dispatchers.IO) {
            log(tag, "b launch start")
        }
        log(tag, "end")
    }

    // 挂起函数不会阻塞其所在线程
    fun test_suspend_withContext(tag: String = "test_suspend_withContext") {
        // 模拟网络请求
        suspend fun getNetData(url: String): String = withContext(Dispatchers.IO) {
            delay(2000L)
            "Get it."
        }

        GlobalScope.launch(context = Dispatchers.Main) {

            val result = getNetData("https://developer.android.com") // Dispatchers.IO for `get`

            // 下面拿到结果就可以更新 UI 了
            log(tag, "result = $result")
        }
        log(tag, "主线程并没有被上面的协程阻塞")
    }

    /**
     * 运行结果：
     * [main] start
     * [main] end
     * [DefaultDispatcher-worker-2] GlobalScope
     * [DefaultDispatcher-worker-1] launch B
     * [DefaultDispatcher-worker-1] launch A
     */
    suspend fun test_GlobeScope(tag: String = "test_GlobeScope") {
        log(tag, "start")
        GlobalScope.launch {
            launch {
                delay(400)
                log(tag, "launch A")
            }
            launch {
                delay(300)
                log(tag, "launch B")
            }
            log(tag, "GlobalScope")
        }
        log(tag, "end")
    }

    suspend fun test_runBlocking(tag: String = "test_runBlocking") {
        log(tag, "start")
        runBlocking {
            launch {
                repeat(3) {
                    delay(100)
                    log(tag, "launchA - $it")
                }
            }
            launch {
                repeat(3) {
                    delay(100)
                    log(tag, "launchB - $it")
                }
            }
            GlobalScope.launch {
                repeat(3) {
                    delay(120)
                    log(tag, "GlobalScope - $it")
                }
            }

            log(tag, "runBlocking end")
        }
        log(tag, "end")
    }

    suspend fun test_coroutineScope(tag: String = "test_coroutineScope") {
        fun log(msg: String) = log(tag, msg)

        runBlocking {
            log("CoroutineScope1 is $this")
            launch {
                log("CoroutineScope2 is $this")
                delay(100)
                log("Task from runBlocking")
            }

            // coroutineScope 是一个挂起函数
            coroutineScope {
                log("CoroutineScope3 is $this")
                launch {
                    delay(500)
                    log("Task from nested launch")
                }
                delay(50)
                log("Task from coroutine scope")
            }

            // 这行，需要等待 coroutineScope 中的所有代码执行完，才会执行，因为 coroutineScope 是挂起函数
            log("Coroutine scope is over")
        }
    }

    suspend fun test_supervisorScope(tag: String = "test_supervisorScope") = supervisorScope {
        launch { throw IllegalArgumentException("supervisorScope 中抛出异常") }
        launch {
            delay(1000)
            Log.e(tag, "supervisorScope 中另一个协程")
        }
    }

    // 相较于 supervisorScope ， coroutineScope 中抛出异常后，同一个作用域下的其他协程不再执行
    @SuppressLint("LongLogTag")
    private suspend fun test_CoroutineScopeException(tag: String = "test_CoroutineScopeException") =
        coroutineScope {
            launch { throw IllegalArgumentException("coroutineScope 中抛出异常") }
            launch {
                delay(1000)
                Log.e(tag, "coroutineScope 中另一个协程")
            }
        }

    suspend fun test_job(tag: String = "test_job") {
        fun log(msg: String) = log(tag, msg)

        //将协程设置为延迟启动
        val job = GlobalScope.launch(start = CoroutineStart.LAZY) {
            for (i in 0..100) {
                //每循环一次均延迟一百毫秒
                delay(100)
            }
        }
        job.invokeOnCompletion {
            log("invokeOnCompletion：$it")
        }
        log("1. job.isActive：${job.isActive}")
        log("1. job.isCancelled：${job.isCancelled}")
        log("1. job.isCompleted：${job.isCompleted}")

        job.start()

        log("2. job.isActive：${job.isActive}")
        log("2. job.isCancelled：${job.isCancelled}")
        log("2. job.isCompleted：${job.isCompleted}")

        job.cancel(CancellationException("test"))

        log("3. job.isActive：${job.isActive}")
        log("3. job.isCancelled：${job.isCancelled}")
        log("3. job.isCompleted：${job.isCompleted}")
    }

    suspend fun test_async(tag: String = "test_async") {
        val time = measureTimeMillis {
            runBlocking {
                val asyncA = async {
                    log(tag, "1")
                    delay(3000)
                    1
                }
                val asyncB = async {
                    log(tag, "2")
                    delay(4000)
                    2
                }
                log(tag, asyncA.await() + asyncB.await())
            }
        }
        log(tag, time)
    }

    fun test_coroutineDispatcher(tag: String = "test_coroutineDispatcher") {
        fun log(msg: String) = log(tag, msg)

        runBlocking(CoroutineName("test_coroutineDispatcher")) {
            launch {
                log("main runBlocking")
            }
            launch(Dispatchers.Default) {
                log("Default")
                launch(Dispatchers.Unconfined) {
                    log("Unconfined 1")
                }
            }
            launch(Dispatchers.IO) {
                log("IO")
                launch(Dispatchers.Unconfined) {
                    log("Unconfined 2")
                }
            }
            launch(newSingleThreadContext("MyOwnThread")) {
                log("newSingleThreadContext")
                launch(Dispatchers.Unconfined) {
                    log("Unconfined 4")
                }
            }
            launch(Dispatchers.Unconfined) {
                log("Unconfined 3")
            }
            GlobalScope.launch {
                log("GlobalScope")
            }
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun test_coroutineContext(tag: String = "test_coroutineContext") {
        runBlocking<Unit> {
            // 指定协程名称
            launch(Dispatchers.IO + CoroutineName("test")) {
                // 打印协程名称
                log(tag, "Hello World , ${coroutineContext[CoroutineName]}")
                log(tag, "Hello World , ${coroutineContext[CoroutineDispatcher]}")
            }
        }
    }

    /**
     * 测试协程的 [cancel]，
     * 以及 cancel 后的 try/catch/finally，可以通过这种方式在协程被取消后释放资源
     */
    fun test_cancel(tag: String = "test_cancel") {
        fun log(msg: String) = log(tag, msg)

        runBlocking {
            val job = launch {

                try {
                    repeat(1000) { i ->
                        log("job: I'm sleeping $i ...")
                        delay(500L)
                    }
                } catch (e: Throwable) {
                    log(e.message.toString())
                } finally {
                    log("job: I'm running finally")
                }

            }
            delay(1300L)
            log("main: I'm tired of waiting!")
            job.cancel()
            job.join()
            log("main: Now I can quit.")
        }
    }

    /**
     * 区别于 [test_cancel]，这个方法在执行循环的时候没有用到挂起函数 [delay]
     * 因此，在 cancel 后还会继续执行
     * 如果需要在 cancel 后更快的退出协程，可以看方法 [test_cancel_3]
     */
    fun test_cancel_2(tag: String = "test_cancel_2") {
        fun log(msg: String) = log(tag, msg)

        runBlocking {
            val startTime = System.currentTimeMillis()
            val job = launch(Dispatchers.Default) {
                var nextPrintTime = startTime
                var i = 0
                while (i < 5) {
                    if (System.currentTimeMillis() >= nextPrintTime) {
                        log("job: I'm sleeping ${i++} ...")
                        nextPrintTime += 500L
                    }
                }
            }
            delay(1300L)
            log("main: I'm tired of waiting!")
            job.cancelAndJoin()
            log("main: Now I can quit.")
        }
    }

    /**
     * 通过在协程执行的代码块中，主动加上判断协程状态的方法，来主动退出
     */
    fun test_cancel_3(tag: String = "test_cancel_3") {
        fun log(msg: String) = log(tag, msg)

        runBlocking {
            val startTime = System.currentTimeMillis()
            val job = launch(Dispatchers.Default) {
                var nextPrintTime = startTime
                var i = 0
                while (i < 5) {

                    if (isActive) {
                        if (System.currentTimeMillis() >= nextPrintTime) {
                            log("job: I'm sleeping ${i++} ...")
                            nextPrintTime += 500L
                        }
                    } else {
                        return@launch
                    }
                }
            }
            delay(1300L)
            log("main: I'm tired of waiting!")
            job.cancelAndJoin()
            log("main: Now I can quit.")
        }
    }

    /**
     * 在 cancel 后的 finally 中，不能再次执行挂起喊出，否则会抛出异常，导致后续代码不执行
     */
    fun test_cancel_4(tag: String = "test_cancel_4") {
        fun log(msg: String) = log(tag, msg)

        runBlocking {
            log("start")
            val launchA = launch {
                try {
                    repeat(5) {
                        delay(50)
                        log("launchA-$it")
                    }
                } finally {
                    // 这个挂起函数将导致 finally 中后面的代码不执行
                    delay(50)
                    log("launchA isCompleted")
                }
            }
            val launchB = launch {
                try {
                    repeat(5) {
                        delay(50)
                        log("launchB-$it")
                    }
                } finally {
                    // 如果想要在 finally 中调用挂起函数，可以做如下操作
                    withContext(NonCancellable) {
                        delay(50)
                        log("launchB isCompleted")
                    }
                }
            }
            //延时 200 毫秒，保证两个协程都已经被启动了
            delay(200)
            launchA.cancel()
            launchB.cancel()
            log("end")
        }
    }

    fun test_parent_children_coroutine(tag: String = "test_parent_children_coroutine") {
        fun log(msg: String) = log(tag, msg)

        runBlocking {
            val parentJob = launch {
                repeat(3) { i ->
                    launch {
                        delay((i + 1) * 200L)
                        log("Coroutine $i is done")
                    }
                }
                log("request: I'm done and I don't explicitly join my children that are still active")
            }
        }
    }

    fun test_cancel_5(tag: String = "test_cancel_5") {
        fun log(msg: String) = log(tag, msg)

        runBlocking {
            val request = launch {
                val job1 = launch {
                    repeat(10) {
                        delay(300)
                        log("job1: $it")
                        if (it == 2) {
                            log("job1 canceled")
                            cancel()
                        }
                    }
                }
                val job2 = launch {
                    repeat(10) {
                        delay(300)
                        log("job2: $it")
                    }
                }
            }
            delay(1600)
            log("parent job canceled")
            request.cancel()
            delay(1000)
        }
    }

    fun test_withTimeout(tag: String = "test_withTimeout") {
        fun log(msg: String) = log(tag, msg)

        runBlocking {
            log("start")
            try {
                val result = withTimeout(300) {
                    repeat(5) {
                        delay(100)
                    }
                    200
                }
                log("result is : $result")
            } catch (e: Throwable) {
                log(e.message.toString())
            }

            log("end")
        }
    }

    /**
     * withTimeout 在抛出异常时，会使协程结束运行
     * 如果不想因为异常结束，可以使用 withTimeoutOrNull，在有异常时输出 null
     */
    fun test_withTimeoutOrNull(tag: String = "test_withTimeoutOrNull") {
        fun log(msg: String) = log(tag, msg)

        runBlocking {
            log("start")
            try {
                val result = withTimeoutOrNull(300) {
                    repeat(5) {
                        delay(100)
                    }
                    200
                }
                log("result is : $result")
            } catch (e: Throwable) {
                log(e.message.toString())
            }

            log("end")
        }
    }

    fun test_exception(tag: String = "test_exception") {
        fun log(msg: String) = log(tag, msg)

        runBlocking {
            val handler = CoroutineExceptionHandler { _, exception ->
                log("Caught $exception")
            }
            val deferred = GlobalScope.async(handler) {
                throw ArithmeticException()
            }
            val job = GlobalScope.launch(handler) {
                throw AssertionError()
            }
            try {
                deferred.await()
            } catch (e: Exception) {
                log(tag, e)
            }
            joinAll(job, deferred)
        }
    }

    fun test_exception1(tag: String = "test_exception1") {
        fun log(msg: String) = log(tag, msg)

        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            log(throwable.toString())
        }
        CoroutineScope(Dispatchers.Default + Job() + exceptionHandler).launch {
            val a = launch {
                try {
                    throw IllegalArgumentException("job 中抛出异常")
                } catch (e: Exception) {
                    log("a exception is try/catch, $e")
                }
            }
            val b = launch {
                delay(1000)
                log("另一个协程")
            }
            b.invokeOnCompletion {
                log("b onCompletion, throwable is $it")
            }
        }
    }

    // 测试协程返回值
    fun testReturn(tag: String = "testReturn") {
        val runBlockingJob = runBlocking {
            log("runBlocking", "启动一个协程")
            41
        }
        log("runBlockingJob", "$runBlockingJob")
        val launchJob = GlobalScope.launch {
            log("launch", "启动一个协程")
        }
        log("launchJob", "$launchJob")
        val asyncJob = GlobalScope.async {
            log("async", "启动一个协程")
            "我是返回值"
        }
        log("asyncJob", "$asyncJob")
    }


    override fun getItems(): List<String> {
        return listOf(
            "basic",
            "test_suspend_withContext",
            "GlobeScope",
            "runBlocking",
            "test_coroutineScope",         // 作用域
            "test_supervisorScope",
            "test_CoroutineScopeException",
            "test_job",
            "test_async",
            "test_coroutineDispatcher",
            "test_coroutineContext",
            "test_cancel",
            "test_cancel_2",
            "test_cancel_3",
            "test_cancel_4",
            "test_parent_children_coroutine",
            "test_cancel_5",
            "test_withTimeout",
            "test_withTimeoutOrNull",
            "test_exception",
            "test_exception1",
            "testReturn",
            "test_CoroutineStart",
            "",
        )
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        Log.e("test_协程中的异常", "异常信息: ${e.message}")
    }

    override fun onItemClick(itemName: String) {
        lifecycleScope.launch(exceptionHandler) {
            when (itemName) {
                // 创建操作符
                "basic" -> basicUse()
                "test_suspend_withContext" -> test_suspend_withContext()
                "GlobeScope" -> test_GlobeScope()
                "runBlocking" -> test_runBlocking()
                "test_coroutineScope" -> test_coroutineScope()
                "test_supervisorScope" -> test_supervisorScope()
                "test_CoroutineScopeException" -> test_CoroutineScopeException()
                "test_job" -> test_job()
                "test_async" -> test_async()
                "test_coroutineDispatcher" -> test_coroutineDispatcher()
                "test_coroutineContext" -> test_coroutineContext()
                "test_cancel" -> test_cancel()
                "test_cancel_2" -> test_cancel_2()
                "test_cancel_3" -> test_cancel_3()
                "test_cancel_4" -> test_cancel_4()
                "test_parent_children_coroutine" -> test_parent_children_coroutine()
                "test_cancel_5" -> test_cancel_5()
                "test_withTimeout" -> test_withTimeout()
                "test_withTimeoutOrNull" -> test_withTimeoutOrNull()
                "test_exception" -> test_exception()
                "test_exception1" -> test_exception1()
                "testReturn" -> testReturn()
                "test_CoroutineStart" -> test_CoroutineStart()
            }
        }
    }
}
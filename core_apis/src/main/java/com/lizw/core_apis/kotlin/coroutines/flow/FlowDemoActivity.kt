package com.lizw.core_apis.kotlin.coroutines.flow

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.lizw.core_apis.databinding.ActivityFlowDemoBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread

/**
 * 作用：
 * 技术栈：
 */
class FlowDemoActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "FlowDemoActivity"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vb = ActivityFlowDemoBinding.inflate(layoutInflater)
        setContentView(vb.root)
        
        val list = listOf(
                "flow", "callbackFlow", "callbackFlow,模拟网络请求","emptyFlow","channelFlow",
                "flatMapContact",
                "debounce"
        )
        list.onEach { item ->
            val btn = Button(this).apply {
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100)
                text = item
                isAllCaps = false
                setOnClickListener {
                    lifecycleScope.launch {
                        
                        when (item) {
                            // 创建操作符
                            "flow" -> CreateOperator.test_flow()
                            "callbackFlow" -> CreateOperator.test_callbackFlow()
                            "callbackFlow,模拟网络请求" -> CreateOperator.test_callbackFlow_2()
                            "emptyFlow" -> CreateOperator.test_emptyFlow()
                            "channelFlow" -> CreateOperator.test_channelFlow()
                            //
                            "flatMapContact" -> flatMapContact()
                            
                            "debounce" -> testDebounce()
                        }
                        
                    }
                }
            }
            vb.container.addView(btn)
        }
    }
    
    // 创建操作符
    object CreateOperator {
        // flow flowOf asFlow
        suspend fun test_flow() {
            val testFlow = flow<String> {
                emit("hello")
                withContext(Dispatchers.Main) {
                    emit("20")
                }
                emit("flow")
            }
            
            val f1 = flowOf(1, 2, 3)
            
            val f2 = flow<Int> {
                emit(1)
                withContext(Dispatchers.Main) {
                    emit(20)
                }
                emit(2)
                emitAll(flowOf(1, 2, 3))
            }
            
            val f3 = listOf(1, 2, 3).asFlow()
            
            //接收
            testFlow.collect { value ->
                println(value)
            }
            
            // collect 不使用 lambda
            // testFlow.collect(object : FlowCollector<String> {
            //     override suspend fun emit(value: String) {
            //         println(value)
            //     }
            // })
        }
        
        // callbackFlow
        suspend fun test_callbackFlow() {
            val flow = callbackFlow<Int> {
                send(1)
                send(2)
                send(3)
                
                // tag1
                close()
                // tag2
                cancel()
                
                // 1、最后一行必须加上 awaitClose ，否则会抛出异常。如果不加，回调方法可能存在内存泄漏
                // 2、当执行 collect 后，awaitClose 并不会执行。那是什么时候执行的呢？
                // tag1 调用 close()
                // tag2 调用 cancel()
                awaitClose {
                    // 在这里做一些回收操作，避免内存泄漏
                    Log.i("callbackFlow_awaitClose", "done release here")
                }
            }.collect {
                Log.i("callbackFlow_collect", it.toString())
            }
            
            /**
             * 模拟网络请求
             */
            fun requestApi(block: (String) -> Unit) {
                thread {
                    Log.d("requestApi - ", "网络请求")
                    Thread.sleep(3000)
                    block("网络请求结果：xxx")
                }
            }
            
            callbackFlow {
                
                //模拟网络请求回调
                requestApi { result ->
                    //发送数据
                    trySend(result)
                }
                
                awaitClose {
                    //当数据接收者所在的协程被关闭的时候会调用。
                    //作用是：用来释放资源，取消网络请求等操作
                    Log.i("callbackFlow_awaitClose", "")
                }
            }.collect {
                //接收结果
                Log.i("callbackFlow_collect", it)
            }
        }
        
        // callbackFlow 模拟网络请求
        suspend fun test_callbackFlow_2() {
            /**
             * 模拟网络请求
             */
            fun requestApi(block: (String) -> Unit) {
                thread {
                    Log.d("requestApi - ", "网络请求")
                    Thread.sleep(3000)
                    block("网络请求结果：xxx")
                }
            }
            
            callbackFlow {
                
                //模拟网络请求回调
                requestApi { result ->
                    //发送数据
                    trySend(result)
                }
                
                awaitClose {
                    //当数据接收者所在的协程被关闭的时候会调用。
                    //作用是：用来释放资源，取消网络请求等操作
                    Log.i("callbackFlow_awaitClose", "")
                }
            }.collect {
                //接收结果
                Log.i("callbackFlow_collect", it)
            }
        }
        
        suspend fun test_emptyFlow() {
            // 创建一个空的 Flow
            val emptyFlowInstance = emptyFlow<String>()
            
            // 观察这个空的 Flow
            emptyFlowInstance.collect {
                // 这个 lambda 函数永远不会执行，因为 emptyFlow 不发出任何元素
                Log.i("emptyFlow", it)
            }
        }
        
        // channelFlow
        suspend fun test_channelFlow() {
            //构建
            val channelFlow = channelFlow<String> {
                send("hello")
                withContext(Dispatchers.IO) {
                    send("channel flow")
                }
            }
            
            //监听
            channelFlow.collect { value ->
                println(value)
            }
        }
        
        // StateFlow，热流
        suspend fun test_stateFlow() {
            //创建
            val uiState = MutableStateFlow(0)
            
            //监听
            uiState.collect { value ->
                println(value)
            }
            
            //赋值
            uiState.value = 1
        }
        
        // SharedFlow，热流
        suspend fun test_shareFlow() {
            //创建
            val signEvent = MutableSharedFlow<String>()
            
            //监听
            signEvent.collect { value ->
                println(value)
            }
            
            //赋值
            signEvent.tryEmit("hello")
            signEvent.tryEmit("shared flow")
        }
    }
    
    suspend fun flatMapContact() {
        flowOf(1, 2, 3).flatMapConcat {
            flowOf("$it map")
        }.collect { value ->
            println(value)
        }
        
        
        
        flowOf(1, 2, 3).flatMapConcat {
            // way1
            flowOf("$it map1", "$it map2")
            
            // 下面的写法和 way1 的运行效果是一样的
            // 同时写两个 flow ，只有最后一个生效
            flow {
                emit("$it map11")
                delay(1010L)
                emitAll(flowOf("$it map22"))
            }
        }.onEach {
            println("onEach , $it")
        }.debounce {
            if (it.equals("1 map11")) {
                0L
            } else {
                1000L
            }
        }.onEach {
            println("onEach2 , $it")
        }.onCompletion {
            println("onCompletion")
            emit("onCompletion")
        }.collect { value ->
            Log.i("collect", value)
        }
    }
    
    suspend fun testDebounce() {
        flow {
            emit(1)
            delay(1009)
            emit(2)
            delay(900)
            emit(3)
            delay(200)
            emit(4)
            delay(1010)
            emit(5)
        }.debounce {
            if (it == 1) {
                0L
            } else {
                1000L
            }
        }.collect { value ->
            println(value)
        }
    }
}
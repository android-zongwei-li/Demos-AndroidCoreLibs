package com.lizw.core_apis.thirdpartlibs.okhttp

import okhttp3.Dns
import java.net.InetAddress
import java.net.UnknownHostException
import java.util.concurrent.FutureTask
import java.util.concurrent.TimeUnit

class CustomNDS(private val timeout: Long) : Dns {
    @Throws(UnknownHostException::class)
    override fun lookup(hostname: String): List<InetAddress> {
        return try {
            val task = FutureTask { listOf(*InetAddress.getAllByName(hostname)) }
            Thread(task).start()
            task[timeout, TimeUnit.MILLISECONDS]
        } catch (e: Exception) {
            val unknownHostException = UnknownHostException("Broken system behaviour for dns lookup of $hostname")
            unknownHostException.initCause(e)
            throw unknownHostException
        }
    }
}
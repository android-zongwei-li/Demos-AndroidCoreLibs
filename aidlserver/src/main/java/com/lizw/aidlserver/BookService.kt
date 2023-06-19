package com.lizw.aidlserver

import android.app.Service
import android.content.Intent
import android.os.IBinder

class BookService : Service() {
    private val binder = BookService()

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    private class BookService : IBookService.Stub() {
        override fun getBookInfo(): BookInfo {
            return BookInfo().apply {
                name = "testBook"
                this.type = 1
                this.price = 33
            }
        }

        override fun isExist(bookInfo: BookInfo): Boolean {
            val demoBook = BookInfo().apply {
                name = "demoBook"
            }

            return demoBook.name == bookInfo.name
        }
    }
}
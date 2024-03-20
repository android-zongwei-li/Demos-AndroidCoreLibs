package com.lizw.core_apis.android.contentprovider.demo

class Picture {
    @JvmField var name: String? = null
    @JvmField var url: String? = null
    @JvmField var path: String? = null
    override fun toString(): String {
        return ("Picture [name=" + name + ", url=" + url + ", path=" + path
                + "]")
    }
}
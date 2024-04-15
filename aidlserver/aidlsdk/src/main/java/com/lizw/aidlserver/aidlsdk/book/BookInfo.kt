package com.lizw.aidlserver.aidlsdk.book

import android.os.Parcel
import android.os.Parcelable

/**
 * 图书信息，实现Parcelable，用于进程间传输
 *
 * author:lizw
 * created on: 2022/6/25
 */
class BookInfo() : Parcelable {
    var name: String = ""
    var type = 0
    var price = 0

    constructor(inParcel: Parcel) : this() {
        readFromParcel(inParcel)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(outParcel: Parcel, flags: Int) {
        outParcel.writeString(name)
        outParcel.writeInt(type)
        outParcel.writeInt(price)
    }

    private fun readFromParcel(inParcel: Parcel) {
        name = inParcel.readString().toString()
        type = inParcel.readInt()
        price = inParcel.readInt()
    }

    companion object CREATOR : Parcelable.Creator<BookInfo> {
        override fun createFromParcel(parcel: Parcel): BookInfo {
            return BookInfo(parcel)
        }

        override fun newArray(size: Int): Array<BookInfo> {
            return Array(size) { BookInfo() }
        }
    }
}
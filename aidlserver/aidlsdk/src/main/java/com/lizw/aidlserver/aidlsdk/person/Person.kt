package com.lizw.aidlserver.aidlsdk.person

import android.os.Parcel
import android.os.Parcelable

/**
 * 作用：Bean类，人员信息
 * 技术栈：aidl bean 类定义，Parcel
 */
class Person() : Parcelable {
    var name: String = ""
    var age = 0
    
    constructor(parcel: Parcel) : this() {
        name = parcel.readString().toString()
        age = parcel.readInt()
    }
    
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(age)
    }
    
    override fun describeContents(): Int {
        return 0
    }
    
    companion object CREATOR : Parcelable.Creator<Person> {
        override fun createFromParcel(parcel: Parcel): Person {
            return Person(parcel)
        }
        
        override fun newArray(size: Int): Array<Person?> {
            return arrayOfNulls(size)
        }
    }
}
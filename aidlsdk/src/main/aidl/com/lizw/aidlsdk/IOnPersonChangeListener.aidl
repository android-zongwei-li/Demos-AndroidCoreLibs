// IOnPersonChangeListener.aidl
package com.lizw.aidlsdk;

// Declare any non-default types here with import statements

interface IOnPersonChangeListener {
    /**
    *  @params personNums 当前人数
    */
    void onChange(int personNums);
}
// ICommonDoBusinessCallback.aidl
package com.lizw.aidlsdk;

// Declare any non-default types here with import statements

interface ICommonDoBusinessCallback {
    void onSuccess(in Bundle bundle,in Intent intent);

    void onError(String errorMsg);
}
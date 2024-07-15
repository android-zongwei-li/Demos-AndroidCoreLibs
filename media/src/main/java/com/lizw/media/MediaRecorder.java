package com.lizw.media;

public class MediaRecorder{
    static {
        System.loadLibrary("media_jni");//1
        native_init();//2
    }
    private static native final void native_init();//3

    public native void start() throws IllegalStateException;

    private native final void native_setup(Object mediarecorder_this,
                                           String clientName, String opPackageName) throws IllegalStateException;
}
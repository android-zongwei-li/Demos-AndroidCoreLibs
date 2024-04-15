// 指定包路径，就是这个aidl文件的路径。客户端需将这个aidl文件复制到相同路径。
// 然后build生成接口实现类，即可使用。
package com.lizw.aidlserver.aidlsdk;

/**
 * 一个服务端的aidl接口示例。定义服务端可以为客户端提供的方法。
 * 将通过Service返回给客户端。
 */
interface IRemoteService {
    /**
     * 获取pid。
     */
    int getPid();

    /**
     * 可以作为参数和返回值的基本类型。
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}
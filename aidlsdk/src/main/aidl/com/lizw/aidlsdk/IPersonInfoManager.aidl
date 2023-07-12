// IPersonInfoManager.aidl
package com.lizw.aidlsdk;

// Declare any non-default types here with import statements
import com.lizw.aidlsdk.ICommonDoBusinessCallback;
import com.lizw.aidlsdk.IOnPersonChangeListener;
import com.lizw.aidlsdk.Person;

/**
* 通过此接口来查询人员信息
*/
interface IPersonInfoManager {
    /**
    * 添加人员信息
    * @return true，添加成功；false，添加失败
    */
    boolean addPerson(in Person person);

    /**
    * 查询有多少人员信息
    */
    int howManyPersons();

    /**
    * 执行业务
    */
    int doBusiness(in Bundle bundle,in Intent intent,ICommonDoBusinessCallback commonDoBusinessCallback);

    /**
    *  添加回调
    */
    void addOnPersonChangeListener(IOnPersonChangeListener onPersonChangeListener);

    /**
    *  移除回调
    */
    void removeOnPersonChangeListener(IOnPersonChangeListener onPersonChangeListener);

}
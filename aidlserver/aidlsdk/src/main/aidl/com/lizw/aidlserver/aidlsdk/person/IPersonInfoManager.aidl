// 指定包路径，就是这个aidl文件的路径。客户端需将这个aidl文件复制到相同路径。
// 然后build生成接口实现类，即可使用。
// IPersonInfoManager.aidl
package com.lizw.aidlserver.aidlsdk.person;

// Declare any non-default types here with import statements
import com.lizw.aidlserver.aidlsdk.ICommonDoBusinessCallback;
import com.lizw.aidlserver.aidlsdk.person.IOnPersonChangeListener;
import com.lizw.aidlserver.aidlsdk.person.Person;

/**
* 通过此接口来查询人员信息
*
* 一个服务端的aidl接口示例。定义服务端可以为客户端提供的方法。
* 将通过Service返回给客户端。
*/
interface IPersonInfoManager {
    /**
    * 添加人员信息
    * @return true，添加成功；false，添加失败
    */
    boolean addPerson(in Person person);

    // 获取人员信息
    Person getPerson();

    // 查询某人是否存在
    boolean isExist(in Person person);

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

    /**
     * 通用的接口。获取pid。
     */
    int getPid();

    /**
     * 通用的接口。可以作为参数和返回值的基本类型。
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    /**
    * 通过 AIDL 传输 List
    * 返回年龄为 age 的 Person 集合
    */
    List<Person> getPersonsList(int age);

    /**
    * 通过 AIDL 传输 Map
    * 注意：Key-Value 不支持 Integer 等包装类型
    */
    Map<String,Person> getPersonsMap();

    /**
     * 发送数据。
     * 可以用来验证 AIDL 传输时，对最大数据量的限制问题
     * 需要注意：数组必须指定方向 in/out/inout
     */
    void sendData(inout byte[] data);

    /**
     * 使用 AIDL 接口，获取 Bitmap 图片。
     */
    Bitmap getIcon();

    /**
     * 从服务端获取文件
     * 返回值可能为 null
     */
    ParcelFileDescriptor getFileDescriptor();
}
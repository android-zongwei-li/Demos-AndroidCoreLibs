在写aidl client 时，下面两点需要注意
1、要在 build.gradle 中添加 如下代码，否则run会提示没有BookInfo文件
sourceSets {
    main {
        java {
            // src/main/aidl 这个路径配置成java后，aidl下的文件会被放到java包下
            srcDirs = [
                'src/main/java',
                'src/main/aidl'
            ]
        }
    }
}

2、在 AndroidManifest 中要添加如下代码，否则不能访问到服务端App
<queries>
    <package android:name="com.lizw.aidlserver" />
</queries>

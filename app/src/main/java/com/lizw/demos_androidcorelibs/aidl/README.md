libs 目录下，放入 aidlsdk-release.aar 包。
然后 引入 lib 下的 sdk
implementation fileTree(include: ['*.jar', '*.aar'], dir: 'libs')

在写aidl client 时，下面两点需要注意
1、通过aar sdk 引入的方式不需要。
~~要在 build.gradle 中添加 如下代码，否则run会提示没有BookInfo文件~~
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

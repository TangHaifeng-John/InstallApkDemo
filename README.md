由于Android7.0开始，对权限控制加强，APK安装方法也有了一些变化，接下来直接进入到代码讲解


**kotlin代码**

	

 

     fun installApk(path: String, mContext: Activity) {
    
                val apkFile = File(path)
                if (!apkFile.exists()) {
                    throw RuntimeException("安装文件不存在!")
                }
    
                val intent = Intent(Intent.ACTION_VIEW)
    
    
                if (Build.VERSION.SDK_INT >= 24) {
                    //7.0和7.0以上手机按照这样执行
                    val apkUri = FileProvider.getUriForFile(mContext, mContext.packageName + ".fileprovider",
                        apkFile)
                    intent.flags=Intent.FLAG_GRANT_READ_URI_PERMISSION
                    intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
                } else {
                    //7.0以下的手机
                    intent.flags =Intent.FLAG_ACTIVITY_NEW_TASK;
                    intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive")
                }
    
                mContext.startActivity(intent)
    
    
            }

除了上面的Kotlin代码以外，你必须在Manifest添加Provider，否则7.0或者以上的手机会报错

    <provider
                    android:name="android.support.v4.content.FileProvider"
                    android:authorities="${applicationId}.fileprovider"
                    android:exported="false"
                    android:grantUriPermissions="true">
                <meta-data
                        android:name="android.support.FILE_PROVIDER_PATHS"
                        android:resource="@xml/file_paths_public"></meta-data>
            </provider>


其中  android:resource="@xml/file_paths_public"></meta-data> 这行代码需要注意，如果要正常使用Provider你必须添加路径清单

**file_paths_public.xml文件**

    <?xml version="1.0" encoding="utf-8"?>
    <!--
      Copyright 2017 Zhihu Inc.
    
      Licensed under the Apache License, Version 2.0 (the "License");
      you may not use this file except in compliance with the License.
      You may obtain a copy of the License at
    
      http://www.apache.org/licenses/LICENSE-2.0
    
      Unless required by applicable law or agreed to in writing, software
      distributed under the License is distributed on an "AS IS" BASIS,
      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
      See the License for the specific language governing permissions and
      limitations under the License.
      -->
    <paths>
        <!--内置SD卡 Environment.getExternalStorageDirectory() .表示共享所有的目录，也可以指定共享的目录-->
        <external-path
            name="external-path"
            path="."/>
        <!--内置SD卡 Context.getExternalCacheDir() .表示共享所有的目录，也可以指定共享的目录-->
        <external-cache-path
            name="external-cache-path"
            path="."/>
        <!--内置SD卡 Context.getExternalFilesDir(null) .表示共享所有的目录，也可以指定共享的目录-->
        <external-files-path
            name="external-files-path"
            path="."/>
        <!--data目录下 Context.getFilesDir() .表示共享所有的目录，也可以指定共享的目录-->
        <files-path
            name="files_path"
            path="."/>
        <!--data缓存目录 Context.getCacheDir() .表示共享所有的目录，也可以指定共享的目录-->
        <cache-path
            name="cache-path"
            path="."/>
        <!--这个标签Android官方文档中是没有提及，Android设备的根目录，该目录下包含着手机内部存储器，外置SD卡等所有文件的目录-->
        <root-path
            name="name"
            path="."/>
    </paths>

**file_paths_public.xml 需要放入到 rest/xml文件目录下面，如下图**

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190702191608134.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1RFMjgwOTMxNjM=,size_16,color_FFFFFF,t_70)

**8.0手机适配**
8.0手机还需要在Manifest清单里面添加安装权限

    <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES"/>


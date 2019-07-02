package com.haifeng.example.installapkdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import com.haifeng.example.installapkdemo.InstallApk.Companion.installApk
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        install_Apk.setOnClickListener{
//            Environment.getExternalStorageDirectory().absolutePath+"/a.apk" 替换成你的APK的真实路径
            installApk(Environment.getExternalStorageDirectory().absolutePath+"/a.apk",this@MainActivity)
        }
    }
}

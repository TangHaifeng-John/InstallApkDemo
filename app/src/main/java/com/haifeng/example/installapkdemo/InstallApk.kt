package com.haifeng.example.installapkdemo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.support.v4.content.FileProvider
import java.io.File
import java.lang.RuntimeException

class InstallApk {

    companion object {
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
                intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive")
            }

            mContext.startActivityForResult(intent,0)


        }


    }

}


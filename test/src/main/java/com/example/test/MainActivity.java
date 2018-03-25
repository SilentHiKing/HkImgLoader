package com.example.test;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.hiking.hkimgloader.loader.HkImageLoader;

public class MainActivity extends Activity implements View.OnClickListener {

    private final static int REQUEST_CODE_STORAGE = 101;
    int permissionsFlag=0;
    ImageView imageView;
    Button btn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        writeExternalStorage(null);
//        readExternalStorage(null);

        requestPremission();
        imageView = (ImageView) findViewById(R.id.img);
        imageView.setImageResource(R.mipmap.ic_launcher);
        btn=(Button) findViewById(R.id.click);
        btn.setOnClickListener(this);

//        HkImageLoader.getInstance(getApplicationContext()).displayImage(imageView,"http://www.baidu.com/img/bdlogo.png");
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.click:
                if(permissionsFlag ==0){
                    requestPremission();
                }else{
                    HkImageLoader.getInstance(getApplicationContext()).displayImage(imageView,"http://www.baidu.com/img/bdlogo.png");
                }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_CODE_STORAGE:
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        permissionsFlag=1;
                        Toast.makeText(this, "" + "权限" + permissions[i] + "申请成功", Toast.LENGTH_SHORT).show();
                    } else {
                        permissionsFlag=0;
                        Toast.makeText(this, "" + "权限" + permissions[i] + "申请失败", Toast.LENGTH_SHORT).show();
                    }
                }
        }

    }


    private void requestPremission(){
        /**
         * 动态获取权限，Android 6.0 新特性，一些保护权限，除了要在AndroidManifest中声明权限，还要使用如下代码动态获取
         */
        if (Build.VERSION.SDK_INT >= 23) {

            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_STORAGE);
                    return;
                }
            }
        }
    }



}



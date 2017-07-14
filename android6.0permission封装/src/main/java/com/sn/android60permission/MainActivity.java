package com.sn.android60permission;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //根据点击事件执行打电话
    public void callPhone(View view) {
        //进行了动态权限的适配,程序正常运行
        callPhone();
    }

    //根据点击事件执行往SD卡写入东西的功能
    public void writeSDCade(View view) {
        //进行了动态权限的适配,程序正常运行
        sdCardPermission();
    }

    //1.拨打电话是否有权限的判断逻辑
    private void callPhone() {
        //判断当前APP是否拥有打电话权限,参数: 1.上下文  2.Manifest.permission.某权限   PackageManager.PERMISSION_GRANTED:授予权限
        if (hasPermission(Manifest.permission.CALL_PHONE)){
            //2.申请动态的权限
            requestPermission(Constants.CALL_PHONE,Manifest.permission.CALL_PHONE);
        } else {
            //3.申请权限了,直接做打电话的业务逻辑
            doCallPhone();
        }
    }

    private void sdCardPermission() {
        if (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            //要申请权限
            requestPermission(Constants.WRITE_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }else{
            doSdCardPermission();
        }
    }

    //子类实现打电话的业务逻辑
    public void doCallPhone() {
        //执行打电话到10086的操作
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri uri = Uri.parse("tel:" + "10086");
        intent.setData(uri);
        //使用AS,这里会报红,编译能通过,只是提醒你要做Android6.0权限的适配
        startActivity(intent);
    }

    //子类实现写入SD卡的业务逻辑
    public void doSdCardPermission() {

    }

}

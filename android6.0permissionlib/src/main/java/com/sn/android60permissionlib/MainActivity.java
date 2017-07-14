package com.sn.android60permissionlib;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * 1.在清单文件配置权限
 * 2.在build.gradle文件进行依赖
 * 3.判断涉及到用户隐私的功能是否授权了对应的权限,如果没有自动申请
 * 4.申请权限成功,自动执行的逻辑(使用注解的形式,使用根据简单灵活)
 * 5.用户不同意权限,自动回调的逻辑
 * 6.覆写处理回调的接口方法
 */
public class MainActivity extends AppCompatActivity {

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

    private void callPhone() {
        //判断涉及到用户隐私的功能是否授权了对应的权限,如果没有自动申请
        //参数:1.上下文   2.int,权限区分码    3.String数组,要申请的权限
        PermissionGen.needPermission(this, 100,
                new String[] {
                        Manifest.permission.CALL_PHONE
                }
        );
    }

    /**
     * 申请权限成功,自动执行的逻辑(使用注解的形式,使用根据简单灵活)
     */
    @PermissionSuccess(requestCode = 100)
    private void doCallPhone() {
        //执行打电话到10086的操作
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri uri = Uri.parse("tel:" + "10086");
        intent.setData(uri);
        //使用AS,这里会报红,编译能通过,只是提醒你要做Android6.0权限的适配
        startActivity(intent);
    }

    //用户不同意权限,自动回调的逻辑
    @PermissionFail(requestCode = 100)
    private void doCallPhoneFail() {
        //执行打电话到10086的操作的权限没有通过
        Toast.makeText(this, "不好意思!权限没有通过不能打电话", Toast.LENGTH_SHORT).show();
    }

    //覆写处理回调的接口方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this,requestCode, permissions, grantResults);
    }

}

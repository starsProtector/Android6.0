package com.sn.android60;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * 1.所有权限都必须先在清单文件里配置,否则程序会报错,且在6.0系统上报错还不容易查找,直接退出了程序
 * 2.判断涉及到用户隐私的功能是否授权了对应的权限
 * 3.没有申请权限,做权限的申请处理;申请权限了,直接做对应的业务逻辑即可
 * 4.异步回调接口,判断是否通过了授权,做对应的逻辑操作
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //根据点击事件执行打电话
    public void callPhone(View view) {
        //直接拨打电话,在Android6.0的系统,没有进行动态权限的适配,程序直接退出
//        doCallPhone();
        //进行了动态权限的适配,程序正常运行
        callPhone();

    }

    private void callPhone() {
        //1.判断拨打电话是否授权了对应的权限
        //参数: 1.上下文  2.Manifest.permission.某权限   3.PackageManager.PERMISSION_GRANTED:授予权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //2.没有申请权限,做权限的申请处理  参数:1.上下文  2.字符串数组,可以一次申请多个权限 3.int型,请求码方便我们后面权限的区分
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 0);
        } else {
            //3.申请权限了,直接做打电话的业务逻辑
            doCallPhone();
        }
    }

    //4.异步回调接口,判断是否通过了授权,做对应的逻辑操作
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //因为这是处理所有权限申请的回调,为方便对应申请权限做对应逻辑操作,使用switch判断之前请求码的设置,区分权限
        switch (requestCode) {
            case 0://打电话权限的回调处理
                //判断打电话申请权限是否成功,成功就执行打电话的逻辑
                //注意:因为集合里只有一个权限申请,所以参数为0代表打电话权限
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doCallPhone();//打电话
                } else {
                    //用户拒绝了权限请求,给用户提示权限的功能
                    Toast.makeText(MainActivity.this, "权限没有授予", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    //拨打电话的业务逻辑
    private void doCallPhone() {
        //执行打电话到10086的操作
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri uri = Uri.parse("tel:" + "10086");
        intent.setData(uri);
        //使用AS,这里会报红,编译能通过,只是提醒你要做Android6.0权限的适配
        startActivity(intent);
    }

}

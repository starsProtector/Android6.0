package com.sn.android60permission;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
/**
 * 0.常量类
 * 1.在清单文件里配置权限
 * 2.创建一个基类,并在基类中为子类提供一个权限检查方法
 * 3.在基类中为子类提供一个权限申请方法
 * 4.在基类中集中处理请求权限回调的业务逻辑
 * 5.暴露给子类实现具体业务逻辑的方法,子类如果有此功能,覆写此方法即可,不用再管权限的配置,子类没有此功能,就不用管此方法
 * 6.暴露给子类实现打电话业务逻辑的方法,子类如果有此功能,覆写此方法即可,不用再管权限的配置,子类没有此功能,就不用管此方法
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    /**
     * 为子类提供一个权限检查方法
     * String... permission表示不定参数，也就是调用这个方法的时候这里可以传入多个String对象(JDK5新特性)
     * @return
     */
    //String... permission表示不定参数，也就是调用这个方法的时候这里可以传入多个String对象(JDK5新特性)
    public boolean hasPermission(String... permissions ){
        for(String permission: permissions ){
            if (ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED){
                return true;
            }
        }
        return false;
    }

    /**
     * 为子类提供一个权限申请方法
     */
    public void requestPermission(int code,String... permissions){
        ActivityCompat.requestPermissions(this,permissions,code);
    }

    /**
     * 集中处理请求权限回调的业务逻辑
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            //打电话权限的回调处理
            case Constants.CALL_PHONE:
                //判断打电话申请权限是否成功,成功就执行打电话的逻辑
                //注意:因为集合里只有一个权限申请,所以参数为0代表打电话权限
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doCallPhone();
                } else {
                    //用户拒绝了权限请求,给用户提示权限的功能
                    Toast.makeText(this, "权限没有授予", Toast.LENGTH_SHORT).show();
                }
                break;
            //SD卡权限的回调处理
            case Constants.WRITE_EXTERNAL_STORAGE:
                //判断打电话申请权限是否成功,成功就执行打电话的逻辑
                //注意:因为集合里只有一个权限申请,所以参数为0代表打电话权限
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doSdCardPermission();
                } else {
                    //用户拒绝了权限请求,给用户提示权限的功能
                    Toast.makeText(this, "权限没有授予", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 暴露给子类实现打电话业务逻辑的方法,子类如果有此功能,覆写此方法即可,不用再管权限的配置
     * 子类没有此功能,就不用管此方法
     */
    public void doCallPhone() {
    }

    /**
     * 暴露给子类实现往SD写入数据业务逻辑的方法,子类如果有此功能,覆写此方法即可,不用再管权限的配置
     * 子类没有此功能,就不用管此方法
     */
    public void doSdCardPermission() {}

}

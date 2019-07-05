package com.mt.common;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mt.common.common.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void setPermission() {
        //TODO 需要申请权限的，在这里调用permissionUtils里面的申请函数
    }

    @Override
    public void setNetWorkChangeReceiver() {
        //TODO 需要监听网络变化的时候，在这里是实例化NetworkChangeReceiver，记得需要在ondestory，注销的receiver

    }
}

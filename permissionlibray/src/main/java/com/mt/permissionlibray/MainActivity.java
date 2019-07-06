package com.mt.permissionlibray;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.mt.common.common.BaseActivity;
import com.mt.common.common.apiRequest.BaseOkHttpClient;
import com.mt.common.common.apiRequest.BaseRetrofitClient;
import com.mt.common.common.listener.PermissionListener;
import com.mt.common.utils.OkHttpUtils;
import com.mt.common.utils.PermissionUtils;

import okhttp3.OkHttpClient;


/*
 * Created by cmt on 2019/7/5
 */
public class MainActivity extends MBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this,TestApiActivity.class);
        startActivity(intent);
    }
}

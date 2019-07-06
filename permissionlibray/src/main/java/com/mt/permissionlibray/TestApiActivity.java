package com.mt.permissionlibray;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mt.common.common.apiRequest.response.BaseResponseIntercepter;
import com.mt.permissionlibray.common.LoginLoader;
import com.mt.permissionlibray.common.MResponseIntercepter;

/*
 * Created by cmt on 2019/7/5
 */
public class TestApiActivity extends MBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginLoader loginLoader = new LoginLoader();
        loginLoader.getBackGroundImage().subscribeWith(new MResponseIntercepter(){
            @Override
            public void successCallback(String result) {
                Log.e("TestApiActivity","successCallback"+result);
                Toast.makeText(TestApiActivity.this,result,Toast.LENGTH_LONG).show();
            }

            @Override
            public void errorCallBack(Throwable e) {
                Log.e("TestApiActivity","errorCallBack"+e.getMessage());
            }
        });
    }
}

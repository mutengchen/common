package com.mt.permissionlibray.common;

import android.util.Log;

import com.mt.common.common.apiRequest.response.BaseResponseIntercepter;

/*
 * Created by cmt on 2019/7/6
 */
public abstract class MResponseIntercepter extends BaseResponseIntercepter {
    @Override
    public void hideLoading() {
        Log.e("MResponseIntercepter","hideLoading");
    }

    @Override
    public void showLoading() {
        Log.e("MResponseIntercepter","showLoading");
    }

}

package com.mt.permissionlibray.common;

import com.mt.common.common.apiRequest.BaseRetrofitClient;
import com.mt.common.common.apiRequest.loader.ObjectLoader;

import okhttp3.ResponseBody;

/*
 * Created by cmt on 2019/7/5
 */
public class LoginLoader extends ObjectLoader {
    private LoginApi loginApi;

    public LoginLoader() {
        this.loginApi = BaseRetrofitClient.getRfClient(LoginApi.class,ApiAddress.HOST);
    }

    public io.reactivex.Observable<ResponseBody> getBackGroundImage() {
        return observe(loginApi.getbackgroundImage());
    }

}

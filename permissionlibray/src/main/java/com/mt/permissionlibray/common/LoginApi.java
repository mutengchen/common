package com.mt.permissionlibray.common;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.GET;

/*
 * Created by cmt on 2019/7/5
 */
public interface LoginApi {
    @GET("/interface/wynnUsers/mma_background_image")
    Observable<ResponseBody> getbackgroundImage();
}

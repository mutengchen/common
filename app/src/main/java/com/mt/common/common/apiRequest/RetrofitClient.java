package com.mt.common.common.apiRequest;

import com.mt.common.common.apiRequest.api.Api;
import com.mt.common.utils.OkHttpUtils;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

// Retrofit
public class RetrofitClient {


    private static final int DEFAULT_TIME_OUT = 5;
    public static<T> T getInstance(Class<T> serveice){
          Retrofit  client = new Retrofit.Builder()
                    .client(OkHttpUtils.getBuilder().build()) //okhttpClickent 参数拦截
                    .baseUrl(Api.API_DEFAULT)
                    .addConverterFactory(GsonConverterFactory.create())  //将json 转化成bean
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        return client.create(serveice);
    }

    /**
     * 用户多baseUrl且每个url对应需要https
     * @param type
     * @return
     */
    public static<T> T getInstance(int type, Class<T> service){
        Retrofit client = new Retrofit.Builder()
                .client(OkHttpUtils.getBuilder(type).build()) //okhttpClickent 参数拦截
                .baseUrl(Api.API_DEFAULT)
                .addConverterFactory(GsonConverterFactory.create())  //将json 转化成bean
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return client.create(service);
    }
    //创建接口对象
//    public <T> T create(Class<T> service){
//        return client.create(service);
//    }

//    //运行的时候动态改变baseUrl
//    public static Retrofit changeApiBaseUrl(String changeUrl){
//        client = new Retrofit.Builder()
//                .client(OkHttpUtils.getBuilder().build())
//                .baseUrl(changeUrl)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build();
//        return client;
//    }

}

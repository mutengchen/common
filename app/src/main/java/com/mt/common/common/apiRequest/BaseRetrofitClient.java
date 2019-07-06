package com.mt.common.common.apiRequest;

import com.mt.common.common.apiRequest.api.Api;
import com.mt.common.utils.OkHttpUtils;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

// Retrofit
public class BaseRetrofitClient {

    //不需要传入
    public static<T> T getRfClient(Class<T> serveice, String host){
          Retrofit  client = new Retrofit.Builder()
                    .client(OkHttpUtils.getBuilder().build()) //okhttpClickent 参数拦截
                    .baseUrl(host)
                    .addConverterFactory(GsonConverterFactory.create())  //将json 转化成bean
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        return client.create(serveice);
    }

    /**
     * 对应需要https双向证书的获取retrofit
     * @return
     */
    public static<T> T getRfClient(Class<T> service,String host,int sslcertRawResId){
        Retrofit client = new Retrofit.Builder()
                .client(OkHttpUtils.getBuilder(sslcertRawResId).build()) //okhttpClickent 参数拦截
                .baseUrl(host)
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

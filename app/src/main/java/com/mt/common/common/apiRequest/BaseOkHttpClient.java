package com.mt.common.common.apiRequest;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/*
 * Created by cmt on 2019/7/6
 */
public abstract class BaseOkHttpClient extends OkHttpClient {

    public static final class Builder{
        public OkHttpClient.Builder builder;
        public Builder(OkHttpClient.Builder builder) {
            this.builder = builder;
        }
        public void getA(){
            builder.addNetworkInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    return null;
                }
            });
        }

    }



}

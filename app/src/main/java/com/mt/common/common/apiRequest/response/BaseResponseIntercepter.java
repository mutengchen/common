package com.mt.common.common.apiRequest.response;

import android.util.Log;


import com.mt.common.common.BaseApplication;
import com.mt.common.common.bean.ErrorDto;
import com.mt.common.utils.GsonUtils;
import com.mt.common.utils.LogUtils;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLHandshakeException;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

//统一的response预处理
public abstract class BaseResponseIntercepter extends DisposableObserver<ResponseBody> {
    private static final String TAG ="BaseResponseIntercepter";

    @Override
    public void onNext(ResponseBody responseBody) {
        Log.i(TAG,"onNext");
        //成功接收到有效的数据,并返回给调用者
        try {
            final String result = responseBody.string();
            successCallback(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 对错误进行统一处理
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        Log.i(TAG, "onError" + e.getClass());
        errorCallBack(e);
//        if (e instanceof SocketTimeoutException) {
//            Log.i(TAG, "SocketTimeoutException");
//            responsePretreatListener.showResponseToast("Network connection timeout", 400);//网络连接超时
//        }//请求超时
//        else if (e instanceof ConnectException) {
//            Log.i(TAG, "网络连接超时");
//            responsePretreatListener.showResponseToast("Network connection timeout", 400);//网络连接超时
//
//        } else if (e instanceof SSLHandshakeException) {
//            Log.i(TAG, "SSL证书异常");
//            responsePretreatListener.showResponseToast("Ssl certificate is abnormal", 400);
//        }
//        else if(e instanceof HttpException){
//            //获取错误码
//            int code = ((HttpException) e).code();
//            String msg = null;
//            try {
//                //获取错误的response信息
//                msg = ((HttpException)e).response().errorBody().string();
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//
//            Log.i(TAG,"code="+code);
//            if(code == 504||code==502){
//                responsePretreatListener.showResponseToast(msg,code);
//            }else if(code==500){
//                ErrorDto errorDto = GsonUtils.jsonToObject(msg, ErrorDto.class);
//                if(errorDto!=null){
//                    responsePretreatListener.showResponseToast(errorDto.getMessage(),code);
//                }
//                else{
//                    responsePretreatListener.showResponseToast(msg,code);
//                }
//            }
//            else if(code == 404){
//                responsePretreatListener.showResponseToast(msg,code);
//            }else if(code == 601 || code == 600) {
//                //包括了token过期，sso未登录，
//                responsePretreatListener.showResponseToast(msg,code);
//            }else if(code ==827){
////                responsePretreatListener.showResponseToast(BaseApplication.getContext().getString(R.string.user_is_disabled),code);
//            }
//            else{
//                responsePretreatListener.showResponseToast(msg,code);
//            }
//            String temp = "error : code ="+code+"; msg = "+msg;
//
//        }
//        else if(e instanceof UnknownHostException){
//            Log.i(TAG,"域名解析失败");
////            responsePretreatListener.showResponseToast(BaseApplication.getContext().getString(R.string.network_break),400);
//            LogUtils.setLog(e.toString());
//
//        }else{
//            //其他未知类型的错误
//            Log.i(TAG,"other");
//            responsePretreatListener.showResponseToast(e.getMessage(),400);
//
//        }
    }

    @Override
    public void onComplete() {
        //TODO 请求成功之后，需要隐藏加载框，在这里，自己实现
        hideLoading();
    }
    @Override
    protected void onStart() {
        super.onStart();
        //TODO 需要显示加载框的，看这里，自己实现
        showLoading();
    }
    public abstract void successCallback(String result);
    public abstract  void errorCallBack(Throwable e);
    public abstract  void showLoading();
    public abstract  void hideLoading();
}

package com.mt.common.utils;


import android.content.Context;
import android.content.res.Resources;
import android.net.http.SslCertificate;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;


import com.mt.common.R;
import com.mt.common.common.BaseApplication;
import com.mt.common.common.apiRequest.response.MoreBaseUrlInterceptor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;


/**
 * okHttpclient 模式的单例类
 * Created by cmt on 2017/9/28.
 */

public class OkHttpUtils {
    public final static int CONNECT_TIMEOUT = 15;
    public final static int READ_TIMEOUT = 15;
    public final static int WRITE_TIMEOUT = 15;
    public static int HTTP_TIMEOUT =  5;
    //证书密码
    static final String CLIENT_KET_PASSWORD = "P@ssw0rd20190204";
    //GCM证书密码
    static final String CLIENT_GCM_PASSWORD  = "P@ssw0rd20190131";

    private static OkHttpClient client = null;
    private static HttpLoggingInterceptor httpLoggingInterceptor;

    private OkHttpUtils() {

    }

    public static OkHttpClient getInstance() {
        if (client == null) {
            synchronized (OkHttpUtils.class) {
                if (client == null)
                    client = new OkHttpClient().newBuilder().build();
            }
        }
        return client;
    }

    public static HttpLoggingInterceptor getHttpLoggingInterceptor(){
        if(httpLoggingInterceptor==null){
            httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.i("OkHttp3","url = "+message);
                    LogUtils.setLog(message);
                }
            });
        }
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    /**
     * 多baseurl,多https证书
     * @return
     */
    public static OkHttpClient.Builder getBuilder(int sslcertRawResId){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);//设置连接超时间
        builder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS); //写操作超时时间
        builder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS); //读操作超时时间
        builder.retryOnConnectionFailure(true); //错误重连
        //0表示不需要https证书，其他的话，就是该证书对应的raw文件夹里面的resID
        if(sslcertRawResId==0){
            TrustAllManager trustAllManager = new TrustAllManager();
            SSLSocketFactory sslSocketFactory=OkHttpUtils.createTrustAllSSLFactory(trustAllManager);
            builder.sslSocketFactory(sslSocketFactory,trustAllManager); //QA测试的时候用这个，全信任模式
        }else{
            builder.sslSocketFactory(createSslContext(BaseApplication.getContext(),sslcertRawResId).getSocketFactory());
        }

        builder.hostnameVerifier((hostname, session) -> true);
//        builder.addInterceptor(new MoreBaseUrlInterceptor()); //多baseUrl拦截
        builder.addInterceptor(getHttpLoggingInterceptor()); //请求日志拦截
        builder.protocols(Collections.singletonList(Protocol.HTTP_1_1)); //添加http协议
        return builder;
    }


    public static OkHttpClient.Builder getBuilder(){
        //全信任证书模式，可能无法通过双向的证书验证
        TrustAllManager trustAllManager = new TrustAllManager();
        SSLSocketFactory sslSocketFactory=OkHttpUtils.createTrustAllSSLFactory(trustAllManager);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);//设置连接超时间
        builder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS); //写操作超时时间
        builder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS); //读操作超时时间
        builder.retryOnConnectionFailure(true); //错误重连
        builder.sslSocketFactory(sslSocketFactory,trustAllManager); //QA测试的时候用这个，全信任模式
        builder.hostnameVerifier((hostname, session) -> true);
//        builder.addInterceptor(new MoreBaseUrlInterceptor()); //多baseUrl拦截
        builder.addInterceptor(getHttpLoggingInterceptor()); //请求日志拦截
        builder.protocols(Collections.singletonList(Protocol.HTTP_1_1));
        return builder;
    }

    //配置连接超时时间，缓存，拦截器

    public static SSLSocketFactory createTrustAllSSLFactory(TrustAllManager trustAllManager) {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{trustAllManager}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }

        return ssfFactory;
    }
    /**
     * 根据wifi地址的SSID动态判断api host
     */



    public static SSLContext createSslContext(Context context, int sslcertRawResId){
        SSLContext sslContext = null;
        sslContext = getPfx(context.getResources().openRawResource(sslcertRawResId),1);
        return sslContext;
    }

//    public static void changeApiHostFromWifiIP(Context context){
//        String contect_ip = Tools.getConnectWifiIP(context);
//        Log.i("now_connect_wifi_ip",contect_ip);
//        setApiHost(3);
//        List<IpRange> wp_list = SharePreferencesUtils.getInstance().getListData("wp_ip_list",IpRange.class);
//        List<IpRange> wm_list = SharePreferencesUtils.getInstance().getListData("wm_ip_list",IpRange.class);
//        for(IpRange a: wp_list){
//            if(ipAddressIsInRange(a.getIp_start(),a.getIp_end(),Tools.getConnectWifiIP(context))){
//                setApiHost(1);
//            }
//        }
//        for(IpRange b:wm_list){
//            if(ipAddressIsInRange(b.getIp_start(),b.getIp_end(),Tools.getConnectWifiIP(context))){
//                setApiHost(2);
//            }
//        }
//    }
    //判斷wifi ip地址是否在範圍之內
    public static boolean ipAddressIsInRange(String start_ip, String end_ip, String targetIP){
        long start = IP2Long(start_ip);
        long end = IP2Long(end_ip);
        long target = IP2Long(targetIP);
        Log.i("ipAddressIsInRange","start="+start_ip+",target="+targetIP+"end="+end_ip);
        if(target>=start&&target<=end) {
            return true;
        }
        return false;
    }

    //将ip地址转换成整数
    public static long IP2Long(String ipaddress)
    {
        long result = 0;

        String[] ipAddressInArray = ipaddress.split("\\.");

        for (int i = 3; i >= 0; i--) {

            long ip = Long.parseLong(ipAddressInArray[3 - i]);
            result |= ip << (i * 8);
        }
        return (long)result;
    }

//    public static void setApiHost(int type){
//        if(type==1){
//            Tools.MMA_URL = "https://10.101.194.30";
////            Tools.MMA_URL = "https://Wpmpmobregv01.wpm.wynnresorts.com";
//            Tools.GCM_URL = Tools.WP_GCM;
//            Tools.numberGCM = 2;
//            Api.mra_api_select = 2;
//            Api.mra_gcm_api_select = 4;
//        }else if(type==2){
//            Tools.MMA_URL = "https://10.101.194.30";
////            Tools.MMA_URL = "https://macvmrproapp01.mac.wynnresorts.com";
//            Tools.GCM_URL = Tools.WM_GCM;
//            Tools.numberGCM = 1;
//            Api.mra_api_select = 1;
//            Api.mra_gcm_api_select = 3;
//        }else if(type==3){
//            Tools.MMA_URL = "https://10.101.194.30";
//        }else{
//            Tools.MMA_URL = "https://macvmrproapp01.mac.wynnresorts.com";
//            Tools.GCM_URL = Tools.WM_GCM;
//            Tools.numberGCM = 1;
//            Api.mra_api_select = 1;
//            Api.mra_gcm_api_select = 3;
//        }
//        Log.i("wifi_mma_url",Tools.MMA_URL);
//        Log.i("wifi_gcm_url",Tools.GCM_URL);
//    }
    //获取当前wm和wp的配置地址，和当前连接wifi的ip，判断是否在其范围内
//    public static Interceptor wifiIPAddressInterpector(Context context){
//        Interceptor interceptor = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                String contect_ip = Tools.getConnectWifiIP(context);
//                Log.i("now_connect_wifi_ip",contect_ip);
//                boolean signal = false;
//                List<IpRange> wp_list = SharePreferencesUtils.getInstance().getListData("wp_ip_list",IpRange.class);
//                List<IpRange> wm_list = SharePreferencesUtils.getInstance().getListData("wm_ip_list",IpRange.class);
//                for(IpRange a: wp_list){
//                    if(ipAddressIsInRange(a.getIp_start(),a.getIp_end(),Tools.getConnectWifiIP(context))){
//                        Log.i("wp_wp",a.getIp_start()+"--"+a.getIp_end());
//                        signal = true;
//                    }
//                }
//                for(IpRange b:wm_list){
//                    if(ipAddressIsInRange(b.getIp_start(),b.getIp_end(),Tools.getConnectWifiIP(context))){
//                        Log.i("wm_wm",b.getIp_start()+"--"+b.getIp_end());
//                        signal = true;
//                    }
//                }
//                if(!signal){
//                    Looper.prepare();
//                    Toast.makeText(context,"IP address is out of range", Toast.LENGTH_LONG).show();
//                    Looper.loop();
//                    return null;
//                }
//                return chain.proceed(chain.request());
//            }
//        };
//        return interceptor;
//
//
//    }

    public static String getIPAddress(int ipInt){
        return (ipInt & 0xFF ) + "." +
                ((ipInt >> 8 ) & 0xFF) + "." +
                ((ipInt >> 16 ) & 0xFF) + "." +
                ( ipInt >> 24 & 0xFF) ;
    }
    public static SSLContext getPfx(InputStream con, int type){
        SSLContext context = null;
        try {
            //将ca证书导入输入流
            InputStream inputStream = con;

            //密码根据不同证书更换
            String pwd = "";
            if(type == 1||type==2) pwd = CLIENT_KET_PASSWORD;
            if(type == 3||type==4) pwd = CLIENT_GCM_PASSWORD;
            //keystore添加证书内容和密码
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(inputStream, pwd.toCharArray());
//            keyStore.load(inputStream, CLIENT_TEST_PASSWORD.toCharArray());

////            //key管理器工厂
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, pwd.toCharArray());

            //信任管理器工厂
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

            //构建一个ssl上下文，加入ca证书格式，与后台保持一致
            context = SSLContext.getInstance("TLS");

            //参数，添加受信任证书和生成随机数
            context.init(keyManagerFactory.getKeyManagers(),trustManagers, new SecureRandom());
            inputStream.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return context;
    }

    //在webview中使用证书验证
public static Certificate getCertificateForRawResource(int resourceId, Context context) {
    CertificateFactory cf = null;
    Certificate ca = null;
    Resources resources = context.getResources();
    InputStream caInput = resources.openRawResource(resourceId);

    try {
        cf = CertificateFactory.getInstance("X.509");
        ca = cf.generateCertificate(caInput);
    } catch (CertificateException e) {
    } finally {
        try {
            caInput.close();
        } catch (IOException e) {
        }
    }

    return ca;
}

    public static Certificate convertSSLCertificateToCertificate(SslCertificate sslCertificate) {
        CertificateFactory cf = null;
        Certificate certificate = null;
        Bundle bundle = sslCertificate.saveState(sslCertificate);
        byte[] bytes = bundle.getByteArray("x509-certificate");

        if (bytes != null) {
            try {
                CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
                Certificate cert = certFactory.generateCertificate(new ByteArrayInputStream(bytes));
                certificate = cert;
            } catch (CertificateException e) {
            }
        }

        return certificate;
    }
}

class TrustAllManager implements X509TrustManager {
    @Override
    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

    }

    @Override
    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

    }

    @Override
    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
        return new java.security.cert.X509Certificate[0];
    }
}
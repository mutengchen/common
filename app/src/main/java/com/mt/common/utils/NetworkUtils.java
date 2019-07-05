package com.mt.common.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static android.content.Context.CONNECTIVITY_SERVICE;

/** 网络连接工具类
 * Created by cmt on 2018/6/20.
 */

public class NetworkUtils {

    /**
     * 判断当前wifi 连接状态
     * @param context
     * @return
     */
    public static boolean isWifiState(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi =  connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        //判断wifi是否连接
        if(wifi.isConnected()){
            //判断当前wifi是否可以用
            return true;
        }
        else{
            return false;
        }
    }

    private static final int NETWORK_NONE = 0;

    private static final int NETWORK_OK = 1;

    /**
     * 判断网络是否可以连接
     * @param context
     * @return
     */
    public static int getNetWorkStates(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = manager.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isAvailable()) {
            return NETWORK_OK;
        } else {
            return NETWORK_NONE;
        }

    }


}

package com.mt.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.mt.permissionlibray.common.PermissionListener;

import java.util.ArrayList;
import java.util.List;

/* 权限工具类
 * Created by cmt on 2019/7/3
 */
public class PermissionUtils {

    /**
     * 申请权限列表和权限授予监听
     * @param rqList
     * @param permissionListener
     */
    public static void requestRunTimePermission(Context context,String [] rqList, PermissionListener permissionListener){
        List<String> permissionList = new ArrayList<>();
        //遍历所有需要请求的权限,剔除已经授予的权限
        for(String permission : rqList){
            if(ContextCompat.checkSelfPermission(context,permission)!= PackageManager.PERMISSION_GRANTED){
                permissionList.add(permission);
            }
        }
        //如果有需要授权的权限，开始弹出权限授予框
        if(!permissionList.isEmpty()){
            ActivityCompat.requestPermissions((Activity) context,permissionList.toArray(new String[permissionList.size()]),1);
        }else{
            permissionListener.onAllGranted();
        }

    }


}

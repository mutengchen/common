package com.mt.common.common.listener;

/* 权限回调监听
 * Created by cmt on 2019/7/3
 */
public interface PermissionListener {
    void onAllGranted(); //全部权限都授予了
    void onGranted(String[] allowList); //授予权限的回调
    void onDeny(); //拒绝权限
}

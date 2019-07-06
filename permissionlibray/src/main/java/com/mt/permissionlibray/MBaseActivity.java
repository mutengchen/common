package com.mt.permissionlibray;

import android.Manifest;
import android.util.Log;

import com.mt.common.common.BaseActivity;
import com.mt.common.common.listener.PermissionListener;
import com.mt.common.utils.PermissionUtils;

/*
 * Created by cmt on 2019/7/5
 */
public class MBaseActivity extends BaseActivity {

    @Override
    public void setNetWorkChangeReceiver() {

    }

    @Override
    public void setPermission() {
        String[] permission = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE
        };
        PermissionUtils.requestRunTimePermission(this, permission, new PermissionListener() {
            @Override
            public void onAllGranted() {

            }

            @Override
            public void onGranted(String[] allowList) {

            }

            @Override
            public void onDeny() {

            }
        });
    }
}

package com.mt.common.common;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

/* 抽象baseActivity，实现了基本会用到的功能
 * Created by cmt on 2019/7/3
 */
public abstract class BaseActivity extends Activity {

    /**
     * 凡是继承该类的activity，app字体大小都不受系统字体大小的影响
     * @return
     */
    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        if (resources != null && resources.getConfiguration().fontScale != 1) {
            Configuration configuration = resources.getConfiguration();
            configuration.fontScale = 1.01f;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
        return resources;
    }

    /**
     * 监听app配置的切换包括多语言，字体大小
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        getResources();
        super.onConfigurationChanged(newConfig);
    }

    public abstract void setPermission(); //设置权限
    public abstract void setNetWorkChangeReceiver();//设置全局的网络监听
}

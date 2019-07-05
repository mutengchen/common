package com.mt.common.common;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

/*
 * Created by cmt on 2019/7/3
 */
public abstract class BaseApplication extends Application {
    private static final String TAG ="MyApplication";
    //构建全局单例的context
    static Context context;

    //获取全局单例的context
    public static Context getContext(){
        return context;
    }

    //8.0以下的系统可以在application设置，而改变全局的多语言和字体大小，但是8.0以上系统，需要在activity里面才能生效，所以建议8.0以上的继承baseAcitivity
    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        System.out.println("getFontsize="+resources.getConfiguration().fontScale);

        if (resources != null && resources.getConfiguration().fontScale != 1) {
            Log.i(TAG,"fontSizefontSize="+resources.getConfiguration().fontScale);
            android.content.res.Configuration configuration = resources.getConfiguration();
            configuration.fontScale = 1.01f;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
        return resources;
    }
}

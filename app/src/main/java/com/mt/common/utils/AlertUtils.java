package com.mt.common.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.mt.common.common.BaseApplication;

import javax.annotation.Nullable;

/* 提示控件
 * Created by cmt on 2019/7/5
 */
public class AlertUtils {
    private static AlertDialog alertDialog;
    //统一的toast短提示（0.5s）
    public static void ToastAlert(String alert){
        Toast.makeText(BaseApplication.getContext(),alert,Toast.LENGTH_SHORT).show();
    }
    //统一的toast长提示（1.0s)
    public static void toastLongAlert(String alert){
        Toast.makeText(BaseApplication.getContext(),alert,Toast.LENGTH_LONG).show();
    }

    //显示加载框
    public static void loadingShow(Context context,int layoutId,boolean outsdieCancel){
        alertDialog = new AlertDialog.Builder(context).create();
        //设置自定义的加载框 xml
        View view = LayoutInflater.from(context).inflate(layoutId,null);
        alertDialog.setView(view);
        //设置点击弹窗之外的区域是否隐藏该弹框
        alertDialog.setCanceledOnTouchOutside(outsdieCancel);
        //显示
        alertDialog.show();
    }

    //隐藏加载框
    public static void loadingDissmiss(Context context){
        if(alertDialog!=null)
        alertDialog.dismiss();
    }
}

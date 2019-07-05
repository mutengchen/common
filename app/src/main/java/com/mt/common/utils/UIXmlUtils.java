package com.mt.common.utils;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

import androidx.core.content.ContextCompat;

/* 修改 xml 布局属性
 * Created by cmt on 2019/7/5
 */
public class UIXmlUtils {

    /**
     * 设置xml shape 背景颜色
     * @param context
     * @param view
     * @param colorID
     */
    public static void setShapeColor(Context context, View view,int colorID){
        GradientDrawable gradientDrawable = (GradientDrawable) view.getBackground();
        gradientDrawable.setColor(ContextCompat.getColor(context,colorID));
    }

    /**
     * 设置xml 四个圆角的半径
     * @param view
     * @param corners float[左上，右上，右下，左下] 角的半径
     */
    public static void setShapeCorners(View view,float[] corners){
        GradientDrawable gradientDrawable = (GradientDrawable) view.getBackground();
        gradientDrawable.setCornerRadii(corners);
    }

    /**
     * 设置外部描边的厚度和颜色
     * @param view
     * @param width
     * @param colorID
     */
    public static void setShapeStroke(Context context,View view,int width,int colorID){
        GradientDrawable gradientDrawable = (GradientDrawable) view.getBackground();
        gradientDrawable.setStroke(width,ContextCompat.getColor(context,colorID));
    }


}

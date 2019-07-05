package com.mt.common.utils;

import android.util.Log;

import java.util.List;

/* 检查数据格式是否正确，处理数据格式
 * Created by cmt on 2019/7/5
 */
public class CheckDataUtils {

    /**
     * 判断对象是否为"" 或者"null"或者null 或者list为空
     * @param temp
     * @param <T>
     * @return
     */
    public static <T> boolean checkNull(T temp){
        if(temp==null||temp.equals("")||temp.equals("null"))
            return true;
        if(temp instanceof List){
            if(((List)temp).size()==0){
                Log.i("listNull","listNull");
                return true;
            }
        }
        return false;
    }
}

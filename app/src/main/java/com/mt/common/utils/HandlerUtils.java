package com.mt.common.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * handler utils
 */
public class HandlerUtils {

    /**
     * Parcelable数据通过handler来传递对象信息
     * @param handler
     * @param signal
     * @param cls
     * @param <T> 继承Parcelable的任何对象
     */
    public static<T extends Parcelable> void sendParcelableWithHandler(Handler handler, int signal, T cls){
        Message message = Message.obtain();
        message.what = signal;
        Bundle bundle = new Bundle();
        bundle.putParcelable("dto",cls);
        message.setData(bundle);
        handler.sendMessage(message);
    }

    public static<T extends Parcelable> void sendParcelableArrayWithHandler(Handler handler, int signal, List<T> cls){
        Message message = Message.obtain();
        message.what = signal;
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("dto", (ArrayList<? extends Parcelable>) cls);
        message.setData(bundle);
        handler.sendMessage(message);
    }
}

package com.mt.common.utils;

import android.view.View;

/* 处理重复点击
 * Created by cmt on 2019/6/11
 */
public abstract class FastClickUtils implements View.OnClickListener {
    private long mLastClickTime;
    private long timeInterval = 1000L;

    public FastClickUtils() { }

    //设置两次点击的间隔秒数判断
    public FastClickUtils(long timeInterval){
        this.timeInterval = timeInterval;
    }

    @Override
    public void onClick(View v) {
        long nowTime = System.currentTimeMillis();
        if (nowTime - mLastClickTime > timeInterval) {
            // 单次点击事件
            onSingleClick();
            mLastClickTime = nowTime;
        } else {
            // 快速点击事件
            onFastClick();
        }
    }
    protected abstract void onSingleClick();
    protected abstract void onFastClick();
}

package com.mt.common.common.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


//适用于整个列表的都是同一种item类型的
public  abstract class CommonAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {

    //用来加载item布局
    private LayoutInflater layoutInflater;

    //数据源
    private List<T> data;

    //item的 layout_id
    private int layoutId;


    //手动更新数据
    private void updateData(){
        notifyDataSetChanged();
    }

    public CommonAdapter(Context context, List<T> datalist, int layoutId){
        this.layoutId = layoutId;
        this.data =  datalist;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(layoutId,parent,false);
        return new CommonViewHolder(view);
    }


    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        Log.i("onBindViewHolder",position+"done");
        bindData(holder,data.get(position));
    }

    //绑定数据方法不在adapter里面实现,由子类来进行绑定操作
    public abstract void bindData(CommonViewHolder holder, T data);
    @Override
    public int getItemCount() {
        Log.i("getItemCount",""+data.size());
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}

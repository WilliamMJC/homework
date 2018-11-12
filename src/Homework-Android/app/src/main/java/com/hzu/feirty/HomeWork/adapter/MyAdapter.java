package com.hzu.feirty.HomeWork.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hzu.feirty.HomeWork.R;
import com.hzu.feirty.HomeWork.db.Email;
import com.hzu.feirty.HomeWork.entity.OnRecyclerviewItemClickListener;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/12.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements View.OnClickListener {
    private ArrayList<Email> mData;
    private LayoutInflater inflater;
    private Context context;
    //声明自定义的监听接口
    private OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener = null;
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.works_item,viewGroup,false);
        MyViewHolder vh = new MyViewHolder(v);
        v.setOnClickListener(this);
        return vh;
    }
    //构造方法中添加自定义监听接口
    public MyAdapter(Context context, ArrayList<Email> data, OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
        this.context = context;
        this.mData = data;
        inflater = LayoutInflater.from(context);
        this.mOnRecyclerviewItemClickListener = mOnRecyclerviewItemClickListener;
    }

    public MyAdapter(ArrayList<Email> data){
        mData = data;
    }
    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_subject.setText(mData.get(i).getSubject());
        myViewHolder.tv_sentdate.setText(mData.get(i).getSentdata());
        myViewHolder.itemView.setTag(i);
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }
    @Override
    public void onClick(View v) {
        //将监听传递给自定义接口
        mOnRecyclerviewItemClickListener.onItemClickListener(v, ((int) v.getTag()));
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_subject;
        public TextView tv_sentdate;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_subject = (TextView) itemView.findViewById(R.id.tv_subject);
            tv_sentdate = (TextView) itemView.findViewById(R.id.tv_sentdate);
        }
    }
}

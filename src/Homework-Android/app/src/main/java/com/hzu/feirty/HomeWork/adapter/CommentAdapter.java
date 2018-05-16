package com.hzu.feirty.HomeWork.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hzu.feirty.HomeWork.R;
import com.hzu.feirty.HomeWork.db.Comment;
import com.hzu.feirty.HomeWork.db.MsgModel;
import com.hzu.feirty.HomeWork.entity.OnRecyclerviewItemClickListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/1 0001.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> implements View.OnClickListener {
    private ArrayList<Comment> mData = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    //声明自定义的监听接口
    private OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener = null;
    @Override
    public CommentAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.commit_item,viewGroup,false);
        CommentAdapter.MyViewHolder vh = new CommentAdapter.MyViewHolder(v);
        v.setOnClickListener(this);
        return vh;
    }
    //构造方法中添加自定义监听接口
    public CommentAdapter(Context context, ArrayList<Comment> data, OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
        this.context = context;
        this.mData = data;
        inflater = LayoutInflater.from(context);
        this.mOnRecyclerviewItemClickListener = mOnRecyclerviewItemClickListener;
    }
    public void addAll(ArrayList<Comment> list) {
        if (mData != null && list != null) {
            mData.addAll(list);
            notifyItemRangeChanged(mData.size(),list.size());
        }
    }

    public CommentAdapter(ArrayList<Comment> data){
        mData = data;
    }
    @Override
    public void onBindViewHolder(CommentAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.userName.setText(mData.get(i).getUserName());
        myViewHolder.lastMagTime.setText(mData.get(i).getCreateTime());
        myViewHolder.lastMagContext.setText(mData.get(i).getContext());
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
        public TextView userName;
        public TextView lastMagTime;
        public TextView lastMagContext;

        public MyViewHolder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.userName);
            lastMagTime = (TextView) itemView.findViewById(R.id.lastMagTime);
            lastMagContext = (TextView) itemView.findViewById(R.id.lastMagContext);
        }
    }
}

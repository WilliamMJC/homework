package com.hzu.feirty.HomeWork.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hzu.feirty.HomeWork.R;
import com.hzu.feirty.HomeWork.db.ChatUser;
import com.hzu.feirty.HomeWork.entity.OnRecyclerviewItemClickListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/25 0025.
 */

public class ChatUserAdaper extends RecyclerView.Adapter<ChatUserAdaper.MyViewHolder> implements View.OnClickListener {
    private ArrayList<ChatUser> mData;
    private LayoutInflater inflater;
    private Context context;
    //声明自定义的监听接口
    private OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener = null;
    @Override
    public ChatUserAdaper.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_item,viewGroup,false);
        ChatUserAdaper.MyViewHolder vh = new ChatUserAdaper.MyViewHolder(v);
        v.setOnClickListener(this);
        return vh;
    }
    //构造方法中添加自定义监听接口
    public ChatUserAdaper(Context context, ArrayList<ChatUser> data, OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
        this.context = context;
        this.mData = data;
        inflater = LayoutInflater.from(context);
        this.mOnRecyclerviewItemClickListener = mOnRecyclerviewItemClickListener;
    }
    public ChatUserAdaper(ArrayList<ChatUser> data){
        mData = data;
    }
    @Override
    public void onBindViewHolder(ChatUserAdaper.MyViewHolder myViewHolder, int i) {
        myViewHolder.userName.setText(mData.get(i).getUserName());
        myViewHolder.lastMagTime.setText(mData.get(i).getLastMsgTime());
        myViewHolder.lastMagContext.setText(mData.get(i).getLastMsgContext());
        if(mData.get(i).getUnReadNum().equals("0")){
            myViewHolder.tvCount.setVisibility(View.GONE);
        }else{
            myViewHolder.tvCount.setText(mData.get(i).getUnReadNum());
        }
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
        public TextView tvCount;

        public MyViewHolder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.userName);
            lastMagTime = (TextView) itemView.findViewById(R.id.lastMagTime);
            lastMagContext = (TextView) itemView.findViewById(R.id.lastMagContext);
            tvCount = (TextView) itemView.findViewById(R.id.tvCount);
        }
    }
}

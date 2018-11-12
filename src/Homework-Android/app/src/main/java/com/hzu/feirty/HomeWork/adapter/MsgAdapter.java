package com.hzu.feirty.HomeWork.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzu.feirty.HomeWork.R;
import com.hzu.feirty.HomeWork.db.HomeWork;
import com.hzu.feirty.HomeWork.db.Message;
import com.hzu.feirty.HomeWork.db.MsgModel;
import com.hzu.feirty.HomeWork.entity.OnRecyclerviewItemClickListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/25 0025.
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.BaseAdapter>{
    private ArrayList<MsgModel> dataList = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context; //声明自定义的监听接口
    private OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener = null;

    /*@Override
    public void onClick(View v) {
        //将监听传递给自定义接口
        mOnRecyclerviewItemClickListener.onItemClickListener(v, ((int) v.getTag()));
    }*/

    public void replaceAll(ArrayList<MsgModel> list) {
        dataList.clear();
        if (list != null && list.size() > 0) {
            dataList.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<MsgModel> list) {
        if (dataList != null && list != null) {
            dataList.addAll(list);
            notifyItemRangeChanged(dataList.size(),list.size());
        }

    }
   /* //构造方法中添加自定义监听接口
    public MsgAdapter(Context context, ArrayList<MsgModel> data, OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
        this.context = context;
        this.dataList = data;
        inflater = LayoutInflater.from(context);
        this.mOnRecyclerviewItemClickListener = mOnRecyclerviewItemClickListener;
    }*/

    @Override
    public MsgAdapter.BaseAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case MsgModel.MSG_FORM:
                return new ChatAViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_form_item, parent, false));
            case MsgModel.MSG_TO:
                return new ChatBViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_to_item, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(MsgAdapter.BaseAdapter holder, int position) {
        holder.setData(dataList.get(position).object);
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).type;
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public class BaseAdapter extends RecyclerView.ViewHolder {

        public BaseAdapter(View itemView) {
            super(itemView);
        }

        void setData(Object object) {

        }
    }

    private class ChatAViewHolder extends BaseAdapter {
        private TextView context;
        private TextView sendTime;

        public ChatAViewHolder(View view) {
            super(view);
            context = (TextView) itemView.findViewById(R.id.context);
            sendTime = (TextView) itemView.findViewById(R.id.sendTime);
        }

        @Override
        void setData(Object object) {
            super.setData(object);
            Message model = (Message) object;
           // Picasso.with(itemView.getContext()).load(model.getIcon()).placeholder(R.mipmap.ic_launcher).into(ic_user);
            context.setText(model.getContext());
            sendTime.setText(model.getCreateTime());
        }
    }

    private class ChatBViewHolder extends BaseAdapter {
        private TextView context;
        private TextView sendTime;

        public ChatBViewHolder(View view) {
            super(view);
            context = (TextView) itemView.findViewById(R.id.context);
            sendTime = (TextView) itemView.findViewById(R.id.sendTime);

        }

        @Override
        void setData(Object object) {
            super.setData(object);
            Message model = (Message) object;
            //Picasso.with(itemView.getContext()).load(model.getIcon()).placeholder(R.mipmap.ic_launcher).into(ic_user);
            context.setText(model.getContext());
            sendTime.setText(model.getCreateTime());
        }
    }

}

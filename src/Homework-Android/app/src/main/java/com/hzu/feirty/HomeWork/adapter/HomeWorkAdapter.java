package com.hzu.feirty.HomeWork.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hzu.feirty.HomeWork.R;
import com.hzu.feirty.HomeWork.db.HomeWork;
import com.hzu.feirty.HomeWork.entity.OnRecyclerviewItemClickListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-10-23.
 */

public class HomeWorkAdapter extends RecyclerView.Adapter<HomeWorkAdapter.MyViewHolder> implements View.OnClickListener {
    private ArrayList<HomeWork> mData;
    private LayoutInflater inflater;
    private Context context;
    //声明自定义的监听接口
    private OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener = null;
    @Override
    public HomeWorkAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.homework_item,viewGroup,false);
            HomeWorkAdapter.MyViewHolder vh = new HomeWorkAdapter.MyViewHolder(v);
            v.setOnClickListener(this);
            return vh;
            }
    //构造方法中添加自定义监听接口
    public HomeWorkAdapter(Context context, ArrayList<HomeWork> data, OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
            this.context = context;
            this.mData = data;
            inflater = LayoutInflater.from(context);
            this.mOnRecyclerviewItemClickListener = mOnRecyclerviewItemClickListener;
            }
    public HomeWorkAdapter(ArrayList<HomeWork> data){
            mData = data;
            }
    @Override
    public void onBindViewHolder(HomeWorkAdapter.MyViewHolder myViewHolder, int i) {
            myViewHolder.tv_stu_id.setText(mData.get(i).getStu_id());
            myViewHolder.tv_file_name.setText(mData.get(i).getFile_name());
            myViewHolder.tv_file_size.setText(mData.get(i).getFile_size());
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
        public TextView tv_stu_id;
        public TextView tv_file_name;
        public TextView tv_file_size;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_stu_id = (TextView) itemView.findViewById(R.id.tv_stu_id);
            tv_file_name = (TextView) itemView.findViewById(R.id.tv_filename);
            tv_file_size = (TextView) itemView.findViewById(R.id.tv_fileSize);
        }
    }
}

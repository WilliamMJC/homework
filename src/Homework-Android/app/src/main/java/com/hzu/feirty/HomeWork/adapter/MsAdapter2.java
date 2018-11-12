package com.hzu.feirty.HomeWork.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzu.feirty.HomeWork.R;
import com.hzu.feirty.HomeWork.db.Email;
import com.hzu.feirty.HomeWork.entity.OnRecyclerviewItemClickListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-10-6.
 */

public class MsAdapter2 extends RecyclerView.Adapter<MsAdapter2.MyViewHolder> implements View.OnClickListener  {
    private ArrayList<Email> mData;
    private LayoutInflater inflater;
    private Context context;
    //声明自定义的监听接口
    private OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener = null;
    @Override
    public MsAdapter2.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.work_item,viewGroup,false);
        MsAdapter2.MyViewHolder vh = new MsAdapter2.MyViewHolder(v);
        v.setOnClickListener(this);
        return vh;
    }
    //构造方法中添加自定义监听接口
    public MsAdapter2(Context context, ArrayList<Email> data, OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
        this.context = context;
        this.mData = data;
        inflater = LayoutInflater.from(context);
        this.mOnRecyclerviewItemClickListener = mOnRecyclerviewItemClickListener;
    }
    public MsAdapter2(ArrayList<Email> data){
        mData = data;
    }
    @Override
    public void onBindViewHolder(MsAdapter2.MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_subject.setText(mData.get(i).getSubject());
        myViewHolder.tv_course.setText(mData.get(i).getCourse());
        myViewHolder.tv_work_number.setText(mData.get(i).getWork_number());
        myViewHolder.tv_shu02.setText(mData.get(i).getCc());
        //myViewHolder.tv_submit_state.setText(mData.get(i).getSubmit_state());
        myViewHolder.tv_work_state.setText(mData.get(i).getWork_state());
        myViewHolder.tv_sentdate.setText(mData.get(i).getSentdata());
        myViewHolder.tv_update_time.setText(mData.get(i).getUpdate_time());
        if(mData.get(i).getWork_state().equals("已收取")){
            myViewHolder.img.setImageResource(R.drawable.right2);
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
        public ImageView img;
        public TextView tv_subject;
        public TextView tv_sentdate;
        public TextView tv_course;
        public TextView tv_shu02;
        public TextView tv_work_number;
        //public TextView tv_submit_state;
        public TextView tv_work_state;
        public TextView tv_update_time;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_subject = (TextView) itemView.findViewById(R.id.tv_subject);
            tv_sentdate = (TextView) itemView.findViewById(R.id.tv_start_time);
            tv_course = (TextView) itemView.findViewById(R.id.tv_course);
            tv_shu02 = (TextView) itemView.findViewById(R.id.tv_all_number);
            tv_work_number = (TextView) itemView.findViewById(R.id.tv_work_number);
            //tv_submit_state = (TextView) itemView.findViewById(R.id.tv_submit_state);
            tv_work_state =(TextView) itemView.findViewById(R.id.tv_work_state);
            tv_update_time = (TextView) itemView.findViewById(R.id.tv_update_time);
            img = (ImageView)itemView.findViewById(R.id.img2);
        }
    }
}

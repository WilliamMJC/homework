package com.hzu.feirty.HomeWork.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hzu.feirty.HomeWork.R;
import com.hzu.feirty.HomeWork.db.Course;
import com.hzu.feirty.HomeWork.db.Student;
import com.hzu.feirty.HomeWork.entity.OnRecyclerviewItemClickListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-10-15.
 */

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder> implements View.OnClickListener {
    private ArrayList<Student> mData;
    private LayoutInflater inflater;
    private Context context;
    //声明自定义的监听接口
    private OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener = null;
    @Override
    public StudentAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_item,viewGroup,false);
        StudentAdapter.MyViewHolder vh = new StudentAdapter.MyViewHolder(v);
        v.setOnClickListener(this);
        return vh;
    }
    //构造方法中添加自定义监听接口
    public StudentAdapter(Context context, ArrayList<Student> data, OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
        this.context = context;
        this.mData = data;
        inflater = LayoutInflater.from(context);
        this.mOnRecyclerviewItemClickListener = mOnRecyclerviewItemClickListener;
    }
    public StudentAdapter(ArrayList<Student> data){
        mData = data;
    }
    @Override
    public void onBindViewHolder(StudentAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.lv_name.setText("NULL");
        myViewHolder.lv_number.setText(mData.get(i).getNumber());
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
        public TextView lv_name;
        public TextView lv_number;
        public MyViewHolder(View itemView) {
            super(itemView);
            lv_name = (TextView) itemView.findViewById(R.id.list_name);
            lv_number = (TextView) itemView.findViewById(R.id.list_number);
        }
    }
}

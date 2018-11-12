package com.hzu.feirty.HomeWork.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hzu.feirty.HomeWork.R;
import com.hzu.feirty.HomeWork.db.Course;
import com.hzu.feirty.HomeWork.entity.OnRecyclerviewItemClickListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-10-11.
 */

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyViewHolder> implements View.OnClickListener {
    private ArrayList<Course> mData;
    private LayoutInflater inflater;
    private Context context;
    //声明自定义的监听接口
    private OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener = null;
    @Override
    public CourseAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_item,viewGroup,false);
        CourseAdapter.MyViewHolder vh = new CourseAdapter.MyViewHolder(v);
        v.setOnClickListener(this);
        return vh;
    }
    //构造方法中添加自定义监听接口
    public CourseAdapter(Context context, ArrayList<Course> data, OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
        this.context = context;
        this.mData = data;
        inflater = LayoutInflater.from(context);
        this.mOnRecyclerviewItemClickListener = mOnRecyclerviewItemClickListener;
    }
    public CourseAdapter(ArrayList<Course> data){
        mData = data;
    }
    @Override
    public void onBindViewHolder(CourseAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_course.setText(mData.get(i).getName());
        myViewHolder.tv_works.setText("作业次数:"+mData.get(i).getWorks());
        myViewHolder.tv_students.setText("学生人数:"+mData.get(i).getStudents());
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
        public TextView tv_students;
        public TextView tv_works;
        public TextView tv_course;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_students = (TextView) itemView.findViewById(R.id.tv_students);
            tv_course = (TextView) itemView.findViewById(R.id.tv_course);
            tv_works = (TextView) itemView.findViewById(R.id.tv_works);
        }
    }
}

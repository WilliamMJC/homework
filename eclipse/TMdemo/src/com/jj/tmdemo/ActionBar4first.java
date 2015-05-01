package com.jj.tmdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

public class ActionBar4first extends FrameLayout{
	public ActionBar4first(Context context,AttributeSet attrs) {
		// TODO Auto-generated constructor stub
		super(context,attrs);
		LayoutInflater.from(context).inflate(R.layout.view_actionbar4first, this);
		
	}
}
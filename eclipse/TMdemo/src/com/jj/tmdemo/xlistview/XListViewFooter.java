package com.jj.tmdemo.xlistview;

import com.jj.tmdemo.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;



public class XListViewFooter extends LinearLayout {
	//底部的自定义布局，
	//三个整型变量，正常0准备1加载2
	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_LOADING = 2;
	//上下文对象
	private Context mContext;
	//两个view对象和一个TextView对象
	private View mContentView;
	private View mProgressBar;
	private TextView mHintView;
	//单参构造函数
	public XListViewFooter(Context context) {
		super(context);
		initView(context);
	}
	//双参构造函数
	public XListViewFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	//设置状态
	public void setState(int state) {
		//一设置，不管什么先，弄到textview看不见，
		mHintView.setVisibility(View.INVISIBLE);
		mProgressBar.setVisibility(View.INVISIBLE);
		mHintView.setVisibility(View.INVISIBLE);
		//如果要设置状态为准备，
		//设置textview可以见，并且文字     松开加载更多
		if (state == STATE_READY) {
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText(R.string.xlistview_footer_hint_ready);
		} else if (state == STATE_LOADING) {
			//如果设置状态加载，则搞一个进度条圆形的滚滚滚
			mProgressBar.setVisibility(View.VISIBLE);
		} else {
			//再否则，如果设置状态normal，则设置textview可以见，字体   查看更多
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText(R.string.xlistview_footer_hint_normal);
		}
	}
	//设置底部边缘函数，传入一个高度。
	public void setBottomMargin(int height) {
		
		if (height < 0) return ;
		//布局配置对象lp，
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)mContentView.getLayoutParams();
		lp.bottomMargin = height;
		mContentView.setLayoutParams(lp);
	}
	//获取底部边缘，
	public int getBottomMargin() {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)mContentView.getLayoutParams();
		return lp.bottomMargin;
	}
	
	/**
	 * normal status
	 */
	//平时状态
	public void normal() {
		mHintView.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.GONE);
	}
	
	
	/**
	 * loading status 
	 */
	//loading函数，设置textview为隐藏
	//进度条可见性为可见
	public void loading() {
		mHintView.setVisibility(View.GONE);
		mProgressBar.setVisibility(View.VISIBLE);
	}
	
	/**
	 * hide footer when disable pull load more
	 */
	//hide函数，high为零
	public void hide() {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)mContentView.getLayoutParams();
		lp.height = 0;
		mContentView.setLayoutParams(lp);
	}
	
	/**
	 * show footer
	 */
	//show函数，设置高度随内容，整个相对布局设置。
	public void show() {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)mContentView.getLayoutParams();
		lp.height = LayoutParams.WRAP_CONTENT;
		mContentView.setLayoutParams(lp);
	}
	//初始化控件
	private void initView(Context context) {
		//传入的上下文赋值给我的上下文
		mContext = context;
		//更多控件的LL布局文件把xlistview_footer的xml文件转换成view对象moreview，并且添加进来。
		LinearLayout moreView = (LinearLayout)LayoutInflater.from(mContext).inflate(R.layout.xlistview_footer, null);
		addView(moreView);
		
		moreView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		//获取整个相对布局文件，获取进度条，获取textview
		mContentView = moreView.findViewById(R.id.xlistview_footer_content);
		mProgressBar = moreView.findViewById(R.id.xlistview_footer_progressbar);
		mHintView = (TextView)moreView.findViewById(R.id.xlistview_footer_hint_textview);
	}
	
	
}


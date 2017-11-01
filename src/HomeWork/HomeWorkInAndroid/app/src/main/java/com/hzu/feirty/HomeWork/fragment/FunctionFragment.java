package com.hzu.feirty.HomeWork.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.hzu.feirty.HomeWork.R;
import com.hzu.feirty.HomeWork.activity.funcation.AllCourseActivity;
import com.hzu.feirty.HomeWork.activity.funcation.AllSchoolActivity;
import com.hzu.feirty.HomeWork.activity.funcation.SeleteCourseActivity;
import com.hzu.feirty.HomeWork.activity.funcation.SendCourseActivity;
import com.hzu.feirty.HomeWork.activity.funcation.SendWorkActivity;
import com.hzu.feirty.HomeWork.activity.funcation.WorkStateActivity;
import com.hzu.feirty.HomeWork.activity.index.MailApplication;
import com.hzu.feirty.HomeWork.activity.index.MyApplication;
import com.hzu.feirty.HomeWork.entity.Ip;
import com.hzu.feirty.HomeWork.utils.PreferencesUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONException;
import org.json.JSONObject;

public class FunctionFragment extends Fragment {
	private LinearLayout send_work;
	private LinearLayout send_course;
	private Toolbar toolbar;
	private ProgressDialog pd;
	private ProgressDialog pd2;
	private LinearLayout receivework;
	private LinearLayout showwork;
	private LinearLayout all_school;
	private LinearLayout all_course;
	private LinearLayout teacher_show;
	private TextView toolbarTv;
	private static final int CHANGED = 0x0010;
	private MyHandler handler1 = null;
	private MyApplication mAPP = null;
	private static final String IDENTITY = "saveidentity";
	final String items[] = { "学生","老师"};
	private String url3 = Ip.ip + "/HomeWork/DoGetType?";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view= inflater.inflate(R.layout.fragment_search, null);
		return view;
	}
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		MailApplication mAPP = (MailApplication)getActivity().getApplication();
		handler1 = new MyHandler();
		send_work = (LinearLayout) getActivity().findViewById(R.id.send_work);
		send_course = (LinearLayout) getActivity().findViewById(R.id.send_course);
		all_course = (LinearLayout) getActivity().findViewById(R.id.all_course);
		all_school = (LinearLayout) getActivity().findViewById(R.id.all_school);
		receivework = (LinearLayout) getActivity().findViewById(R.id.receivework01);
		showwork = (LinearLayout) getActivity().findViewById(R.id.showwork);
		teacher_show = (LinearLayout) getActivity().findViewById(R.id.teacher_show_fun);
		toolbarTv = (TextView) getActivity().findViewById(R.id.toolbarTv01);
		toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
		AppCompatActivity activity = (AppCompatActivity) getActivity();
		if (activity.getSupportActionBar() != null){
			activity.getSupportActionBar().hide();
		}
		toolbarTv.setText("功能");
		isType();
		all_course.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), AllCourseActivity.class));
			}
		});
		all_school.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), AllSchoolActivity.class));
			}
		});
		showwork.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), WorkStateActivity.class);
				startActivity(intent);
			}
		});
		receivework.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), SeleteCourseActivity.class);
				startActivity(intent);
			}
		});
		send_work.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), SendWorkActivity.class);
				startActivity(intent);
			}
		});
		send_course.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), SendCourseActivity.class);
				startActivity(intent);
			}
		});
	}
	private Handler handler =new Handler(){
		@Override
		//当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			if(msg.what==0){
				pd.dismiss();
			}else if(msg.what==1){
				pd2.dismiss();
			}
		}
	};
	private void isType(){
		RequestParams params = new RequestParams();
		params.put("action", "istype");
		String user =PreferencesUtil.getSharedStringData(FunctionFragment.this.getActivity(),"username");
		params.put("user", user);
		AsyncHttpClient client = new AsyncHttpClient();
		client.setConnectTimeout(50000);
		client.post(url3, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int i, org.apache.http.Header[] headers, byte[] responseBody) {
				String str = new String(responseBody);
				if (str != null) {
					try {
						JSONObject object = new JSONObject(str);
						if (object.getString("code").equals("teacher")) {
							showwork.setVisibility(View.GONE);
							teacher_show.setVisibility(View.VISIBLE);
						} else if(object.getString("code").equals("student")){
							teacher_show.setVisibility(View.GONE);
						} else {
							showwork.setVisibility(View.GONE);
							teacher_show.setVisibility(View.GONE);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
				}
			}

			@Override
			public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
				Toast.makeText(FunctionFragment.this.getActivity(), "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
			}
		});
	}

	/**
	 * 自己实现 Handler 处理消息更新UI
	 *
	 * @author mark
	 */
	final public  class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case 1:// 更新UI
					showwork.setVisibility(View.GONE);
					teacher_show.setVisibility(View.VISIBLE);
					break;
				case 0:
					teacher_show.setVisibility(View.GONE);
			}
		}
	}
}

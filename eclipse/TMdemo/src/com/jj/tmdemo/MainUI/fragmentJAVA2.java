package com.jj.tmdemo.MainUI;



import DatabasePart.DatabasePart;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jj.tmdemo.AllOfHomework;
import com.jj.tmdemo.AllOfInform;
import com.jj.tmdemo.AllOfPhoto;
import com.jj.tmdemo.R;

//添加3个textview，每个都弹到对应的activity

public class fragmentJAVA2 extends Fragment{

	String s4CheckIfTeacher;
	String s4BasicUserName;
	String s4BasicUserIdentity;
	DatabasePart dp;

	Cursor cursor1;
	String s4UserIdentity;
//	String ss4UserIdentity;
	
	static fragmentJAVA2 newInstance() {
		fragmentJAVA2 newFragment = new fragmentJAVA2();

        return newFragment;
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view =  inflater.inflate(R.layout.fragment2, container, false);
		LinearLayout ll4homework = (LinearLayout)view.findViewById(R.id.ll4homework);
		LinearLayout ll4inform = (LinearLayout)view.findViewById(R.id.ll4inform);
		LinearLayout ll4photo =(LinearLayout)view.findViewById(R.id.ll4photo);
		
		s4CheckIfTeacher = "select identity from User where account = ?";
		Intent intent1 = getActivity().getIntent();
		Bundle bundle1 = intent1.getExtras();
		s4BasicUserName = bundle1.getString("userName");
		s4BasicUserIdentity = bundle1.getString("userIdentity");
		
//		dp = new DatabasePart("jim/sjk2", "a1.db");
//		cursor1 = dp.query(s4CheckIfTeacher, new String [] { s4BasicUserName });
//		cursor1.moveToFirst();
//		s4UserIdentity = cursor1.getString(0);
//		ss4UserIdentity = s4UserIdentity;
		//在创建fragment界面的时候就获取了用户登陆的身份，依此来判断身份作出相应的改变。
		
		ll4homework.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent2all4homework = new Intent();
//				intent2all4homework.putExtra("keyIdentity", s4UserIdentity);
				//这里还没intent还没绑定bundle？ AllOfHomework.java里面如何拿出来这个值？
				//还要传入用户账号，不然发布通知和作业如何写入数据库？
				//如何传入2个参数，AllOf哪里又如何取出。
				intent2all4homework.setClass(getActivity(), AllOfHomework.class);
				Bundle b1 = new Bundle();
				b1.putString("userNameF2", s4BasicUserName);
				b1.putString("userIdentityF2", s4BasicUserIdentity);
				intent2all4homework.putExtras(b1);
				startActivity(intent2all4homework);
			}
		});
		ll4inform.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent2all4inform = new Intent();
				//还要传入用户账号，不然发布通知和作业如何写入数据库？
				
//				intent2all4inform.putExtra("keyIdentityInInform", s4UserIdentity);
				intent2all4inform.setClass(getActivity(), AllOfInform.class);
				Bundle b2 = new Bundle();
				b2.putString("userNameF2", s4BasicUserName);
				b2.putString("userIdentityF2", s4BasicUserIdentity);
				intent2all4inform.putExtras(b2);
				startActivity(intent2all4inform);
			}
		});
		ll4photo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent2all4photo = new Intent();
				intent2all4photo.setClass(getActivity(), AllOfPhoto.class);
				Bundle b3 = new Bundle();
				b3.putString("userNameF2", s4BasicUserName);
				b3.putString("userIdentityF2", s4BasicUserIdentity);
				intent2all4photo.putExtras(b3);
				startActivity(intent2all4photo);
			}
		});

		
		
        return view;
	}
}

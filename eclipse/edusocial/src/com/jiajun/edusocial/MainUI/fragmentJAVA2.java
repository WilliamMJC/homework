package com.jiajun.edusocial.MainUI;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;


import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jiajun.edusocial.R;
import com.jiajun.edusocial.Homework.AllOfHomework;
import com.jiajun.edusocial.Inform.AllOfInform;
import com.jiajun.edusocial.Photo.AllOfPhoto;

//添加3个textview，每个都弹到对应的activity

public class fragmentJAVA2 extends Fragment{

	String s4CheckIfTeacher;
	String s4BasicUserName;
	String s4BasicUserIdentity;
	String s4UserSch;
	ArrayList<String> al4UserCla = new ArrayList<String>();

	ArrayList<String> AL4myAllFriend;


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
		

		Intent intent1 = getActivity().getIntent();
		Bundle bundle1 = intent1.getExtras();
		s4BasicUserName = bundle1.getString("userName");
		s4BasicUserIdentity = bundle1.getString("userIdentity");
		s4UserSch = bundle1.getString("userSch");
		al4UserCla = bundle1.getStringArrayList("userClaArray");
//		System.out.println(al4UserCla+"aa?");
		
		
//		final ArrayList<String> friendNames = new ArrayList<String>();
		final Handler h = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				Bundle newb = msg.getData();
				AL4myAllFriend = newb.getStringArrayList("AL4friendNames");
//				System.out.println("i am colser to to to" + AL4myAllFriend);
			}
		};
		new Thread(){
			@Override
			public void run(){
			//你要执行的方法
			//执行完毕后给handler发送一个空消息
				HttpGet httpGet = new HttpGet("http://zeng.shaoning.net/edusocial/FriendShip.json");
				HttpClient httpClient = new DefaultHttpClient();
				Message msg = new Message();
				InputStream inputStream = null;
				try {
					HttpResponse httpResponse = httpClient.execute(httpGet);
					HttpEntity httpEntity = httpResponse.getEntity();
					inputStream = httpEntity.getContent();
					//GB2312
					//utf-8
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));

					
					String result = "";
					String line = "";
					while((line = reader.readLine()) != null){
						result = result + line;
					}
//					System.out.println(result);
					JSONObject JO4FriendShipJson = new JSONObject(result);
					JSONArray JA4TotalSingleFriendShip = JO4FriendShipJson.getJSONArray("FriendShip");
					JSONObject JO4SingleFriendShip;
					ArrayList<String> friendNames = new ArrayList<String>();
					for (int i = 0; i < JA4TotalSingleFriendShip.length(); i++) {
						JO4SingleFriendShip = JA4TotalSingleFriendShip.getJSONObject(i);
						if (JO4SingleFriendShip.getString("PositiveU").equals(s4BasicUserName)) {
							friendNames.add(JO4SingleFriendShip.getString("NegativeU"));
						}
					}
					Bundle b = new Bundle();
					b.putStringArrayList("AL4friendNames", friendNames);
					msg.setData(b);



				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					try{
						inputStream.close();
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}					
				h.sendMessage(msg);

			}
		}.start();
		
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
				b1.putString("userSchF2", s4UserSch);
				b1.putStringArrayList("userClaArrayF2", al4UserCla);
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
				b2.putString("userSchF2", s4UserSch);
				b2.putStringArrayList("userClaArrayF2", al4UserCla);
				intent2all4inform.putExtras(b2);
				startActivity(intent2all4inform);
			}
		});
		ll4photo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (AL4myAllFriend.size() == 0) {
					Toast.makeText(getActivity(), "网络异常，请稍后重试！", Toast.LENGTH_LONG).show();
				}else {
					Intent intent2all4photo = new Intent();
					intent2all4photo.setClass(getActivity(), AllOfPhoto.class);
					Bundle b3 = new Bundle();
					b3.putStringArrayList("friendList", AL4myAllFriend);
					System.out.println("ffffff " + AL4myAllFriend);
					b3.putString("userNameF2", s4BasicUserName);
					b3.putString("userIdentityF2", s4BasicUserIdentity);
					b3.putString("userSchF2", s4UserSch);
					b3.putStringArrayList("userClaArrayF2", al4UserCla);
					intent2all4photo.putExtras(b3);
					startActivity(intent2all4photo);
				}
				

			}
		});

		
		
        return view;
	}
}

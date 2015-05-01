package com.jiajun.edusocial.PersonalInfo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.json.JSONArray;
import org.json.JSONObject;

import com.jiajun.edusocial.R;
import com.jiajun.edusocial.R.id;
import com.jiajun.edusocial.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class personalInfo extends Activity{
	
	String who;
	String hisTX;
	String hisNickName;
	String hisIdentity;
	String hisSchool;
	String hisNoClass;
	
	String hisTXInRun;
	String hisNickNameInRun;
	String hisIdentityInRun;
	String hisSchoolInRun;
	String hisNoClassInRun;

//	LinearLayout ll4
	TextView tv_identity4personalInfo;
	TextView tv_nickName4personalInfo;
	TextView tv_school4personalInfo;
	TextView tv_class4personalInfo;
	
	String hisClass="";
	String hisTx;
	ImageView iv ;
	String fromw ;
	Button addF;
	
	HttpEntity httpEntity;
	HttpResponse httpResponse;
	
	ArrayList<String> hisNoClassList = new ArrayList<String>();
	ArrayList<String> hisNoClassListInRun;
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			Bundle bb = msg.getData();
			hisNoClassList = bb.getStringArrayList("noClassFromRun");
			System.out.println(hisNoClassList);
			hisIdentity = bb.getString("hisIdentity");
			hisNickName = bb.getString("hisNickName");
			hisSchool = bb.getString("hisSchool");
			
			
			tv_identity4personalInfo.setText(hisIdentity);
			tv_nickName4personalInfo.setText(hisNickName);
			tv_school4personalInfo.setText(hisSchool);
//			try {
//	            URL url = new URL(hisTx);
//	            URLConnection con = (URLConnection) url.openConnection();
//	            InputStream stream = con.getInputStream();
//	            Bitmap bitmap = BitmapFactory.decodeStream(stream);
//	            iv.setImageBitmap(bitmap);
//	        } catch (IOException e) {
//	            // TODO Auto-generated catch block
//	            e.printStackTrace();
//	        }
//			iv.setImageBitmap(bm)
			Bitmap bitmapp = bb.getParcelable("bitmap4");
			iv.setImageBitmap(bitmapp);
			String result [] = new String[hisNoClassList.size()];
//			String resultZZ = new String();
			result  = hisNoClassList.toArray(result);
			hisClass = result[0];
			if (result.length > 1) {
				for (int i = 1; i < result.length; i++) {
					hisClass = hisClass + " " + result[i];
				}
			}
			tv_class4personalInfo.setText(hisClass);
//
//			System.out.println("personal " + resultZZ);
			
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.personal_info);
		
		Intent get = getIntent();
		who = get.getStringExtra("nick");
		hisTx = get.getStringExtra("userTx");
		fromw = get.getStringExtra("from");
		addF = (Button)findViewById(R.id.addF);
		if (fromw.equals("search")) {
			
		}else {
			addF.setVisibility(View.GONE);
		}
		addF.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "请求已发送！", Toast.LENGTH_SHORT).show();
				//好友和自己的检测
			}
		});
		
		System.out.println("balapipi " + hisTx);
//		who = bbb.getString("userName");
//		System.out.println(bbb+"-----------");
		//传递个nickname过来，根据nickname，载入个人信息，填充到控件里面
		//需要做“资料修改”功能吗?
		
		tv_class4personalInfo = (TextView)findViewById(R.id.tv_class4personalInfo);
		tv_identity4personalInfo = (TextView)findViewById(R.id.tv_identity4personalInfo);
		tv_nickName4personalInfo = (TextView)findViewById(R.id.tv_nickName4personalInfo);
		tv_school4personalInfo = (TextView)findViewById(R.id.tv_school4personalInfo);
		iv = (ImageView)findViewById(R.id.perTx);
		
		hisTXInRun = "";
		hisNickNameInRun = "";
		hisIdentityInRun = "";
		hisSchoolInRun = "";
		hisNoClassInRun = "";
		
//		final Handler haha = new Handler(){
//			@Override
//			public void handleMessage(Message msg6) {
//				// TODO Auto-generated method stub
//				Bundle bundlee = msg6.getData();
//				Bitmap bitmapp = bundlee.getParcelable("bitmap4");
//				iv.setImageBitmap(bitmapp);
//			}
//		};
//		
//		new Thread(){
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				Message msg7 = new Message();
//				try {
//
//		            URL url = new URL(hisTx);
//		            URLConnection con = (URLConnection) url.openConnection();
//		            InputStream stream = con.getInputStream();
//		            Bitmap bitmap = BitmapFactory.decodeStream(stream);
////		            iv.setImageBitmap(bitmap);
//		            Bundle bundlelolo = new Bundle();
//		            bundlelolo.putParcelable("bitmap4", bitmap);
//		            msg7.setData(bundlelolo);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
////				iv.setImageBitmap(bitmap)
//				haha.handleMessage(msg7);
//			}
//		}.start();
		
		new Thread(){
			public void run() {
				HttpGet httpGet = new HttpGet("http://zeng.shaoning.net/edusocial/User.json");
				HttpGet httpGet2 = new HttpGet(hisTx);
				
				HttpClient httpClient = new DefaultHttpClient();
				Message msg2 = new Message();
				//Looper.loop();
				InputStream inputStream = null;
				InputStream is = null;
				try {
					//zheli 这里

					
					httpResponse = httpClient.execute(httpGet2);
					httpEntity = httpResponse.getEntity();
					is = httpEntity.getContent();
		            Bitmap bitmap = BitmapFactory.decodeStream(is);
//	            iv.setImageBitmap(bitmap);
//		            Bundle bundlelolo = new Bundle();
//		            bundlelolo.putParcelable("bitmap4", bitmap);
//		            msg7.setData(bundlelolo);
					
		            
					httpResponse = httpClient.execute(httpGet);
					httpEntity = httpResponse.getEntity();
					inputStream = httpEntity.getContent();
					//GB2312
					BufferedReader reader2 = new BufferedReader(new InputStreamReader(inputStream,"GB2312"));
					String result2 = "";
					String line2 = "";
					while((line2 = reader2.readLine()) != null){
						result2 = result2 + line2;
					}

//		            URL url = new URL(hisTx);
//		            URLConnection con = (URLConnection) url.openConnection();
//		            InputStream stream = con.getInputStream();
//		            Bitmap bitmap = BitmapFactory.decodeStream(stream);
//		            iv.setImageBitmap(bitmap);
//		            stream.close();
					
					JSONObject JO4UserJson = new JSONObject(result2);
					JSONArray JA4User = JO4UserJson.getJSONArray("User");
					hisNoClassListInRun = new ArrayList<String>();
					for (int q = 0; q < JA4User.length(); q++) {
						JSONObject JO4SingleUser = JA4User.getJSONObject(q);
						if (JO4SingleUser.getString("nickname").equals(who)) {
							hisIdentityInRun = JO4SingleUser.getString("identity");
							hisNickNameInRun = JO4SingleUser.getString("nickname");
							hisSchoolInRun = JO4SingleUser.getString("school");
							JSONArray JA4Cla = JO4SingleUser.getJSONArray("noclass");
							for (int i = 0; i < JA4Cla.length(); i++) {
								hisNoClassListInRun.add(JA4Cla.getString(i));
							}
						}
					}
					Bundle bNor = new Bundle();
					bNor.putString("hisIdentity", hisIdentityInRun);
					bNor.putString("hisNickName", hisNickNameInRun);
					bNor.putString("hisSchool", hisSchoolInRun);
					bNor.putStringArrayList("noClassFromRun", hisNoClassListInRun);
					bNor.putParcelable("bitmap4", bitmap);
					msg2.setData(bNor);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					try{//这里zheli
						inputStream.close();
						is.close();
//						httpEntity.getContent().close();
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}					
				handler.sendMessage(msg2);
			}
		}.start();
		
		
		ImageView back2personalMain = (ImageView)findViewById(R.id.back2personalMain);
		back2personalMain.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
	}
}

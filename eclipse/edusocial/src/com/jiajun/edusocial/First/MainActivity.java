package com.jiajun.edusocial.First;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.jiajun.edusocial.R;
import com.jiajun.edusocial.MainUI.MainUI4me;
import com.jiajun.edusocial.Register.Register;

public class MainActivity extends Activity {

	private String identityName;
	String String4UserJson;

	EditText InputAccount ;
	EditText InputPassword ;
	Spinner mSpinner ;
	String selectedIdentity;
	
	String isitright4identity;
	String ifexistaccount ;
	String SInputAccount ;
	String SInputPassword ;
	String ifexistpassword ;
	String gotNickName;

	HttpResponse httpResponse;
	HttpEntity httpEntity;
	String uNickName;
	ArrayList<String> targetList;
	ArrayList<String> userIdArrayT;
	ArrayList<String> userIdenArrayT;
	ArrayList<String> userAccArrayT;
	ArrayList<String> userPswArrayT;
	ArrayList<String> userNickArrayT;
	ArrayList<String> userSchArrayT;
	ArrayList<String> userTxArrayT = new ArrayList<String>();
	ArrayList<String> userClaArrayArrayT = new ArrayList<String>();
	
//	ArrayList<ArrayList<String>> className;
	
	ArrayList<String> targetClassList;
	ArrayList<String> userIdArray;
	ArrayList<String> userIdenArray;
	ArrayList<String> userAccArray;
	ArrayList<String> userPswArray;
	ArrayList<String> userNickArray;
	ArrayList<String> userSchArray;
	ArrayList<String> userTxArray;
	ArrayList<ArrayList<String>> userClaArrayArray;
	JSONArray targetClassJA;
	JSONArray JA4whoseCla;
	String test;
	ArrayList list = new ArrayList();
	
	Handler handler4class =new Handler(){
	@Override
	//当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg){
		//super.handleMessage(msg);
			
			Bundle b4tc = msg.getData();
			test = b4tc.getString("ok");

//			targetList = b4tc.getStringArrayList("target");
//			System.out.println(targetList);
			userAccArrayT = b4tc.getStringArrayList("Uacc");

			userIdArrayT = b4tc.getStringArrayList("Uid");

			userPswArrayT = b4tc.getStringArrayList("Upsw");

			userIdenArrayT = b4tc.getStringArrayList("Uiden");

			userNickArrayT = b4tc.getStringArrayList("Unick");
	
			userSchArrayT = b4tc.getStringArrayList("Usch");
			
			userTxArrayT = b4tc.getStringArrayList("Utx");

//			userClaArrayArrayT = b4tc.getParcelableArrayList("Ucla");
			list = b4tc.getParcelableArrayList("Ucla");
//			System.out.println("aha?" + list);
//			//这里
//			userClaArrayArrayT = (ArrayList<ArrayList<String>>) list.get(0);
//			System.out.println("hollo " + userClaArrayArrayT );
//			userClaArrayArrayT = b4tc.getParcelableArrayList("Ucla");
		//处理UI
		}
	};
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		if (isNetworkConnected() == false) {
			Toast.makeText(this, "当前网络不可用！", Toast.LENGTH_LONG).show();
			finish();
//			System.exit(0);  
		}

		new Thread(){
			@Override
			public void run(){
			//你要执行的方法
			//执行完毕后给handler发送一个空消息
				HttpGet httpGet = new HttpGet("http://zeng.shaoning.net/edusocial/User.json");

				HttpClient httpClient = new DefaultHttpClient();
				Message msg2 = new Message();
				//Looper.loop();
				InputStream inputStream = null;
				try {
					//zheli 这里
					httpResponse = httpClient.execute(httpGet);
					httpEntity = httpResponse.getEntity();
					inputStream = httpEntity.getContent();
					//GB2312
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"GB2312"));
					String result = "";
					String line = "";
					while((line = reader.readLine()) != null){
						result = result + line;
					}
//					System.out.println(result);
					JSONObject JO4UserJson = new JSONObject(result);
					JSONArray JA4User = JO4UserJson.getJSONArray("User");
//					System.out.println(JA4User.length());
//					JSONObject myJOSingleObject2;
//					JSONArray myJOSingleArray2;
////					schoolName = new ArrayList<String>();
//					className = new ArrayList<ArrayList<String>>();
					
					ArrayList<String> temp;
					//位置很重要，放下面for循环里面实现就只能读取最后一个数据
					userIdArray = new ArrayList<String>();
					userAccArray = new ArrayList<String>();
					userPswArray = new ArrayList<String>();
					userIdenArray = new ArrayList<String>();
					userNickArray = new ArrayList<String>();
					userSchArray = new ArrayList<String>();
					userTxArray = new ArrayList<String>();
					userClaArrayArray = new ArrayList<ArrayList<String>>();
//					userClaArrayArray = new ArrayList<ArrayList<String>>();
					for (int q = 0; q < JA4User.length(); q++) {
						JSONObject JO4SingleUser = JA4User.getJSONObject(q);

//						userClaArrayArray = new ArrayList<ArrayList<String>>();
						
						userIdArray.add(JO4SingleUser.getString("_id"));

						userAccArray.add(JO4SingleUser.getString("account"));
						userPswArray.add(JO4SingleUser.getString("password"));
						userIdenArray.add(JO4SingleUser.getString("identity"));
						userNickArray.add(JO4SingleUser.getString("nickname"));
						userSchArray.add(JO4SingleUser.getString("school"));
						userTxArray.add(JO4SingleUser.getString("tx"));
						JSONArray ja = JO4SingleUser.getJSONArray("noclass");
						
						temp = new ArrayList<String>();

						for (int w = 0; w < ja.length(); w++) {
							temp.add(ja.getString(w));

						}
						userClaArrayArray.add(temp);
					}
					Bundle bNor = new Bundle();
					bNor.putStringArrayList("Uid", userIdArray);

					bNor.putStringArrayList("Uacc", userAccArray);

					bNor.putStringArrayList("Upsw", userPswArray);

					bNor.putStringArrayList("Uiden", userIdenArray);

					bNor.putStringArrayList("Unick", userNickArray);

					bNor.putStringArrayList("Usch", userSchArray);
					
					bNor.putStringArrayList("Utx", userTxArray);

					bNor.putParcelableArrayList("Ucla", (ArrayList<? extends Parcelable>) userClaArrayArray);

					bNor.putString("ok", result);
					msg2.setData(bNor);


				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					try{//这里zheli
						inputStream.close();
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}					
				handler4class.sendMessage(msg2);

			}
		}.start();
		
		Button register = (Button)findViewById(R.id.register);
		Button submit = (Button)findViewById(R.id.submit);
		mSpinner = (Spinner)findViewById(R.id.mySpinner);
		InputAccount =  (EditText)findViewById(R.id.InputAccount);
		InputPassword = (EditText)findViewById(R.id.InputPassword);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, 
				R.array.identity, R.layout.spinner_item);  //这种很瘦
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinner.setAdapter(adapter);

		mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 2:
					identityName = "parent";
					break;
				case 1:
					identityName = "student";
					break;
				case 0:
					identityName = "teacher";
					break;
					default:
						identityName = "teacher";
						break;
				}
			}

			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}			
		});
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Toast.makeText(MainActivity.this, "hahaha", Toast.LENGTH_LONG).show();
				SInputAccount = InputAccount.getText().toString().trim();
				SInputPassword = InputPassword.getText().toString().trim();
				
				if (SInputAccount.equals("")) {
					Toast.makeText(MainActivity.this, "请输入账号！", Toast.LENGTH_LONG).show();
				}else if (SInputPassword.equals("")) {
					Toast.makeText(MainActivity.this, "请输入密码！", Toast.LENGTH_LONG).show();
				}else if (userAccArrayT== null) {
					Toast.makeText(getApplicationContext(), "网络异常，请稍后重试！", Toast.LENGTH_LONG).show();
//					finish();
				}else{
					boolean accRight = false;
					for (int e = 0; e < userAccArrayT.size(); e++) {
						if (SInputAccount.equals(userAccArrayT.get(e))) {
							accRight = true;
							if (SInputPassword.equals(userPswArrayT.get(e))) {
								if (identityName.equals(userIdenArrayT.get(e))) {
									final Intent intentL = new Intent();
									final Handler hl = new Handler(){
										@Override
										public void handleMessage(Message msg) {
											// TODO Auto-generated method stub
											Bundle dl = msg.getData();
											
											String lala = dl.getString("success");
											System.out.println("main " + lala);
											if (lala.equals("success")) {
												startActivity(intentL);
												finish();
											}
										}
									};
									new Thread(){
										@Override
										public void run() {
											// TODO Auto-generated method stub
											HttpPost httpPost = new HttpPost("http://zeng.shaoning.net/edusocial/success.json");
											
											HttpClient httpClient = new DefaultHttpClient();
											Message msg = new Message();
											InputStream inputStream = null;
											try {
												httpResponse = httpClient.execute(httpPost);
												httpEntity = httpResponse.getEntity();
												
//												List<NameValuePair> params = new  ArrayList<NameValuePair>();
//												params.add(new BasicNameValuePair("success", "false"));
//												httpPost.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));

												inputStream = httpEntity.getContent();
												BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
												String result = "";
												String line = "";
												while((line = reader.readLine()) != null){
													result = result + line;
												}
//												System.out.println("post " + result);
												
												JSONObject jo = new JSONObject(result);
												String success = jo.getString("success");
												Bundle bdd = new Bundle();
//												System.out.println("du  " + success);
												bdd.putString("success", success);
												msg.setData(bdd);
											} catch (Exception e) {
												// TODO: handle exception
												e.printStackTrace();
											}
											finally{
												try {
													inputStream.close();
												} catch (Exception e2) {
													// TODO: handle exception
													e2.printStackTrace();
												}
											}
											hl.sendMessage(msg);

											
										}
									}.start();
									

									intentL.setClass(MainActivity.this, MainUI4me.class);
									Bundle bundleL = new Bundle();
									bundleL.putString("userName", userNickArrayT.get(e));
									bundleL.putString("userIdentity", userIdenArrayT.get(e));//zheli报错
									userClaArrayArrayT = (ArrayList<String>) list.get(e);
									bundleL.putStringArrayList("userClaArray",userClaArrayArrayT);
									bundleL.putString("userSch", userSchArrayT.get(e));
									bundleL.putString("userTx", userTxArrayT.get(e));
									intentL.putExtras(bundleL);
//									Intent intentL = new Intent();
//									intentL.setClass(MainActivity.this, MainUI4me.class);
//									Bundle bundleL = new Bundle();
//									bundleL.putString("userName", userNickArrayT.get(e));
//									bundleL.putString("userIdentity", userIdenArrayT.get(e));//zheli报错
//									userClaArrayArrayT = (ArrayList<String>) list.get(e);
//									bundleL.putStringArrayList("userClaArray",userClaArrayArrayT);
//									bundleL.putString("userSch", userSchArrayT.get(e));
//									bundleL.putString("userTx", userTxArrayT.get(e));
//									intentL.putExtras(bundleL);
//									startActivity(intentL);
//									finish();
								}else {
									Toast.makeText(MainActivity.this, "身份不正确，请选择！", Toast.LENGTH_LONG).show();
									break;
								}
							}else {
								Toast.makeText(MainActivity.this, "密码不正确，请重新输入！", Toast.LENGTH_LONG).show();
								break;
							}
						}
//						else if(e == userAccArrayT.size()){
//							Toast.makeText(MainActivity.this, "账号不正确，请重新输入！", Toast.LENGTH_LONG).show();
//							break;
//						}
					}
					if (accRight == false) {
						Toast.makeText(MainActivity.this, "账号不正确，请重新输入！", Toast.LENGTH_LONG).show();
					}

				}
				
				
			}
		});

		register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (userAccArrayT == null) {
					Toast.makeText(getApplicationContext(), "网络异常，请稍后重试！", Toast.LENGTH_LONG).show();
				}else {
					Intent intent1 = new Intent();
					intent1.setClass(MainActivity.this, Register.class);
					Bundle bd = new Bundle();
					bd.putStringArrayList("existedUnick", userNickArrayT);
					bd.putStringArrayList("existedUacc", userAccArrayT);
					intent1.putExtras(bd);
					startActivity(intent1);
				}

				
			}
		});
		
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		dp.DataClose();
	}
	private boolean isNetworkConnected() {  
		//1
        ConnectivityManager cm =   
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);  
        NetworkInfo network = cm.getActiveNetworkInfo();  
        if (network != null) {  
            return network.isAvailable();  
        }  
        return false;  
    }

}


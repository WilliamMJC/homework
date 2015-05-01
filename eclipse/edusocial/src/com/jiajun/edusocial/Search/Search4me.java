package com.jiajun.edusocial.Search;

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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.jiajun.edusocial.R;
import com.jiajun.edusocial.PersonalInfo.personalInfo;

/*
 * 查找功能基本实现，但是对查找结果点击之后的具体操作未做。
 */

public class Search4me extends Activity implements SearchView.OnQueryTextListener{
	
	private SearchView sv;
	ListView lv;

//	ArrayList<String> tempAllFromUser;
	
	ArrayList<String> tempAlltx;
	ArrayList<String> tempAllidentity;
	ArrayList<String> tempAllnickname;
	ArrayList<String> tempAllsch;
	ArrayList<String> tempAllnoclass;
	
	ArrayList<String> tempName;
	ArrayList<String> friName;
	
	SimpleAdapter sa ;
	ArrayList<String> Alltx = new ArrayList<String>();
	ArrayList<String> Allidentity = new ArrayList<String>();
	ArrayList<String> Allnickname = new ArrayList<String>();
	ArrayList<String> Allsch = new ArrayList<String>();
//	ArrayList<String> Allnoclass = new ArrayList<String>();
	ArrayList list = new ArrayList();
	ArrayList<ArrayList<String>> tempList = new ArrayList<ArrayList<String>>();
//	ArrayList<String> allUserListt;
//	ArrayList<String> allUserList = new ArrayList<String>();
	Context context;
	HttpEntity httpEntity;
	HttpResponse httpResponse;
	String resultName;
	
	String iGet;
	TextView sousuo;
	String djj;
	LinearLayout loadLL;
	
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			Bundle bundle = msg.getData();
			Allnickname = bundle.getStringArrayList("listAllFromUser");
			Allidentity = bundle.getStringArrayList("identity");
			Allsch = bundle.getStringArrayList("sch");
			Alltx = bundle.getStringArrayList("tx");
			list = bundle.getParcelableArrayList("noclass");
			
			friName = bundle.getStringArrayList("friends");
//			System.out.println(Allnickname);
//			System.out.println(Allsch);
//			System.out.println(Allidentity);
//			System.out.println(Alltx);
//			System.out.println(list);
			loadLL.setVisibility(View.GONE);
			lv.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,Allnickname));
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.search);
		
		loadLL = (LinearLayout)findViewById(R.id.loadLL);
		
		Intent intentt = getIntent();
		djj = intentt.getStringExtra("a");
		context = this;
		sousuo = (TextView)findViewById(R.id.pubBtnPhoto);
		sousuo.setVisibility(View.GONE);
//		Toast.makeText(Search4me.this, djj, Toast.LENGTH_SHORT).show();
		ImageView back2mainfromsearch = (ImageView)findViewById(R.id.back2mainfromsearch);
		back2mainfromsearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpGet httpGet = new HttpGet("http://zeng.shaoning.net/edusocial/User.json");
				HttpGet httpGet2 = new HttpGet("http://zeng.shaoning.net/edusocial/FriendShip.json");
				HttpClient httpClient = new DefaultHttpClient();
				Message msg2 = new Message();
				InputStream inputStream = null;
				InputStream inputStream2 = null;
				try {
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
//					System.out.println("1111" + result);
					
					JSONObject JO4UserJson = new JSONObject(result);
					JSONArray JA4UserShip = JO4UserJson.getJSONArray("User");
//					System.out.println(JA4UserShip.length());
//					tempAllFromUser = new ArrayList<String>();
					tempAllidentity = new ArrayList<String>();
					tempAllnickname = new ArrayList<String>();
					tempAllnoclass = new ArrayList<String>();
					tempAllsch = new ArrayList<String>();
					tempAlltx = new ArrayList<String>();
					tempList = new ArrayList<ArrayList<String>>();
//					allUserList = new ArrayList<String>();
					for (int i = 0; i < JA4UserShip.length(); i++) {
						JSONObject JO4SingleJA4UserShip = JA4UserShip.getJSONObject(i);
						if (JO4SingleJA4UserShip.getString("nickname").equals(djj)) {
							
						}else {
							tempAllnickname.add(JO4SingleJA4UserShip.getString("nickname"));
							tempAllidentity.add(JO4SingleJA4UserShip.getString("identity"));
							tempAlltx.add(JO4SingleJA4UserShip.getString("tx"));
							tempAllsch.add(JO4SingleJA4UserShip.getString("school"));
							JSONArray tempJA = JO4SingleJA4UserShip.getJSONArray("noclass");
							for (int j = 0; j < tempJA.length(); j++) {
								tempAllnoclass.add(tempJA.getString(j));
							}
							tempList.add(tempAllnoclass);
						}
					}
					
					httpResponse = httpClient.execute(httpGet2);
					httpEntity = httpResponse.getEntity();
					inputStream2 = httpEntity.getContent();
					//GB2312
					BufferedReader reader2 = new BufferedReader(new InputStreamReader(inputStream2,"GB2312"));
					String result2 = "";
					String line2 = "";
					while((line2 = reader2.readLine()) != null){
						result2 = result2 + line2;
					}
//					System.out.println("1111" + result2);
					
					JSONObject JO4FriendShipJson = new JSONObject(result2);
					JSONArray JA4FriendShip = JO4FriendShipJson.getJSONArray("FriendShip");
//					System.out.println(JA4FriendShip.length());

					tempName = new ArrayList<String>();
					for (int q = 0; q < JA4FriendShip.length(); q++) {
						JSONObject JO4SingleFriendShip = JA4FriendShip.getJSONObject(q);
						if (JO4SingleFriendShip.getString("PositiveU").equals(djj)) {
//							hisTX = JO4SingleFriendShip.getString("");
							tempName.add(JO4SingleFriendShip.getString("NegativeU"));
						}
					}
					
					
					Bundle bNor = new Bundle();
//					bNor.putStringArrayList("listName", tempName);
					bNor.putParcelableArrayList("noclass", (ArrayList<? extends Parcelable>) tempList);
					bNor.putStringArrayList("tx", tempAlltx);
					bNor.putStringArrayList("sch", tempAllsch);
					bNor.putStringArrayList("identity", tempAllidentity);
					bNor.putStringArrayList("listAllFromUser", tempAllnickname);
					bNor.putStringArrayList("friends", tempName);
//					System.out.println("YS 4 " + tempName);
//					bNor.putStringArrayList("Utx", arrUtx2);
					msg2.setData(bNor);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}finally{
					try {
						inputStream.close();
						inputStream2.close();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
				handler.sendMessage(msg2);
			}
		}.start();
		

		sv = (SearchView)findViewById(R.id.sv4search);
		lv = (ListView)findViewById(R.id.list_user);
    	lv.setTextFilterEnabled(true);
    	lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				//每一个学校点击之后记录，并返回前activity，
//				String resultC = (String)lv.getItemAtPosition(position);
//				Toast.makeText(getApplicationContext(), resultC, Toast.LENGTH_SHORT).show();
//				Intent intent = new Intent();
//				intent.putExtra("resultC", resultC);
//				Search4me.this.setResult(3, intent);
//				Search4me.this.finish();
				boolean isf = false;
				for (int i = 0; i < friName.size(); i++) {
					if (Allnickname.get(position).equals(friName.get(i))) {
						isf = true;
						break;
					}else {

					}
				}
				if (isf == false) {
					Intent intent = new Intent();
					intent.putExtra("nick", Allnickname.get(position));
					intent.putExtra("userTx", Alltx.get(position));
					intent.putExtra("from", "search");
					intent.setClass(Search4me.this, personalInfo.class);
					startActivity(intent);
				}else {
					Intent intent = new Intent();
					intent.putExtra("nick", Allnickname.get(position));
					intent.putExtra("userTx", Alltx.get(position));
					intent.putExtra("from", "fri");
					intent.setClass(Search4me.this, personalInfo.class);
					startActivity(intent);
				}

//				Intent intent = new Intent();
////				intent2perInfo.putExtra("nick", userNickName);
////				intent2perInfo.putExtra("userTx", userTx);
////				System.out.println("balabala " + userTx);
//				intent.putExtra("nick", Allnickname.get(position));
//				intent.putExtra("userTx", Alltx.get(position));
//				intent.putExtra("from", "search");
//				intent.setClass(Search4me.this, personalInfo.class);
//				startActivity(intent);
			}
		});
		
		
		
//		doMy.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				dp = new DatabasePart("jim/sjk2", "a1.db");
//				iGet = inputMy.getText().toString().trim();
//				Cursor cursor1 = dp.query(searchsql, new String[]{iGet});
//				cursor1.moveToFirst();
//				String my = cursor1.getString(0);
//				System.out.println(my);
//				if (cursor1.getString(0).equals(iGet)) {
//					showMy.setText(iGet);
//				}
//				else {
//					System.out.println("zz");
//				}
//				dp.DataClose();
//			}
//		});

		
		sv.setIconifiedByDefault(false);
		sv.setOnQueryTextListener(this);
		sv.setSubmitButtonEnabled(true);
		sv.setQueryHint("输入好友昵称");
		
		
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		//点击搜索后的操作
		  return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		  ArrayList<String> oklist = searchItem(newText);
		  updateLayout(oklist);
		  return false;
	}
	 public ArrayList<String> searchItem(String name) {
		  ArrayList<String> mSearchList = new ArrayList<String>();
		  for (int i = 0; i < Allnickname.size(); i++) {
		   int index = Allnickname.get(i).indexOf(name);
		   // 存在匹配的数据
		   if (index != -1) {
		    mSearchList.add(Allnickname.get(i));
		   }
		  }
		  return mSearchList;
		 }
	 public void updateLayout(ArrayList<String> obj) {
//		  listView.setAdapter(new ArrayAdapter<Object>(getApplicationContext(),
//		    android.R.layout.simple_expandable_list_item_1, obj));
			// = b4tc.getStringArrayList("target");
//			System.out.println(className);
//			mAdapter = new MyAdapter4ClassLIST(className, context);
			// ��Adapter

			//mAdapter
			lv.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,obj));
//			lv.setAdapter(mAdapter);
//	    	lv4schoollist.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,obj));
		 }
}

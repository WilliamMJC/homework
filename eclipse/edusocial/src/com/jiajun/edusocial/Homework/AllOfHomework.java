package com.jiajun.edusocial.Homework;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;


import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.jiajun.edusocial.R;
import com.jiajun.edusocial.R.id;
import com.jiajun.edusocial.R.layout;
import com.jiajun.edusocial.xlistview.XListView;
import com.jiajun.edusocial.xlistview.XListView.IXListViewListener;

//

public class AllOfHomework extends Activity implements IXListViewListener{
	
	private XListView xListView=null;
	SimpleAdapter sAdapter;
	private Handler mHandler;
	HttpEntity httpEntity;
	HttpResponse httpResponse;
	
	TextView publishHW;
	String s4UserNameInHW;
	String s4UserIdentityInHW;
	String s4UserSchInHW;
	ArrayList<String> al4UserClaInHW = new ArrayList<String>();
//	XListView xlistview4allHW;
//	String sql4HW;
//	Cursor cursor4GotClass;

	
	ArrayList<String> arrUnick = new ArrayList<String>();
	ArrayList<String> arrTitle = new ArrayList<String>();
	ArrayList<String> arrTime = new ArrayList<String>();
	ArrayList<String> arrDetail = new ArrayList<String>();
	ArrayList<String> arrType = new ArrayList<String>();
	ArrayList<String> arrNewsId = new ArrayList<String>();
	ArrayList<String> arrGood = new ArrayList<String>();
	ArrayList<String> arrCom = new ArrayList<String>();
	ArrayList<String> arrIfGood = new ArrayList<String>();
	ArrayList<String> arrIfCom = new ArrayList<String>();
	
	ArrayList<String> arrUnickT;
	ArrayList<String> arrTitleT;
	ArrayList<String> arrTimeT;
	ArrayList<String> arrDetailT;
	ArrayList<String> arrTypeT;
	ArrayList<String> arrNewsIdT;
	ArrayList<String> arrGoodT;
	ArrayList<String> arrComT;
	ArrayList<String> arrIfGoodT;
	ArrayList<String> arrIfComT;
	
	ArrayList<String> arrCMID = new ArrayList<String>();
	ArrayList<String> arrCMNEWSID = new ArrayList<String>();
	ArrayList<String> arrCMP = new ArrayList<String>();
	ArrayList<String> arrCMN  = new ArrayList<String>();
	ArrayList<String> arrCMTIME = new ArrayList<String>();
	ArrayList<String> arrCMCONTENT = new ArrayList<String>();

	ArrayList<String> arrCMIDt;
	ArrayList<String> arrCMNEWSIDt;
	ArrayList<String> arrCMPt;
	ArrayList<String> arrCMNt;
	ArrayList<String> arrCMTIMEt;
	ArrayList<String> arrCMCONTENTt;
	
	ArrayList<String> classS;
	LinearLayout loadLL;
	
	String sql4gotHW;
	
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			Bundle bd = msg.getData();
			arrDetail = bd.getStringArrayList("arrDetail");
			arrNewsId = bd.getStringArrayList("arrNewsId");
//			System.out.println(arrNewsId);
			arrTime = bd.getStringArrayList("arrTime");
			arrTitle = bd.getStringArrayList("arrTitle");
			arrType = bd.getStringArrayList("arrType");
			arrUnick = bd.getStringArrayList("arrUnick");
			
			arrIfGood = bd.getStringArrayList("arrIfGood");
			arrIfCom = bd.getStringArrayList("arrIfCm");
			
			arrCMCONTENT = bd.getStringArrayList("cmcontent");
			arrCMID = bd.getStringArrayList("cmid");
			arrCMN = bd.getStringArrayList("cmnega");
			arrCMP = bd.getStringArrayList("cmpos");
			arrCMTIME = bd.getStringArrayList("cmtime");
			arrCMNEWSID = bd.getStringArrayList("cmnewsid");
			
			arrCom = bd.getStringArrayList("arrCommm");
			arrGood = bd.getStringArrayList("arrGood");
			
			if (arrNewsId.size() == 0) {
				xListView.setVisibility(View.GONE);
			}

			GetHwList();
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.all_of_homework);
		
		loadLL = (LinearLayout)findViewById(R.id.loadLL);

		
		
		Intent intent1 = getIntent();
		Bundle bundle1 = intent1.getExtras();
		s4UserNameInHW = bundle1.getString("userNameF2");
		s4UserIdentityInHW = bundle1.getString("userIdentityF2");
		al4UserClaInHW = bundle1.getStringArrayList("userClaArrayF2");
//		System.out.println("a ? " + al4UserClaInHW);
		s4UserSchInHW = bundle1.getString("userSchF2");
		
		ImageView back2function = (ImageView)findViewById(R.id.back2function);
		//如何取出传入的用户账号和身份？
		
		publishHW = (TextView)findViewById(R.id.publishHW);
		if (s4UserIdentityInHW.equals("teacher")) {
			
			
			
		}else {
			
			publishHW.setVisibility(View.GONE);
		}


		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpGet httpGet = new HttpGet("http://zeng.shaoning.net/edusocial/News.json");
				HttpGet httpGet2 = new HttpGet("http://zeng.shaoning.net/edusocial/comment.json");
				
				HttpClient httpClient = new DefaultHttpClient();
				Message msg2 = new Message();
				//Looper.loop();
				InputStream inputStream = null;
				InputStream inputStream2 = null;
				try {
					//zheli 这里
					arrDetailT = new ArrayList<String>();
					arrNewsIdT = new ArrayList<String>();
					arrTimeT = new ArrayList<String>();
					arrTitleT = new ArrayList<String>();
					arrUnickT = new ArrayList<String>();
					arrTypeT = new ArrayList<String>();
					arrIfGoodT = new ArrayList<String>();
					arrGoodT = new ArrayList<String>();
					arrIfComT = new ArrayList<String>();
					arrComT = new ArrayList<String>();
					
					arrCMCONTENTt = new ArrayList<String>();
					arrCMIDt = new ArrayList<String>();
					arrCMNt = new ArrayList<String>();
					arrCMPt = new ArrayList<String>();
					arrCMTIMEt = new ArrayList<String>();
					arrCMNEWSIDt = new ArrayList<String>();
					
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
					
					
					JSONObject JO4NewsJson = new JSONObject(result);
					JSONArray JA4News = JO4NewsJson.getJSONArray("News");
//					System.out.println(JA4News.length());

					//
					
					for (int q = 0; q < JA4News.length(); q++) {
						JSONObject JO4SingleNews = JA4News.getJSONObject(q);
						String thisIG = JO4SingleNews.getString("ifgood");
						String thisIC = JO4SingleNews.getString("ifcomment");
						if (JO4SingleNews.getString("newstype").equals("hw")) {
//							System.out.println("yes");
//							if (JO4SingleNews.getString("negausernick").equals(s4UserNameInHW) ||
//									JO4SingleNews.getString("negausernick").equals(s4UserSchInHW)) {
//								arrDetailT.add(JO4SingleNews.getString("detail"));
//								arrNewsIdT.add(JO4SingleNews.getString("newsid"));
//								arrTimeT.add(JO4SingleNews.getString("time"));
//								arrTitleT.add(JO4SingleNews.getString("title"));
//								arrTypeT.add(JO4SingleNews.getString("newstype"));
//								arrUnickT.add(JO4SingleNews.getString("usernick"));
//								
////								hisTX = JO4SingleNews.getString("");
//								hisIdentity = JO4SingleNews.getString("identity");
//								hisNickName = JO4SingleNews.getString("nickname");
//								hisSchool = JO4SingleNews.getString("school");
//								JSONArray JA4Cla = JO4SingleNews.getJSONArray("noclass");
////								String tempString = ""; 
//								hisNoClassListInRun = new ArrayList<String>();
//								for (int i = 0; i < JA4Cla.length(); i++) {
//									hisNoClassListInRun.add(JA4Cla.getString(i));
//								}

//							}else {
//							System.out.println(s4UserSchInHW+" have ?");
//							System.out.println(al4UserClaInHW+" have ?");
								for (int i = 0; i < al4UserClaInHW.size(); i++) {
									if (JO4SingleNews.getString("negausernick").equals(s4UserSchInHW + al4UserClaInHW.get(i))) {
										arrDetailT.add(JO4SingleNews.getString("detail"));
										arrNewsIdT.add(JO4SingleNews.getString("newsid"));
										arrTimeT.add(JO4SingleNews.getString("time"));
										arrTitleT.add(JO4SingleNews.getString("title"));
										arrTypeT.add(JO4SingleNews.getString("newstype"));
										arrUnickT.add(JO4SingleNews.getString("usernick"));
										arrIfGoodT.add(JO4SingleNews.getString("ifgood"));
										arrIfComT.add(JO4SingleNews.getString("ifcomment"));
										if (thisIG.equals("true")) {

											arrGoodT.add(JO4SingleNews.getString("whogood"));
										}else if(thisIG.equals("false")){
											arrGoodT.add("");

										}
//										String thisIC = JO4SingleNews.getString("ifcomment");
										if (thisIC.equals("true")) {
//											JSONArray JA4cmid = JO4SingleNews.getJSONArray("newscmid");
											arrComT.add(JO4SingleNews.getString("newscmid"));
										}else  if(thisIC.equals("false")){
											arrComT.add("");
										}
									}
								}
//							}
						}else {
							
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
//					System.out.println("44444" + result2);
					
					JSONObject JO4ComJson = new JSONObject(result2);
					JSONArray JA4Com = JO4ComJson.getJSONArray("comment");
					for (int y = 0; y < JA4Com.length(); y++) {
						JSONObject JO4singleCom = JA4Com.getJSONObject(y);
						arrCMNEWSIDt.add(JO4singleCom.getString("cmnewsid"));
						arrCMIDt.add(JO4singleCom.getString("cmid"));
						arrCMTIMEt.add(JO4singleCom.getString("cmTime"));
						arrCMCONTENTt.add(JO4singleCom.getString("cmContent"));
						arrCMNt.add(JO4singleCom.getString("cmN"));
						arrCMPt.add(JO4singleCom.getString("cmP"));
					}
					

					Bundle bNor = new Bundle();
					bNor.putStringArrayList("arrUnick", arrUnickT);
					bNor.putStringArrayList("arrType", arrTypeT);
					bNor.putStringArrayList("arrTitle", arrTitleT);
					bNor.putStringArrayList("arrTime", arrTimeT);
					bNor.putStringArrayList("arrNewsId", arrNewsIdT);
					bNor.putStringArrayList("arrDetail", arrDetailT);
					
					bNor.putStringArrayList("arrGood",  arrGoodT);
					bNor.putStringArrayList("arrIfGood", arrIfGoodT);
					
					bNor.putStringArrayList("arrIfCm", arrIfComT);
					bNor.putStringArrayList("arrCommm", arrComT);
					
					bNor.putStringArrayList("cmid", arrCMIDt);
					bNor.putStringArrayList("cmnewsid", arrCMNEWSIDt);
					bNor.putStringArrayList("cmnega", arrCMNt);
					bNor.putStringArrayList("cmpos", arrCMPt);
					bNor.putStringArrayList("cmtime", arrCMTIMEt);
					bNor.putStringArrayList("cmcontent", arrCMCONTENTt);
					
					msg2.setData(bNor);


				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					try{//这里zheli
						inputStream.close();
						inputStream2.close();
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}					
				handler.sendMessage(msg2);
			}
		}.start();
		
		xListView=(XListView) findViewById(R.id.xListView);
		xListView.setPullLoadEnable(true);
//		xListView.setAdapter(sAdapter);
		xListView.setXListViewListener(this);
		
//		GetHwList();


		mHandler = new Handler();

		back2function.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		xListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
//				String thisTitle = arrTitle.get(position);
//				String sql4detail = "select detail from News where title = ?";
//				
//				Cursor c1 = dp.query(sql4detail, new String[]{sql4detail});
//				String thisDetail = c1.getString(0);
////				String thisDetail = arrDetail.get(position);
				
//				String thisTitle = arrTitle.get(position);
//				String GotClickNewsID = "select news_id from news where title = ?";
//				dp = new DatabasePart("jim/sjk", "a1.db");
//				Cursor c1  = dp.query(GotClickNewsID, new String[]{thisTitle});
//				ArrayList<String> newid = new ArrayList<String>();
//				for (c1.moveToFirst();!c1.isAfterLast();c1.moveToNext()) {
//					newid.add(c1.getString(c1.getColumnIndex("news_id")));
//				}
//				dp.DataClose();
				String newid = arrNewsId.get(position-1);
				Intent intent2HWdetail = new Intent();
				intent2HWdetail.setClass(AllOfHomework.this, HomeworkDetail.class);
//				intent2HWdetail.putExtra("Title", thisTitle);
//				intent2HWdetail.putExtra("Detail", thisDetail);
				intent2HWdetail.putExtra("newid", newid);
				intent2HWdetail.putExtra("whoAmI", s4UserNameInHW);
				intent2HWdetail.putExtra("type", arrType.get(position-1));
				
				intent2HWdetail.putExtra("ifgood", arrIfGood.get(position-1));
				intent2HWdetail.putExtra("title", arrTitle.get(position-1));
				intent2HWdetail.putExtra("detail", arrDetail.get(position-1));
				intent2HWdetail.putExtra("pubuser", arrUnick.get(position-1));
				intent2HWdetail.putExtra("time", arrTime.get(position-1));
				
				String s3 = arrCom.get(position-1);
				
			    String [] temp = null;  
			    temp = s3.split("-");  
				
			    if (arrIfGood.get(position-1).equals("true")) {
			    	intent2HWdetail.putExtra("whogood", arrGood.get(position-1));
				}else {
					intent2HWdetail.putExtra("whogood", "");
				}
			    System.out.println("outside " + arrIfCom.get(position-1).equals("true"));
				if (arrIfCom.get(position-1).equals("true")) {
					intent2HWdetail.putExtra("ifHave", "true");
					ArrayList<String> cmidL = new ArrayList<String>();
					ArrayList<String> posL = new ArrayList<String>();
					ArrayList<String> negaL = new ArrayList<String>();
					ArrayList<String> timeL = new ArrayList<String>();
					ArrayList<String> contentL = new ArrayList<String>();
					System.out.println("is true de hua " + arrIfCom.get(position-1).equals("true"));
					System.out.println(arrCMID.size());
					System.out.println(temp.length);
					for (int q = 0; q < arrCMID.size(); q++) {
						for (int r = 0; r < temp.length; r++) {
//							System.out.println(arrCMID.get(position-1));
//							cmidL.add(arrCMID.get(position-1));
							if (arrCMID.get(q).equals(temp[r])) {
								System.out.println(arrCMID.get(q));
								System.out.println(temp[r]);
								System.out.println("inside" + arrIfCom.get(position-1).equals("true"));
								cmidL.add(arrCMID.get(q));
								System.out.println(arrCMID.get(q));
								posL.add(arrCMP.get(q));
								System.out.println(arrCMP.get(q));
								negaL.add(arrCMN.get(q));
								System.out.println(arrCMN.get(q));
								timeL.add(arrCMTIME.get(q));
								System.out.println(arrCMTIME.get(q));
								contentL.add(arrCMCONTENT.get(q));
								System.out.println(arrCMCONTENT.get(q));
								
								break;
							}
						}
					}
//					intent2detail.putStringArrayListExtra("cmmarrCom",cm);
					intent2HWdetail.putStringArrayListExtra("cmmid", cmidL);
					intent2HWdetail.putStringArrayListExtra("cmmpos", posL);
					intent2HWdetail.putStringArrayListExtra("cmmnega", negaL);
					intent2HWdetail.putStringArrayListExtra("cmmtime", timeL);
					intent2HWdetail.putStringArrayListExtra("cmmcontent", contentL);
//					intent2detail.putStringArrayListExtra("cmmnewsid", n);
					
				}else {
					intent2HWdetail.putExtra("ifHave", "false");
				}

				startActivity(intent2HWdetail);

			}
		});
		publishHW.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent2pubHW = new Intent();
				//需要传入发布作业的老师的账号吗？
				intent2pubHW.setClass(AllOfHomework.this, pubHW.class);
//				intent2pubHW.putExtra("classA", classArray);
				intent2pubHW.putExtra("userN", s4UserNameInHW);
				startActivity(intent2pubHW);
			}
		});
	};
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		//每次这个页面被置顶都在此刷新listview？
//		System.out.println("aSun");
//		arrDetail.clear();
//		arrTime.clear();
//		arrTitle.clear();
//		arrType.clear();
//		arrUnick.clear();
//		arrNewsId.clear();
		GetHwList();
		
		super.onResume();
	}
	public void GetHwList(){
		
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
		
		if (arrNewsId.size() == 0) {
////			xListView.removeAllViews()
//			xListView.setVisibility(View.GONE);
		}else {
			for (int n = 0; n < arrNewsId.size(); n++) {
				HashMap<String, Object> hm = new HashMap<String, Object>();
				hm.put("title", arrTitle.get(n));

				hm.put("time", arrTime.get(n));
				hm.put("user", arrUnick.get(n));
				hm.put("type", arrType.get(n));
				data.add(hm);
			}
			loadLL.setVisibility(View.GONE);
			sAdapter = new SimpleAdapter(this, data, R.layout.xlistview_item, 
				new String [] {"title","time","user","type"},
				new  int [] {R.id.tv4title,R.id.tv4time,R.id.tv4user,R.id.tv4type});
			sAdapter.notifyDataSetChanged();
			xListView.setAdapter(sAdapter);
		}
	}
	private void onLoad() {
		xListView.stopRefresh();
		xListView.stopLoadMore();
		xListView.setRefreshTime("刚刚");
	}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
//				System.out.println("refresh");
//				start = ++refreshCnt;
//				items.clear();
//				arrDetail.clear();
//				arrTime.clear();
//				arrTitle.clear();
//				arrType.clear();
//				arrUnick.clear();
//				arrNewsId.clear();
//				
				GetHwList();

//				mAdapter = new ArrayAdapter<String>(fragmentJAVA1.this.getActivity(), R.layout.xlistview_item, items);
//				xListView.setAdapter(sAdapter);
				onLoad();
			}
		}, 2000);
	}
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
//				System.out.println("onLoadMore");
				GetHwList();

//				sAdapter.notifyDataSetChanged();
//				xListView.setAdapter(sAdapter);
				onLoad();
			}
		}, 2000);
	}
}

package com.jiajun.edusocial.Inform;

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
import com.jiajun.edusocial.Homework.HomeworkDetail;
import com.jiajun.edusocial.R.id;
import com.jiajun.edusocial.R.layout;
import com.jiajun.edusocial.xlistview.XListView;
import com.jiajun.edusocial.xlistview.XListView.IXListViewListener;

//

public class AllOfInform extends Activity implements IXListViewListener{
	
	TextView publishInform;
	String s4UserIdentityInInform ;
	String s4UserNameInInform;
	String s4UserSchInInform;
	ArrayList<String> al4UserClaInInform = new ArrayList<String>();
	
	String sql4allInform;
	String sql4GotSchClass;

	SimpleAdapter sAdapter;
	LinearLayout loadLL;
	
	HttpEntity httpEntity;
	HttpResponse httpResponse;
	
	ArrayList<String> arrUnick = new ArrayList<String>();
	ArrayList<String> arrTitle = new ArrayList<String>();
	ArrayList<String> arrTime = new ArrayList<String>();
	ArrayList<String> arrDetail = new ArrayList<String>();
	ArrayList<String> arrType = new ArrayList<String>();
	ArrayList<String> arrNewsId = new ArrayList<String>();
	
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

	ArrayList<String> arrGood = new ArrayList<String>();
	ArrayList<String> arrCom = new ArrayList<String>();
	ArrayList<String> arrIfGood = new ArrayList<String>();
	ArrayList<String> arrIfCom = new ArrayList<String>();

	
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

	
	String [] classArray;
	
	String  sql4gotInform;
	
	private Handler mHandler;
	XListView xListView;
	
	
	
	
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			Bundle bd = msg.getData();
			arrDetail = bd.getStringArrayList("arrDetail");
			arrNewsId = bd.getStringArrayList("arrNewsId");
			System.out.println("a haha? + " + arrNewsId);
			arrTime = bd.getStringArrayList("arrTime");
			System.out.println("a haha? + " + arrTime);
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
			System.out.println("wo bu ZD " + arrCom);
//			arrGood = bd.getStringArrayList("arrGood");

			
			
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
		setContentView(R.layout.all_of_inform);
		
		publishInform = (TextView)findViewById(R.id.publishInform);
		
		xListView = (XListView)findViewById(R.id.xListView);
		loadLL = (LinearLayout)findViewById(R.id.loadLL);
		
		Intent intent1 = getIntent();
		Bundle bundle1 = intent1.getExtras();
		s4UserIdentityInInform = bundle1.getString("userIdentityF2");
		s4UserNameInInform = bundle1.getString("userNameF2");
		al4UserClaInInform = bundle1.getStringArrayList("userClaArrayF2");
		s4UserSchInInform = bundle1.getString("userSchF2");
		
		
		ImageView back2function2 = (ImageView)findViewById(R.id.back2function2);
		
		
		
		if (s4UserIdentityInInform.equals("teacher")) {
			
		}else {
			publishInform.setVisibility(View.GONE);
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
					
					
					JSONObject JO4NewsJson = new JSONObject(result);
					JSONArray JA4News = JO4NewsJson.getJSONArray("News");
//					System.out.println(JA4News.length());

					//
					for (int q = 0; q < JA4News.length(); q++) {
						
						JSONObject JO4SingleNews = JA4News.getJSONObject(q);
						String thisIC = JO4SingleNews.getString("ifcomment");
						if (JO4SingleNews.getString("newstype").equals("inform")) {
							if (JO4SingleNews.getString("negausernick").equals(s4UserNameInInform) ||
									JO4SingleNews.getString("negausernick").equals(s4UserSchInInform)) {
								arrDetailT.add(JO4SingleNews.getString("detail"));
								arrNewsIdT.add(JO4SingleNews.getString("newsid"));
								arrTimeT.add(JO4SingleNews.getString("time"));
								arrTitleT.add(JO4SingleNews.getString("title"));
								arrTypeT.add(JO4SingleNews.getString("newstype"));
								arrUnickT.add(JO4SingleNews.getString("usernick"));
								arrIfComT.add(JO4SingleNews.getString("ifcomment"));
								if (thisIC.equals("true")) {
//									JSONArray JA4cmid = JO4SingleNews.getJSONArray("newscmid");
									arrComT.add(JO4SingleNews.getString("newscmid"));
								}else  if(thisIC.equals("false")){
									arrComT.add("");
								}
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

							}else {
								for (int i = 0; i < al4UserClaInInform.size(); i++) {
									if (JO4SingleNews.getString("negausernick").equals(s4UserSchInInform + al4UserClaInInform.get(i))) {
										arrDetailT.add(JO4SingleNews.getString("detail"));
										arrNewsIdT.add(JO4SingleNews.getString("newsid"));
										arrTimeT.add(JO4SingleNews.getString("time"));
										arrTitleT.add(JO4SingleNews.getString("title"));
										arrTypeT.add(JO4SingleNews.getString("newstype"));
										arrUnickT.add(JO4SingleNews.getString("usernick"));
										arrIfComT.add(JO4SingleNews.getString("ifcomment"));
										
										if (thisIC.equals("true")) {
//											JSONArray JA4cmid = JO4SingleNews.getJSONArray("newscmid");
											arrComT.add(JO4SingleNews.getString("newscmid"));
										}else  if(thisIC.equals("false")){
											arrComT.add("");
										}
										System.out.println(arrNewsIdT);
										System.out.println(arrIfComT);
										System.out.println(arrComT);
									}
								}
							}
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
					System.out.println(arrNewsIdT);
					bNor.putStringArrayList("arrDetail", arrDetailT);
					bNor.putStringArrayList("arrGood",  arrGoodT);
					bNor.putStringArrayList("arrIfGood", arrIfGoodT);
					
					bNor.putStringArrayList("arrIfCm", arrIfComT);
					System.out.println("wo bu ZD before" + arrComT);
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
		//412zheli
//		GetHwList();


		mHandler = new Handler();

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
				String thisNewsId = arrNewsId.get(position-1);
				
//				String thisTitle = arrTitle.get(position);
//				String GotClickNewsID = "select news_id from news where title = ?";
//				dp = new DatabasePart("jim/sjk", "a1.db");
//				Cursor c1  = dp.query(GotClickNewsID, new String[]{thisTitle});
//				ArrayList<String> newid = new ArrayList<String>();
//				for (c1.moveToFirst();!c1.isAfterLast();c1.moveToNext()) {
//					newid.add(c1.getString(c1.getColumnIndex("news_id")));
//				}
//				dp.DataClose();
				Intent intent2Informdetail = new Intent();
				intent2Informdetail.setClass(AllOfInform.this, HomeworkDetail.class);
				intent2Informdetail.putExtra("type", arrType.get(position-1));
				intent2Informdetail.putExtra("newid", thisNewsId);
				intent2Informdetail.putExtra("whoAmI", s4UserNameInInform);
//				intent2Informdetail.putExtra("Title", thisTitle);
//				intent2Informdetail.putExtra("Detail", thisDetail);

				intent2Informdetail.putExtra("ifgood", "false");
				intent2Informdetail.putExtra("title", arrTitle.get(position-1));
				intent2Informdetail.putExtra("detail", arrDetail.get(position-1));
				intent2Informdetail.putExtra("pubuser", arrUnick.get(position-1));
				intent2Informdetail.putExtra("time", arrTime.get(position-1));
//				System.out.println("bu ZD" + arrCom);
				String s3 = arrCom.get(position-1);
				
			    String [] temp = null;  
			    temp = s3.split("-");  
				
//			    if (arrIfGoodT.get(position-1).equals("true")) {
//			    	intent2Informdetail.putExtra("whogood", arrGoodT.get(position-1));
//				}else {
					intent2Informdetail.putExtra("whogood", "");
//				}
//			    System.out.println("outside " + arrIfCom.get(position-1).equals("true"));
					System.out.println("position " + position);
					System.out.println(arrIfCom);
				if (arrIfCom.get(position-1).equals("true")) {
					intent2Informdetail.putExtra("ifHave", "true");
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
//								System.out.println(arrCMID.get(q));
//								System.out.println(temp[r]);
//								System.out.println("inside" + arrIfCom.get(position-1).equals("true"));
								cmidL.add(arrCMID.get(q));
//								System.out.println(arrCMID.get(q));
								posL.add(arrCMP.get(q));
//								System.out.println(arrCMP.get(q));
								negaL.add(arrCMN.get(q));
//								System.out.println(arrCMN.get(q));
								timeL.add(arrCMTIME.get(q));
//								System.out.println(arrTime.get(q));
								contentL.add(arrCMCONTENT.get(q));
//								System.out.println(arrCMCONTENT.get(q));
								
								break;
							}
						}
					}
//					intent2detail.putStringArrayListExtra("cmmarrCom",cm);
					intent2Informdetail.putStringArrayListExtra("cmmid", cmidL);
					intent2Informdetail.putStringArrayListExtra("cmmpos", posL);
					intent2Informdetail.putStringArrayListExtra("cmmnega", negaL);
					intent2Informdetail.putStringArrayListExtra("cmmtime", timeL);
					intent2Informdetail.putStringArrayListExtra("cmmcontent", contentL);
//					intent2detail.putStringArrayListExtra("cmmnewsid", n);
					
				}else {
					intent2Informdetail.putExtra("ifHave", "false");
				}

				startActivity(intent2Informdetail);


			}
		});
		
		
//		if (s4UserIdentityInInform.equals("teacher")) {
//			publishInform.setVisibility(0);
//		}else {
//			publishInform.setVisibility(2);
//		}
		
		
		
		back2function2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		publishInform.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent2pubInform = new Intent();
				//需要传入发布通知老师的账号吗？
				intent2pubInform.setClass(AllOfInform.this, pubInform.class);
//				intent2pubInform.putExtra("classA", classArray);
				intent2pubInform.putExtra("userN", s4UserNameInInform);
				startActivity(intent2pubInform);
			}
		});
		
	};
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
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
//			for(cursorInform1.moveToFirst();!cursorInform1.isAfterLast();cursorInform1.moveToNext())
//	        {
//				arrDetail.add(cursorInform1.getString(cursorInform1.getColumnIndex("detail")));
//				arrUnick.add(cursorInform1.getString(cursorInform1.getColumnIndex("userid")));
//				arrTitle.add(cursorInform1.getString(cursorInform1.getColumnIndex("title")));
//				arrTime.add(cursorInform1.getString(cursorInform1.getColumnIndex("time")));
//				arrType.add(cursorInform1.getString(cursorInform1.getColumnIndex("type")));
//				arrNewsId.add(cursorInform1.getString(cursorInform1.getColumnIndex("news_id")));
//				
//				HashMap<String, Object> hm = new HashMap<String, Object>();
//				hm.put("title", arrTitle.get(cursorInform1.getPosition()));
//
//				hm.put("time", arrTime.get(cursorInform1.getPosition()));
//				hm.put("user", arrUnick.get(cursorInform1.getPosition()));
//				hm.put("type", arrType.get(cursorInform1.getPosition()));
//				data.add(hm);
//	        }
			for (int n = 0; n < arrNewsId.size(); n++) {
				HashMap<String, Object> hm = new HashMap<String, Object>();
				hm.put("title", arrTitle.get(n));

				hm.put("time", arrTime.get(n));
				hm.put("user", arrUnick.get(n));
				hm.put("type", arrType.get(n));
				data.add(hm);
			}
			sAdapter = new SimpleAdapter(this, data, R.layout.xlistview_item, 
				new String [] {"title","time","user","type"},
				new  int [] {R.id.tv4title,R.id.tv4time,R.id.tv4user,R.id.tv4type});
			loadLL.setVisibility(View.GONE);
			sAdapter.notifyDataSetChanged();
			xListView.setAdapter(sAdapter);
		}
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
	private void onLoad() {
		xListView.stopRefresh();
		xListView.stopLoadMore();
		xListView.setRefreshTime("刚刚");
	}
}


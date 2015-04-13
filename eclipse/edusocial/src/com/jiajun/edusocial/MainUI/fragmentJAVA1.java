package com.jiajun.edusocial.MainUI;




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

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jiajun.edusocial.HomeworkDetail;
import com.jiajun.edusocial.R;
import com.jiajun.edusocial.xlistview.XListView;
import com.jiajun.edusocial.xlistview.XListView.IXListViewListener;




//设计好news.json文件，并且设计好
public class fragmentJAVA1 extends Fragment implements IXListViewListener{
	View view;

	private XListView xListView=null;

	SimpleAdapter sAdapter;

	private Handler mHandler;
	HttpEntity httpEntity;
	HttpResponse httpResponse;
	LinearLayout ll4req;
	LinearLayout loadLL;
	
	ArrayList<String> arrUnick = new ArrayList<String>();
	ArrayList<String> arrTitle = new ArrayList<String>();
	ArrayList<String> arrTime = new ArrayList<String>();
	ArrayList<String> arrDetail = new ArrayList<String>();
	ArrayList<String> arrType = new ArrayList<String>();
	ArrayList<String> arrNewsIdn = new ArrayList<String>();
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
	
	ArrayList<String> arrUnickT;
	ArrayList<String> arrTitleT;
	ArrayList<String> arrTimeT;
	ArrayList<String> arrDetailT;
	ArrayList<String> arrTypeT;
	ArrayList<String> arrNewsIdTn;
	ArrayList<String> arrGoodT;
	ArrayList<String> arrComT;
	ArrayList<String> arrIfGoodT;
	ArrayList<String> arrIfComT;
	
	ArrayList list = new ArrayList();
//	ArrayList list2 = new ArrayList();
	 
	
	TextView tv1;
	String Oschool;
	String Oclass;
	int OclassIndex;
	int OschoolIndex;
	int reqCount;
	int index;

	String newid;
	
	String userNickName = "";
	String userSch = "";
	String userIdentity ="";
	ArrayList<String> userClaArray = new ArrayList<String>();
	
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			Bundle bd = msg.getData();
			arrDetail = bd.getStringArrayList("arrDetail");
			arrNewsIdn = bd.getStringArrayList("arrNewsId");
//			System.out.println(arrNewsId);
			arrTime = bd.getStringArrayList("arrTime");
			arrTitle = bd.getStringArrayList("arrTitle");
			arrType = bd.getStringArrayList("arrType");
			arrUnick = bd.getStringArrayList("arrUnick");
//			list = bd.getParcelableArrayList("arrGood");
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
//			System.out.println("en en en " +arrCom);
			if (arrNewsIdn.size() == 0) {
				xListView.setVisibility(View.GONE);
				Toast.makeText(getActivity(), "网络不可用，请开启网络再重新启动软件！", Toast.LENGTH_LONG).show();
				try {
			        Thread.sleep(2000);//括号里面的5000代表5000毫秒，也就是5秒，可以该成你需要的时间
				} catch (InterruptedException e) {
				        e.printStackTrace();
				}
				getActivity().finish();
			}else {
				DropDownGet();
			}
			
		}
	};

	static fragmentJAVA1 newInstance() {
		fragmentJAVA1 newFragment = new fragmentJAVA1();
        return newFragment;

    }
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment1, container, false);

		
		loadLL = (LinearLayout)view.findViewById(R.id.loadLL);
		Intent intent1 = getActivity().getIntent();
		Bundle bundle = intent1.getExtras();
		userNickName = bundle.getString("userName");
		//在此获取该用户的学校，班级喔；
		userSch = bundle.getString("userSch");
		userClaArray = bundle.getStringArrayList("userClaArray");

		//读取消息的数据
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
					arrNewsIdTn = new ArrayList<String>();
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

//					for (int t = 0; t < JA4News.length(); t++) {
//						System.out.println(JA4News.getJSONObject(t).getString("newscmid"));
//					}
					//
					for (int q = 0; q < JA4News.length(); q++) {
						JSONObject JO4SingleNews = JA4News.getJSONObject(q);
						String thisIG = JO4SingleNews.getString("ifgood");
						String thisIC = JO4SingleNews.getString("ifcomment");
						if (JO4SingleNews.getString("newstype").equals("req") && JO4SingleNews.getString("negausernick").equals(userNickName)) {
							arrDetailT.add(JO4SingleNews.getString("detail"));
							arrNewsIdTn.add(JO4SingleNews.getString("newsid"));
							arrTimeT.add(JO4SingleNews.getString("time"));
							arrTitleT.add(JO4SingleNews.getString("title"));
							arrTypeT.add(JO4SingleNews.getString("newstype"));
							arrUnickT.add(JO4SingleNews.getString("usernick"));
							arrIfGoodT.add(JO4SingleNews.getString("ifgood"));
							arrIfComT.add(JO4SingleNews.getString("ifcomment"));
							arrComT.add("");
							arrGoodT.add("");
							continue;
						}else {
							for (int i = 0; i < userClaArray.size(); i++) {
								if (JO4SingleNews.getString("negausernick").equals(userSch + userClaArray.get(i))) {
									arrDetailT.add(JO4SingleNews.getString("detail"));
									arrNewsIdTn.add(JO4SingleNews.getString("newsid"));
									arrTimeT.add(JO4SingleNews.getString("time"));
									arrTitleT.add(JO4SingleNews.getString("title"));
									arrTypeT.add(JO4SingleNews.getString("newstype"));
									arrUnickT.add(JO4SingleNews.getString("usernick"));
									arrIfGoodT.add(JO4SingleNews.getString("ifgood"));
									arrIfComT.add(JO4SingleNews.getString("ifcomment"));


//									System.out.println();
									if (thisIG.equals("true")) {

										arrGoodT.add(JO4SingleNews.getString("whogood"));
									}else if(thisIG.equals("false")){
										arrGoodT.add("");

									}
//									String thisIC = JO4SingleNews.getString("ifcomment");
									if (thisIC.equals("true")) {
//										JSONArray JA4cmid = JO4SingleNews.getJSONArray("newscmid");
										arrComT.add(JO4SingleNews.getString("newscmid"));
									}else  if(thisIC.equals("false")){
										arrComT.add("");
									}
//									arrGoodT.add(JO4SingleNews.getString("whogood"));
									break;
								}else {
									
								}
							}
							if (JO4SingleNews.getString("negausernick").equals(userNickName) ||
									JO4SingleNews.getString("negausernick").equals(userSch)) {
								arrDetailT.add(JO4SingleNews.getString("detail"));
								arrNewsIdTn.add(JO4SingleNews.getString("newsid"));
								arrTimeT.add(JO4SingleNews.getString("time"));
								arrTitleT.add(JO4SingleNews.getString("title"));
								arrTypeT.add(JO4SingleNews.getString("newstype"));
								arrUnickT.add(JO4SingleNews.getString("usernick"));
								arrIfGoodT.add(JO4SingleNews.getString("ifgood"));
								arrIfComT.add(JO4SingleNews.getString("ifcomment"));
//								ArrayList<String> tempgood = new ArrayList<String>();
//								String thisIG = JO4SingleNews.getString("ifgood");
//								System.out.println(JO4SingleNews.getString("newsid") + thisIG);

								if (thisIG.equals("true")) {
//									System.out.println("ba");
//									ArrayList<String> tempgood = new ArrayList<String>();
//									JSONArray JA4goodman = JO4SingleNews.getJSONArray("whogood");
//									for (int j = 0; j < JA4goodman.length(); j++) {
//										tempgood.add(JA4goodman.getString(j));
//										System.out.println("good de " + JA4goodman.getString(j));
//										System.out.println(JO4SingleNews.getString("newsid"));
//									}
									arrGoodT.add(JO4SingleNews.getString("whogood"));
								}else if (thisIG.equals("false")) {
//									System.out.println("ab");
									arrGoodT.add("");
//									System.out.println("a ha ?");
//									System.out.println(JO4SingleNews.getString("whogood"));
								}

//								String thisIC = JO4SingleNews.getString("ifcomment");
								if (thisIC.equals("true")){
//									ArrayList<String> tempcom = new ArrayList<String>();
//									JSONArray JA4cmid = JO4SingleNews.getJSONArray("newscmid");
//									for (int u = 0; u < JA4cmid.length(); u++) {
//										tempcom.add(JA4cmid.getString(u));
//										System.out.println("cm de " + JA4cmid.getString(u));
//										System.out.println(JO4SingleNews.getString("newsid"));
//									}
									arrComT.add(JO4SingleNews.getString("newscmid"));
								}else if (thisIC.equals("false")) {
									arrComT.add("");
//									System.out.println("a ha 2?");
//									System.out.println(JO4SingleNews.getString("newsid"));
								}
							}
						}
					}
					
					
					

//					System.out.println("suobi " + arrGoodT);
//					System.out.println("suobi " + arrComT);
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
//					

					Bundle bNor = new Bundle();
					bNor.putStringArrayList("arrUnick", arrUnickT);
					bNor.putStringArrayList("arrType", arrTypeT);
					bNor.putStringArrayList("arrTitle", arrTitleT);
					bNor.putStringArrayList("arrTime", arrTimeT);
					bNor.putStringArrayList("arrNewsId", arrNewsIdTn);
//					System.out.println("song guo qu le !");
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


		
		xListView=(XListView) view.findViewById(R.id.xListView);
		xListView.setPullLoadEnable(true);
		xListView.setXListViewListener(this);
		
//		DropDownGet();


		mHandler = new Handler();

		//在此读消息表的数据 

		//
				
		xListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				System.out.println("position " + position);
				if (arrType.get(position-1).equals("req")) {
					ll4req = (LinearLayout)getActivity().getLayoutInflater().inflate(R.layout.xml4req, null);
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setIcon(R.drawable.ic_launcher).setTitle("好友请求")
					.setView(ll4req)
					.setPositiveButton("同意添加",	 new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

//							TextView tv = (TextView)xListView.findViewById(R.id.tv4user);
//							String theWho = tv.getText().toString();
//							
//							
//							Cursor cursor3 ;
////							Cursor cursor4;
//							String gotID = "select fs_id from FriendShip where PositiveU = ? and NegativeU = ?";
//
//							cursor3 = dp.query(gotID, new String[]{theWho,userNickName});
//							cursor3.moveToFirst();
//							String id  = cursor3.getString(0);
//							
//							ContentValues cv = new ContentValues();
//							cv.put("PositiveU", userNickName);
//							cv.put("NegativeU", theWho);
//							cv.put("FSstate", 1);
//							dp.insert("FriendShip", cv);
//							ContentValues cv2 = new ContentValues();
//							cv2.put("FSstate", 1);
//							dp.updatebyid("FriendShip", cv2, id);
//							
//							for (int i = 0; i < arrUnick.size(); i++) {
//								if (arrUnick.get(i).equals(theWho) && arrType.get(i).equals("req")) {
//									System.out.println(arrUnick.get(i));
//									System.out.println(arrType.get(i));
//									System.out.println(arrNewsId.get(i));
//									newid = arrNewsId.get(i);
//									System.out.println(newid);
//									dp.deletebyid("News", newid);
//									dp.DataClose();
//									DropDownGet();
//								}
//							}
							//需要刷新好友请求吗？
						}
					//加拒绝按钮吗？
					})
					.setNegativeButton("拒绝", null);
					builder.create().show();

				}else {
					//作业和通知页面，统一，
					
					String newid = arrNewsIdn.get(position-1);
					
					Intent intent2detail = new Intent();
					
					intent2detail.setClass(getActivity(), HomeworkDetail.class);
					
					intent2detail.putExtra("type",arrType.get(position-1));
					intent2detail.putExtra("newid",newid);
					intent2detail.putExtra("whoAmI", userNickName);
					intent2detail.putExtra("ifgood", arrIfGood.get(position-1));
					
//					intent2detail.putStringArrayListExtra("whogood", arrGoodT);
					
					intent2detail.putExtra("title", arrTitle.get(position-1));
					intent2detail.putExtra("detail", arrDetail.get(position-1));
					intent2detail.putExtra("pubuser", arrUnick.get(position-1));
					intent2detail.putExtra("time", arrTime.get(position-1));
//					intent2detail.putExtra("", value)
//					System.out.println(position);
//					System.out.println(arrIfCom);
//					System.out.println(arrIfCom.get(position-1));
					String s3 = arrCom.get(position-1);
					
				    String [] temp = null;  
				    temp = s3.split("-");  

//					if (arrIfCom.get(position-1).equals("true")) {
//						System.out.println("huo nari" + arrCom.get(position-1));
////						for (int b = 0; b < temp.length; b++) {
//							intent2detail.putStringArrayListExtra("cmmarrCom",arrCom);
//							intent2detail.putStringArrayListExtra("cmmid", arrCMIDt);
//							intent2detail.putStringArrayListExtra("cmmpos", arrCMPt);
//							intent2detail.putStringArrayListExtra("cmmnega", arrCMNt);
//							intent2detail.putStringArrayListExtra("cmmtime", arrCMTIMEt);
//							intent2detail.putStringArrayListExtra("cmmcontent", arrCMCONTENTt);
//							intent2detail.putStringArrayListExtra("cmmnewsid", arrCMNEWSIDt);
//							System.out.println("here ????" + arrCMCONTENTt);
////						}
//
//					}
				    if (arrIfGoodT.get(position-1).equals("true")) {
						intent2detail.putExtra("whogood", arrGoodT.get(position-1));
					}else {
						intent2detail.putExtra("whogood", "");
					}
//				    System.out.println("outside " + arrIfCom.get(position-1).equals("true"));
					if (arrIfCom.get(position-1).equals("true")) {
						intent2detail.putExtra("ifHave", "true");
						ArrayList<String> cmidL = new ArrayList<String>();
						ArrayList<String> posL = new ArrayList<String>();
						ArrayList<String> negaL = new ArrayList<String>();
						ArrayList<String> timeL = new ArrayList<String>();
						ArrayList<String> contentL = new ArrayList<String>();
//						System.out.println("is true de hua " + arrIfCom.get(position-1).equals("true"));
//						System.out.println(arrCMID.size());
//						System.out.println(temp.length);
						for (int q = 0; q < arrCMID.size(); q++) {
							for (int r = 0; r < temp.length; r++) {
//								System.out.println(arrCMID.get(position-1));
//								cmidL.add(arrCMID.get(position-1));
								if (arrCMID.get(q).equals(temp[r])) {
//									System.out.println(arrCMID.get(q));
//									System.out.println(temp[r]);
//									System.out.println("inside" + arrIfCom.get(position-1).equals("true"));
									cmidL.add(arrCMID.get(q));
//									System.out.println(arrCMID.get(q));
									posL.add(arrCMP.get(q));
//									System.out.println(arrCMP.get(q));
									negaL.add(arrCMN.get(q));
//									System.out.println(arrCMN.get(q));
									timeL.add(arrTime.get(q));
//									System.out.println(arrTime.get(q));
									contentL.add(arrCMCONTENT.get(q));
//									System.out.println(arrCMCONTENT.get(q));
									
									break;
								}
							}
						}
//						intent2detail.putStringArrayListExtra("cmmarrCom",cm);
						intent2detail.putStringArrayListExtra("cmmid", cmidL);
						intent2detail.putStringArrayListExtra("cmmpos", posL);
						intent2detail.putStringArrayListExtra("cmmnega", negaL);
						intent2detail.putStringArrayListExtra("cmmtime", timeL);
						intent2detail.putStringArrayListExtra("cmmcontent", contentL);
//						intent2detail.putStringArrayListExtra("cmmnewsid", n);
						
					}else {
						intent2detail.putExtra("ifHave", "false");
					}

					startActivity(intent2detail);
				}
			}
		});		
        return view;  
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		

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
//				DropDownGet();

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
				
				
				
//				DropDownGet();
				if (arrNewsIdn.size() == 0) {
					xListView.setVisibility(View.GONE);
					Toast.makeText(getActivity(), "网络不可用，请开启网络再重新启动软件！", Toast.LENGTH_LONG).show();
					try {
				        Thread.sleep(2000);//括号里面的5000代表5000毫秒，也就是5秒，可以该成你需要的时间
					} catch (InterruptedException e) {
					        e.printStackTrace();
					}
					getActivity().finish();
				}else {
					DropDownGet();
				}
				onLoad();
			}
		}, 2000);
	}

	public void DropDownGet(){
		
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
//		
//		
////		System.out.println("look:" + arrNewsIdT);
//		System.out.println("looked:" + arrNewsId);
//		System.out.println("looked:" + arrDetail);
//		System.out.println("looked:" + arrTime);
//		System.out.println("looked:" + arrTitle);
//		System.out.println("looked:" + arrType);
//		System.out.println("looked:" + arrUnick);
		
		if (arrNewsIdn.size() == 0) {//zheli

//			xListView.setVisibility(View.GONE);
		}else {
//			for(cursor1.moveToFirst();!cursor1.isAfterLast();cursor1.moveToNext())
//	        {
//				arrDetail.add(cursor1.getString(cursor1.getColumnIndex("detail")));
//				arrUnick.add(cursor1.getString(cursor1.getColumnIndex("userid")));
//				arrTitle.add(cursor1.getString(cursor1.getColumnIndex("title")));
//				arrTime.add(cursor1.getString(cursor1.getColumnIndex("time")));
//				arrType.add(cursor1.getString(cursor1.getColumnIndex("type")));
//				arrNewsId.add(cursor1.getString(cursor1.getColumnIndex("news_id")));
//			

			for (int i = 0; i < arrNewsIdn.size(); i++) {
				HashMap<String, Object> hm = new HashMap<String, Object>();
				if (arrType.get(i).equals("hw")) {
					hm.put("typelogo", R.drawable.hwlogo);
				}else if (arrType.get(i).equals("req")) {
					hm.put("typelogo", R.drawable.requestlogo);
				}else{
					hm.put("typelogo", R.drawable.informlogo);
				}
				
				hm.put("title", arrTitle.get(i));

				hm.put("time", arrTime.get(i));
				hm.put("user", arrUnick.get(i));
				hm.put("type", arrType.get(i));
				data.add(hm);
			}
//	        }
//			for (int n = 0; n < 20 + reqCount; n++) {
//	
//			}
			sAdapter = new SimpleAdapter(getActivity(), data, R.layout.xlistview_item3, 
				new String [] {"typelogo","title","time","user","type"},
				new  int [] {R.id.typeLogo,R.id.tv4title,R.id.tv4time,R.id.tv4user,R.id.tv4type});
			loadLL.setVisibility(View.GONE);
			sAdapter.notifyDataSetChanged();
			xListView.setAdapter(sAdapter);

//			onCreateView(inflater, container, savedInstanceState)

		}
	}
	private void onLoad() {
		xListView.stopRefresh();
		xListView.stopLoadMore();
		xListView.setRefreshTime("刚刚");
	}

}

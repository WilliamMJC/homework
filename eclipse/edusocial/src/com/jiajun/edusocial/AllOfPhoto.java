package com.jiajun.edusocial;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiajun.edusocial.pic.AsyncImageLoader;
import com.jiajun.edusocial.pic.Bimp;
import com.jiajun.edusocial.pic.CallbackImpl;
import com.jiajun.edusocial.pic.MyGridView;
import com.jiajun.edusocial.pic.MyGridViewAdapter;
import com.jiajun.edusocial.pic.PActivity;
import com.jiajun.edusocial.xlistview.XListView;
import com.jiajun.edusocial.xlistview.XListView.IXListViewListener;

//
	//是一个activity，实现了自定义下拉view的监听器，可以实现刷新listview
public class AllOfPhoto extends Activity implements IXListViewListener{
	
	ArrayList<String> friendNames = new ArrayList<String>();
	//我的listview对象，
	private XListView xListView=null;
	private Handler mHandler;
//	DatabasePart dp;
//	Cursor cursorgetPhoto;
	//图片地址字符串数组
	
	HttpResponse httpResponse;
	HttpEntity httpEntity;
	
	String [] arraypicP;
	String picP;
	ArrayList<String> AL4myAllFriend;
	int dtCount;	
	String SuserNameInPhoto;
	LinearLayout loadLL;

	ImageView back2funciton3 ;
	TextView publishPhoto ;
	//这一页的的适配器
	MyPhotoListAdapter sAdapter ;
	//动态的id，人名，时间，内容，图片
	ArrayList<String> arrdtid = new ArrayList<String>();
	ArrayList<String> arrpubUser = new ArrayList<String>();
	ArrayList<String> arrpubTime = new ArrayList<String>();
	ArrayList<String> arrdtContent = new ArrayList<String>();
	ArrayList<ArrayList<String>> arrdtPic = new ArrayList<ArrayList<String>>();
	ArrayList<String> arrdttx = new ArrayList<String>();
	
	
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
	
	ArrayList<String> friendList = new ArrayList<String>();
	List<Parcelable> lists;
	ArrayList list = new ArrayList();
	
	ArrayList<ArrayList<String>> arrdtPic2 = new ArrayList<ArrayList<String>>();
	ArrayList<String> arrdtid2 = new ArrayList<String>();
	ArrayList<String> arrpubUser2 = new ArrayList<String>();
	ArrayList<String> arrpubTime2 = new ArrayList<String>();
	ArrayList<String> arrdtContent2 = new ArrayList<String>();
	ArrayList<String> arrdttx2 = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.all_of_photo);
		
		Intent intentl = getIntent();
		Bundle bundle1 = intentl.getExtras();
		SuserNameInPhoto = bundle1.getString("userNameF2");
		friendList = bundle1.getStringArrayList("friendList");
//		System.out.println("la la la la la " + friendList);
		loadLL = (LinearLayout)findViewById(R.id.loadLL);
		
		back2funciton3 = (ImageView)findViewById(R.id.back2function3);
		back2funciton3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		publishPhoto = (TextView)findViewById(R.id.publishPhoto);

		xListView=(XListView) findViewById(R.id.xListView);
		xListView.setPullLoadEnable(true);
		xListView.setXListViewListener(this);
		
		getData();
		
		sAdapter = new MyPhotoListAdapter(this);
		xListView.setAdapter(sAdapter);
		
		mHandler = new Handler();
		
		xListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

			}
		});
		publishPhoto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent2pubPhoto = new Intent();
				intent2pubPhoto.setClass(AllOfPhoto.this, pubPhoto.class);
				intent2pubPhoto.putExtra("userN", SuserNameInPhoto);
				startActivity(intent2pubPhoto);
			}
		});
		
	}
	
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		//每次这个页面被置顶都在此刷新listview？
//		//3.14 觉得页面置顶的时候不应该刷新listview，而应该保持静态；尚未修改
//		System.out.println("aSun");
//		
////		arrdtid.clear();
////		arrpubTime.clear();
////		arrpubUser.clear();
////		arrdtPic.clear();
////		arrdtContent.clear();
//////		bmList.clear();
////		
////		getData();
//		sAdapter.notifyDataSetChanged();
//		xListView.setAdapter(sAdapter);
////		dp.DataClose();
//		
//		super.onResume();
//	}
	//点开朋友圈图片详情时返回毁掉函数；
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 88) {
			Bimp.drr = data.getStringArrayListExtra("resultPath");
		}
	
	}

	public void getData(){
		
		//获取动态数据，
		//应该
//		dp = new DatabasePart("jim/sjk", "a1.db");
//		
//		cursorgetPhoto = dp.query(gotPhoto, new String[]{SuserNameInPhoto,SuserNameInPhoto});
//		if (cursorgetPhoto.getCount() == 0) {
//
//			xListView.setVisibility(View.GONE);
//		}else {
//			for(cursorgetPhoto.moveToFirst();!cursorgetPhoto.isAfterLast();cursorgetPhoto.moveToNext())
//	        {
//				arrpubUser.add(cursorgetPhoto.getString(cursorgetPhoto.getColumnIndex("pubUser")));
//				arrpubTime.add(cursorgetPhoto.getString(cursorgetPhoto.getColumnIndex("pubTime")));
//				arrdtContent.add(cursorgetPhoto.getString(cursorgetPhoto.getColumnIndex("dtContent")));
//				arrdtPic.add(cursorgetPhoto.getString(cursorgetPhoto.getColumnIndex("dtPic")));
//				arrdtid.add(cursorgetPhoto.getString(cursorgetPhoto.getColumnIndex("dt_id")));
//	        }
//			dp.DataClose();
//		}
//		final ArrayList<ArrayList<String>> arrdtPic2 = new ArrayList<ArrayList<String>>();
//		final ArrayList<String> arrdtid2 = new ArrayList<String>();
//		final ArrayList<String> arrpubUser2 = new ArrayList<String>();
//		final ArrayList<String> arrpubTime2 = new ArrayList<String>();
//		final ArrayList<String> arrdtContent2 = new ArrayList<String>();
//		
//		final ArrayList<String> arrdtPicL = new ArrayList<String>();

		final Handler h = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				Bundle newb = msg.getData();
				arrdtid = newb.getStringArrayList("dtid");
//				System.out.println("laMoXi " + arrdtid);
				arrdtContent = newb.getStringArrayList("dtcontent");
//				System.out.println("laMoXi " + arrdtContent);
				arrpubTime = newb.getStringArrayList("dtpubtime");
//				System.out.println("laMoXi " + arrpubTime);
				arrpubUser = newb.getStringArrayList("pubuser");
//				System.out.println("laMoXi " + arrpubUser);
				list = newb.getParcelableArrayList("dtpic");
//				System.out.println("laMoXi " + list);
				
				arrdttx = newb.getStringArrayList("tx");
//				System.out.println(arrdttx);
//				arrpubUser = newb.getStringArrayList("pubuser");
//				arrpubTime = newb.getStringArrayList("dtcontent");
//				arrdtContent = newb.getStringArrayList("pubtime");
//				lists = newb.getParcelableArrayList("dtpic");
//				arrdtPic = (ArrayList<ArrayList<String>>)lists.get(0);
//				dtCount = newb.getInt("dtcount");
//				for (int i = 0; i < arrdtid.size(); i++) {
//					System.out.println(arrdtid.get(i));
//				}
//				System.out.println("test " + arrdtid.get(0));
//				System.out.println("test " + arrpubUser.get(0));
//				System.out.println("test " + arrpubTime.get(0));
//				System.out.println("test " + arrdtContent.get(0));
//				System.out.println("test " + lists.get(0));
//				System.out.println("test " + dtCount);
//				sAdapter = new MyPhotoListAdapter(getApplicationContext());
//				xListView.setAdapter(sAdapter);
//				getData();
				loadLL.setVisibility(View.GONE);
				sAdapter.notifyDataSetChanged();//zheli
				onLoad();
			}
		};
		
		arrdtPic2 = new ArrayList<ArrayList<String>>();
		arrdtid2 = new ArrayList<String>();
		arrpubUser2 = new ArrayList<String>();
		arrpubTime2 = new ArrayList<String>();
		arrdtContent2 = new ArrayList<String>();
//		arrdttx = new ArrayList<String>();
		
		new Thread(){
			@Override
			public void run(){
			//你要执行的方法
			//执行完毕后给handler发送一个空消息
				


//				int dtcount = 0;
				HttpGet httpGet = new HttpGet("http://zeng.shaoning.net/edusocial/DongTai.json");
				HttpGet httpGet2 = new HttpGet("http://zeng.shaoning.net/edusocial/User.json");
				HttpClient httpClient = new DefaultHttpClient();
				Message msg = new Message();
				InputStream inputStream = null;
				InputStream inputStream2 = null;
				try {
					httpResponse = httpClient.execute(httpGet);
					httpEntity = httpResponse.getEntity();
					inputStream = httpEntity.getContent();
					//GB2312
					//utf-8
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"GB2312"));

					
					String result = "";
					String line = "";
					while((line = reader.readLine()) != null){
						result = result + line;
					}
					System.out.println(result);
					
					
					JSONObject JO4DongTaiJson = new JSONObject(result);
					JSONArray JA4TotalSingleDongTai = JO4DongTaiJson.getJSONArray("DongTai");
//					JSONObject JO4SingleDongTai;
					for (int i = 0; i < JA4TotalSingleDongTai.length(); i++) {
						ArrayList<String> arrdtPicL = new ArrayList<String>();
						JSONObject JO4SingleDongTai = JA4TotalSingleDongTai.getJSONObject(i);
						if (JO4SingleDongTai.getString("pubUser").equals(SuserNameInPhoto)) {
							arrdtid2.add(JO4SingleDongTai.getString("dt_id"));
							arrdtContent2.add(JO4SingleDongTai.getString("dtContent"));
							arrpubTime2.add(JO4SingleDongTai.getString("pubTime"));
							arrpubUser2.add(JO4SingleDongTai.getString("pubUser"));
							JSONArray JA4ThisDongTai = JO4SingleDongTai.getJSONArray("dtPic");
							for (int o = 0; o < JA4ThisDongTai.length(); o++) {
								arrdtPicL.add(JA4ThisDongTai.getString(o));
							}
							arrdtPic2.add(arrdtPicL);
//							arrdttx2.add(JO4SingleDongTai.getString("tx"));//zheli
						}else {
							
							for (int k = 0; k < friendList.size(); k++) {
								if (JO4SingleDongTai.getString("pubUser").equals(friendList.get(k))) {
									arrdtid2.add(JO4SingleDongTai.getString("dt_id"));
									arrdtContent2.add(JO4SingleDongTai.getString("dtContent"));
									arrpubTime2.add(JO4SingleDongTai.getString("pubTime"));
									arrpubUser2.add(JO4SingleDongTai.getString("pubUser"));
									JSONArray JA4ThisDongTai = JO4SingleDongTai.getJSONArray("dtPic");
									for (int o = 0; o < JA4ThisDongTai.length(); o++) {
										arrdtPicL.add(JA4ThisDongTai.getString(o));
									}
									arrdtPic2.add(arrdtPicL);
//									arrdttx2.add(JO4SingleDongTai.getString("tx"));
								}
							}
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
//					System.out.println("2222" + result2);
					
					JSONObject JO4UserJson = new JSONObject(result2);
					JSONArray JA4UserShip = JO4UserJson.getJSONArray("User");
//					System.out.println(JA4UserShip.length());
//					tempAllFromUser = new ArrayList<String>();
//					tempName = new ArrayList<String>();
//					System.out.println(arrpubUser2.size() + "   PangTS");
					for (int r = 0; r < arrpubUser2.size(); r++) {
						for (int t = 0; t < JA4UserShip.length(); t++) {
							JSONObject JO4SingleJA4UserShip = JA4UserShip.getJSONObject(t);
							System.out.println(JO4SingleJA4UserShip.getString("nickname"));
							if (JO4SingleJA4UserShip.getString("nickname").equals(arrpubUser2.get(r))) {
								arrdttx2.add(JO4SingleJA4UserShip.getString("tx"));
								System.out.println("here   PangTS");
								break;
							}
						}
					}
//					for (int q = 0; q < JA4UserShip.length(); q++) {
//						JSONObject JO4SingleJA4UserShip = JA4UserShip.getJSONObject(q);
////						if (JO4SingleJA4UserShip.getString("positiveU").equals(userNickName)) {
////							hisTX = JO4SingleFriendShip.getString("");
//							tempAllFromUser.add(JO4SingleJA4UserShip.getString("nickname"));
////						}
////						tempAllName.add(JO4SingleJA4UserShip.getString(""));
//					}
//					JO4SingleDongTai = JA4TotalSingleDongTai.getJSONObject(0);
//					arrdtid2.add(JO4SingleDongTai.getString("dt_id"));

//					for (int i = 0; i < JA4TotalSingleDongTai.length(); i++) {
//						JO4SingleDongTai = JA4TotalSingleDongTai.getJSONObject(i);
//						if (JO4SingleDongTai.getString("pubUser").equals(SuserNameInPhoto)) {
//							arrdtid2.add(JO4SingleDongTai.getString("dt_id"));
//							arrpubUser2.add(JO4SingleDongTai.getString("pubUser"));
//							arrdtContent2.add(JO4SingleDongTai.getString("dtContent"));
//							System.out.println(JO4SingleDongTai.getString("dtContent"));
//							arrpubTime2.add(JO4SingleDongTai.getString("pubTime"));
//							JSONArray tempArray = JO4SingleDongTai.getJSONArray("dtPic");
//							for (int k = 0; k < tempArray.length(); k++) {
//								arrdtPicL.add(tempArray.getString(k));
//							}
//							arrdtPic2.add(arrdtPicL);
//						}else {
//							for (int j = 0; j < friendList.size(); j++) {
//								if (JO4SingleDongTai.getString("pubUser").equals(friendList.get(j))) {
//									arrdtid2.add(JO4SingleDongTai.getString("dt_id"));
//									arrpubUser2.add(JO4SingleDongTai.getString("pubUser"));
//									arrdtContent2.add(JO4SingleDongTai.getString("dtContent"));
//									System.out.println(JO4SingleDongTai.getString("dtContent"));
//									arrpubTime2.add(JO4SingleDongTai.getString("pubTime"));
//									JSONArray tempArray = JO4SingleDongTai.getJSONArray("dtPic");
//									for (int k = 0; k < tempArray.length(); k++) {
//										arrdtPicL.add(tempArray.getString(k));
//									}
//									arrdtPic2.add(arrdtPicL);
//								}
//							}
//						}
//					}
//					dtcount = arrdtPic2.size();
//					System.out.println(dtcount);
					Bundle bb = new Bundle();
//					System.out.println("put " + arrdtid2);
					bb.putStringArrayList("dtid", arrdtid2);
					System.out.println("put success!");
					bb.putStringArrayList("dtcontent", arrdtContent2);
					bb.putStringArrayList("dtpubtime", arrpubTime2);
					bb.putStringArrayList("pubuser", arrpubUser2);
					bb.putParcelableArrayList("dtpic", (ArrayList<? extends Parcelable>) arrdtPic2);
					bb.putStringArrayList("tx", arrdttx2);
//					b.putStringArrayList("pubuser", arrpubUser2);
//					b.putStringArrayList("dtcontent", arrdtContent2);
//					b.putStringArrayList("pubtime", arrpubTime2);
//					b.putInt("dtcount", dtcount);
////					ArrayList list = new ArrayList();
////					list.add(arrdtPic2);
//					//arrdtPic2
//					b.putParcelableArrayList("dtpic", (ArrayList<? extends Parcelable>) arrdtPic2);

					msg.setData(bb);



				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					try{
						inputStream.close();
						inputStream2.close();
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}					
				h.sendMessage(msg);

			}
		}.start();
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
				System.out.println("refresh");
				getData();
				
				sAdapter.notifyDataSetChanged();
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
				System.out.println("onLoadMore");
				getData();
				sAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}
	
	protected void onRestart() {
		System.out.println("restart");
		getData();
		sAdapter.notifyDataSetChanged();
		super.onRestart();
	}
	
	//自定义这一页的XListView的适配器；
	class MyPhotoListAdapter extends BaseAdapter{
		private Context mContext;
		LayoutInflater inflater;
		public MyPhotoListAdapter(Context mContext){
			this.inflater = LayoutInflater.from(mContext);
			this.mContext = mContext;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return arrdtid.size();//zheli
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		//每一个列表项的具体实现
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			ViewHolder holder = null;
			holder = new ViewHolder();
			
			convertView = inflater.inflate(R.layout.xlistview_item4photo, null,false);
			
			holder.tv4c = (TextView)convertView.findViewById(R.id.tv4content);
//			holder.tv4p =(TextView)convertView.findViewById(R.id.tv4path);
			holder.iv4tx = (ImageView)convertView.findViewById(R.id.iv4dttx);
			holder.tv4t = (TextView)convertView.findViewById(R.id.tv4time);
			holder.tv4u = (TextView)convertView.findViewById(R.id.tv4user);
			holder.gv = (MyGridView)convertView.findViewById(R.id.noScrollgridview);
	
			final ArrayList<String> tempList = (ArrayList<String>) list.get(position);
			
			holder.tv4c.setText(arrdtContent.get(position).toString());
//			holder.tv4p.setText(tempList.get(position).toString());
			holder.iv4tx.setImageResource(R.drawable.withouttouxiang);
			
	    	CallbackImpl callbackImpl = new CallbackImpl(holder.iv4tx);
	    	AsyncImageLoader loader = new AsyncImageLoader();
	    	Drawable cacheImage = 
	        		loader.loadDrawable(arrdttx.get(position), callbackImpl);//zheli
	    		if (cacheImage != null) {
	    			holder.iv4tx.setImageDrawable(cacheImage);
	    		}
	    		
	    		
	    		
//			holder.iv4tx.setText(tempList.get(0));
			holder.tv4t.setText(arrpubTime.get(position).toString());
			holder.tv4u.setText(arrpubUser.get(position).toString());
			//arrdtPic.get(position).toString().equals("")

			if (tempList.size() == 0) {
				holder.gv.setVisibility(View.GONE);
			}else {
				holder.gv.setVisibility(View.VISIBLE);
				
//				String [] arrayPicPath = arrdtPic.get(position).toString().split("\\*");
//				final ArrayList<String> arrayList4GVsPath = new ArrayList<String>();
//				for (int t = 0; t < arrayPicPath.length; t++) {
//					arrayList4GVsPath.add(arrayPicPath[t]);
//				}
				
				MyGridViewAdapter gAdapter = new MyGridViewAdapter(mContext, tempList);
				holder.gv.setAdapter(gAdapter);
				holder.gv.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						Intent in = new Intent();
						in.setClass(AllOfPhoto.this, PActivity.class);
						in.putExtra("ID", position);
						in.putStringArrayListExtra("selectPicPath", tempList);
						startActivity(in);
					}
				});				
			}
			return convertView;
		}
		  private class ViewHolder {
			    TextView tv4c;
			    TextView tv4u;
			    TextView tv4t;
			    ImageView iv4tx;
			    
			    MyGridView gv;
		  }
	}


}

package com.jj.tmdemo;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
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

import DatabasePart.DatabasePart;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jj.tmdemo.pic.Bimp;
import com.jj.tmdemo.pic.MyGridView;
import com.jj.tmdemo.pic.MyGridViewAdapter;
import com.jj.tmdemo.pic.PActivity;
import com.jj.tmdemo.pic.PhotoActivity;
import com.jj.tmdemo.xlistview.XListView;
import com.jj.tmdemo.xlistview.XListView.IXListViewListener;

//
	//是一个activity，实现了自定义下拉view的监听器，可以实现刷新listview
public class AllOfPhoto extends Activity implements IXListViewListener{
	
//	ArrayList<String> Bimp.drr = new ArrayList<String>();
//	ArrayList<Bitmap> Bimp.bmp = new ArrayList<Bitmap>();
	ArrayList<String> friendNames = new ArrayList<String>();
	//我的listview对象，
	private XListView xListView=null;
//	SimpleAdapter sAdapter;
	private Handler mHandler;
	DatabasePart dp;
	Cursor cursorgetPhoto;
	//图片地址字符串数组
	String [] arraypicP;
	String picP;
	ArrayList<String> AL4myAllFriend;
	
	String SuserNameInPhoto;
	String gotPhoto = "select * from DongTai where (DongTai.pubUser = ? ) or (DongTai.pubUser in (select NegativeU from FriendShip where PositiveU = ? and FSstate = 1)) order by pubTime DESC";
	
//	private MyGridView noScrollgridview;
//	private GridAdapter adapter;

	ImageView back2funciton3 ;
	TextView publishPhoto ;
	//这一页的的适配器
	MyPhotoListAdapter sAdapter ;
	//动态的id，人名，时间，内容，图片
	ArrayList<String> arrdtid = new ArrayList<String>();
	ArrayList<String> arrpubUser = new ArrayList<String>();
	ArrayList<String> arrpubTime = new ArrayList<String>();
	ArrayList<String> arrdtContent = new ArrayList<String>();
	ArrayList<String> arrdtPic = new ArrayList<String>();
	
//	ArrayList<Bitmap> bmList = new ArrayList<Bitmap>();
//	Handler handler4gotDTpic =new Handler(){
//		@Override
//		//当有消息发送出来的时候就执行Handler的这个方法
//		public void handleMessage(Message msg){
//			
////			Bundle b4tc = msg.getData();
////			targetList = b4tc.getStringArrayList("target");
////			System.out.println(targetList);
////			mAdapter = new MyAdapter4ClassLIST(targetList, context);
////
////			lv.setAdapter(mAdapter);
//
//		}
//	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.all_of_photo);
		
		Intent intentl = getIntent();
		Bundle bundle1 = intentl.getExtras();
		SuserNameInPhoto = bundle1.getString("userNameF2");
		
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
//		xListView.setAdapter(sAdapter);
		xListView.setXListViewListener(this);
		
		getData();
		sAdapter = new MyPhotoListAdapter(this);
		xListView.setAdapter(sAdapter);
//		dp.DataClose();

		
//		new Thread(){
//			@Override
//			public void run(){
//			//你要执行的方法
//			//执行完毕后给handler发送一个空消息
//				
//				handler4gotDTpic.sendMessage(msg2);
//
//			}
//		}.start();
//		
		
		
		mHandler = new Handler();
		//初始化函数
//		Init();
		
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
				
				
//				String dtid = arrdtid.get(position-1);
//				Intent intent2HWdetail = new Intent();
//				intent2HWdetail.setClass(AllOfPhoto.this, HomeworkDetail.class);
////				intent2HWdetail.putExtra("Title", thisTitle);
////				intent2HWdetail.putExtra("Detail", thisDetail);
//				intent2HWdetail.putExtra("newid", newid);
//				intent2HWdetail.putExtra("woAmI", s4UserNameInHW);
//				intent2HWdetail.putExtra("type", arrType.get(position-1));
//				startActivity(intent2HWdetail);
				
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
//				startActivityForResult(intent2pubPhoto , 88);
			}
		});
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		//每次这个页面被置顶都在此刷新listview？
		//3.14 觉得页面置顶的时候不应该刷新listview，而应该保持静态；尚未修改
		System.out.println("aSun");
		
		arrdtid.clear();
		arrpubTime.clear();
		arrpubUser.clear();
		arrdtPic.clear();
		arrdtContent.clear();
//		bmList.clear();
		
		getData();
		sAdapter.notifyDataSetChanged();
		xListView.setAdapter(sAdapter);
//		dp.DataClose();
		
		super.onResume();
	}
	//点开朋友圈图片详情时返回毁掉函数；
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 88) {
			Bimp.drr = data.getStringArrayListExtra("resultPath");
			
		}
	
	}

//	public void Init() {
//		noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
//		//设置背景setSelector，透明。
//		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
//		
//		//新建监听器，并且update；而update里面调用load函数；
//		adapter = new GridAdapter(this);
//		adapter.update();
//		//设置GridView的监听器，并且update；
//		noScrollgridview.setAdapter(adapter);
//		//设置每张照片的单击事件；
//		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {
//
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				//如果你点的位置是加号？
//				System.out.print("down");
//				System.out.print("" + Bimp.bmp.size());
//				if (arg2 == Bimp.bmp.size()) {
//
//				} else {
//					Intent intent = new Intent(AllOfPhoto.this,
//							PhotoActivity.class);
//					//把你点的是adapter中第几张图片传过去，
//					intent.putExtra("ID", arg2);
//					startActivity(intent);
//				}
//			}
//		});
//		//发送按钮
//	
//	}
	



	public void getData(){
		
//		HttpResponse httpResponse;
//		HttpEntity httpEntity;
		//开一个内部线程
		final Handler hd = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				Bundle newb = msg.getData();
				AL4myAllFriend = newb.getStringArrayList("AL4friendNames");
				System.out.println(AL4myAllFriend);

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
				//Looper.loop();
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
					System.out.println(result);
					JSONObject JO4FriendShipJson = new JSONObject(result);
					JSONArray JA4TotalSingleFriendShip = JO4FriendShipJson.getJSONArray("FriendShip");
					JSONObject JO4SingleFriendShip;
//					JSONArray myJOSingleArray;
//					schoolName = new ArrayList<String>();
//					className = new ArrayList<ArrayList<String>>();
					for (int i = 0; i < JA4TotalSingleFriendShip.length(); i++) {
						JO4SingleFriendShip = JA4TotalSingleFriendShip.getJSONObject(i);
						if (JO4SingleFriendShip.getString("PositiveU").equals(SuserNameInPhoto)) {
							friendNames.add(JO4SingleFriendShip.getString("NegativeU"));
						}
//						schoolName.add(myJOSingleObject.getString("SchoolName"));
						
						
//						schoolName = Arrays.copyOf(schoolName, schoolName.length + 1);
//						schoolName[schoolName.length - 1] =
//						className.add(myJOSingleObject.getJSONArray("ClassName"));
						
						
//						myJOSingleArray = myJOSingleObject.getJSONArray("ClassName");
//						ArrayList<String> classSName = new ArrayList<String>();
////						String S4classNameSingle [];
//						for (int k = 0; k < myJOSingleArray.length(); k++) {
//							classSName.add(myJOSingleArray.getString(k));
//						}
//						
//						className.add(classSName);
						
						
						
//						S4classNameSingle = new String [classSName.size()];
//						S4classNameSingle = classSName.toArray(S4classNameSingle);

					}
//					S4schoolName = new String [schoolName.size()];
//					S4schoolName = schoolName.toArray(S4schoolName);
					Bundle b = new Bundle();
					//b.putCharSequenceArrayList("schoolAL", schoolName);
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
				hd.sendMessage(msg);

			}
		}.start();
		
		//获取动态数据，
		dp = new DatabasePart("jim/sjk", "a1.db");
		
		cursorgetPhoto = dp.query(gotPhoto, new String[]{SuserNameInPhoto,SuserNameInPhoto});
		if (cursorgetPhoto.getCount() == 0) {

			xListView.setVisibility(View.GONE);
		}else {
			for(cursorgetPhoto.moveToFirst();!cursorgetPhoto.isAfterLast();cursorgetPhoto.moveToNext())
	        {
				arrpubUser.add(cursorgetPhoto.getString(cursorgetPhoto.getColumnIndex("pubUser")));
				arrpubTime.add(cursorgetPhoto.getString(cursorgetPhoto.getColumnIndex("pubTime")));
				arrdtContent.add(cursorgetPhoto.getString(cursorgetPhoto.getColumnIndex("dtContent")));
				arrdtPic.add(cursorgetPhoto.getString(cursorgetPhoto.getColumnIndex("dtPic")));
				arrdtid.add(cursorgetPhoto.getString(cursorgetPhoto.getColumnIndex("dt_id")));
				
//				HashMap<String, Object> hm = new HashMap<String, Object>();
//				
//				hm.put("content", arrdtContent.get(cursorgetPhoto.getPosition()));
//				
//				hm.put("time", arrpubTime.get(cursorgetPhoto.getPosition()));
//				hm.put("user", arrpubUser.get(cursorgetPhoto.getPosition()));
//				picP  = arrdtPic.get(cursorgetPhoto.getPosition());
//				if (picP.equals("")) {
//				ArrayList<String> picPathList = new ArrayList<String>();
//				if (cursorgetPhoto.getString(cursorgetPhoto.getColumnIndex("dtPic")).equals("")) {
//					
//				}
//				}else {
//					arraypicP = picP.split("*");
//				}
//				ArrayList<Bitmap> bmpList = new ArrayList<Bitmap>();
//				for (int u = 0; u < arraypicP.length; u++) {
//					try{
//					bmpList.add(Bimp.revitionImageSize(arraypicP[u]));
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//				
//				hm.put("pic", arrdtPic.get(cursorgetPhoto.getPosition()));
//				data.add(hm);
	        }
			
//			sAdapter = new SimpleAdapter(this, data, R.layout.xlistview_item4photo, 
//				new String [] {"content","time","user","pic"},
//				new  int [] {R.id.tv4content,R.id.tv4time,R.id.tv4user,R.id.tv4path});
//			sAdapter.notifyDataSetChanged();
//			xListView.setAdapter(sAdapter);
			dp.DataClose();
//			xListView.setAdapter(sAdapter);
		}
	}
//	public void GetPhotoList(){
//		dp = new DatabasePart("jim/sjk", "a1.db");
//		
//		cursorgetPhoto = dp.query(gotPhoto, new String[]{SuserNameInPhoto,SuserNameInPhoto});
//		
////		List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
//		
////		if (cursorgetPhoto.getCount() == 0) {
////
////			xListView.setVisibility(View.GONE);
////		}else {
////			for(cursorgetPhoto.moveToFirst();!cursorgetPhoto.isAfterLast();cursorgetPhoto.moveToNext())
////	        {
////				arrpubUser.add(cursorgetPhoto.getString(cursorgetPhoto.getColumnIndex("pubUser")));
////				arrpubTime.add(cursorgetPhoto.getString(cursorgetPhoto.getColumnIndex("pubTime")));
////				arrdtContent.add(cursorgetPhoto.getString(cursorgetPhoto.getColumnIndex("dtContent")));
////				arrdtPic.add(cursorgetPhoto.getString(cursorgetPhoto.getColumnIndex("dtPic")));
////				arrdtid.add(cursorgetPhoto.getString(cursorgetPhoto.getColumnIndex("dt_id")));
////				
////				HashMap<String, Object> hm = new HashMap<String, Object>();
////				
////				hm.put("content", arrdtContent.get(cursorgetPhoto.getPosition()));
////				
////				hm.put("time", arrpubTime.get(cursorgetPhoto.getPosition()));
////				hm.put("user", arrpubUser.get(cursorgetPhoto.getPosition()));
////				picP  = arrdtPic.get(cursorgetPhoto.getPosition());
////				if (picP.equals("")) {
////					
////				}else {
////					arraypicP = picP.split("*");
////				}
////				ArrayList<Bitmap> bmpList = new ArrayList<Bitmap>();
////				for (int u = 0; u < arraypicP.length; u++) {
////					try{
////					bmpList.add(Bimp.revitionImageSize(arraypicP[u]));
////					} catch (IOException e) {
////						e.printStackTrace();
////					}
////				}
////				
////				hm.put("pic", arrdtPic.get(cursorgetPhoto.getPosition()));
////				data.add(hm);
////	        }
////			sAdapter = new SimpleAdapter(this, data, R.layout.xlistview_item4photo, 
////				new String [] {"content","time","user","pic"},
////				new  int [] {R.id.tv4content,R.id.tv4time,R.id.tv4user,R.id.tv4path});
////			sAdapter.notifyDataSetChanged();
////			xListView.setAdapter(sAdapter);
////		}
//	}
//
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
//				start = ++refreshCnt;
//				items.clear();
//				arrpubUser.clear();
//				arrdtContent.clear();
//				arrdtid.clear();
//				arrdtPic.clear();
//				arrpubTime.clear();

				
				getData();
				sAdapter.notifyDataSetChanged();
//				xListView.setAdapter(sAdapter);
//				dp.DataClose();
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
				System.out.println("onLoadMore");
//				arrpubUser.clear();
//				arrdtContent.clear();
//				arrdtid.clear();
//				arrdtPic.clear();
//				arrpubTime.clear();
				getData();
				sAdapter.notifyDataSetChanged();
//				xListView.setAdapter(sAdapter);
//				dp.DataClose();
//				sAdapter.notifyDataSetChanged();
//				xListView.setAdapter(sAdapter);
				onLoad();
			}
		}, 2000);
	}
	
	protected void onRestart() {
		System.out.println("restart");
//		Bimp.drr.clear();
//		Bimp.bmp.clear();
//		gAdapter.update();
//		arrpubUser.clear();
//		arrdtContent.clear();
//		arrdtid.clear();
//		arrdtPic.clear();
//		arrpubTime.clear();
		getData();
		sAdapter.notifyDataSetChanged();
//		xListView.setAdapter(sAdapter);
		super.onRestart();
	}
	
	
	class MyPhotoListAdapter extends BaseAdapter{
		private Context mContext;
		LayoutInflater inflater;
		public MyPhotoListAdapter(Context mContext){
//			super();
			this.inflater = LayoutInflater.from(mContext);
			this.mContext = mContext;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return cursorgetPhoto.getCount();
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
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			ViewHolder holder = null;
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.xlistview_item4photo, null,false);
			holder.tv4c = (TextView)convertView.findViewById(R.id.tv4content);
			holder.tv4p =(TextView)convertView.findViewById(R.id.tv4path);
			holder.tv4t = (TextView)convertView.findViewById(R.id.tv4time);
			holder.tv4u = (TextView)convertView.findViewById(R.id.tv4user);
			holder.gv = (MyGridView)convertView.findViewById(R.id.noScrollgridview);
			
			holder.tv4c.setText(arrdtContent.get(position).toString());
			holder.tv4p.setText(arrdtPic.get(position).toString());
			holder.tv4t.setText(arrpubTime.get(position).toString());
			holder.tv4u.setText(arrpubUser.get(position).toString());
			if (arrdtPic.get(position).toString().equals("")) {
//				holder.gv.setVisibility(View.GONE);
			}else {
				holder.gv.setVisibility(View.VISIBLE);
				String [] arrayPicPath = arrdtPic.get(position).toString().split("\\*");
				final ArrayList<String> arrayList4GVsPath = new ArrayList<String>();
				for (int t = 0; t < arrayPicPath.length; t++) {
					arrayList4GVsPath.add(arrayPicPath[t]);
				}
//				GridAdapter gAdapter;
//				gAdapter = new GridAdapter(mContext,arrayList4GVsPath);
//				holder.gv.setAdapter(gAdapter);
				
				MyGridViewAdapter gAdapter = new MyGridViewAdapter(mContext, arrayList4GVsPath);
				holder.gv.setAdapter(gAdapter);
				holder.gv.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						Intent in = new Intent();
						in.setClass(AllOfPhoto.this, PActivity.class);
						in.putExtra("ID", position);
						in.putStringArrayListExtra("selectPicPath", arrayList4GVsPath);
						startActivity(in);
					}
					
				});
//				gAdapter.update();
//				ArrayList<HashMap<String, Object>> arrayList4EveryGridView =
//						new ArrayList<HashMap<String,Object>>();
				
			}
			
			return convertView;
		}
		  private class ViewHolder {
			    TextView tv4c;
			    TextView tv4u;
			    TextView tv4t;
			    TextView tv4p;
			    
			    MyGridView gv;
			  }
	}


}

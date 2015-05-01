package com.jj.tmdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DatabasePart.DatabasePart;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.jj.tmdemo.xlistview.XListView;
import com.jj.tmdemo.xlistview.XListView.IXListViewListener;

//

public class AllOfHomework extends Activity implements IXListViewListener{
	
	private XListView xListView=null;
	SimpleAdapter sAdapter;
	private Handler mHandler;
	
	TextView publishHW;
	String s4UserNameInHW;
	String s4UserIdentityInHW;
//	XListView xlistview4allHW;
//	String sql4HW;
	Cursor cursorHW1;
//	Cursor cursor4GotClass;
	String sql4GotClass;
	DatabasePart dp;
	
	ArrayList<String> arrUid;
	ArrayList<String> arrTitle;
	ArrayList<String> arrTime;
	ArrayList<String> arrDetail;
	ArrayList<String> arrType;
	ArrayList<String> arrNewsId;
	
	ArrayList<String> classS;
	
	String sql4gotHW;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.all_of_homework);
		
//		xListView=(XListView) view.findViewById(R.id.xListView);
//		xListView.setPullLoadEnable(true);
//		mAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.xlistview_item, items);
//		xListView.setAdapter(mAdapter);
//		xListView.setXListViewListener(this);
//		mHandler = new Handler();
		sql4gotHW = "select * from News where News.negauserid = (select User.noclass from User where User.nickname = ?)";
		
//		sql4GotClass = "select noclass from User where nickname = ?";
//		sql4HW = "select * from News where negauserid = ?";

		
		
		Intent intent1 = getIntent();
		Bundle bundle1 = intent1.getExtras();
		s4UserNameInHW = bundle1.getString("userNameF2");
		s4UserIdentityInHW = bundle1.getString("userIdentityF2");
		
		ImageView back2function = (ImageView)findViewById(R.id.back2function);
		//如何取出传入的用户账号和身份？
		
		publishHW = (TextView)findViewById(R.id.publishHW);
		if (s4UserIdentityInHW.equals("teacher")) {
			
			
			
		}else {
			
			publishHW.setVisibility(View.GONE);
		}

		arrUid = new ArrayList<String>();
		arrTitle = new ArrayList<String>();
		arrDetail = new ArrayList<String>();
		arrTime = new ArrayList<String>();
		arrType = new ArrayList<String>();
		arrNewsId = new ArrayList<String>();
		
		xListView=(XListView) findViewById(R.id.xListView);
		xListView.setPullLoadEnable(true);
//		xListView.setAdapter(sAdapter);
		xListView.setXListViewListener(this);
		
		GetHwList();
		dp.DataClose();

		mHandler = new Handler();
//		dp = new DatabasePart("jim/sjk", "a1.db");
		

		
		
		
		
//		cursor4GotClass = dp.query(sql4GotClass, new String[]{s4UserNameInHW});
		
//		int ClsIndex = cursor4GotClass.getColumnIndex("noclass");
		
		
//		for (cursor4GotClass.moveToFirst();!cursor4GotClass.isAfterLast();cursor4GotClass.moveToNext()) {
//			classS.add(cursor4GotClass.getString(cursor4GotClass.getPosition()));
//		}
		
		
//		int SchIndex = cursor4GotSchClass.getColumnIndex("school");
		
//		String hisClass = cursor4GotClass.getString(ClsIndex);

//		String hisSch = cursor4GotSchClass.getColumnName(SchIndex);
		
//		classArray = hisClass.split(" ");

		
//		cursorHW1 = dp.query(sql4gotHW, new String[]{s4UserNameInHW});
//		
//		int indexTitle = cursorHW1.getColumnIndex("title");
//		int indexTime = cursorHW1.getColumnIndex("time");
//		int indexUID = cursorHW1.getColumnIndex("userid");
//		int indexType = cursorHW1.getColumnIndex("type");
//		int indexDetail = cursorHW1.getColumnIndex("detail");
		
//		int indexGoodman = 
		
//		if (cursorHW1.getCount() == 0) {
//			
//		}else {
//			if (cursorHW1.getCount() > 20) {
//				for (int i = 0; i < 20; i++) {
//					arrTitle.add(cursorHW1.getColumnName(indexTitle));
//					arrTime.add(cursorHW1.getColumnName(indexTime));
//					arrUid.add(cursorHW1.getColumnName(indexUID));
//					arrType.add(cursorHW1.getColumnName(indexType));
//					arrDetail.add(cursorHW1.getColumnName(indexDetail));
//				}
//			}else {
//				for (cursorHW1.moveToFirst();!cursorHW1.isAfterLast();cursorHW1.moveToNext()) {
//					arrTitle.add(cursorHW1.getColumnName(indexTitle));
//					arrTime.add(cursorHW1.getColumnName(indexTime));
//					arrUid.add(cursorHW1.getColumnName(indexUID));
//					arrType.add(cursorHW1.getColumnName(indexType));
//					arrDetail.add(cursorHW1.getColumnName(indexDetail));
//				}
//			}
//			List<HashMap<String, Object>> data  = new ArrayList<HashMap<String,Object>>();
//			HashMap<String, Object> map = new HashMap<String, Object>();
//			
//			for (int l = 0; l < 20; l++) {
//				map.put("title", arrTitle.get(l));
//				map.put("time", arrTime.get(l));
//				map.put("user", arrUid.get(l));
//				map.put("type", arrType.get(l));
//			}
//			data.add(map);
//			SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.xlistview_item, 
//					new String[]{"title","time","user","type"}, 
//					new int [] {R.id.tv4title,R.id.tv4time,R.id.tv4user,R.id.tv4type});
//			xlistview4allHW.setAdapter(adapter);
//		}

		

//		xlistview4allHW = (XListView)findViewById(R.id.xListView4allHW);
		

		
		
		
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
				intent2HWdetail.putExtra("woAmI", s4UserNameInHW);
				intent2HWdetail.putExtra("type", arrType.get(position-1));
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
		System.out.println("aSun");
		arrDetail.clear();
		arrTime.clear();
		arrTitle.clear();
		arrType.clear();
		arrUid.clear();
		arrNewsId.clear();
		GetHwList();
		dp.DataClose();
		
		super.onResume();
	}
	public void GetHwList(){
		dp = new DatabasePart("jim/sjk", "a1.db");
		
		cursorHW1 = dp.query(sql4gotHW, new String[]{s4UserNameInHW});
		
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
		
		if (cursorHW1.getCount() == 0) {
//			xListView.removeAllViews()
			xListView.setVisibility(View.GONE);
		}else {
			for(cursorHW1.moveToFirst();!cursorHW1.isAfterLast();cursorHW1.moveToNext())
	        {
				arrDetail.add(cursorHW1.getString(cursorHW1.getColumnIndex("detail")));
				arrUid.add(cursorHW1.getString(cursorHW1.getColumnIndex("userid")));
				arrTitle.add(cursorHW1.getString(cursorHW1.getColumnIndex("title")));
				arrTime.add(cursorHW1.getString(cursorHW1.getColumnIndex("time")));
				arrType.add(cursorHW1.getString(cursorHW1.getColumnIndex("type")));
				arrNewsId.add(cursorHW1.getString(cursorHW1.getColumnIndex("news_id")));
				
				HashMap<String, Object> hm = new HashMap<String, Object>();
				hm.put("title", arrTitle.get(cursorHW1.getPosition()));

				hm.put("time", arrTime.get(cursorHW1.getPosition()));
				hm.put("user", arrUid.get(cursorHW1.getPosition()));
				hm.put("type", arrType.get(cursorHW1.getPosition()));
				data.add(hm);
	        }
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
				System.out.println("refresh");
//				start = ++refreshCnt;
//				items.clear();
				arrDetail.clear();
				arrTime.clear();
				arrTitle.clear();
				arrType.clear();
				arrUid.clear();
				arrNewsId.clear();
				
				GetHwList();
				dp.DataClose();
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
				GetHwList();
				dp.DataClose();
//				sAdapter.notifyDataSetChanged();
//				xListView.setAdapter(sAdapter);
				onLoad();
			}
		}, 2000);
	}
}

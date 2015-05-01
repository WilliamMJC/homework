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

public class AllOfInform extends Activity implements IXListViewListener{
	
	TextView publishInform;
	String s4UserIdentityInInform ;
	String s4UserNameInInform;
	String sql4allInform;
	String sql4GotSchClass;
	Cursor cursor4GotSchClass;
	Cursor cursor4allInform;
	SimpleAdapter sAdapter;
	Cursor cursorInform1;
	
	ArrayList<String> arrUid;
	ArrayList<String> arrTitle;
	ArrayList<String> arrTime;
	ArrayList<String> arrDetail;
	ArrayList<String> arrType;
	ArrayList<String> arrNewsId;
	
	DatabasePart dp;
	String [] classArray;
	
	String  sql4gotInform;
	
	private Handler mHandler;
	XListView xListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.all_of_inform);

		sql4gotInform  = "select * from News where (News.negauserid  = (select User.school from User where User.nickname = ?)) or (News.negauserid  in (select User.noclass from User where User.nickname = ?) and type = ?)";
		
		
		
		publishInform = (TextView)findViewById(R.id.publishInform);
		
		xListView = (XListView)findViewById(R.id.xListView);
		
		Intent intent1 = getIntent();
		Bundle bundle1 = intent1.getExtras();
		s4UserIdentityInInform = bundle1.getString("userIdentityF2");
		s4UserNameInInform = bundle1.getString("userNameF2");
		
		ImageView back2function2 = (ImageView)findViewById(R.id.back2function2);
		
		
		
		if (s4UserIdentityInInform.equals("teacher")) {
			
		}else {
			publishInform.setVisibility(View.GONE);
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

		
//		sql4GotSchClass = "select school,noclass from User where account = ?";
//		cursor4GotSchClass = dp.query(sql4GotSchClass, new String[]{s4UserNameInInform});
//		int index1 = cursor4GotSchClass.getColumnIndex("school");
//		int index2 = cursor4GotSchClass.getColumnIndex("noclass");
//		String hisSch = cursor4GotSchClass.getColumnName(index1);
//		String hisClass = cursor4GotSchClass.getColumnName(index2);
//		String hisClassS = hisClass + " " + hisSch;
//		classArray = hisClassS.split(" ");
//		
//		
//		sql4allInform = "select * from News where negauserid = ? ";
//		cursor4allInform = dp.query(sql4allInform, classArray);
//		
//		int indexTitle = cursor4allInform.getColumnIndex("title");
//		int indexTime = cursor4allInform.getColumnIndex("time");
//		int indexUID = cursor4allInform.getColumnIndex("userid");
//		int indexType = cursor4allInform.getColumnIndex("type");
//		int indexDetail = cursor4allInform.getColumnIndex("detail");
//		
//		if (cursor4allInform.getCount() == 0) {
//			
//		}else {
//			if (cursor4allInform.getCount() > 20) {
//				for (cursor4allInform.moveToFirst();!cursor4allInform.isAfterLast();cursor4allInform.moveToNext()) {
//					arrTitle.add(cursor4allInform.getColumnName(indexTitle));
//					arrTime.add(cursor4allInform.getColumnName(indexTime));
//					arrUid.add(cursor4allInform.getColumnName(indexUID));
//					arrType.add(cursor4allInform.getColumnName(indexType));
//					arrDetail.add(cursor4allInform.getColumnName(indexDetail));
//				}
//			}else {
//				for (int m = 0; m < 20; m++) {
//					arrTitle.add(cursor4allInform.getColumnName(indexTitle));
//					arrTime.add(cursor4allInform.getColumnName(indexTime));
//					arrUid.add(cursor4allInform.getColumnName(indexUID));
//					arrType.add(cursor4allInform.getColumnName(indexType));
//					arrDetail.add(cursor4allInform.getColumnName(indexDetail));
//				}
//			}
//			List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
//			HashMap<String, Object> map = new HashMap<String, Object>();
//			for (int n = 0; n < 20; n++) {
//				map.put("title", arrTitle.get(0));
//				map.put("time", arrTime.get(0));
//				map.put("user", arrUid.get(0));
//				map.put("type", arrType.get(0));
//			}
//			data.add(map);
//			SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.xlistview_item, 
//					new String[]{"title","time","user","type"}, 
//					new int []{R.id.tv4title,R.id.tv4time,R.id.tv4user,R.id.tv4type});
//			xListView.setAdapter(adapter);
//		}
		
		
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
		
		cursorInform1 = dp.query(sql4gotInform, new String[]{s4UserNameInInform,s4UserNameInInform,"ClaInform"});
		
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
		
		if (cursorInform1.getCount() == 0) {
//			xListView.removeAllViews()
			xListView.setVisibility(View.GONE);
		}else {
			for(cursorInform1.moveToFirst();!cursorInform1.isAfterLast();cursorInform1.moveToNext())
	        {
				arrDetail.add(cursorInform1.getString(cursorInform1.getColumnIndex("detail")));
				arrUid.add(cursorInform1.getString(cursorInform1.getColumnIndex("userid")));
				arrTitle.add(cursorInform1.getString(cursorInform1.getColumnIndex("title")));
				arrTime.add(cursorInform1.getString(cursorInform1.getColumnIndex("time")));
				arrType.add(cursorInform1.getString(cursorInform1.getColumnIndex("type")));
				arrNewsId.add(cursorInform1.getString(cursorInform1.getColumnIndex("news_id")));
				
				HashMap<String, Object> hm = new HashMap<String, Object>();
				hm.put("title", arrTitle.get(cursorInform1.getPosition()));

				hm.put("time", arrTime.get(cursorInform1.getPosition()));
				hm.put("user", arrUid.get(cursorInform1.getPosition()));
				hm.put("type", arrType.get(cursorInform1.getPosition()));
				data.add(hm);
	        }
			sAdapter = new SimpleAdapter(this, data, R.layout.xlistview_item, 
				new String [] {"title","time","user","type"},
				new  int [] {R.id.tv4title,R.id.tv4time,R.id.tv4user,R.id.tv4type});
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
	private void onLoad() {
		xListView.stopRefresh();
		xListView.stopLoadMore();
		xListView.setRefreshTime("刚刚");
	}
}


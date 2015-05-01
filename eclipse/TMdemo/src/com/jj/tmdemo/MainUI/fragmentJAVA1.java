package com.jj.tmdemo.MainUI;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DatabasePart.DatabasePart;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jj.tmdemo.HomeworkDetail;
import com.jj.tmdemo.R;
import com.jj.tmdemo.xlistview.XListView;
import com.jj.tmdemo.xlistview.XListView.IXListViewListener;





public class fragmentJAVA1 extends Fragment implements IXListViewListener{
	View view;
	String [] tc = {"a1","a2","a3","a4","a5","a6","a7","a8","a9","a10","a11"};
	private XListView xListView=null;
//	private ArrayAdapter<String> mAdapter;
	SimpleAdapter sAdapter;
//	private ArrayList<String> items = new ArrayList<String>();
	private Handler mHandler;
//	private int start = 0;
//	private static int refreshCnt = 0;
	
	LinearLayout ll4req;
	
	DatabasePart dp;
//	String gotNews = "select * from News where News.userid in " +
//			"(select NegativeU from FriendShip where PositiveU = ? and FSstate = 1) order by time DESC";
//	String gotFRequest = "select * from News where type = req and " +
//			"News.userid in (select PositiveU from FriendShip where NegativeU = ? and FSstate = 2)";
//	
	String gotSchClass  = "select * from User where nickname = ?";
//	String gotNews2 = "select * from News where negauserid = ?";
	
	String gotNews  = "select * from News where (News.negauserid = ?) or (News.negauserid = (select User.school from User where User.nickname = ?)) or (News.negauserid in (select User.noclass from User where User.nickname = ?))";
//			"select News.* from News,User where (News.negauserid = User.school and User.nickname = ?) " +
//			"or (News.negauserid = User.noclass and User.nickname = ?) or (News.negauserid = ?)" ;
//			//"or (News.negauserid = ?) "	;
//	;+
////			"or (News.negauserid = User.school and User.nickname = ?) ";
//	//+
//			"or (News.negauserid = User.noclass and User.nickname = ?)";
	String userNickName;

	
	Cursor cursor1;
//	Cursor cursor2;
	ArrayList<String> arrUid;
	ArrayList<String> arrTitle;
	ArrayList<String> arrTime;
	ArrayList<String> arrDetail;
	ArrayList<String> arrType;
	ArrayList<String> arrNewsId;
	
	TextView tv1;
	String Oschool;
	String Oclass;
	int OclassIndex;
	int OschoolIndex;
	int reqCount;
	int index;
	Cursor cursor3;
	String newid;
	
	static fragmentJAVA1 newInstance() {
		fragmentJAVA1 newFragment = new fragmentJAVA1();
        return newFragment;

    }
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment1, container, false);

		tv1 = (TextView)view.findViewById(R.id.tv1);
		
		Intent intent1 = getActivity().getIntent();
		Bundle bundle = intent1.getExtras();
		userNickName = bundle.getString("userName");

		dp = new DatabasePart("jim/sjk", "a1.db");
		cursor3 = dp.query(gotSchClass, new String[]{userNickName});
		cursor3.moveToFirst();
		
		OschoolIndex = cursor3.getColumnIndex("school");
		OclassIndex = cursor3.getColumnIndex("noclass");
		String school = cursor3.getString(OschoolIndex);
		System.out.println(school);
		String [] classArray = cursor3.getString(OclassIndex).split(" ");

		ArrayList<String> classList = new ArrayList<String>();
		for (int i = 0; i < classArray.length; i++) {
			classList.add(classArray[i]);
		}
		classList.add(school);
		classList.add(userNickName);
		
		String [] classArray2 = new String[classList.size()]; 
		classArray2 = classList.toArray(classArray2);
		
		dp.DataClose();
//		cursor1 = dp.query(gotNews, new String []{userNickName,userNickName,userNickName});
//		cursor2 = dp.query(gotFRequest, new String[]{userNickName});
//		
		
		arrUid = new ArrayList<String>();
		arrTitle = new ArrayList<String>();
		arrDetail = new ArrayList<String>();
		arrTime = new ArrayList<String>();
		arrType = new ArrayList<String>();
		arrNewsId = new ArrayList<String>();
		
		xListView=(XListView) view.findViewById(R.id.xListView);
		xListView.setPullLoadEnable(true);
//		xListView.setAdapter(sAdapter);
		xListView.setXListViewListener(this);
		
		DropDownGet();
		dp.DataClose();

		mHandler = new Handler();
		
//		List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
//		
//		if (cursor1.getCount() == 0) {
//			
//		}else {
//			for(cursor1.moveToFirst();!cursor1.isAfterLast();cursor1.moveToNext())
//	        {
//				arrDetail.add(cursor1.getString(cursor1.getColumnIndex("detail")));
//				arrUid.add(cursor1.getString(cursor1.getColumnIndex("userid")));
//				arrTitle.add(cursor1.getString(cursor1.getColumnIndex("title")));
//				arrTime.add(cursor1.getString(cursor1.getColumnIndex("time")));
//				arrType.add(cursor1.getString(cursor1.getColumnIndex("type")));
//				arrNewsId.add(cursor1.getString(cursor1.getColumnIndex("news_id")));
//				
//				HashMap<String, Object> hm = new HashMap<String, Object>();
//				hm.put("title", arrTitle.get(cursor1.getPosition()));
//
//				hm.put("time", arrTime.get(cursor1.getPosition()));
//				hm.put("user", arrUid.get(cursor1.getPosition()));
//				hm.put("type", arrType.get(cursor1.getPosition()));
//				data.add(hm);
//	        }
//			
////		
////		if (cursor1.getCount() == 0) {
////			//listview空
////		
////	}else {
////		//读cursor的长度，大于100则读入100条，否则就读cursor的长度条，然后遍历到下一条为空记录，获取数据，处理后填充到listview，
////		int i = 1;
////		for(cursor1.moveToFirst();!cursor1.isAfterLast();cursor1.moveToNext())
////        {
////			i++;
////        }
////		if (i > 20) {
////			for(int j = 0;j < 20 ; j++){
////				arrDetail.add(cursor1.getString(cursor1.getColumnIndex("detail")));
////				arrUid.add(cursor1.getString(cursor1.getColumnIndex("userid")));
////				arrTitle.add(cursor1.getString(cursor1.getColumnIndex("title")));
////				arrTime.add(cursor1.getString(cursor1.getColumnIndex("time")));
////				arrType.add(cursor1.getString(cursor1.getColumnIndex("type")));
////			}
//////			for (int k = 0; k < 20; k++) {
//////				//填充数据
//////			}
////		}else {
////			for(cursor1.moveToFirst();!cursor1.isAfterLast();cursor1.moveToNext())
////	        {
////				arrDetail.add(cursor1.getString(cursor1.getColumnIndex("detail")));
////				arrUid.add(cursor1.getString(cursor1.getColumnIndex("userid")));
////				arrTitle.add(cursor1.getString(cursor1.getColumnIndex("title")));
////				arrTime.add(cursor1.getString(cursor1.getColumnIndex("time")));
////				arrType.add(cursor1.getString(cursor1.getColumnIndex("type")));
////	        }
////		}
////		
//		for (int n = 0; n < 20 + reqCount; n++) {
//
//		}
//		sAdapter = new SimpleAdapter(getActivity(), data, R.layout.xlistview_item, 
//				new String [] {"title","time","user","type"},
//				new  int [] {R.id.tv4title,R.id.tv4time,R.id.tv4user,R.id.tv4type});
//	}		
		
		
		
		
//		if (cursor2.getCount() == 0) {
//			tv1.setVisibility(View.GONE);
//		}else {
//			for (cursor2.moveToFirst();!cursor2.isAfterLast();cursor2.moveToNext()) {
//				ArrayList<String> requestList = new ArrayList<String>();
//				index  = cursor2.getColumnIndex("PositiveU");
//				
//				requestList.add(cursor2.getColumnName(index));
//				arrDetail.add("");
//				arrUid.add(cursor2.getColumnName(index));
//				//  + "请求加您为好友！"
//				arrTitle.add(cursor2.getColumnName(index));
//				arrTime.add("");
//				arrType.add("req");
//				reqCount  = cursor2.getCount();
//			}			
//		
//		
//		//cursor1.getString(0).equals("")
//
//		
//
//		}
		

//		items.add("1");
//		items.add("2");
//		items.add("3");
//		items.add("4");
//		items.add("5");
		
		
//		mAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.xlistview_item, items);

		//在此读消息表的数据 


		

		
		//
		

		
		xListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
//				TextView tv = (TextView)xListView.getChildAt(position)
//						.findViewById(R.id.tv4title);
//				TextView tv2 = (TextView)xListView.getChildAt(position)
//						.findViewById(R.id.tv4type);
				
//				TextView tv3 = (TextView)xListView.getChildAt(position)
//						.findViewById(R.id.tv4user);
				
//				String thisTitle = tv.getText().toString();
//				String thisType = tv2.getText().toString();
				
				if (arrType.get(position-1).equals("req")) {
					ll4req = (LinearLayout)getActivity().getLayoutInflater().inflate(R.layout.xml4req, null);
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setIcon(R.drawable.ic_launcher).setTitle("好友请求")
					.setView(ll4req)
					.setPositiveButton("同意添加",	 new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

							TextView tv = (TextView)xListView.findViewById(R.id.tv4user);
							String theWho = tv.getText().toString();
							
							dp = new DatabasePart("jim/sjk", "a1.db");
							
							Cursor cursor3 ;
//							Cursor cursor4;
							String gotID = "select fs_id from FriendShip where PositiveU = ? and NegativeU = ?";
//							String gotNews2Delete = "select news_id from News where negauserid = ?";
//							cursor4 = dp.query(gotNews2Delete, new String[]{userNickName});
//							+theWho+" and NegativeU = ?";
							cursor3 = dp.query(gotID, new String[]{theWho,userNickName});
							cursor3.moveToFirst();
							String id  = cursor3.getString(0);
							
							ContentValues cv = new ContentValues();
							cv.put("PositiveU", userNickName);
							cv.put("NegativeU", theWho);
							cv.put("FSstate", 1);
							dp.insert("FriendShip", cv);
							ContentValues cv2 = new ContentValues();
							cv2.put("FSstate", 1);
							dp.updatebyid("FriendShip", cv2, id);
							
							for (int i = 0; i < arrUid.size(); i++) {
								if (arrUid.get(i).equals(theWho) && arrType.get(i).equals("req")) {
									System.out.println(arrUid.get(i));
									System.out.println(arrType.get(i));
									System.out.println(arrNewsId.get(i));
									newid = arrNewsId.get(i);
									System.out.println(newid);
									dp.deletebyid("News", newid);
									dp.DataClose();
									DropDownGet();
								}
							}


							//需要刷新好友请求吗？
							
						}
					//加拒绝按钮吗？
					})
					.setNegativeButton("拒绝", null);
//					new DialogInterface.OnClickListener() {
//						
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							// TODO Auto-generated method stub
//							
//						}
//					});
					builder.create().show();

				}else {
					//作业和通知页面，统一，
//					String sql4detail = "select detail from News where title = ?";
//					
//					Cursor c1 = dp.query(sql4detail, new String[]{sql4detail});
//					String thisDetail = c1.getString(0);
//					
					
					String newid = arrNewsId.get(position-1);
					
					Intent intent2detail = new Intent();
					
					intent2detail.setClass(getActivity(), HomeworkDetail.class);
					
					intent2detail.putExtra("type",arrType.get(position-1));
					intent2detail.putExtra("newid",newid);
					intent2detail.putExtra("whoAmI", userNickName);
					
					startActivity(intent2detail);
//					String this
//					dp.DataClose();
				}
			}
		});
		
//		String [] hwnames = (String[]) this.getResources().getStringArray(R.array.hwname4t);
//		String [] hwdates = (String[]) this.getResources().getStringArray(R.array.hwdate4t);
//		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
//		for (int i = 0; i < hwnames.length; i ++ ){
//			Map<String, Object> listItem = new HashMap<String,Object>();
//			listItem.put("hwName", hwnames[i]);
//			listItem.put("hwDate", hwdates[i]);
//			listItem.put("more", R.drawable.right4more);
//			listItems.add(listItem);
//		}
//		SimpleAdapter sAdapter4th = new SimpleAdapter(getActivity(), listItems, R.layout.itemlayout4t_homework, 
//				new String[] {"hwName","hwDate","more"}, new int[] {R.id.hwName,R.id.hwDate,R.id.more });
//		ListView list4th = (ListView)view.findViewById(R.id.pagerlist4th);
//		list4th.setAdapter(sAdapter4th);
//		
//		list4th.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				// TODO Auto-generated method stub
//
//			}
//			
//		});
		
		
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
				System.out.println("refresh");
//				start = ++refreshCnt;
//				items.clear();
				arrDetail.clear();
				arrTime.clear();
				arrTitle.clear();
				arrType.clear();
				arrUid.clear();
				arrNewsId.clear();
				
				DropDownGet();
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
				DropDownGet();
				dp.DataClose();
//				sAdapter.notifyDataSetChanged();
//				xListView.setAdapter(sAdapter);
				onLoad();
			}
		}, 2000);
	}
//	private void geneItems() {
//		//在此处获取item的值
//		for (int i = 0; i != 5; ++i) {
//			items.add("refresh cnt " + (++start));
//		}
//	}
	public void DropDownGet(){
		dp = new DatabasePart("jim/sjk", "a1.db");
		cursor1 = dp.query(gotNews, new String []{userNickName,userNickName,userNickName});
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
		
		if (cursor1.getCount() == 0) {
//			xListView.removeAllViews()
			xListView.setVisibility(View.GONE);
		}else {
			for(cursor1.moveToFirst();!cursor1.isAfterLast();cursor1.moveToNext())
	        {
				arrDetail.add(cursor1.getString(cursor1.getColumnIndex("detail")));
				arrUid.add(cursor1.getString(cursor1.getColumnIndex("userid")));
				arrTitle.add(cursor1.getString(cursor1.getColumnIndex("title")));
				arrTime.add(cursor1.getString(cursor1.getColumnIndex("time")));
				arrType.add(cursor1.getString(cursor1.getColumnIndex("type")));
				arrNewsId.add(cursor1.getString(cursor1.getColumnIndex("news_id")));
				
				HashMap<String, Object> hm = new HashMap<String, Object>();
				hm.put("title", arrTitle.get(cursor1.getPosition()));

				hm.put("time", arrTime.get(cursor1.getPosition()));
				hm.put("user", arrUid.get(cursor1.getPosition()));
				hm.put("type", arrType.get(cursor1.getPosition()));
				data.add(hm);
	        }
			for (int n = 0; n < 20 + reqCount; n++) {
	
			}
			sAdapter = new SimpleAdapter(getActivity(), data, R.layout.xlistview_item, 
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

}

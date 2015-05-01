package com.jj.tmdemo;

import java.util.ArrayList;

import DatabasePart.DatabasePart;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

public class HomeworkDetail extends Activity{
	
//	String newid;
	DatabasePart dp;
	String targetNewsId;
	String whoAmI;
	String type;
	Cursor cursor4GotNews;
	String sql4GotNews= "select * from News where news_id = ?";
	
	
	ArrayList<String> titleList;
	ArrayList<String> pubUserList;
	ArrayList<String> detailList;
	ArrayList<String> goodmanList;
	
	String Sgoodman;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.homework_detail);
		Intent intent = getIntent();
		targetNewsId = intent.getStringExtra("newid");
		whoAmI = intent.getStringExtra("whoAmI");
		type = intent.getStringExtra("type");
//		String title = intent.getStringExtra("Title");
//		String detail = intent.getStringExtra("Detail");
		
		
		TextView tv4title = (TextView)findViewById(R.id.title);
		TextView tv4detail = (TextView)findViewById(R.id.detail);
		TextView tv4pubUser = (TextView)findViewById(R.id.pubUser);
		TextView tv4good = (TextView)findViewById(R.id.good);
		TextView tv4saySth = (TextView)findViewById(R.id.saySth);
		TextView tv4showGood = (TextView)findViewById(R.id.showGood);
		TextView tv4pubTime = (TextView)findViewById(R.id.pubTime);
		
		titleList = new ArrayList<String>();
		pubUserList = new ArrayList<String>();
		detailList = new ArrayList<String>();
		goodmanList = new ArrayList<String>();
		
		
//		if (newidList.size() == 1) {
//			targetNewsId = newidList.get(0);
//		}else {
//			
//		}
//		
		dp = new DatabasePart("jim/sjk", "a1.db");
		cursor4GotNews = dp.query(sql4GotNews, new String[]{targetNewsId});
		cursor4GotNews.moveToFirst();
		tv4title.setText(cursor4GotNews.getString(cursor4GotNews.getColumnIndex("title")));
		tv4detail.setText(cursor4GotNews.getString(cursor4GotNews.getColumnIndex("detail")));
		tv4pubUser.setText(cursor4GotNews.getString(cursor4GotNews.getColumnIndex("userid")));
		tv4pubTime.setText(cursor4GotNews.getString(cursor4GotNews.getColumnIndex("time")));
		tv4showGood.setText(cursor4GotNews.getString(cursor4GotNews.getColumnIndex("goodman")));
		
		Sgoodman = cursor4GotNews.getString(cursor4GotNews.getColumnIndex("goodman"));
		dp.DataClose();
		
		if (type.equals("SchInform") || type.equals("ClaInform")) {
			
		}else {
			tv4good.setVisibility(View.GONE);
		}
		tv4good.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (Sgoodman.equals(null)) {
					Sgoodman = whoAmI + " 提交了作业!";
				}else {
					Sgoodman = whoAmI + "," + Sgoodman;
				}
				ContentValues cv = new ContentValues();
				cv.put("goodman", Sgoodman);
				dp = new DatabasePart("jim/sjk", "a1.db");
				dp.updatebyid("News", cv, targetNewsId);
				dp.DataClose();
			}
		});
	}
}

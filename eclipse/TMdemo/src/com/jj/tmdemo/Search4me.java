package com.jj.tmdemo;

import DatabasePart.DatabasePart;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

/*
 * 查找功能基本实现，但是对查找结果点击之后的具体操作未做。
 */

public class Search4me extends Activity implements SearchView.OnQueryTextListener{
	
	private SearchView sv;
	private TextView tv;
	//private  String[] searchResult = {"aaaaa","bbbbb","ccccc"};;
	DatabasePart dp ;
	String searchsql;
	String resultName;
	
	EditText inputMy ;
	Button doMy;
	TextView showMy;
	String iGet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.search);
		
		ImageView back2mainfromsearch = (ImageView)findViewById(R.id.back2mainfromsearch);
		back2mainfromsearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
		searchsql = "select nickname from User where nickname = ?";
		
		tv = (TextView)findViewById(R.id.tv4search);
		sv = (SearchView)findViewById(R.id.sv4search);
		
		inputMy = (EditText)findViewById(R.id.inputMy);
		doMy = (Button)findViewById(R.id.doMy);
		showMy = (TextView)findViewById(R.id.showMy);
		
		
		
		doMy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dp = new DatabasePart("jim/sjk2", "a1.db");
				iGet = inputMy.getText().toString().trim();
				Cursor cursor1 = dp.query(searchsql, new String[]{iGet});
				cursor1.moveToFirst();
				String my = cursor1.getString(0);
				System.out.println(my);
				if (cursor1.getString(0).equals(iGet)) {
					showMy.setText(iGet);
				}
				else {
					System.out.println("zz");
				}
				dp.DataClose();
			}
		});
//		cursor1 = dp.query(searchsql, new String[] {iGet});
//		if (cursor1.moveToFirst()) {
//			showMy.setText(iGet);
//		}
//		else {
//			System.out.println("zz");
//		}
		//lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,searchResult));
		
		//lv.setTextFilterEnabled(true);
		
		sv.setIconifiedByDefault(false);
		sv.setOnQueryTextListener(this);
		sv.setSubmitButtonEnabled(true);
		sv.setQueryHint("输入好友昵称");
		
		
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		//点击搜索后的操作
		String input = query;
//		Toast.makeText(this, "您的选择是：" + query, Toast.LENGTH_SHORT).show();
////		dp = new DatabasePart("jim/sjk2", "a1.db");
//		System.out.println("访问了数据库");
//		cursor1 = dp.query(searchsql, new String[] {query});
//		System.out.println("访问了数据库2");
//		if (cursor1.moveToFirst()) {
//			//String[] searchResult = {query};
//			//lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,searchResult));
//			tv.setText(input);
//			System.out.println("访问了数据库3");
//		}
//		else {
//			Toast.makeText(this, "抱歉，找不到该用户！" + query, Toast.LENGTH_SHORT).show();
//			
//		}
		
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		
//		if (TextUtils.isEmpty(newText)) {
//			lv.clearTextFilter();
//		}
//		else {
//			lv.setFilterText(newText);
//		}

//		
		return false;
	}
	
}

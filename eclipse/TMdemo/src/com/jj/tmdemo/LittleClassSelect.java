package com.jj.tmdemo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class LittleClassSelect extends Activity implements SearchView.OnQueryTextListener{

	private ListView lv;
	private MyAdapter4ClassLIST mAdapter;

	private ArrayList<String> list;
	private Button bt_selectall;
	private Button bt_submit;
	private Button bt_deselectall;
	Context context;
	DatabasePart dp;


	SearchView sv;
	String []classA = null;
	ArrayList<String> classList = new ArrayList<String>();
	ArrayList<String> classResult = new ArrayList<String>();
	
	String userNameInLittle;
	String sql4GotClass = "select noclass from User where nickname = ?";
	
	Cursor c1;
	
	
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bigclasslist);

		context = this;
		
		Intent intent1 = getIntent();
		userNameInLittle = intent1.getStringExtra("uname");
		System.out.println(userNameInLittle);
		
		dp = new DatabasePart("jim/sjk", "a1.db");
		c1 = dp.query(sql4GotClass, new String[]{userNameInLittle});
		for (c1.moveToFirst();!c1.isAfterLast();c1.moveToNext()) {
			classList.add(c1.getString(c1.getColumnIndex("noclass")));
		}
		dp.DataClose();
//		classA = intent1.getStringArrayExtra("classA");
		
		
		lv = (ListView) findViewById(R.id.lv);
		bt_selectall = (Button) findViewById(R.id.bt_selectall);
		bt_submit = (Button) findViewById(R.id.bt_SUBMITselectall);
		bt_deselectall = (Button) findViewById(R.id.bt_deselectall);

//		list = new ArrayList<String>();
//		for (int j = 0; j < classA.length; j++) {
//			targetList.add(classA[j]);
//		}
		
		mAdapter = new MyAdapter4ClassLIST(classList, context);
		lv.setAdapter(mAdapter);
		
		sv = (SearchView)findViewById(R.id.sv);
	    	
//		arr = new ArrayList<String>();

		lv.setTextFilterEnabled(true);
 

		bt_selectall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				for (int i = 0; i < list.size(); i++) {
					MyAdapter4ClassLIST.getIsSelected().put(i, true);
				}

				dataChanged();

			}
		});

		bt_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				String result [] = new String[classList.size()];
//				String resultZZ = new String();
//				 result  = classList.toArray(result);
//				for (int i = 0; i < result.length; i++) {
//					resultZZ = resultZZ + " " + result[i];
//				}
//				System.out.println(resultZZ);
				Intent intent = new Intent();
				intent.putStringArrayListExtra("resultC", classResult);
//				intent.putExtra("resultC", resultZZ);
				LittleClassSelect.this.setResult(3, intent);
				LittleClassSelect.this.finish();
			}
		});


		bt_deselectall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				for (int i = 0; i < list.size(); i++) {
					if (MyAdapter4ClassLIST.getIsSelected().get(i)) {
						MyAdapter4ClassLIST.getIsSelected().put(i, false);

					} else {
						MyAdapter4ClassLIST.getIsSelected().put(i, true);

					}
					

				}

				dataChanged();
			}
		});


		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				ViewHolder holder = (ViewHolder) arg1.getTag();

				holder.cb.toggle();

				MyAdapter4ClassLIST.getIsSelected().put(arg2, holder.cb.isChecked());

				if (holder.cb.isChecked() == true) {

				} else {

				}


				TextView tvv = (TextView)lv.getChildAt(arg2).findViewById(R.id.item_tv);
				String StrClassItem = tvv.getText().toString();
				System.out.println(StrClassItem);
				classResult.add(StrClassItem);
			}
		});
		sv.setIconifiedByDefault(false);
		sv.setOnQueryTextListener(this);
		sv.setSubmitButtonEnabled(true);
		sv.setQueryHint("输入好友昵称");

	}

	private void dataChanged() {

		mAdapter.notifyDataSetChanged();
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		if (TextUtils.isEmpty(newText)) {
			lv.clearTextFilter();
		}
		else {
			lv.setFilterText(newText);
			mAdapter.notifyDataSetChanged();
		}
		return true;
	}

}

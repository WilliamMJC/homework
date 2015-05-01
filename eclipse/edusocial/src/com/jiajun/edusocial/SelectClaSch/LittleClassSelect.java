package com.jiajun.edusocial.SelectClaSch;

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

import com.jiajun.edusocial.R;
import com.jiajun.edusocial.R.id;
import com.jiajun.edusocial.R.layout;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;

//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
//import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
//import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
//import android.widget.SearchView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
// implements SearchView.OnQueryTextListener
public class LittleClassSelect extends Activity{

	ListView lv;
	private MyAdapter4ClassLIST mAdapter;

	private ArrayList<String> list;

	Context context;
	HttpEntity httpEntity;
	HttpResponse httpResponse;
	
	TextView title;
	TextView submit;
	
	String userNameInLittle;

//	SearchView sv;
	String []classA = null;
	ArrayList<String> classList = new ArrayList<String>();
	ArrayList<String> classResult = new ArrayList<String>();
	ArrayList<String> classTemp;
	
	ImageView back;
	LinearLayout loadLL;
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			Bundle bd = msg.getData();
			classList = bd.getStringArrayList("noClassFromRun");
//			System.out.println("ok de classlist = " + classList);
			loadLL.setVisibility(View.GONE);
			mAdapter = new MyAdapter4ClassLIST(classList, context);
			lv.setAdapter(mAdapter);
		}
	};
	
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bigclasslist2);

		context = this;
		loadLL = (LinearLayout)findViewById(R.id.loadLL);
		Intent intent1 = getIntent();
		userNameInLittle = intent1.getStringExtra("uname");
		System.out.println(userNameInLittle);
		lv = (ListView)findViewById(R.id.lv4schoollist);
		back = (ImageView)findViewById(R.id.back2mainfromsearch);
//		sv = (SearchView)findViewById(R.id.sv4search);
		title = (TextView)findViewById(R.id.title4t1);
		submit = (TextView)findViewById(R.id.pubBtnPhoto);
		title.setText("选择班级");
		submit.setText("选定");
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putStringArrayListExtra("resultC", null);
	            setResult(RESULT_OK, intent); 
//				SelectSchool.this.setResult(4, intent);
				LittleClassSelect.this.finish();
			}
		});
		new Thread(){
			public void run() {
				HttpGet httpGet = new HttpGet("http://zeng.shaoning.net/edusocial/User.json");
				HttpClient httpClient = new DefaultHttpClient();
				Message msg2 = new Message();
				//Looper.loop();
				InputStream inputStream = null;
//				InputStream is = null;
				try {
					//zheli 这里

					httpResponse = httpClient.execute(httpGet);
					httpEntity = httpResponse.getEntity();
					inputStream = httpEntity.getContent();
					//GB2312
					BufferedReader reader2 = new BufferedReader(new InputStreamReader(inputStream,"GB2312"));
					String result2 = "";
					String line2 = "";
					while((line2 = reader2.readLine()) != null){
						result2 = result2 + line2;
					}

//		            URL url = new URL(hisTx);
//		            URLConnection con = (URLConnection) url.openConnection();
//		            InputStream stream = con.getInputStream();
//		            Bitmap bitmap = BitmapFactory.decodeStream(stream);
//		            iv.setImageBitmap(bitmap);
//		            stream.close();
					
					JSONObject JO4UserJson = new JSONObject(result2);
					JSONArray JA4User = JO4UserJson.getJSONArray("User");
					classTemp = new ArrayList<String>();
					for (int q = 0; q < JA4User.length(); q++) {
						JSONObject JO4SingleUser = JA4User.getJSONObject(q);
						if (JO4SingleUser.getString("nickname").equals(userNameInLittle)) {
//							hisIdentityInRun = JO4SingleUser.getString("identity");
//							hisNickNameInRun = JO4SingleUser.getString("nickname");
//							hisSchoolInRun = JO4SingleUser.getString("school");
							JSONArray JA4Cla = JO4SingleUser.getJSONArray("noclass");
							for (int i = 0; i < JA4Cla.length(); i++) {
								classTemp.add(JA4Cla.getString(i));
							}
						}
					}
					Bundle bNor = new Bundle();
//					bNor.putString("hisIdentity", hisIdentityInRun);
//					bNor.putString("hisNickName", hisNickNameInRun);
//					bNor.putString("hisSchool", hisSchoolInRun);
					bNor.putStringArrayList("noClassFromRun", classTemp);
					System.out.println(classTemp + "hahaa a ?a ?");
					
//					bNor.putParcelable("bitmap4", bitmap);
					msg2.setData(bNor);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					try{//这里zheli
						inputStream.close();
//						is.close();
//						httpEntity.getContent().close();
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}					
				handler.sendMessage(msg2);

			}
		}.start();
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
////			String result [] = new String[classList.size()];
////			String resultZZ = new String();
////			 result  = classList.toArray(result);
////			for (int i = 0; i < result.length; i++) {
////				resultZZ = resultZZ + " " + result[i];
////			}
////			System.out.println(resultZZ);
			Intent intent = new Intent();
			intent.putStringArrayListExtra("resultC", classResult);
//			intent.putExtra("resultC", resultZZ);
			LittleClassSelect.this.setResult(3, intent);
			LittleClassSelect.this.finish();
			}
		});
//		classA = intent1.getStringArrayExtra("classA");
		
		
//		lv = (ListView) findViewById(R.id.lv);
//		bt_selectall = (Button) findViewById(R.id.bt_selectall);
//		bt_submit = (Button) findViewById(R.id.bt_SUBMITselectall);
//		bt_deselectall = (Button) findViewById(R.id.bt_deselectall);

//		list = new ArrayList<String>();
//		for (int j = 0; j < classA.length; j++) {
//			targetList.add(classA[j]);
//		}
		
//		mAdapter = new MyAdapter4ClassLIST(classList, context);
//		lv.setAdapter(mAdapter);
		
//		sv = (SearchView)findViewById(R.id.sv);
	    	
//		arr = new ArrayList<String>();

		lv.setTextFilterEnabled(true);
 

//		bt_selectall.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				for (int i = 0; i < list.size(); i++) {
//					MyAdapter4ClassLIST.getIsSelected().put(i, true);
//				}
//
//				dataChanged();
//
//			}
//		});
//
//		bt_submit.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
////				String result [] = new String[classList.size()];
////				String resultZZ = new String();
////				 result  = classList.toArray(result);
////				for (int i = 0; i < result.length; i++) {
////					resultZZ = resultZZ + " " + result[i];
////				}
////				System.out.println(resultZZ);
//				Intent intent = new Intent();
//				intent.putStringArrayListExtra("resultC", classResult);
////				intent.putExtra("resultC", resultZZ);
//				LittleClassSelect.this.setResult(3, intent);
//				LittleClassSelect.this.finish();
//			}
//		});
//
//
//		bt_deselectall.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				for (int i = 0; i < list.size(); i++) {
//					if (MyAdapter4ClassLIST.getIsSelected().get(i)) {
//						MyAdapter4ClassLIST.getIsSelected().put(i, false);
//
//					} else {
//						MyAdapter4ClassLIST.getIsSelected().put(i, true);
//
//					}
//					
//
//				}
//
//				dataChanged();
//			}
//		});


		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				ViewHolder holder = (ViewHolder) arg1.getTag();

				holder.cbclass.toggle();

				MyAdapter4ClassLIST.getIsSelected().put(arg2, holder.cbclass.isChecked());

				if (holder.cbclass.isChecked() == true) {

				} else {

				}


				TextView tvv = (TextView)lv.getChildAt(arg2).findViewById(R.id.item_tv);
				String StrClassItem = tvv.getText().toString();
				System.out.println(StrClassItem);
				classResult.add(StrClassItem);
			}
		});
//		sv.setIconifiedByDefault(false);
//		sv.setOnQueryTextListener(this);
//		sv.setSubmitButtonEnabled(true);
//		sv.setQueryHint("输入好友昵称");

	}

//	private void dataChanged() {
//
//		mAdapter.notifyDataSetChanged();
//	}
//
//	@Override
//	public boolean onQueryTextSubmit(String query) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean onQueryTextChange(String newText) {
//		// TODO Auto-generated method stub
//		if (TextUtils.isEmpty(newText)) {
//			lv.clearTextFilter();
//		}
//		else {
//			lv.setFilterText(newText);
//			mAdapter.notifyDataSetChanged();
//		}
//		return true;
//	}
	@Override  
    public void onBackPressed() {  
        //A跳转到B如果采用的是startActivityForResult这种方式，  
        //如果不重写返回键，程序不知道要返回给Activity1什么内容就会报错  
        //有super.onBackPressed() 时 不能把在此方法中设置的 intent 传回  
        //上一个Activity ，因此 去掉super.onBackPressed()  在末尾加上finish  
          
            Intent intent=new Intent();    
            intent = getIntent().putStringArrayListExtra("resultC", null);  
            setResult(RESULT_OK, intent);    
            finish();    
    } 
}

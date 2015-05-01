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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class SelectSchool extends Activity implements SearchView.OnQueryTextListener{
	
	private SearchView sv;
//	private TextView tv;
	private  String[] searchResult = {"aaaaa","bbbbb","ccccc"};

	ArrayList<String> mydarling;
//	ArrayAdapter<String> adapter4school;
//	Spinner mySpinner4School;
	Context context;
	private ListView lv4schoollist;
	HttpResponse httpResponse;
	HttpEntity httpEntity;
	String [] S4schoolName = null;
	ArrayList<String> schoolName;
	TextView title4t1;
	TextView sousuo;
	LinearLayout loadLL;
	
	Handler handler =new Handler(){
	@Override
	//当有消息发送出来的时候就执行Handler的这个方法
	public void handleMessage(Message msg){
	//super.handleMessage(msg);
		Bundle newb = msg.getData();
		mydarling = newb.getStringArrayList("schoolN");
//		System.out.println(mydarling);
		loadLL.setVisibility(View.GONE);
    	lv4schoollist.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,mydarling));
//		adapter4school = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, mydarling);
//		adapter4school.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		mySpinner4School.setAdapter(adapter4school);
		//android.R.layout.simple_list_item_1
		
	//处理UI
	}
};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.bigschoollist);
		context = this;
		loadLL = (LinearLayout)findViewById(R.id.loadLL);
		//新线程，获取服务器json文件的学校名称，得到字符串或者arraylist。填充listview，
		sousuo = (TextView)findViewById(R.id.pubBtnPhoto);
		sousuo.setVisibility(View.GONE);
		title4t1 = (TextView)findViewById(R.id.title4t1);
		title4t1.setText("选择学校");
		ImageView back2mainfromsearch = (ImageView)findViewById(R.id.back2mainfromsearch);
		back2mainfromsearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("result", "false");
	            setResult(RESULT_OK, intent); 
//				SelectSchool.this.setResult(4, intent);
				SelectSchool.this.finish();

			}
		});
		new Thread(){
			@Override
			public void run(){
			//你要执行的方法
			//执行完毕后给handler发送一个空消息
				HttpGet httpGet = new HttpGet("http://zeng.shaoning.net/edusocial/school.json");

				HttpClient httpClient = new DefaultHttpClient();
				Message msg = new Message();
				//Looper.loop();
				InputStream inputStream = null;
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
					JSONObject myJO = new JSONObject(result);
					JSONArray myJA = myJO.getJSONArray("school");
					JSONObject myJOSingleObject;
//					JSONArray myJOSingleArray;
					schoolName = new ArrayList<String>();
//					className = new ArrayList<ArrayList<String>>();
					for (int i = 0; i < myJA.length(); i++) {
						myJOSingleObject = myJA.getJSONObject(i);
						schoolName.add(myJOSingleObject.getString("name"));
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
					S4schoolName = new String [schoolName.size()];
					S4schoolName = schoolName.toArray(S4schoolName);
					Bundle b = new Bundle();
					//b.putCharSequenceArrayList("schoolAL", schoolName);
					b.putStringArrayList("schoolN", schoolName);
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
				handler.sendMessage(msg);

			}
		}.start();
		
		

		
		

    	sv = (SearchView)findViewById(R.id.sv4search);
    	
    	lv4schoollist = (ListView)findViewById(R.id.lv4schoollist);
//    	List<HashMap<String, Object>> data=new ArrayList<HashMap<String,Object>>();   
//        for(int i=0;i < 10;i++){   
//          HashMap<String, Object> hashMap=new HashMap<String, Object>();  
//          hashMap.put("id", i);  
//          data.add(hashMap);  
//  }   
    	
//    	SimpleAdapter adapter4sl = new SimpleAdapter(this,data,R.layout.listitem_school,
//    			new String[]{"id"}, new int[]{R.id.item_tv});

		lv4schoollist.setTextFilterEnabled(true);
		lv4schoollist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				//每一个学校点击之后记录，并返回前activity，
//				TextView resultLV = (TextView)view.findViewById(android.R.id.item_tv);
//				String result = resultLV.getText().toString();
//				Intent intent = new Intent();
//				intent.putExtra("result", result);
//				SelectSchool.this.setResult(1, intent);
//				SelectSchool.this.finish();
				String result = (String)lv4schoollist.getItemAtPosition(position);
				
				String result2 = mydarling.get(position).toString();
				Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
//				Toast.makeText(getApplicationContext(), result2  +"2", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.putExtra("result", result);
				SelectSchool.this.setResult(1, intent);
				SelectSchool.this.finish();
			}
		});
		
		
		sv.setIconifiedByDefault(false);
		sv.setOnQueryTextListener(this);
		sv.setSubmitButtonEnabled(true);
		sv.setQueryHint("输入学校名称");
		
		
	}

	@Override  
    public void onBackPressed() {  
        //A跳转到B如果采用的是startActivityForResult这种方式，  
        //如果不重写返回键，程序不知道要返回给Activity1什么内容就会报错  
        //有super.onBackPressed() 时 不能把在此方法中设置的 intent 传回  
        //上一个Activity ，因此 去掉super.onBackPressed()  在末尾加上finish  
          
            Intent intent=new Intent();    
            intent = getIntent().putExtra("result", "false");  
            setResult(RESULT_OK, intent);    
            finish();    
    }  
//	@Override
//	public boolean onQueryTextSubmit(String query) {
//		// TODO Auto-generated method stub
//		//点击搜索后的操作
//		String input = query;
////		Toast.makeText(this, "您的选择是：" + query, Toast.LENGTH_SHORT).show();
//////		dp = new DatabasePart("jim/sjk2", "a1.db");
////		System.out.println("访问了数据库");
////		cursor1 = dp.query(searchsql, new String[] {query});
////		System.out.println("访问了数据库2");
////		if (cursor1.moveToFirst()) {
////			//String[] searchResult = {query};
////			//lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,searchResult));
////			tv.setText(input);
////			System.out.println("访问了数据库3");
////		}
////		else {
////			Toast.makeText(this, "抱歉，找不到该用户！" + query, Toast.LENGTH_SHORT).show();
////			
////		}
//		
//		return false;
//	}
//
//	@Override
//	public boolean onQueryTextChange(String newText) {
//		// TODO Auto-generated method stub
//		
//		if (TextUtils.isEmpty(newText)) {
//			lv4schoollist.clearTextFilter();
//		}
//		else {
//			lv4schoollist.setFilterText(newText);
//		}
//
////		
//		return true;
//	}
	 @Override
	 public boolean onQueryTextChange(String newText) {
	  ArrayList<String> oklist = searchItem(newText);
	  updateLayout(oklist);
	  return false;
	 }

	 @Override
	 public boolean onQueryTextSubmit(String query) {
	  // TODO Auto-generated method stub
	  return false;
	 }
	 
	 
//	 public Object[] searchItem(String name) {
//	  ArrayList<String> mSearchList = new ArrayList<String>();
//	  for (int i = 0; i < mydarling.size(); i++) {
//	   int index = mydarling.get(i).indexOf(name);
//	   // 存在匹配的数据
//	   if (index != -1) {
//	    mSearchList.add(mydarling.get(i));
//	   }
//	  }
//	  return mSearchList.toArray();
//	 }
	 public ArrayList<String> searchItem(String name) {
		  ArrayList<String> mSearchList = new ArrayList<String>();
		  for (int i = 0; i < mydarling.size(); i++) {
		   int index = mydarling.get(i).indexOf(name);
		   // 存在匹配的数据
		   if (index != -1) {
		    mSearchList.add(mydarling.get(i));
		   }
		  }
		  return mSearchList;
		 }

//	 public void updateLayout(Object[] obj) {
//	  listView.setAdapter(new ArrayAdapter<Object>(getApplicationContext(),
//	    android.R.layout.simple_expandable_list_item_1, obj));
//	 }
	 public void updateLayout(ArrayList<String> obj) {
//		  listView.setAdapter(new ArrayAdapter<Object>(getApplicationContext(),
//		    android.R.layout.simple_expandable_list_item_1, obj));
	    	lv4schoollist.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,obj));
		 }
	
}

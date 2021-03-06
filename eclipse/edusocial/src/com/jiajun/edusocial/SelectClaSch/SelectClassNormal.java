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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class SelectClassNormal extends Activity implements SearchView.OnQueryTextListener{
	
	private SearchView sv;
//	private TextView tv;
	private  String[] searchResult = {"aaaaa","bbbbb","ccccc"};;
	HttpEntity httpEntity;
	HttpResponse httpResponse;
	String schoolP;
	ArrayList<String> 	className = new ArrayList<String>();
	private ListView lv4classlist;
	ArrayList<String> targetClassList;
	JSONArray targetClassJA;
	ArrayList<String> targetList;
	Context context;
	TextView sousuo;
	TextView title;
	LinearLayout loadLL ;
	
	Handler handler4class =new Handler(){
	@Override
	//当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg){
		//super.handleMessage(msg);
			
			Bundle b4tc = msg.getData();
			className = b4tc.getStringArrayList("target");
//			System.out.println(targetList);
			loadLL.setVisibility(View.GONE);
	    	lv4classlist.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,className));
		//处理UI
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.bigclasslist_normal);
		context = this;
		
		loadLL = (LinearLayout)findViewById(R.id.loadLL);
		
		//新线程，获取服务器json文件的学校名称，得到字符串或者arraylist。填充listview，
		Intent intent1 = getIntent();
//		Bundle b1 = intent1.getBundleExtra("ssch");
		schoolP = intent1.getStringExtra("Ssch");
		
		sousuo = (TextView)findViewById(R.id.pubBtnPhoto);
		sousuo.setVisibility(View.GONE);
		title = (TextView)findViewById(R.id.title4t1);
		title.setText("选择班级");
		
		ImageView back2mainfromsearch = (ImageView)findViewById(R.id.back2mainfromsearch);
		back2mainfromsearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("result", "false");
	            setResult(RESULT_OK, intent); 
//				SelectSchool.this.setResult(4, intent);
				SelectClassNormal.this.finish();
			}
		});
		
		
      new Thread(){
			@Override
			public void run(){
			//你要执行的方法
			//执行完毕后给handler发送一个空消息
				HttpGet httpGet = new HttpGet("http://zeng.shaoning.net/edusocial/class.json");

				HttpClient httpClient = new DefaultHttpClient();
				Message msg2 = new Message();
				//Looper.loop();
				InputStream inputStream = null;
				try {
					httpResponse = httpClient.execute(httpGet);
					httpEntity = httpResponse.getEntity();
					inputStream = httpEntity.getContent();
					//GB2312
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"GB2312"));
					String result = "";
					String line = "";
					while((line = reader.readLine()) != null){
						result = result + line;
					}
					JSONObject myJO2 = new JSONObject(result);
					JSONArray myJA2 = myJO2.getJSONArray("class");
					JSONObject myJOSingleObject2;
					JSONArray myJOSingleArray2;
//					schoolName = new ArrayList<String>();

					targetClassList = new ArrayList<String>();
					for (int i = 0; i < myJA2.length(); i++) {
						myJOSingleObject2 = myJA2.getJSONObject(i);
//						if (myJOSingleObject2.getString("SchoolName").equals(schoolP)) {

//							targetClassJA = myJOSingleObject2.getJSONArray("ClassName");
//							for (int la = 0; la < targetClassJA.length(); la++) {
								targetClassList.add(myJOSingleObject2.getString("ClassName"));
//							}
//						}
//						else {
//							
//						}
////						schoolName.add(myJOSingleObject2.getString("SchoolName"));
////						schoolName = Arrays.copyOf(schoolName, schoolName.length + 1);
////						schoolName[schoolName.length - 1] =
////						className.add(myJOSingleObject.getJSONArray("ClassName"));
//						myJOSingleArray2 = myJOSingleObject2.getJSONArray("ClassName");
//						ArrayList<String> classSName = new ArrayList<String>();
////						String S4classNameSingle [];
//						for (int k = 0; k < myJOSingleArray2.length(); k++) {
//							classSName.add(myJOSingleArray2.getString(k));
//						}
//						className.add(classSName);
////						S4classNameSingle = new String [classSName.size()];
////						S4classNameSingle = classSName.toArray(S4classNameSingle);

					}
//					S4schoolName = new String [schoolName.size()];
//					S4schoolName = schoolName.toArray(S4schoolName);
					
					
					
					
//					Bundle b2 = new Bundle();
//					//b.putCharSequenceArrayList("schoolAL", schoolName);
//					ArrayList list = new ArrayList();
//					list.add(className);
//					b2.putParcelableArrayList("classN", list);
////					b2.putInt("which", selectNum);
//					msg2.setData(b2);
					Bundle bNor = new Bundle();
					bNor.putStringArrayList("target", targetClassList);
					msg2.setData(bNor);


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
				handler4class.sendMessage(msg2);

			}
		}.start();


    	sv = (SearchView)findViewById(R.id.sv4search);
    	lv4classlist = (ListView)findViewById(R.id.lv4classlist);
//    	List<HashMap<String, Object>> data=new ArrayList<HashMap<String,Object>>();   
//        for(int i=0;i < 10;i++){   
//          HashMap<String, Object> hashMap=new HashMap<String, Object>();  
//          hashMap.put("id", i);  
//          data.add(hashMap);  
//  }   
    	
//    	SimpleAdapter adapter4sl = new SimpleAdapter(this,data,R.layout.listitem_school,
//    			new String[]{"id"}, new int[]{R.id.item_tv});

    	lv4classlist.setTextFilterEnabled(true);
    	lv4classlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				//每一个学校点击之后记录，并返回前activity，
				String resultC = (String)lv4classlist.getItemAtPosition(position);
				Toast.makeText(getApplicationContext(), resultC, Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.putExtra("resultC", resultC);
				SelectClassNormal.this.setResult(3, intent);
				SelectClassNormal.this.finish();
				
			}
		});
		
//		doMy.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				dp = new DatabasePart("jim/sjk2", "a1.db");
//				iGet = inputMy.getText().toString().trim();
//				Cursor cursor1 = dp.query(searchsql, new String[]{iGet});
//				cursor1.moveToFirst();
//				String my = cursor1.getString(0);
//				System.out.println(my);
//				if (cursor1.getString(0).equals(iGet)) {
//					showMy.setText(iGet);
//				}
//				else {
//					System.out.println("zz");
//				}
//				dp.DataClose();
//			}
//		});
    	
    	
    	
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
	 public ArrayList<String> searchItem(String name) {
		  ArrayList<String> mSearchList = new ArrayList<String>();
		  for (int i = 0; i < className.size(); i++) {
		   int index = className.get(i).indexOf(name);
		   // 存在匹配的数据
		   if (index != -1) {
		    mSearchList.add(className.get(i));
		   }
		  }
		  return mSearchList;
		 }
	// ��ʼ������
//	private void initDate() {
//		for (int i = 0; i < 15; i++) {
//			list.add("data" + "   " + i);
//		}
//	}
	 public void updateLayout(ArrayList<String> obj) {
//		  listView.setAdapter(new ArrayAdapter<Object>(getApplicationContext(),
//		    android.R.layout.simple_expandable_list_item_1, obj));
			// = b4tc.getStringArrayList("target");
//			System.out.println(className);
//			mAdapter = new MyAdapter4ClassLIST(className, context);
			// ��Adapter

			//mAdapter
			lv4classlist.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,className));
//			lv.setAdapter(mAdapter);
//	    	lv4schoollist.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,obj));
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
//			lv4classlist.clearTextFilter();
//		}
//		else {
//			lv4classlist.setFilterText(newText);
//		}
//
////		
//		return true;
//	}
	
	
}
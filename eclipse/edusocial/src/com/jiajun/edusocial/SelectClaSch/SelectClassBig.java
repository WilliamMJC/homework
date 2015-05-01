package com.jiajun.edusocial.SelectClaSch;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;


public class SelectClassBig extends Activity implements SearchView.OnQueryTextListener{

	private ListView lv;
	private MyAdapter4ClassLIST mAdapter;

	private ArrayList<String> list;


	private int checkNum; // ��¼ѡ�е���Ŀ����
//	private TextView tv_show;// ������ʾѡ�е���Ŀ����
	ArrayList<String> arr;
	ArrayList<String> targetList;
	Context context;
	SearchView sv;
	HttpEntity httpEntity;
	HttpResponse httpResponse;
//	ArrayList<ArrayList<String>> className;
	ArrayList<String> className = new ArrayList<String>();;
	ArrayList<String> targetClassList;
	JSONArray targetClassJA;
	String schoolP;
	TextView title;
	TextView sousuo;
	LinearLayout loadLL;
//	className
	Handler handler4class =new Handler(){
		@Override
		//当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg){
		//super.handleMessage(msg);
			
//			Bundle newb4class = msg.getData();
//			ArrayList hello = newb4class.getParcelableArrayList("classN");
//			int target = newb4class.getInt("which");
//			ArrayList<ArrayList<String>> hello2 = (ArrayList<ArrayList<String>>) hello.get(0);
//			System.out.println(hello2);
//			ArrayList<String> targetClass = hello2.get(target); 
//			System.out.println(targetClass);
			
			Bundle b4tc = msg.getData();
			className = b4tc.getStringArrayList("target");
//			System.out.println(className);
			mAdapter = new MyAdapter4ClassLIST(className, context);
			// ��Adapter
			loadLL.setVisibility(View.GONE);
			//mAdapter
			lv.setAdapter(mAdapter);
//	    	lv.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,targetList));
//			adapter4school = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, mydarling);
	//adapter4school.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	//mySpinner4School.setAdapter(adapter4school);



		//处理UI
		}
	};
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.bigclasslist);
		context = this;
		loadLL = (LinearLayout)findViewById(R.id.loadLL);
		Intent intent1 = getIntent();
//		Bundle b1 = intent1.getBundleExtra("ssch");
		schoolP = intent1.getStringExtra("Ssch");
		/* ʵ���������ؼ� */
		arr = new ArrayList<String>();
		
		title = (TextView)findViewById(R.id.title4t1);
		title.setText("选择班级");
		sousuo = (TextView)findViewById(R.id.pubBtnPhoto);
		sousuo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		//			for (int i = 0; i < list.size(); i++) {
		//			if (MyAdapter4ClassLIST.getIsSelected().get(i)) {
		//				MyAdapter4ClassLIST.getIsSelected().put(i, false);
		//				checkNum--;// ������1
		//			}
		//		}
		//		// ˢ��listview��TextView����ʾ
		//		dataChanged();
		//		Intent intent = new Intent();
		//		SelectClassBig.this.setResult(2, intent);
		//		SelectClassBig.this.finish()
				String result [] = new String[arr.size()];
				String resultZZ = new String();
				 result  = arr.toArray(result);
				for (int i = 0; i < result.length; i++) {
					resultZZ = resultZZ + " " + result[i];
				}
				System.out.println(resultZZ);
				Intent intent = new Intent();
				intent.putExtra("resultC", resultZZ);
				SelectClassBig.this.setResult(3, intent);
				SelectClassBig.this.finish();
			}
		});
		sousuo.setText("提交班级");
		lv = (ListView) findViewById(R.id.lv4schoollist);
		
		ImageView back2mainfromsearch = (ImageView)findViewById(R.id.back2mainfromsearch);
		back2mainfromsearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("result", "false");
	            setResult(RESULT_OK, intent); 
//				SelectSchool.this.setResult(4, intent);
				SelectClassBig.this.finish();
			}
		});
		
//		bt_selectall = (Button) findViewById(R.id.bt_selectall);
//		bt_submit = (Button) findViewById(R.id.bt_SUBMITselectall);
//		bt_deselectall = (Button) findViewById(R.id.bt_deselectall);
//		tv_show = (TextView) findViewById(R.id.tv);
//		list = new ArrayList<String>();

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
//						JSONArray myJOSingleArray2;
//						schoolName = new ArrayList<String>();

						targetClassList = new ArrayList<String>();
						for (int i = 0; i < myJA2.length(); i++) {
							myJOSingleObject2 = myJA2.getJSONObject(i);
//							if (myJOSingleObject2.getString("SchoolName").equals(schoolP)) {

								targetClassList.add( myJOSingleObject2.getString("ClassName"));
//								for (int la = 0; la < targetClassJA.length(); la++) {
//									targetClassList.add(targetClassJA.getString(la));
//								}
//							}
//							else {
//								
//							}
////							schoolName.add(myJOSingleObject2.getString("SchoolName"));
////							schoolName = Arrays.copyOf(schoolName, schoolName.length + 1);
////							schoolName[schoolName.length - 1] =
////							className.add(myJOSingleObject.getJSONArray("ClassName"));
//							myJOSingleArray2 = myJOSingleObject2.getJSONArray("ClassName");
//							ArrayList<String> classSName = new ArrayList<String>();
////							String S4classNameSingle [];
//							for (int k = 0; k < myJOSingleArray2.length(); k++) {
//								classSName.add(myJOSingleArray2.getString(k));
//							}
//							className.add(classSName);
////							S4classNameSingle = new String [classSName.size()];
////							S4classNameSingle = classSName.toArray(S4classNameSingle);

						}
//						S4schoolName = new String [schoolName.size()];
//						S4schoolName = schoolName.toArray(S4schoolName);
						
						
						
						
//						Bundle b2 = new Bundle();
//						//b.putCharSequenceArrayList("schoolAL", schoolName);
//						ArrayList list = new ArrayList();
//						list.add(className);
//						b2.putParcelableArrayList("classN", list);
////						b2.putInt("which", selectNum);
//						msg2.setData(b2);
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
	    	

		// ΪAdapter׼������
//		initDate();
		// ʵ�����Զ����MyAdapter

		lv.setTextFilterEnabled(true);
 
		// ȫѡ��ť�Ļص��ӿ�
//		bt_selectall.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// ����list�ĳ��ȣ���MyAdapter�е�mapֵȫ����Ϊtrue
//				for (int i = 0; i < list.size(); i++) {
//					MyAdapter4ClassLIST.getIsSelected().put(i, true);
//				}
//				// ������Ϊlist�ĳ���
//				checkNum = list.size();
//				// ˢ��listview��TextView����ʾ
//				dataChanged();
//
//			}
//		});
//		// ȡ����ť�Ļص��ӿ�
//		bt_submit.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// ����list�ĳ��ȣ�����ѡ�İ�ť��Ϊδѡ
////				for (int i = 0; i < list.size(); i++) {
////					if (MyAdapter4ClassLIST.getIsSelected().get(i)) {
////						MyAdapter4ClassLIST.getIsSelected().put(i, false);
////						checkNum--;// ������1
////					}
////				}
////				// ˢ��listview��TextView����ʾ
////				dataChanged();
////				Intent intent = new Intent();
////				SelectClassBig.this.setResult(2, intent);
////				SelectClassBig.this.finish()
//				String result [] = new String[arr.size()];
//				String resultZZ = new String();
//				 result  = arr.toArray(result);
//				for (int i = 0; i < result.length; i++) {
//					resultZZ = resultZZ + " " + result[i];
//				}
//				System.out.println(resultZZ);
//				Intent intent = new Intent();
//				intent.putExtra("resultC", resultZZ);
//				SelectClassBig.this.setResult(3, intent);
//				SelectClassBig.this.finish();
//			}
//		});
//
//		// ��ѡ��ť�Ļص��ӿ�
//		bt_deselectall.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// ����list�ĳ��ȣ�����ѡ����Ϊδѡ��δѡ����Ϊ��ѡ
//				for (int i = 0; i < list.size(); i++) {
//					if (MyAdapter4ClassLIST.getIsSelected().get(i)) {
//						MyAdapter4ClassLIST.getIsSelected().put(i, false);
//						checkNum--;
//					} else {
//						MyAdapter4ClassLIST.getIsSelected().put(i, true);
//						checkNum++;
//					}
//					
//
//				}
//				// ˢ��listview��TextView����ʾ
//				dataChanged();
//			}
//		});
		// ��listView�ļ�����
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// ȡ��ViewHolder����������ʡȥ��ͨ������findViewByIdȥʵ����������Ҫ��cbʵ���Ĳ���
				
				ViewHolder holder = (ViewHolder) arg1.getTag();
				// �ı�CheckBox��״̬
				holder.cbclass.toggle();
				// ��CheckBox��ѡ��״����¼����
				MyAdapter4ClassLIST.getIsSelected().put(arg2, holder.cbclass.isChecked());
				// ����ѡ����Ŀ
				if (holder.cbclass.isChecked() == true) {
					checkNum++;
				} else {
					checkNum--;
				}
				// ��TextView��ʾ
//				tv_show.setText("��ѡ��" + checkNum + "��");
//				TextView tvv = (TextView)arg0.getChildAt(arg2).findViewById(R.id.item_tv);
//				String resultC = tvv.getText().toString();
				String resultC = (String)lv.getItemAtPosition(arg2);
//				System.out.println(resultC);
				arr.add(resultC);
			}
		});
		sv.setIconifiedByDefault(false);
		sv.setOnQueryTextListener(this);
		sv.setSubmitButtonEnabled(true);
		sv.setQueryHint("输入班级");

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
		 arr.clear();
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
			mAdapter = new MyAdapter4ClassLIST(obj, getApplicationContext());
			// ��Adapter

			//mAdapter
			lv.setAdapter(mAdapter);
//	    	lv4schoollist.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,obj));
		 }
	// ˢ��listview��TextView����ʾ
//	private void dataChanged() {
//		// ֪ͨlistViewˢ��
//		mAdapter.notifyDataSetChanged();
////		// TextView��ʾ���µ�ѡ����Ŀ
////		tv_show.setText("��ѡ��" + checkNum + "��");
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
	 
}
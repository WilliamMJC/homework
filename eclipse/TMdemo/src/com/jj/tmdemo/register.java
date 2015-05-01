package com.jj.tmdemo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import DatabasePart.DatabasePart;
import DatabasePart.User;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/*注册主界面
 *  基本功能完成
 */
public class register extends Activity{
	
	String [] arrayIden={"teacher","student","parent"};
    private Context context;  
	User u1 = new User();
	EditText edit4password;
	EditText edit4account;
	EditText edit4nick;
	
	TextView text4class;
	TextView text4school;

	Spinner mySpinner4Identity;

    int selectNum  ;
    String selectedValue ;
	
	String Sedit4account = null;
	String Sedit4nick = null;
	String Sedit4password = null;
	
	String ifexistnick = "select nickname from User where nickname = ?";
	String ifexistaccount = "select account from User where account = ?";
//	ArrayAdapter<String> adapter4school;
	
	DatabasePart dp = null;
	Cursor cursor1 = null;
	Cursor cursor2 = null;
	ArrayList<String> schoolName;
	ArrayList<ArrayList<String>> className;
	String result;
	String resultc;
	String resultcC;
	
	String [] S4schoolName = null;
	String classUSelect ;
	
	ArrayList<String> mydarling ;
	
	private HttpResponse httpResponse = null;
	private HttpEntity httpEntity = null;

//	Handler handler =new Handler(){
//    	@Override
//    	//当有消息发送出来的时候就执行Handler的这个方法
//    	public void handleMessage(Message msg){
//    	//super.handleMessage(msg);
//    		Bundle newb = msg.getData();
//    		mydarling = newb.getStringArrayList("schoolN");
//    		System.out.println(mydarling);
//
//    		adapter4school = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, mydarling);
//	adapter4school.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//	mySpinner4School.setAdapter(adapter4school);
//    		
//    		
//    	//处理UI
//    	}
//    };
//	Handler handler4class =new Handler(){
//    	@Override
//    	//当有消息发送出来的时候就执行Handler的这个方法
//    	public void handleMessage(Message msg){
//    	//super.handleMessage(msg);
//    		Bundle newb4class = msg.getData();
//    		ArrayList hello = newb4class.getParcelableArrayList("classN");
//    		int target = newb4class.getInt("which");
//    		ArrayList<ArrayList<String>> hello2 = (ArrayList<ArrayList<String>>) hello.get(0);
//    		System.out.println(hello2);
//    		ArrayList<String> targetClass = hello2.get(target); 
//    		System.out.println(targetClass);
////    		adapter4school = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, mydarling);
////	adapter4school.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////	mySpinner4School.setAdapter(adapter4school);
//    		adapter4class = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, targetClass);
//    		adapter4class.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//    		mySpinner4Class.setAdapter(adapter4class);
//    		mySpinner4Class.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//				@Override
//				public void onItemSelected(AdapterView<?> parent, View view,
//						int position, long id) {
//					// TODO Auto-generated method stub
//					classUSelect = (String)mySpinner4Class.getSelectedItem();
//					Toast.makeText(context, classUSelect, Toast.LENGTH_SHORT).show();
//				}
//
//				@Override
//				public void onNothingSelected(AdapterView<?> parent) {
//					// TODO Auto-generated method stub
//					
//				}
//			});
//    	//处理UI
//    	}
//    };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.register);
		context = this;
		//dp = new DatabasePart("jim/sjk2", "a1.db");
		mySpinner4Identity = (Spinner)findViewById(R.id.mSpinner4r);

		
		edit4password = (EditText)findViewById(R.id.edit4password);
		edit4account = (EditText)findViewById(R.id.edit4account);
		edit4nick = (EditText)findViewById(R.id.edit4nick);
		
		ifexistnick = "select nickname from User where nickname = ?";
		ifexistaccount = "select account from User where account = ?";
		Button  Registerbtn = (Button)findViewById(R.id.Registerbtn);
		ImageView back2Main = (ImageView)findViewById(R.id.back2Main);
		
		text4class = (TextView)findViewById(R.id.text4class);
		text4school = (TextView)findViewById(R.id.text4school);
		
		text4school.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent2schList =  new Intent();
				intent2schList.setClass(register.this, SelectSchool.class);
				startActivityForResult(intent2schList, 1);
			}
		});
		text4class.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (text4school.getText().toString().equals("")) {
					
				}else {
					if (u1.identity.equals("teacher")) {
						Intent intent2claList = new Intent();
						intent2claList.setClass(register.this, SelectClassBig.class);
//						Bundle b1 = new Bundle();
//						b1.putString("Ssch", result);
////						b1.putString("Ssch", result);
//						intent2claList.putExtra("ssch",b1);
						intent2claList.putExtra("Ssch", result);
						startActivityForResult(intent2claList,2);
					}
					else {
						Intent intent2claListN = new Intent();
						intent2claListN.setClass(register.this, SelectClassNormal.class);
						intent2claListN.putExtra("Ssch", result);
						startActivityForResult(intent2claListN,3);
					}
				}
			}
		});

		
//		Spinner mSpinner4r = (Spinner)findViewById(R.id.mSpinner4r);
//		mySpinner4School = (Spinner)findViewById(R.id.mySpinner4School);
//		mySpinner4Class = (Spinner)findViewById(R.id.mySpinner4Class);
		
//		new Thread(){
//			@Override
//			public void run(){
//			//你要执行的方法
//			//执行完毕后给handler发送一个空消息
//				HttpGet httpGet = new HttpGet("http://222.17.107.163:8080/school.json");
//
//				HttpClient httpClient = new DefaultHttpClient();
//				Message msg = new Message();
//				//Looper.loop();
//				InputStream inputStream = null;
//				try {
//					httpResponse = httpClient.execute(httpGet);
//					httpEntity = httpResponse.getEntity();
//					inputStream = httpEntity.getContent();
//					//GB2312
//					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
//					String result = "";
//					String line = "";
//					while((line = reader.readLine()) != null){
//						result = result + line;
//					}
//					System.out.println(result);
//					JSONObject myJO = new JSONObject(result);
//					JSONArray myJA = myJO.getJSONArray("School");
//					JSONObject myJOSingleObject;
////					JSONArray myJOSingleArray;
//					schoolName = new ArrayList<String>();
////					className = new ArrayList<ArrayList<String>>();
//					for (int i = 0; i < myJA.length(); i++) {
//						myJOSingleObject = myJA.getJSONObject(i);
//						schoolName.add(myJOSingleObject.getString("SchoolName"));
////						schoolName = Arrays.copyOf(schoolName, schoolName.length + 1);
////						schoolName[schoolName.length - 1] =
////						className.add(myJOSingleObject.getJSONArray("ClassName"));
//						
//						
////						myJOSingleArray = myJOSingleObject.getJSONArray("ClassName");
////						ArrayList<String> classSName = new ArrayList<String>();
//////						String S4classNameSingle [];
////						for (int k = 0; k < myJOSingleArray.length(); k++) {
////							classSName.add(myJOSingleArray.getString(k));
////						}
////						
////						className.add(classSName);
//						
//						
//						
////						S4classNameSingle = new String [classSName.size()];
////						S4classNameSingle = classSName.toArray(S4classNameSingle);
//
//					}
//					S4schoolName = new String [schoolName.size()];
//					S4schoolName = schoolName.toArray(S4schoolName);
//					Bundle b = new Bundle();
//					//b.putCharSequenceArrayList("schoolAL", schoolName);
//					b.putStringArrayList("schoolN", schoolName);
//					msg.setData(b);
//
//
//
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				finally{
//					try{
//						inputStream.close();
//					}
//					catch(Exception e){
//						e.printStackTrace();
//					}
//				}					
//				handler.sendMessage(msg);
//
//			}
//		}.start();

//			String[] a = new String [S4schoolName.length];
//			for (int j = 0; j < S4schoolName.length; j++) {
//				System.out.println(S4schoolName[j]);
//				a[j]=S4schoolName[j];
//			}
//			adapter4school = 


//		mySpinner4School.setOnItemSelectedListener(spinnerSelectedListener);
		
		
//		ArrayAdapter<CharSequence> adapter4school = ArrayAdapter.createFromResource(this,
//				a, android.R.layout.simple_spinner_item);
		
		
		
		
		ArrayAdapter<CharSequence> adapter4r = ArrayAdapter.createFromResource(this, 
				R.array.identity, android.R.layout.simple_spinner_item);  
		adapter4r.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mySpinner4Identity.setAdapter(adapter4r);	
		mySpinner4Identity.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					//有点多此一举，等号右边
					u1.identity = arrayIden[0];
					break;
				case 1:
					u1.identity = arrayIden[1];
					break;
				case 2:
					u1.identity = arrayIden[2];
					break;
				default:
					u1.identity = arrayIden[0];
				break;
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
		back2Main.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		Registerbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//首先，要判断昵称/账号/密码时候否空，任意一个为空都提示，从昵称开始检测，如果昵称为空提示账号不能为空，昵称获得焦点，
				Sedit4account = edit4account.getText().toString().trim();
				Sedit4nick = edit4nick.getText().toString().trim();
				Sedit4password = edit4password.getText().toString().trim();
				dp = new DatabasePart("jim/sjk", "a1.db");
				if (Sedit4nick.equals("")) {
					Toast.makeText(register.this, "昵称不能为空，请输入昵称！", Toast.LENGTH_LONG).show();
				}
				else 
				{
					if (Sedit4account.equals("")) {
					Toast.makeText(register.this, "账号不能为空，请输入账号！", Toast.LENGTH_LONG).show();
					}else
					{
						if(Sedit4password.equals("")){
						Toast.makeText(register.this, "密码不能为空，请输入密码！", Toast.LENGTH_LONG).show();
						}else
						{
							cursor1 = dp.query(ifexistnick, new String[]{Sedit4nick} );

							if (cursor1.moveToFirst()) {
								Toast.makeText(register.this, "昵称已存在，请重新输入！", Toast.LENGTH_LONG).show();
							}else 
							{
								cursor2 = dp.query(ifexistaccount, new String[]{Sedit4account});

								if (cursor2.moveToFirst()) {
									Toast.makeText(register.this, "账号已存在，请重新输入！", Toast.LENGTH_LONG).show();
								}else
								{
									
									
									AlertDialog.Builder builder = new AlertDialog.Builder(register.this);
					                //    设置Title的图标
					                builder.setIcon(R.drawable.status_online);
					                //    设置Title的内容
					                builder.setTitle("恭喜");
					                //    设置Content来显示一个信息
					                builder.setMessage("注册成功！");
					                //    设置一个PositiveButton
					                builder.setPositiveButton("马上登录", new DialogInterface.OnClickListener()
					                {
					                    @Override
					                    public void onClick(DialogInterface dialog, int which)
					                    {
					                    	//在此之前要检查是否选了学校班级。
					                    	if (u1.identity.equals("teacher")) {
						                    	String [] classA = resultc.split(" ");
						                    	for (int i = 1; i < classA.length; i++) {
													User u = new User();
													u.account = Sedit4account;
													u.password = Sedit4password;
													u.nickname = Sedit4nick;
													u.identity = u1.identity;
													u.school = result;
													u.noclass = classA[i];
													dp.User.insert(u);
													System.out.println("input");
												}
											}else {
//						                    	String [] classA = resultc.split(" ");

													User u = new User();
													u.account = Sedit4account;
													u.password = Sedit4password;
													u.nickname = Sedit4nick;
													u.identity = u1.identity;
													u.school = result;
													u.noclass = resultc;
													dp.User.insert(u);
													System.out.println("input");
												
											}

											
//					                    	u1.account = Sedit4account;
//					                    	u1.password = Sedit4password;
//					                    	u1.nickname = Sedit4nick;
//					                    	u1.school = result;
//					                    	u1.noclass = resultc;
//					                
//					                    	dp.User.insert(u1);
					                    	dp.DataClose();
					        				finish();
					                    }
					                });
//					                builder.setNegativeButton("完善个人信息", new DialogInterface.OnClickListener()
//					                {
//					                    @Override
//					                    public void onClick(DialogInterface dialog, int which)
//					                    {
//					        				Intent intents1 = new Intent();
//					        				intents1.setClass(register.this, registerD.class);
//					        				startActivity(intents1);
//					                    }
//					                });
					                builder.show();
								}
							}
					
						}
					}
				}
			}
		});
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case 1:
			result = data.getExtras().getString("result");
			text4school.setText(result);
			System.out.println(result);
			break;
		case 2:
			resultc = data.getExtras().getString("resultC");
			text4class.setText(resultc);
			break;
		case 3:
			resultc = data.getExtras().getString("resultC");
			text4class.setText(resultc);
			break;

		}

//		resultc = data.getExtras().getParcelableArrayList("resultC");
//		resultc.toArray()
//		super.onActivityResult(requestCode, resultCode, data);

//		edit4school.setHint(result);
//		edit4class.setHint(resultc);
	}
//	private Spinner.OnItemSelectedListener spinnerSelectedListener = new Spinner.OnItemSelectedListener()    
//    {    
//        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,    
//                long arg3) {    
//            selectedValue = (String)mySpinner4School.getSelectedItem();  
//            selectNum  = (int)mySpinner4School.getSelectedItemId();
//            
//            new Thread(){
//    			@Override
//    			public void run(){
//    			//你要执行的方法
//    			//执行完毕后给handler发送一个空消息
//    				HttpGet httpGet = new HttpGet("http://222.17.107.163:8080/school.json");
//
//    				HttpClient httpClient = new DefaultHttpClient();
//    				Message msg2 = new Message();
//    				//Looper.loop();
//    				InputStream inputStream = null;
//    				try {
//    					httpResponse = httpClient.execute(httpGet);
//    					httpEntity = httpResponse.getEntity();
//    					inputStream = httpEntity.getContent();
//    					//GB2312
//    					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
//    					String result = "";
//    					String line = "";
//    					while((line = reader.readLine()) != null){
//    						result = result + line;
//    					}
//    					JSONObject myJO2 = new JSONObject(result);
//    					JSONArray myJA2 = myJO2.getJSONArray("School");
//    					JSONObject myJOSingleObject2;
//    					JSONArray myJOSingleArray2;
////    					schoolName = new ArrayList<String>();
//    					className = new ArrayList<ArrayList<String>>();
//    					for (int i = 0; i < myJA2.length(); i++) {
//    						myJOSingleObject2 = myJA2.getJSONObject(i);
////    						schoolName.add(myJOSingleObject2.getString("SchoolName"));
////    						schoolName = Arrays.copyOf(schoolName, schoolName.length + 1);
////    						schoolName[schoolName.length - 1] =
////    						className.add(myJOSingleObject.getJSONArray("ClassName"));
//    						myJOSingleArray2 = myJOSingleObject2.getJSONArray("ClassName");
//    						ArrayList<String> classSName = new ArrayList<String>();
////    						String S4classNameSingle [];
//    						for (int k = 0; k < myJOSingleArray2.length(); k++) {
//    							classSName.add(myJOSingleArray2.getString(k));
//    						}
//    						className.add(classSName);
////    						S4classNameSingle = new String [classSName.size()];
////    						S4classNameSingle = classSName.toArray(S4classNameSingle);
//
//    					}
////    					S4schoolName = new String [schoolName.size()];
////    					S4schoolName = schoolName.toArray(S4schoolName);
//    					Bundle b2 = new Bundle();
//    					//b.putCharSequenceArrayList("schoolAL", schoolName);
//    					ArrayList list = new ArrayList();
//    					list.add(className);
//    					b2.putParcelableArrayList("classN", list);
//    					b2.putInt("which", selectNum);
//    					msg2.setData(b2);
//
//
//
//    				} catch (Exception e) {
//    					// TODO Auto-generated catch block
//    					e.printStackTrace();
//    				}
//    				finally{
//    					try{
//    						inputStream.close();
//    					}
//    					catch(Exception e){
//    						e.printStackTrace();
//    					}
//    				}					
//    				handler4class.sendMessage(msg2);
//
//    			}
//    		}.start();
//
//        }    
//            
//        public void onNothingSelected(AdapterView<?> arg0) {    
//            // TODO Auto-generated method stub    
//        }           
//    };  
}			
		
		
	


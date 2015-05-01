package com.jiajun.edusocial.Register;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jiajun.edusocial.R;
import com.jiajun.edusocial.R.anim;
import com.jiajun.edusocial.R.array;
import com.jiajun.edusocial.R.drawable;
import com.jiajun.edusocial.R.id;
import com.jiajun.edusocial.R.layout;
import com.jiajun.edusocial.SelectClaSch.SelectClassBig;
import com.jiajun.edusocial.SelectClaSch.SelectClassNormal;
import com.jiajun.edusocial.SelectClaSch.SelectSchool;
import com.jiajun.edusocial.SelectPicLocally.Bimp;
import com.jiajun.edusocial.SelectPicLocally.selectPicFromAlbum2;

/*注册主界面
 *  基本功能完成
 */
public class Register extends Activity{
	
	String [] arrayIden={"teacher","student","parent"};
    private Context context;  

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
	ArrayList<String> existedUacc = new ArrayList<String>();
	ArrayList<String> existedUnick = new ArrayList<String>();

	String selectedIden;
	

	ArrayList<String> schoolName;
	ArrayList<ArrayList<String>> className;
	String result;
	String resultc;
	String resultcC;
	
	String [] S4schoolName = null;
	String classUSelect ;
	ImageView tx;
	
	HttpResponse httpResponse ;
	HttpEntity httpEntity;
	
	ArrayList<String> mydarling ;
	ArrayList<String> existAcc = new ArrayList<String>();
	ArrayList<String> existNick = new ArrayList<String>();
	
//	private HttpResponse httpResponse = null;
//	private HttpEntity httpEntity = null;

	
	private static final int TAKE_PICTURE = 0x000000;
	private String path = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.register);

//		String [] arrayIden = new String(3);
//				getResources().getStringArray(R.array.arrayIden);
		
		
		context = this;

		Intent intent4gotExisted = getIntent();
		Bundle bundle = intent4gotExisted.getExtras();
		existedUacc = bundle.getStringArrayList("existedUacc");
		existedUnick = bundle.getStringArrayList("existedUnick");
		
		tx = (ImageView)findViewById(R.id.touxiang);
		mySpinner4Identity = (Spinner)findViewById(R.id.mSpinner4r);

		Intent get = this.getIntent();
		Bundle bundle1 = get.getExtras();
		existAcc = bundle1.getStringArrayList("existAcc");
		existNick = bundle1.getStringArrayList("existNick");

		
		edit4password = (EditText)findViewById(R.id.edit4password);
		edit4account = (EditText)findViewById(R.id.edit4account);
		edit4nick = (EditText)findViewById(R.id.edit4nick);
		
		Button  Registerbtn = (Button)findViewById(R.id.Registerbtn);
		ImageView back2Main = (ImageView)findViewById(R.id.back2Main);
		
		text4class = (TextView)findViewById(R.id.text4class);
		text4school = (TextView)findViewById(R.id.text4school);
		
		tx.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					new PopupWindows(Register.this,tx);
			}
		});
		text4school.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent2schList =  new Intent();
				intent2schList.setClass(Register.this, SelectSchool.class);
				startActivityForResult(intent2schList, 1);
			}
		});
		text4class.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				if (text4school.getText().toString().equals("")) {
//					
//				}else {
					if (selectedIden.equals("teacher")) {
						Intent intent2claList = new Intent();
						intent2claList.setClass(Register.this, SelectClassBig.class);
						intent2claList.putExtra("Ssch", result);
						startActivityForResult(intent2claList,2);
					}
					else {
						Intent intent2claListN = new Intent();
						intent2claListN.setClass(Register.this, SelectClassNormal.class);
						intent2claListN.putExtra("Ssch", result);
						startActivityForResult(intent2claListN,3);
					}
//				}
			}
		});
	
		ArrayAdapter<CharSequence> adapter4r = ArrayAdapter.createFromResource(this, 
				R.array.identity, R.layout.spinner_item);  
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
					selectedIden = arrayIden[0];
					break;
				case 1:
					selectedIden = arrayIden[1];
					break;
				case 2:
					selectedIden = arrayIden[2];
					break;
				default:
					selectedIden = arrayIden[0];
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
				Bimp.bmp.clear();
				Bimp.drr.clear();
				Bimp.max = 0;
				Bimp.act_bool = true;
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
				
				if (Sedit4nick.equals("")) {
					Toast.makeText(Register.this, "昵称不能为空，请输入昵称！", Toast.LENGTH_LONG).show();
				}else if(Sedit4account.equals("")){
					Toast.makeText(Register.this, "账号不能为空，请输入账号！", Toast.LENGTH_LONG).show();
				}else if(Sedit4password.equals("")){
					Toast.makeText(Register.this, "密码不能为空，请输入密码！", Toast.LENGTH_LONG).show();
				}else {
					boolean nickIF = false;
					boolean accIF = false;
					for (int i = 0; i < existedUacc.size(); i++) {
						if (Sedit4nick.equals(existedUnick.get(i))) {
							nickIF = true;
							Toast.makeText(Register.this, "昵称已存在，请重新输入！", Toast.LENGTH_LONG).show();
							break;
						}
					}
					if (nickIF == false) {
						for (int j = 0; j < existedUacc.size(); j++) {
							if (Sedit4account.equals(existedUacc.get(j))) {
								accIF = true;
								Toast.makeText(Register.this, "账号已存在，请重新输入！", Toast.LENGTH_LONG).show();
								break;
							}
						}
						if (accIF == false) {
							if (text4school.getText().toString().equals("")) {
								Toast.makeText(Register.this, "请选择学校！", Toast.LENGTH_SHORT).show();
								
							}else if (text4class.getText().toString().equals("")) {
								Toast.makeText(Register.this, "请选择班级！", Toast.LENGTH_SHORT).show();
							}else {
								//在此上传到网络？
								final AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
								builder.setIcon(R.drawable.status_online);
				                //    设置Title的内容
				                builder.setTitle("等待");
				                //    设置Content来显示一个信息
				                builder.setMessage("请稍后。。。。");
				                //    设置一个PositiveButton
//				                builder.setPositiveButton("马上登录", new DialogInterface.OnClickListener()
//				                {
//				                    @Override
//				                    public void onClick(DialogInterface dialog, int which)
//				                    {
//				                    	//在此之前要检查是否选了学校班级。3.21已做
//
//				        				finish();
//				                    }
//				                });
				                builder.show();
				                final Dialog d = builder.show();
								final Handler handler = new Handler(){
									public void handleMessage(android.os.Message msg) {
										Bundle bd = msg.getData();
										String lala = bd.getString("success");
										if (lala.equals("success")) {
											d.dismiss();
											AlertDialog.Builder builder2 = new AlertDialog.Builder(Register.this);
							                //    设置Title的图标
							                builder2.setIcon(R.drawable.status_online);
							                //    设置Title的内容
							                builder2.setTitle("恭喜");
							                //    设置Content来显示一个信息
							                builder2.setMessage("注册成功！");
							                //    设置一个PositiveButton
							                builder2.setPositiveButton("马上登录", new DialogInterface.OnClickListener()
							                {
							                    @Override
							                    public void onClick(DialogInterface dialog, int which)
							                    {
							                    	//在此之前要检查是否选了学校班级。3.21已做

							        				finish();
							                    }
							                });
							                builder2.show();
										}
									}
								};
								new Thread(){
									public void run() {
										
										HttpPost httpPost = new HttpPost("http://zeng.shaoning.net/edusocial/success.json");
										
										HttpClient httpClient = new DefaultHttpClient();
										Message msg = new Message();
										
										
										try {
											JSONObject putt = new JSONObject();
											putt.put("false", "false");
							                StringEntity entity = new StringEntity(putt.toString(),"utf-8");//解决中文乱码问题    
							                entity.setContentEncoding("UTF-8");    
							                entity.setContentType("application/json");    
							                httpPost.setEntity(entity); 
										} catch (JSONException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										} catch (UnsupportedEncodingException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										
										InputStream inputStream = null;
										try {
											httpResponse = httpClient.execute(httpPost);
											httpEntity = httpResponse.getEntity();
											
//											List<NameValuePair> params = new  ArrayList<NameValuePair>();
//											params.add(new BasicNameValuePair("success", "false"));
//											httpPost.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));

											inputStream = httpEntity.getContent();
											BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
											String result = "";
											String line = "";
											while((line = reader.readLine()) != null){
												result = result + line;
											}
//											System.out.println("post " + result);
											
											JSONObject jo = new JSONObject(result);
											String success = jo.getString("success");
											Bundle bdd = new Bundle();
//											System.out.println("du  " + success);
											bdd.putString("success", success);
											msg.setData(bdd);
										} catch (Exception e) {
											// TODO: handle exception
											e.printStackTrace();
										}
										finally{
											try {
												inputStream.close();
											} catch (Exception e2) {
												// TODO: handle exception
												e2.printStackTrace();
											}
										}
										handler.sendMessage(msg);
									}
								}.start();

									

								
//								AlertDialog.Builder builder = new AlertDialog.Builder(register.this);
				                //    设置Title的图标
				                
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
        String sdStatus = Environment.getExternalStorageState();  
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用  
            Log.i("TestFile",  
                    "SD card is not avaiable/writeable right now.");  
            return;  
        }  
		switch (requestCode) {
		case 1:
			result = data.getExtras().getString("result");
			if (result.equals("false")) {
				
			}else {
				text4school.setText(result);
				System.out.println(result);
			}

			break;
		case 2:
			resultc = data.getExtras().getString("resultC");
			text4class.setText(resultc);
			break;
		case 3:
			resultc = data.getExtras().getString("resultC");
			text4class.setText(resultc);
			break;
		case 4:
			
		case TAKE_PICTURE:
			if (Bimp.drr.size() < 9 && resultCode == -1) {
			}
			break;
		}
	}

	public class PopupWindows extends PopupWindow {

		public PopupWindows(Context mContext, View parent) {
			
			View view = View
					.inflate(mContext, R.layout.item_popupwindows, null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.fade_ins));
			LinearLayout ll_popup = (LinearLayout) view
					.findViewById(R.id.ll_popup);
			ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.push_bottom_in_2));

			setWidth(LayoutParams.FILL_PARENT);
			setHeight(LayoutParams.FILL_PARENT);
			setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			update();

//			Button bt1 = (Button) view
//					.findViewById(R.id.item_popupwindows_camera);
//			bt1.setVisibility(View.GONE);
			Button bt2 = (Button) view
					.findViewById(R.id.item_popupwindows_Photo);
			Button bt3 = (Button) view
					.findViewById(R.id.item_popupwindows_cancel);
//			bt1.setOnClickListener(new OnClickListener() {
//				public void onClick(View v) {
//					photo();
//					dismiss();
//				}
//			});
			bt2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(Register.this,
							selectPicFromAlbum2.class);
					Bundle bd = new Bundle();
					bd.putString("LaiZi", "register");
					intent.putExtras(bd);
					startActivity(intent);
					dismiss();
				}
			});
			bt3.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					dismiss();
				}
			});

		}
	}

	//拍照取相片函数；
	public void photo() {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File f1 = new File(Environment.getExternalStorageDirectory() + "/myimage/");
		f1.mkdirs();
		
		File file = new File(Environment.getExternalStorageDirectory()
				+ "/myimage/", String.valueOf(System.currentTimeMillis())
				+ ".jpg");
		path = file.getPath();
		Uri imageUri = Uri.fromFile(file);

		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}
	//拍照回调函数
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        String sdStatus = Environment.getExternalStorageState();  
//        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用  
//            Log.i("TestFile",  
//                    "SD card is not avaiable/writeable right now.");  
//            return;  
//        }  
//		switch (requestCode) {
//		
//		case TAKE_PICTURE:
//			if (Bimp.drr.size() < 9 && resultCode == -1) {
////				Bimp.drr.add(path);
//			}
//			break;
//		}
//	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (Bimp.drr.size() > 0) {
			tx.setImageBitmap(Bimp.bmp.get(0));
		}
	}
	@Override  
    public void onBackPressed() {  
        //A跳转到B如果采用的是startActivityForResult这种方式，  
        //如果不重写返回键，程序不知道要返回给Activity1什么内容就会报错  
        //有super.onBackPressed() 时 不能把在此方法中设置的 intent 传回  
        //上一个Activity ，因此 去掉super.onBackPressed()  在末尾加上finish  
          
		finish();
		Bimp.bmp.clear();
		Bimp.drr.clear();
		Bimp.max = 0;
		Bimp.act_bool = true;
    } 
}			
		
		
	


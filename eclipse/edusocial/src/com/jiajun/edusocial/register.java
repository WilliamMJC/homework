package com.jiajun.edusocial;

import java.io.File;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import com.jiajun.edusocial.pic.Bimp;
import com.jiajun.edusocial.pic.PhotoActivity;
import com.jiajun.edusocial.pic.selectPicFromAlbum;
import com.jiajun.edusocial.pic.selectPicFromAlbum2;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.view.Window;
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

/*注册主界面
 *  基本功能完成
 */
public class register extends Activity{
	
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
		System.out.println(existAcc);
		System.out.println(existNick);
		
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
					new PopupWindows(register.this,tx);
			}
		});
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
//				if (text4school.getText().toString().equals("")) {
//					
//				}else {
					if (selectedIden.equals("teacher")) {
						Intent intent2claList = new Intent();
						intent2claList.setClass(register.this, SelectClassBig.class);
						intent2claList.putExtra("Ssch", result);
						startActivityForResult(intent2claList,2);
					}
					else {
						Intent intent2claListN = new Intent();
						intent2claListN.setClass(register.this, SelectClassNormal.class);
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
					Toast.makeText(register.this, "昵称不能为空，请输入昵称！", Toast.LENGTH_LONG).show();
				}else if(Sedit4account.equals("")){
					Toast.makeText(register.this, "账号不能为空，请输入账号！", Toast.LENGTH_LONG).show();
				}else if(Sedit4password.equals("")){
					Toast.makeText(register.this, "密码不能为空，请输入密码！", Toast.LENGTH_LONG).show();
				}else {
					boolean nickIF = false;
					boolean accIF = false;
					for (int i = 0; i < existedUacc.size(); i++) {
						if (Sedit4nick.equals(existedUnick.get(i))) {
							nickIF = true;
							Toast.makeText(register.this, "昵称已存在，请重新输入！", Toast.LENGTH_LONG).show();
							break;
						}
					}
					if (nickIF == false) {
						for (int j = 0; j < existedUacc.size(); j++) {
							if (Sedit4account.equals(existedUacc.get(j))) {
								accIF = true;
								Toast.makeText(register.this, "账号已存在，请重新输入！", Toast.LENGTH_LONG).show();
								break;
							}
						}
						if (accIF == false) {
							if (text4school.getText().toString().equals("")) {
								Toast.makeText(register.this, "请选择学校！", Toast.LENGTH_SHORT).show();
								
							}else if (text4class.getText().toString().equals("")) {
								Toast.makeText(register.this, "请选择班级！", Toast.LENGTH_SHORT).show();
							}else {
								//在此上传到网络？
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
				                    	//在此之前要检查是否选了学校班级。3.21已做
//				                    	if (selectedIden.equals("teacher")) {
//				                    		//写到服务器去
//					                    	String [] classA = resultc.split(" ");
//					                    	for (int i = 1; i < classA.length; i++) {
//												User u = new User();
//												u.account = Sedit4account;
//												u.password = Sedit4password;
//												u.nickname = Sedit4nick;
//												u.identity = selectedIden;
//												u.school = result;
//												u.noclass = classA[i];
//												dp.User.insert(u);
//												System.out.println("input");
//											}
//										}else {
//											//写入到服务器去
////					                    	String [] classA = resultc.split(" ");
//
//												User u = new User();
//												u.account = Sedit4account;
//												u.password = Sedit4password;
//												u.nickname = Sedit4nick;
//												u.identity = selectedIden;
//												u.school = result;
//												u.noclass = resultc;
//												dp.User.insert(u);
//												System.out.println("input");
//											
//										}
//				                    	dp.DataClose();
				        				finish();
				                    }
				                });
				                builder.show();
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
					Intent intent = new Intent(register.this,
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
		
		
	


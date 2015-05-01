package com.jiajun.edusocial.Homework;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.jiajun.edusocial.R;
import com.jiajun.edusocial.R.id;
import com.jiajun.edusocial.R.layout;
import com.jiajun.edusocial.SelectClaSch.LittleClassSelect;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class pubHW extends Activity{
	
	EditText hwTitle;
	EditText hwDetail;

	ArrayList<String> resultC = new ArrayList<String>();
	String targetClassString ;
	
	
	TextView pubBtn;

	String userNameInPubHw;
	TextView text4class;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.pubhw);
		
		Intent intent1 = getIntent();
		userNameInPubHw = intent1.getStringExtra("userN");
//		System.out.println("aha ? " + userNameInPubHw);
		
		hwTitle = (EditText)findViewById(R.id.hwTitle);
		hwDetail = (EditText)findViewById(R.id.hwDetail);

		pubBtn = (TextView)findViewById(R.id.pubBtnHW);
		text4class = (TextView)findViewById(R.id.text4class);
		

		
		
		ImageView back2hw = (ImageView)findViewById(R.id.back2hw);
		
		pubBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "发布成功!", Toast.LENGTH_SHORT).show();
				// TODO Auto-generated method stub

//				String inputTitle = hwTitle.getText().toString();
//				String inputDetail = hwDetail.getText().toString();
//				
//				SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//				String date = sDateFormat.format(new java.util.Date());
//				
//				String noclassS = resultC.get(0);
//				if (resultC.size() > 1) {
//					for (int i = 0; i < resultC.size(); i++) {
////						dp = new DatabasePart("jim/sjk", "a1.db");
////						ContentValues cv = new ContentValues();
////						cv.put("title", inputTitle);
////						cv.put("userid", userNameInPubHw);
////						cv.put("detail", inputDetail);
////						cv.put("negauserid", resultC.get(i));
////						cv.put("time", date);
////						cv.put("type", "hw");
////						//插入服务器操作
////						dp.insert("News", cv);
////						dp.DataClose();
////						System.out.println("duo");
//						
//					}
//					Toast.makeText(getApplicationContext(), "发布成功!", Toast.LENGTH_SHORT).show();
//				}else {
////					dp = new DatabasePart("jim/sjk", "a1.db");
////					ContentValues cv = new ContentValues();
////					cv.put("title", inputTitle);
////					cv.put("userid", userNameInPubHw);
////					cv.put("detail", inputDetail);
////					cv.put("negauserid", noclassS);
////					cv.put("time", date);
////					cv.put("type", "hw");
////					
//					Toast.makeText(getApplicationContext(), "发布成功!", Toast.LENGTH_SHORT).show();
////					dp.insert("News", cv);
////					dp.DataClose();
////					System.out.println("single");
//				}
				
				finish();
			}
		});
		text4class.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent2 = new Intent();
				intent2.setClass(pubHW.this,LittleClassSelect.class);
				intent2.putExtra("uname", userNameInPubHw);
//				System.out.println("uname + " + userNameInPubHw);
				startActivityForResult(intent2,3);
			}
		});
		
		
		back2hw.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		resultC = data.getExtras().getStringArrayList("resultC");
		if (resultC==null) {
			
		}else {

//			resultC = data.getExtras().getString("resultC");
			targetClassString = resultC.get(0);
			if (resultC.size() > 1) {
				for (int i = 1; i < resultC.size(); i++) {
					targetClassString = targetClassString + " " + resultC.get(i);
				}
			}else {
//				targetClassString = resultC.get(0);
			}

			text4class.setText(targetClassString);
		}

	
	}
	@Override  
    public void onBackPressed() {  
        //A跳转到B如果采用的是startActivityForResult这种方式，  
        //如果不重写返回键，程序不知道要返回给Activity1什么内容就会报错  
        //有super.onBackPressed() 时 不能把在此方法中设置的 intent 传回  
        //上一个Activity ，因此 去掉super.onBackPressed()  在末尾加上finish  
          
            Intent intent=new Intent();    
            intent = getIntent().putExtra("resultC", "false");  
            setResult(RESULT_OK, intent);    
            finish();    
    } 
	
}

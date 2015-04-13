package com.jiajun.edusocial;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class pubInform extends Activity{
	
	EditText informTitle;
	EditText informDetail;
	RadioGroup rg;
//	RadioButton rb1;
//	RadioButton rb2;
	
	ArrayList<String> resultC = new ArrayList<String>();
	
	String informType;
	String resultSch;
	String targetClassString;
	
	TextView selectObject4Inform;
	TextView pubBtn;
//	DatabasePart dp;
	String userNameInPubInform;
	
	RadioButton rb1;
	RadioButton rb2;
	
//	String stringGotSchool = "select school from User where nickname = ?";
//	
//	Cursor cursorGotSchool;
//	
	
	TextView selectType4Inform;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.pubinform);
		
		Intent intent1 = getIntent();
		userNameInPubInform = intent1.getStringExtra("userN");
//		resultC = new ArrayList<String>();
//		rb1 = (RadioButton)findViewById(R.id.rb1);
//		rb2 = (RadioButton)findViewById(R.id.rb2);
		rg = (RadioGroup)findViewById(R.id.rb);
		
		rb1 = (RadioButton)findViewById(R.id.rb1);
		rb2 = (RadioButton)findViewById(R.id.rb2);
		
		informDetail = (EditText)findViewById(R.id.informDetail);
		informTitle = (EditText)findViewById(R.id.informTitle);
		
		selectObject4Inform = (TextView)findViewById(R.id.selectObject4Inform);
		selectType4Inform = (TextView)findViewById(R.id.selectType4Inform);
		pubBtn = (TextView)findViewById(R.id.pubBtnInform);
		
		ImageView back2inform = (ImageView)findViewById(R.id.back2inform);
		
		selectObject4Inform.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent2 = new Intent();
				intent2.setClass(pubInform.this,LittleClassSelect.class);
				intent2.putExtra("uname", userNameInPubInform);
//				System.out.println(userNameInPubInform);
				startActivityForResult(intent2,3);
			}
		});
		
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub

				
				if (checkedId == rb1.getId() ) {
					informType = "inform";
//					dp = new DatabasePart("jim/sjk", "a1.db");
//					cursorGotSchool = dp.query(stringGotSchool, new String[]{userNameInPubInform});
//					cursorGotSchool.moveToFirst();
//					targetClassString = cursorGotSchool.getString(0);
//					dp.DataClose();
//					selectObject4Inform.setText(SchObject);
					selectObject4Inform.setVisibility(View.GONE);
					
				}
				else if(checkedId == rb2.getId()) {
					
					informType = "class";
					selectObject4Inform.setVisibility(View.VISIBLE);
				}
			}
		});

		pubBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "发布成功!", Toast.LENGTH_SHORT).show();
//				String inputTitle = informTitle.getText().toString();
//				String inputDetail = informDetail.getText().toString();
//				
//				SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//				String date = sDateFormat.format(new java.util.Date());
//				
//				if (informType.equals("inform")) {
////					dp = new DatabasePart("jim/sjk", "a1.db");
////					ContentValues cv = new ContentValues();
////					cv.put("title", inputTitle);
////					cv.put("userid", userNameInPubInform);
////					cv.put("detail", inputDetail);
////					cv.put("negauserid", targetClassString);
////					cv.put("time", date);
////					cv.put("type", informType);
////				
//					Toast.makeText(getApplicationContext(), "发布成功!", Toast.LENGTH_SHORT).show();
////					dp.insert("News", cv);
////					dp.DataClose();
//				}else {
//					Toast.makeText(getApplicationContext(), "发布成功!", Toast.LENGTH_SHORT).show();
////					String noclassS = resultC.get(0);
////					if (resultC.size() > 1) {
//////						for (int i = 0; i < resultC.size(); i++) {
//////							dp = new DatabasePart("jim/sjk", "a1.db");
//////							ContentValues cv = new ContentValues();
//////							cv.put("title", inputTitle);
//////							cv.put("userid", userNameInPubInform);
//////							cv.put("detail", inputDetail);
//////							cv.put("negauserid", resultC.get(i));
//////							cv.put("time", date);
//////							cv.put("type", informType);
//////							
//////							dp.insert("News", cv);
//////							dp.DataClose();
//////							System.out.println("duo");
//////							
//////						}
////					}else {
//////						dp = new DatabasePart("jim/sjk", "a1.db");
//////						ContentValues cv = new ContentValues();
//////						cv.put("title", inputTitle);
//////						cv.put("userid", userNameInPubInform);
//////						cv.put("detail", inputDetail);
//////						cv.put("negauserid", noclassS);
//////						cv.put("time", date);
//////						cv.put("type", informType);
//////						
//////						dp.insert("News", cv);
//////						dp.DataClose();
//////						System.out.println("single");
////					}
//				}
				
//				String noclassArray [] = noclassS.split(" ");
//				for (int i=0;i < noclassArray.length;i++) {
//					
//				}
//				dp = new DatabasePart("jim/sjk", "a1.db");
//				ContentValues cv = new ContentValues();
//				cv.put("title", inputTitle);
//				cv.put("userid", userNameInPubHw);
//				cv.put("detail", inputDetail);
//				cv.put("noclass", noclassS);
//				dp.insert("News", cv);
//				dp.DataClose();
				finish();
			}

		});
		
		

		
		
		
		back2inform.setOnClickListener(new OnClickListener() {
			
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

			selectObject4Inform.setText(targetClassString);
		}

	

	
	}
	
}

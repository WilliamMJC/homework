package com.jj.tmdemo;

import DatabasePart.DatabasePart;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.jj.tmdemo.MainUI.MainUI4me;


public class MainActivity extends Activity{
	
	private String identityName;
	DatabasePart dp = null;

	EditText InputAccount ;
	EditText InputPassword ;
	Spinner mSpinner ;
	String selectedIdentity;
	
	String isitright4identity;
	String ifexistaccount ;
	String SInputAccount ;
	String SInputPassword ;
	String ifexistpassword ;
	String gotNickName;
	Cursor cursorx;
	String uNickName;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.relative_main);
		
		
		Button register = (Button)findViewById(R.id.register);
		Button submit = (Button)findViewById(R.id.submit);
		mSpinner = (Spinner)findViewById(R.id.mySpinner);
		InputAccount =  (EditText)findViewById(R.id.InputAccount);
		InputPassword = (EditText)findViewById(R.id.InputPassword);

		ifexistaccount = "select account from User where account = ?";

		ifexistpassword = "select password from User where account = ?";
		isitright4identity = "select identity from User where account  = ?";
		gotNickName = "select nickname from User where account = ?";


		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, 
				R.array.identity, android.R.layout.simple_spinner_item);  //这种很瘦
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinner.setAdapter(adapter);

		mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 2:
					identityName = "parent";
					break;
				case 1:
					identityName = "student";
					break;
				case 0:
					identityName = "teacher";
					break;
					default:
						identityName = "teacher";
						break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		});
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				dp = new DatabasePart("jim/sjk", "a1.db");
				
				
				SInputAccount = InputAccount.getText().toString().trim();
				
				SInputPassword = InputPassword.getText().toString().trim();
				System.out.println("ok1");
				Cursor cursor1 = dp.query(ifexistaccount, new String[]{SInputAccount});
				
				if (SInputAccount.equals("")) {
					Toast.makeText(MainActivity.this, "请输入账号！", Toast.LENGTH_LONG).show();
				}
				else if (SInputPassword.equals("")) {
					Toast.makeText(MainActivity.this, "请输入密码！", Toast.LENGTH_LONG).show();
				}else{
					if (!cursor1.moveToFirst()/*cursor1.getString(0) ==""*/) {
						Toast.makeText(MainActivity.this, "用户名不存在，请重新输入或注册！", Toast.LENGTH_LONG).show();
						System.out.println("ok3");
					}else {

						Cursor cursor2 = dp.query(ifexistpassword, new String[]{SInputAccount});
						cursor2.moveToFirst();
						if(cursor2.getString(0).equals(SInputPassword) ){
							Cursor cursor3 = dp.query(isitright4identity, new String []{SInputAccount});
							cursor3.moveToFirst();
							if(cursor3.getString(0).equals(identityName)){
								if(identityName.equals("teacher")){		
									cursorx = dp.query(gotNickName, new String []{SInputAccount});
									cursorx.moveToFirst();
									uNickName = cursorx.getString(0);
									dp.DataClose();
									Intent intent4t = new Intent();
									intent4t.setClass(MainActivity.this,MainUI4me.class);
									Bundle bundle4t = new Bundle();
									bundle4t.putString("userName", uNickName);
									bundle4t.putString("userIdentity", identityName);
									intent4t.putExtras(bundle4t);
									startActivity(intent4t);
								}
								else if(identityName.equals("student")){
									cursorx = dp.query(gotNickName, new String []{SInputAccount});
									cursorx.moveToFirst();
									uNickName = cursorx.getString(0);
									dp.DataClose();
									Intent intent4s = new Intent();
									intent4s.setClass(MainActivity.this,MainUI4me.class);
									Bundle bundle4s = new Bundle();
									bundle4s.putString("userName", uNickName);
									bundle4s.putString("userIdentity", identityName);
									intent4s.putExtras(bundle4s);
									startActivity(intent4s);
								}
								else{
									cursorx = dp.query(gotNickName, new String []{SInputAccount});
									cursorx.moveToFirst();
									uNickName = cursorx.getString(0);
									dp.DataClose();
									Intent intent4p = new Intent();
									intent4p.setClass(MainActivity.this,MainUI4me.class);
									Bundle bundle4p = new Bundle();
									bundle4p.putString("userName", uNickName);
									bundle4p.putString("userIdentity", identityName);
									intent4p.putExtras(bundle4p);
									startActivity(intent4p);
								}

							}else{
								Toast.makeText(MainActivity.this, "身份不正确，请重新选择！", Toast.LENGTH_LONG).show();
							}
						} else {
							Toast.makeText(MainActivity.this, "密码不正确，请重新输入！", Toast.LENGTH_LONG).show();
						}
					}
				}
				// TODO Auto-generated method stub
				dp.DataClose();
			}
		});
		register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent();
				intent1.setClass(MainActivity.this, register.class);
				startActivity(intent1);
				
			}
		});
		
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		dp.DataClose();
	}

}

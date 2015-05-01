package com.jj.tmdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

public class personalInfo extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.personal_info);
		
		//传递个nickname过来，根据nickname，载入个人信息，填充到控件里面
		//需要做“资料修改”功能吗?
		
		
		ImageView back2personalMain = (ImageView)findViewById(R.id.back2personalMain);
		back2personalMain.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
	}
}

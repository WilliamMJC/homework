package com.hzu.feirty.MailIM.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.Toast;

import com.hzu.feirty.MailIM.R;

public class MainActivity extends AppCompatActivity {
	// tab用参数
	private TabHost tabHost;
	private long exitTime = 0;
	private RadioGroup radiogroup;
	private int menuid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getSupportActionBar().setTitle("收发作业");
		radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup();
		tabHost.addTab(tabHost.newTabSpec("main").setIndicator("main")
				.setContent(R.id.fragment_main));
		tabHost.addTab(tabHost.newTabSpec("mycenter").setIndicator("mycenter")
				.setContent(R.id.fragment_mycenter));
		tabHost.addTab(tabHost.newTabSpec("search").setIndicator("search")
				.setContent(R.id.fragment_search));
		radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				menuid = checkedId;
				int currentTab = tabHost.getCurrentTab();
				switch (checkedId) {
					case R.id.radio_main:
						tabHost.setCurrentTabByTag("main");
						//如果需要动画效果就使用
						setCurrentTabWithAnim(currentTab, 0, "main");
						getSupportActionBar().setTitle("收发作业");
						break;
					case R.id.radio_mycenter:
						//tabHost.setCurrentTabByTag("mycenter");
						setCurrentTabWithAnim(currentTab, 1, "mycenter");
						getSupportActionBar().setTitle("个人中心");

						break;
					case R.id.radio_search:
						tabHost.setCurrentTabByTag("search");
						getSupportActionBar().setTitle("发现");
				}
				// 刷新actionbar的menu
				getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		switch (menuid) {
			case R.id.radio_main:
				getMenuInflater().inflate(R.menu.main, menu);
				break;
			case R.id.radio_mycenter:
				menu.clear();
				break;
			case R.id.radio_search:
				menu.clear();
				break;
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// 这个方法是关键，用来判断动画滑动的方向
	private void setCurrentTabWithAnim(int now, int next, String tag) {
		if (now > next) {
			tabHost.getCurrentView().startAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out));
			tabHost.setCurrentTabByTag(tag);
			tabHost.getCurrentView().startAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in));
		} else {
			tabHost.getCurrentView().startAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
			tabHost.setCurrentTabByTag(tag);
			tabHost.getCurrentView().startAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
		}
	}
/*	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			long firstClickBack=System.currentTimeMillis();
			long secondClickBack = System.currentTimeMillis();
			if(secondClickBack - firstClickBack >1500){
				Toast.makeText(this, "再按一次退出每一天", Toast.LENGTH_SHORT).show();
				firstClickBack = secondClickBack;
				return true;
			}else{
				MyApplication.getInstance().exitApp();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}*/
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
			if((System.currentTimeMillis()-exitTime) > 2000){
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				//MyApplication.getInstance().exitApp();
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}

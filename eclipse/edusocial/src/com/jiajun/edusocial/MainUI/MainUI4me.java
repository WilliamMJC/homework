package com.jiajun.edusocial.MainUI;

import java.util.ArrayList;


import android.content.Intent;
import android.content.res.Resources;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.jiajun.edusocial.R;
import com.jiajun.edusocial.Search4me;
import com.jiajun.edusocial.personalInfo;
import com.jiajun.edusocial.pubHW;
import com.jiajun.edusocial.pubInform;
import com.jiajun.edusocial.pubPhoto;


public class MainUI4me extends FragmentActivity {
	private LinearLayout neirong;//内容布局
	private FragmentManager fmm;//碎片管理器
    private ViewPager mPager;
    private ImageView ivBottomLine;//下划线对象；
	private FragmentTransaction ft;//碎片事务对象
	private ArrayList<Fragment> array4fragment;//碎片集合
	TextView t4aaa , t4bbb , t4ccc ;//三个分页名字文本
	Fragment f1,f2,f3;//声明3个碎片
	Bundle bundle4cont;
	String userNickName;
	String userIdentity;
	String userSch;
	String userTx;
	ArrayList<String> userClaArray = new ArrayList<String>();
	
    private int currIndex = 0;//当前索引
    private int bottomLineWidth;//下划线宽度
    private int offset = 0;//偏移量初始值为0；
    private int position_one;//位置坐标1
    private int position_two;//位置坐标2
    private int position_three;//位置坐标3
    private Resources resources;//资源对象
	PopupMenu popup4addition = null;
	PopupMenu popup4more = null;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.app_main);
		
        resources = getResources();
        InitWidth();//初始化宽度
        //InitTextView();//初始化textview
        InitViewPager();//初始化viewpager
		//neirong = (LinearLayout)findViewById(R.id.neirong);
		t4aaa = (TextView)findViewById(R.id.t4aaa);
		t4bbb = (TextView)findViewById(R.id.t4bbb);
		t4ccc = (TextView)findViewById(R.id.t4ccc);
		
		t4aaa.setOnClickListener(new MyOnClickListener(0));
		t4bbb.setOnClickListener(new MyOnClickListener(1));
		t4ccc.setOnClickListener(new MyOnClickListener(2));
		
		fmm = getSupportFragmentManager();//获取碎片管理器

		Intent get = this.getIntent();
		Bundle bundle = get.getExtras();
		userNickName = bundle.getString("userName");
		userIdentity = bundle.getString("userIdentity");
		userSch = bundle.getString("userSch");
		userClaArray = bundle.getStringArrayList("userClaArray");
		userTx = bundle.getString("userTx");

	}
	public void onAdditionClick(View imageview)
	{
		popup4addition = new PopupMenu(this, imageview);
		getMenuInflater().inflate(R.menu.addition, popup4addition.getMenu());
		if (userIdentity.equals("teacher")) {
			
		}else {
			popup4addition.getMenu().removeItem(R.id.pubInformMenu);
			popup4addition.getMenu().removeItem(R.id.pubHomeworkMenu);
		}
		popup4addition.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				switch (item.getItemId()) {
				case R.id.pubPhoto:
//					Toast.makeText(MainUI4me.this, "添加相册", Toast.LENGTH_SHORT).show();
//					LinearLayout makealbumDialog = (LinearLayout)getLayoutInflater()
//							.inflate( R.layout.make_a_new_album_dialog, null);		
//					new AlertDialog.Builder(teacherLogIn.this)
//							// ���öԻ����ͼ��
//							.setIcon(R.drawable.status_online)
//							// ���öԻ���ı���
//							.setTitle("填写相册信息：")
//							// ���öԻ�����ʾ��View����
//							.setView(makealbumDialog)
//							// Ϊ�Ի�������һ����ȷ������ť
//							.setPositiveButton("新建" , new OnClickListener()
//							{
//								@Override
//								public void onClick(DialogInterface dialog,
//										int which)
//								{
////									EditText albumName4t = (EditText)findViewById(R.id.albumName);
////									EditText albumDescription4t = (EditText)findViewById(R.id.albumDescription);
////									String context4albumName4t = albumName4t.getText().toString();
////									String context4albumDescription4t = albumDescription4t.getText().toString();
////									listItem.put("hwName", hwnames[i]);
////									listItem.put("hwDate", hwdates[i]);
////									listItem.put("more", R.drawable.right4more);
////									listItems.add(listItem);
////									//动态添加一条相册记录的实现代码
//								}
//							})
//
//							.create()
//							.show();
					Intent intent4addAlbum = new Intent();
					intent4addAlbum.setClass(MainUI4me.this, pubPhoto.class);
					startActivity(intent4addAlbum);
					break;
//				case R.id.addContacts:
//					Toast.makeText(MainUI4me.this, "添加联系人", Toast.LENGTH_SHORT).show();
////					Intent intent2addC = new Intent();
////					intent2addC.setClass(teacherLogIn.this, addContact.class);
////					startActivity(intent2addC);
//					break;
//				default:
//					break;
				case R.id.pubHomeworkMenu:
					Toast.makeText(MainUI4me.this, "发布作业", Toast.LENGTH_SHORT).show();
					Intent intent4pubHW = new Intent();
					intent4pubHW.setClass(MainUI4me.this, pubHW.class);
					Bundle bundle4menuPubHW = new Bundle();
					bundle4menuPubHW.putString("userN", userNickName);

					intent4pubHW.putExtras(bundle4menuPubHW);
					startActivity(intent4pubHW);
					break;
				case R.id.pubInformMenu:
					Toast.makeText(MainUI4me.this, "发布通知", Toast.LENGTH_SHORT).show();
					Intent intent4pubInform  = new Intent();
					intent4pubInform.setClass(MainUI4me.this, pubInform.class);
					Bundle bundle4menuPubInform = new Bundle();
					bundle4menuPubInform.putString("userN", userNickName);
					intent4pubInform.putExtras(bundle4menuPubInform);
					startActivity(intent4pubInform);
					break;
				}
				return false;
			}
		});
		popup4addition.show();
	}
	public void onSearchClick(View imageview)
	{
//		Toast.makeText(MainUI4me.this, "搜索啦啦啦", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), Search4me.class);
		String temp = userNickName;
		intent.putExtra("a", userNickName);
		startActivity(intent);
	}
	public void onMoreClick(View imageview)
	{
		popup4more = new PopupMenu(this, imageview);
		getMenuInflater().inflate(R.menu.personal_info, popup4more.getMenu());
		popup4more.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				switch (item.getItemId()) {
				case R.id.personalInfo:
					Intent intent2perInfo = new Intent();
					intent2perInfo.setClass(MainUI4me.this, personalInfo.class);
					intent2perInfo.putExtra("nick", userNickName);
					intent2perInfo.putExtra("userTx", userTx);
					intent2perInfo.putExtra("from", "main");
//					System.out.println("balabala " + userTx);
					
					startActivity(intent2perInfo);
					break;
				case R.id.exit:
					finish();
					break;
				}
				return false;
			}
		});
		popup4more.show();
	}
    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(index);
        }
    };
    
	private void InitViewPager() {
        mPager = (ViewPager) findViewById(R.id.vPager);
        array4fragment = new ArrayList<Fragment>();
        /*
         * LayoutInflater作用是将layout的xml布局文件实例化为View类对象。
			获取LayoutInflater的方法有如下三种:
			?
			LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View layout = inflater.inflate(R.layout.main, null);
			 
			LayoutInflater inflater = LayoutInflater.from(context); (该方法实质就是第一种方法，可参考源代码)
			View layout = inflater.inflate(R.layout.main, null);
			 
			LayoutInflater inflater = getLayoutInflater();（在Activity中可以使用，实际上是View子类下window的一个函数）
			View layout = inflater.inflate(R.layout.main, null);
         * 
         * */
        LayoutInflater mInflater = getLayoutInflater();
        View activityView = mInflater.inflate(R.layout.app_main, null);
        

        Fragment Fragment1 = fragmentJAVA1.newInstance();
        Fragment Fragment2 = fragmentJAVA2.newInstance();
        Fragment Fragment3 = fragmentJAVA3.newInstance();
        bundle4cont = new Bundle();
        bundle4cont.putString("uNickName",userNickName );
        Fragment3.setArguments(bundle4cont);

        array4fragment.add(Fragment1);
        array4fragment.add(Fragment2);
        array4fragment.add(Fragment3);
        
        mPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), array4fragment));
        mPager.setCurrentItem(0);
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    private void InitWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        ivBottomLine = (ImageView) findViewById(R.id.iv_bottom_line);
        ivBottomLine.getLayoutParams().width = (int) (screenW / 3.0);
        position_one = (int) (screenW / 3.0);
        position_two = position_one * 2;
    }
	
	public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
            case 0:
                if (currIndex == 1) {
                    animation = new TranslateAnimation(position_one, 0, 0, 0);
                    t4bbb.setTextColor(resources.getColor(R.color.lightwhite));
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(position_two, 0, 0, 0);
                    t4ccc.setTextColor(resources.getColor(R.color.lightwhite));
                } 
                t4aaa.setTextColor(resources.getColor(R.color.white));
                break;
            case 1:
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, position_one, 0, 0);
                    t4aaa.setTextColor(resources.getColor(R.color.lightwhite));
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(position_two, position_one, 0, 0);
                    t4ccc.setTextColor(resources.getColor(R.color.lightwhite));
                } 
                t4bbb.setTextColor(resources.getColor(R.color.white));
                break;
            case 2:
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, position_two, 0, 0);
                    t4aaa.setTextColor(resources.getColor(R.color.lightwhite));
                } else if (currIndex == 1) {
                    animation = new TranslateAnimation(position_one, position_two, 0, 0);
                    t4bbb.setTextColor(resources.getColor(R.color.lightwhite));
                } 
                t4ccc.setTextColor(resources.getColor(R.color.white));
                break;
            }
            currIndex = arg0;
            animation.setFillAfter(true);
            animation.setDuration(300);
            ivBottomLine.startAnimation(animation);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }
}
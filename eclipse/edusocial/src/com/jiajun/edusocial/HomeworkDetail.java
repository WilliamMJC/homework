package com.jiajun.edusocial;


import java.util.ArrayList;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeworkDetail extends Activity{
	
//	String newid;

	String targetNewsId;
	String whoAmI;
	String type;
	String title;
	String detail;
	String pubuser;
	String time;
	
	TextView tv;
	ImageView iv;
	
	TextView tv4title;
	TextView tv4detail;
	TextView tv4pubUser ;
	TextView tv4good ;
	TextView tv4saySth;
	TextView tv4showGood;
	TextView tv4pubTime;
//	ListView lv;
	
	ArrayList<String> cmmarrCom = new ArrayList<String>();
	ArrayList<String> cmmid = new ArrayList<String>();
	ArrayList<String> cmmpos = new ArrayList<String>();
	ArrayList<String> cmmnega = new ArrayList<String>();
	ArrayList<String> cmmtime = new ArrayList<String>();
	ArrayList<String> cmmcontent = new ArrayList<String>();
	ArrayList<String> cmmnewsid = new ArrayList<String>();
	
	
	
	TextView pubBtn;
	
	ArrayList<String> titleList;
	ArrayList<String> pubUserList;
	ArrayList<String> detailList;
	ArrayList<String> goodmanList;
	
	String ifgood;
	String Sgoodman;
	String newid;
	String show = "";
	String resultgood;
	
	ArrayList<TextView> tval = new ArrayList<TextView>();
	
	LinearLayout ll4comment;
	String ifHave;
	ListView lv ;
	String cont;
	String target= "";
	
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			Bundle dd = msg.getData();
			String what = dd.getString("whatUsay");
//			System.out.println("ok + " + what);
			TextView txtx = new TextView(HomeworkDetail.this);
			ll4comment.setVisibility(View.VISIBLE);
			ll4comment.addView(txtx);
			txtx.setText(what);
			System.out.println("ok ++ ok ++ "  + whoAmI + ":" + what);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.homework_detail);
		
		Intent intent = getIntent();
		targetNewsId = intent.getStringExtra("newid");
		whoAmI = intent.getStringExtra("whoAmI");
		type = intent.getStringExtra("type");
		
		title = intent.getStringExtra("title");
		detail = intent.getStringExtra("detail");
		pubuser = intent.getStringExtra("pubuser");
		time = intent.getStringExtra("time");
		newid = intent.getStringExtra("newid");
		ifgood = intent.getStringExtra("ifgood");
		
		tv4title = (TextView)findViewById(R.id.title);
		tv4detail = (TextView)findViewById(R.id.detail);
		tv4pubUser = (TextView)findViewById(R.id.pubUser);
		tv4good = (TextView)findViewById(R.id.good);
		tv4saySth = (TextView)findViewById(R.id.saySth);
		tv4showGood = (TextView)findViewById(R.id.showGood);
		tv4pubTime = (TextView)findViewById(R.id.pubTime);
		
		ll4comment = (LinearLayout)findViewById(R.id.ll4comment);

		Sgoodman = intent.getStringExtra("whogood");
		

		
		
//		System.out.println("shui " + Sgoodman);
		if (ifgood.equals("true")) {
//			tv4showGood.setVisibility(View.VISIBLE);
			String [] temp = Sgoodman.split("-");
			target = temp[0];
			System.out.println(target + "target");
			if (temp.length > 1) {
				for (int i = 1; i < temp.length; i++) {
					target = target + "," + temp[i];
				}
//				System.out.println("bala " + target);
				tv4showGood.setText(target + " 提交了作业。");
//				Toast.makeText(getApplicationContext(), target, Toast.LENGTH_SHORT).show();
			}else {
				tv4showGood.setText(target + " 提交了作业。");
			}
			
		}else {
			tv4showGood.setVisibility(View.GONE);
			
		}
		ifHave = intent.getStringExtra("ifHave");
		if (ifHave.equals("true")) {
			cmmid = intent.getStringArrayListExtra("cmmid");
			cmmpos = intent.getStringArrayListExtra("cmmpos");
			cmmnega = intent.getStringArrayListExtra("cmmnega");
			cmmtime = intent.getStringArrayListExtra("cmmtime");
			cmmcontent = intent.getStringArrayListExtra("cmmcontent");
			ll4comment.setVisibility(View.VISIBLE);

			for (int i = 0; i < cmmid.size(); i++) {
				TextView tv = new TextView(HomeworkDetail.this);
				final int posi = i;
				tv.setTextSize(25);
				tv.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						final EditText et = new EditText(HomeworkDetail.this);
						new AlertDialog.Builder(HomeworkDetail.this) 
					 	.setTitle("说点什么...")
					 	//ic_dialog_info
					 	.setIcon(android.R.drawable.ic_dialog_email)
					 	.setView(et)
					 	.setPositiveButton("评论", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								String t = et.getText().toString().trim();
								System.out.println("ok ++++ "  + t);
								TextView tvv = new TextView(HomeworkDetail.this);
								tvv.setTextSize(25);
								String tt = "";
								if (cmmnega.get(posi).equals("")) {
									tt = cmmpos.get(posi);
								}else {
									tt = cmmpos.get(posi);
								}
//								ll4comment.setVisibility(View.VISIBLE);
								ll4comment.addView(tvv);
								tvv.setText(whoAmI + "对" + tt + " : " +  t);
//								System.out.println("ok ++++ "  + whoAmI + "对" + tt + t);
//								ll4comment
							}
						})
					 	.setNegativeButton("取消", new DialogInterface.OnClickListener() {
							
					 		@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
							}
						})
					 	.show();
//					 	.create();
					}
				});
				if (cmmnega.get(i).equals("")) {
					cont = cmmpos.get(i) + ":" + cmmcontent.get(i);
					tv.setText(cont);
				}else {
					cont = cmmpos.get(i) + " 对 " + cmmnega.get(i) + ":" + cmmcontent.get(i);
					tv.setText(cont);
				}
				ll4comment.addView(tv);
			}
		}else {
			ll4comment.setVisibility(View.GONE);
		}

		pubBtn = (TextView)findViewById(R.id.publishHW);
		pubBtn.setVisibility(View.GONE);
		tv = (TextView)findViewById(R.id.title4t1);
		if (type.equals("hw")) {
			
		}else {
			tv.setText("通知");
		}
		iv = (ImageView)findViewById(R.id.back2function);
		iv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		titleList = new ArrayList<String>();
		pubUserList = new ArrayList<String>();
		detailList = new ArrayList<String>();
		goodmanList = new ArrayList<String>();

		tv4title.setText(title);
		tv4detail.setText(detail);
		tv4pubUser.setText(pubuser);
		tv4pubTime.setText(time);

		if (type.equals("inform")) {
			tv4good.setVisibility(View.GONE);
		}else {
			tv4good.setVisibility(View.VISIBLE);
			//whoami没穿过来
//			System.out.println(whoAmI + "who am i");
			String [] temp = Sgoodman.split("-");
			for (int j = 0; j < temp.length; j++) {
				if (temp[j].equals(whoAmI)) {
					tv4good.setText("已提交");
					break;
				}else {
					tv4good.setText("提交");
				}
			}
//			if (target.contains(whoAmI)) {
//				tv4good.setText("已提交");
//				
//			}else {
//				tv4good.setText("提交");
//			}
		}
		tv4saySth.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final EditText et = new EditText(HomeworkDetail.this);
				new AlertDialog.Builder(HomeworkDetail.this) 
			 	.setTitle("说点什么...")
			 	//ic_dialog_info
			 	.setIcon(android.R.drawable.ic_dialog_email)
			 	.setView(et)
			 	.setPositiveButton("评论", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						String t = et.getText().toString().trim();
						final String pu = whoAmI + ":" + t;
						new Thread(){
							public void run() {
								Message msg2 = new Message();
								try {

									Bundle bd = new Bundle();
									bd.putString("whatUsay", pu);
									msg2.setData(bd);

								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								finally{
									try{//这里zheli

									}
									catch(Exception e){
										e.printStackTrace();
									}
								}


								handler.sendMessage(msg2);
							}
						}.start();
//						HomeworkDetail.this.ll4comment.addView(et);
					}
				})
			 	.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
			 		@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				})
			 	.show();
//			 	.create();
			}
		});
		tv4good.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//
//				if (tv4showGood.getText().toString().contains(whoAmI)) {
//					
//				}
				if (tv4good.getText().equals("已提交")) {
//					tv4showGood.setVisibility(View.VISIBLE);
				}else {
					if (target.equals("")) {
						resultgood = whoAmI + " 提交了作业!";
						tv4showGood.setText(resultgood);
						tv4showGood.setVisibility(View.VISIBLE);
						tv4good.setText("已提交");
					}else {
						resultgood = whoAmI + "," + target + " 提交了作业!";
						tv4showGood.setText(resultgood);
						tv4showGood.setVisibility(View.VISIBLE);
						tv4good.setText("已提交");
					}
				}
				Toast.makeText(getApplicationContext(), "作业已交！", Toast.LENGTH_LONG).show();
			}
		});
	}
}

package com.jj.tmdemo;



import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import DatabasePart.DatabasePart;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jj.tmdemo.pic.Bimp;
import com.jj.tmdemo.pic.FileUtils;
import com.jj.tmdemo.pic.PhotoActivity;
import com.jj.tmdemo.pic.selectPicFromAlbum;


/*
 * 读取图片和输入内容，
 * */

public class pubPhoto extends Activity{
	
	private GridView noScrollgridview;
	private GridAdapter adapter;
	private TextView activity_selectimg_send;
	EditText theDTuInput;
	DatabasePart dp;
	String userNameInPubPhoto;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.pubphoto);
		
		Intent intent = getIntent();
		userNameInPubPhoto = intent.getStringExtra("userN");
		
		theDTuInput = (EditText)findViewById(R.id.theDTuInput);
		ImageView back2photo = (ImageView)findViewById(R.id.back2photo);
		back2photo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		//初始化函数
		Init();
	}
	public void Init() {
		noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
		//设置背景setSelector，透明。
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		
		//新建监听器，并且update；而update里面调用load函数；
		adapter = new GridAdapter(this);
		adapter.update();
		//设置GridView的监听器，并且update；
		noScrollgridview.setAdapter(adapter);
		//设置每张照片的单击事件；
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//如果你点的位置是加号？
				if (arg2 == Bimp.bmp.size()) {
					new PopupWindows(pubPhoto.this, noScrollgridview);
				} else {
					Intent intent = new Intent(pubPhoto.this,
							PhotoActivity.class);
					//把你点的是adapter中第几张图片传过去，
					intent.putExtra("ID", arg2);
					startActivity(intent);
				}
			}
		});
		//发送按钮
		
		activity_selectimg_send = (TextView) findViewById(R.id.activity_selectimg_send);
		activity_selectimg_send.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				List<String> list = new ArrayList<String>();		
				ArrayList<String> thePathOfPicUSelected = new ArrayList<String>();
				for (int i = 0; i < Bimp.drr.size(); i++) {
					String Str = Bimp.drr.get(i).substring( 
							Bimp.drr.get(i).lastIndexOf("/") + 1,
							Bimp.drr.get(i).lastIndexOf("."));
					list.add(FileUtils.SDPATH+Str+".JPEG");				
					thePathOfPicUSelected.add(Bimp.drr.get(i));
				}
				String picPathTotal = "";
				if (thePathOfPicUSelected.size() == 0 ) {
					
				}else {
					for (int j = 0; j < thePathOfPicUSelected.size(); j ++) {
							picPathTotal = thePathOfPicUSelected.get(j) + "*" + picPathTotal;
					}
				}
				String input = theDTuInput.getText().toString();
				SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String date = sDateFormat.format(new java.util.Date());
				//list里面存的是所选图片的全部地址
				dp = new DatabasePart("jim/sjk", "a1.db");
				ContentValues cv = new ContentValues();
				cv.put("pubUser", userNameInPubPhoto);
				cv.put("pubTime", date);
				cv.put("dtContent", input);
				cv.put("dtPic", picPathTotal);
				dp.insert("DongTai", cv);
				dp.DataClose();
//				Intent intent = new Intent();
//				intent.putStringArrayListExtra("resultPath", thePathOfPicUSelected);
//				pubPhoto.this.setResult(88, intent);
//				
				Bimp.drr.clear();
				Bimp.bmp.clear();
				Bimp.max = 0;
				Bimp.act_bool = true;
				pubPhoto.this.finish();
				
				//在这里要利用用户选的图片的本地路径值（list），把这些图片和该条动态联系在一起；写入数据库，or，写入服务器。
				
				
				// 高清的压缩图片全部就在  list 所指向的路径里面了
				// 高清的压缩过的 bmp 对象  都在 Bimp.bmp里面
				// 完成上传服务器后 .........
				FileUtils.deleteDir();
			}
		});
	}
	//使用@SuppressLint标注忽略指定的警告
	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater; // 视图容器
		private int selectedPosition = -1;// 选中的位置
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		
		
		public void update() {
			loading();
		}
		
		
		
		//有个“添加”所以要图片集+1？
		public int getCount() {
			return (Bimp.bmp.size() + 1);
		}

		public Object getItem(int arg0) {

			return null;
		}

		public long getItemId(int arg0) {

			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		/**
		 * ListView Item设置
		 */
		public View getView(int position, View convertView, ViewGroup parent) {
			
			final int coord = position;
			ViewHolder holder = null;
			if (convertView == null) {

				convertView = inflater.inflate(R.layout.item_published_grida,
						parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == Bimp.bmp.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.icon_addpic_unfocused));
				if (position == 9) {
					holder.image.setVisibility(View.GONE);
				}
			} else {
				holder.image.setImageBitmap(Bimp.bmp.get(position));
			}

			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.max == Bimp.drr.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							try {
								String path = Bimp.drr.get(Bimp.max);
								System.out.println(path);
								Bitmap bm = Bimp.revitionImageSize(path);
								Bimp.bmp.add(bm);
								System.out.println("333");
								String newStr = path.substring(
										path.lastIndexOf("/") + 1,
										path.lastIndexOf("."));
								FileUtils.saveBitmap(bm, "" + newStr);
								Bimp.max += 1;
								Message message = new Message();
								message.what = 1;
								handler.sendMessage(message);
							} catch (IOException e) {

								e.printStackTrace();
							}
						}
					}
				}
			}).start();
		}
	}

	public String getString(String s) {
		String path = null;
		if (s == null)
			return "";
		for (int i = s.length() - 1; i > 0; i++) {
			s.charAt(i);
		}
		return path;
	}

	protected void onRestart() {
		adapter.update();
		super.onRestart();
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

			Button bt1 = (Button) view
					.findViewById(R.id.item_popupwindows_camera);
			Button bt2 = (Button) view
					.findViewById(R.id.item_popupwindows_Photo);
			Button bt3 = (Button) view
					.findViewById(R.id.item_popupwindows_cancel);
			bt1.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					photo();
					dismiss();
				}
			});
			bt2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(pubPhoto.this,
							selectPicFromAlbum.class);
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

	private static final int TAKE_PICTURE = 0x000000;
	private String path = "";

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

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String sdStatus = Environment.getExternalStorageState();  
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用  
            Log.i("TestFile",  
                    "SD card is not avaiable/writeable right now.");  
            return;  
        }  
		switch (requestCode) {
		
		case TAKE_PICTURE:
			if (Bimp.drr.size() < 9 && resultCode == -1) {
//				Bimp.drr.add(path);
			}
			break;
		}
	}
	
}

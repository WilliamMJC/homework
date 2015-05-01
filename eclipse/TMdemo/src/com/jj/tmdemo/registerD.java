package com.jj.tmdemo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


import DatabasePart.DatabasePart;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;

import android.widget.PopupMenu.OnMenuItemClickListener;

public class registerD extends Activity{
	ImageView image;
	private Uri photoUri;
	PopupMenu popup4tper;
	
	
	//private Button requestButton = null;
	private HttpResponse httpResponse = null;
	private HttpEntity httpEntity = null;
	
	
	private final int PIC_FROM_CAMERA = 1;
	private final int PIC_FROM＿LOCALPHOTO = 0;
	private Handler handler =new Handler(){
    	@Override
    	//当有消息发送出来的时候就执行Handler的这个方法
    	public void handleMessage(Message msg){
    	super.handleMessage(msg);
    	//处理UI
    	}
    	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register_detail);
		
		new Thread(){
			@Override
			public void run(){
			//你要执行的方法
			//执行完毕后给handler发送一个空消息
				HttpGet httpGet = new HttpGet("http://172.17.21.14:8080/boss.json");

				HttpClient httpClient = new DefaultHttpClient();

				InputStream inputStream = null;
				try {
					httpResponse = httpClient.execute(httpGet);
					httpEntity = httpResponse.getEntity();
					inputStream = httpEntity.getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"GB2312"));
					String result = "";
					String line = "";
					while((line = reader.readLine()) != null){
						result = result + line;
					}
					System.out.println(result);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					try{
						inputStream.close();
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
			handler.sendEmptyMessage(0);
			}
			}.start();
		
		
		image = (ImageView) findViewById(R.id.image);
		ImageView back2Register = (ImageView)findViewById(R.id.back2Register);
		
		//添加2个spinner的资源获得，根据所选的item，从网络读取对应学校的班级列表，最好列表可以加一个滚动条（屏幕显示不了这么多的时候）的？需要我手动加吗？
		//把学校读出来作为一个数组，然后根据itemNum决定一个String，
		//根据String去决定解析哪一个json文件从未获得班级数组，赋值给Spinner2
		//根据选择的班级又获得一个String，存进去数据库。
		back2Register.setOnClickListener(new  OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(registerD.this, MainActivity.class);
				startActivity(intent);
				registerD.this.finish();
			}
		});
		Button finishAndSubmit = (Button)findViewById(R.id.finishAndSubmit);
		finishAndSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//DatabasePart dp = new DatabasePart("jim/sjk", "a1.db");
				
				AlertDialog.Builder builder = new AlertDialog.Builder(registerD.this);
                //    设置Title的图标
                builder.setIcon(R.drawable.status_online);
                //    设置Title的内容
                builder.setTitle("恭喜");
                //    设置Content来显示一个信息
                builder.setMessage("注册成功！");
                //    设置一个PositiveButton
                builder.setPositiveButton("资料已完善，马上登录", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
        				Intent intents = new Intent();
        				intents.setClass(registerD.this, MainActivity.class);
        				startActivity(intents);
                    }
                });
                builder.show();
			}
		});

	}
	public void onPicAdditionClick(View imageview)
	{
		popup4tper = new PopupMenu(this, imageview);
		getMenuInflater().inflate(R.menu.wherepiccomesfrom, popup4tper.getMenu());
		popup4tper.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				switch (item.getItemId()) {
				case R.id.localAlbum:

					doHandlerPhoto(PIC_FROM＿LOCALPHOTO);// 从相册中去获取
					break;
				case R.id.takePhoto:
					doHandlerPhoto(PIC_FROM_CAMERA);// 用户点击了从照相机获取
					break;
//				default:
//					break;
				}
				return false;
			}
		});
		popup4tper.show();
	}
	/**
	 * 根据不同方式选择图片设置image
	 * @param type 0-本地相册选择，非0为拍照
	 */
	private void doHandlerPhoto(int type)
	{
		try
		{
			//保存裁剪后的图片文件
			File pictureFileDir = new File(Environment.getExternalStorageDirectory(), "/upload");
			if (!pictureFileDir.exists()) {
				pictureFileDir.mkdirs();
			}
			File picFile = new File(pictureFileDir, "upload.jpeg");
			if (!picFile.exists()) {
				picFile.createNewFile();
			}
			photoUri = Uri.fromFile(picFile);
			
			if (type==PIC_FROM＿LOCALPHOTO)
			{
				Intent intent = getCropImageIntent();
				startActivityForResult(intent, PIC_FROM＿LOCALPHOTO);
			}else
			{
				Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
				startActivityForResult(cameraIntent, PIC_FROM_CAMERA);
			}

		} catch (Exception e)
		{
			Log.i("HandlerPicError", "处理图片出现错误");
		}
	}

	/**
	 * 调用图片剪辑程序
	 */
	public Intent getCropImageIntent() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		setIntentParams(intent);
		return intent;
	}

	/**
	 * 启动裁剪
	 */
	private void cropImageUriByTakePhoto() {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(photoUri, "image/*");
		setIntentParams(intent);
		startActivityForResult(intent, PIC_FROM＿LOCALPHOTO);
	}

	/**
	 * 设置公用参数
	 */
	private void setIntentParams(Intent intent)
	{
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 600);
		intent.putExtra("outputY", 600);
		intent.putExtra("noFaceDetection", true); // no face detection
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		switch (requestCode)
		{
		case PIC_FROM_CAMERA: // 拍照
			try 
			{
				cropImageUriByTakePhoto();
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
			break;
		case PIC_FROM＿LOCALPHOTO:
			try
			{
				if (photoUri != null) 
				{
					Bitmap bitmap = decodeUriAsBitmap(photoUri);
					image.setImageBitmap(bitmap);
				}
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
			break;
		}
	}

	private Bitmap decodeUriAsBitmap(Uri uri)
	{
		Bitmap bitmap = null;
		try 
		{
			bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}
}

	


package com.jiajun.edusocial.SelectPicLocally;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

//异步加载图片类
public class AsyncImageLoader {
	//map（形容词，drawable软引用）对象；
	private Map<String, SoftReference<Drawable>> imageCache = 
		new HashMap<String, SoftReference<Drawable>>();
	
	//此类的载入drawable函数loadDrawable，
	//内部字符串图片url，内部接口对象；
	public Drawable loadDrawable(final String imageUrl,final ImageCallback callback){
		//如果map对象包含传入的imageurl字符串，则
		if(imageCache.containsKey(imageUrl)){
			//新建一个软引用drawable对象，
			SoftReference<Drawable> softReference = imageCache.get(imageUrl);
			if(softReference.get() != null){
				return softReference.get();
			}
		}
		//调用loadDrawable函数时开一个内部线程；
		
		//回调设置image的drawable
		final Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				callback.imageLoaded((Drawable) msg.obj);
			}
		};
		//新线程是根据url获取到图片drawable对象，然后加imageurl传入map对象里面，并且新建message对象传回线程，
		new Thread(){
			public void run() {
				Drawable drawable = null;
				Message message = new Message();
				try {
					drawable = loadImageFromUrl(imageUrl);//zheli
					imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
					message = handler.obtainMessage(0, drawable);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

				handler.sendMessage(message);
			};
		}.start();
		return null;
	}
	//定义一个loadImageFromUrl函数，传入图片url。返回drawable对象，否则抛出超时错误；
	protected Drawable loadImageFromUrl(String imageUrl) throws IOException {
		InputStream is = new URL(imageUrl).openStream();
		try {

			//new URL(imageUrl).openStream()
			return Drawable.createFromStream(is, "src");//zheli
		} catch (Exception e) {
			throw new RuntimeException(e);//zheli
		}finally{
			try {
				is.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}
	
	//定义属于该类的接口；定义接口内部函数imageLoaded；
	public interface ImageCallback{
		public void imageLoaded(Drawable imageDrawable);
	}
}

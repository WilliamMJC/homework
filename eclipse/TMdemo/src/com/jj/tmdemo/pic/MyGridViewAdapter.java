package com.jj.tmdemo.pic;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;





import com.jj.tmdemo.R;


public class MyGridViewAdapter extends BaseAdapter {
//	private LayoutInflater inflater4GV; // 视图容器
//	private int selectedPosition = -1;// 选中的位置
//	private boolean shape;
	ArrayList<String> pathList ;
	ArrayList<Bitmap> bmList = new ArrayList<Bitmap>();
	private Context mmContext;
	private AsyncImageLoader loader = new AsyncImageLoader();

//	public boolean isShape() {
//		return shape;
//	}
//
//	public void setShape(boolean shape) {
//		this.shape = shape;
//	}

	public MyGridViewAdapter(Context mmContext,ArrayList<String> alist) {
//		inflater = LayoutInflater.from(context);
		super();
//		inflater4GV = LayoutInflater.from(mmContext);
		this.mmContext = mmContext;
		this.pathList = alist;
	}

	
	
//	public void update() {
//		loading();
//	}
	
	
	
	//有个“添加”所以要图片集+1？
	public int getCount() {
		return pathList.size();
	}

	public Object getItem(int arg0) {

		return null;
	}

	public long getItemId(int arg0) {

		return 0;
	}
	class Holder {
		private ImageView iv;
	}
	public View getView(final int position, View convertView, ViewGroup parent) {

		final Holder holder;

		if (convertView == null) {
			holder = new Holder();
			convertView = View.inflate(mmContext, R.layout.item_image_grid, null);
			holder.iv = (ImageView) convertView.findViewById(R.id.image);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		
    	CallbackImpl callbackImpl = new CallbackImpl(holder.iv);
    	Drawable cacheImage = 
        		loader.loadDrawable("http://zeng.shaoning.net/edusocial/1.png", callbackImpl);
    		if (cacheImage != null) {
    			holder.iv.setImageDrawable(cacheImage);
    		}
		
//		new Thread(){
//			public void run() {
//				try {
////					for (int l = 0; l < pathList.size(); l++) {
////					String pathPic = pathList.get(l);
//			    	CallbackImpl callbackImpl = new CallbackImpl(holder.iv);
//			    	Drawable cacheImage = 
//			        		loader.loadDrawable(pathList.get(position), callbackImpl);
//			    		if (cacheImage != null) {
//			    			holder.iv.setImageDrawable(cacheImage);
//			    		}
////					Bitmap bm = Bimp.revitionImageSize(pathList.get(position));
//					
////					bmList.add(bm);
//			    	
////					holder.iv.setImageBitmap(bm);
//					
////					String newStr = pathPic.substring(
////							pathPic.lastIndexOf("/") + 1,
////							pathPic.lastIndexOf("."));
////					FileUtils.saveBitmap(bm, "" + newStr);
////					}
//				} catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//				}
//			};
//		}.start();



        return convertView;

//		if (position == Bimp.bmp.size()) {
//			holder.image.setImageBitmap(BitmapFactory.decodeResource(
//					getResources(), R.drawable.icon_addpic_unfocused));
//			if (position == 9) {
//				holder.image.setVisibility(View.GONE);
//			}
//		} else {
		
		
//		new Thread(){
//			public void run(){
//			Message msg = new Message();
//			msg.what = 0;
//			try{
////			for (int l = 0; l < pathList.size(); l++) {
////				String pathPic = pathList.get(l);
//				Bitmap bm = Bimp.revitionImageSize(pathList.get(coord));
//				bmList.add(bm);
//				String newStr = pathPic.substring(
//						pathPic.lastIndexOf("/") + 1,
//						pathPic.lastIndexOf("."));
//				FileUtils.saveBitmap(bm, "" + newStr);

//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}

//			//获得图片的Bitmap对象。getBitmap省略了，就是从网上通过http下载图片然后转化成一个Bitmap
//			ArrayList<Bitmap> list = new ArrayList<Bitmap>();
//			for (int x = 0; x < pathList.size(); x++) {
//				Bitmap bitmap = getDiskBitmap(pathList.get(x));
//				list.add(bitmap);
//			}//将bitmap和imageView包装成一个List传到线程外
//			msg.obj = list;
			
//			handler.sendMessage(msg);
//			}
//		}.start();

//		try{
//			for (int l = 0; l < pathList.size(); l++) {
//				String pathPic = pathList.get(l);
//				Bitmap bm = Bimp.revitionImageSize(pathPic);
//				bmList.add(bm);
////				String newStr = pathPic.substring(
////						pathPic.lastIndexOf("/") + 1,
////						pathPic.lastIndexOf("."));
////				FileUtils.saveBitmap(bm, "" + newStr);
//
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//			System.out.println(pathList.get(position).toString());
//			holder.image.setImageBitmap(bmList.get(position));
//			holder.image.setImageResource(R.drawable.ic_launcher);
		
//		}

//		return convertView;
	}
//	private Handler handler = new Handler(){
//		public void handleMessage(android.os.Message msg) {
//			switch (msg.what) {
//			case 0:
//				System.out.println("success");
//				break;
//
//			default:
//				break;
//			}
//		};
//	};

//    private void loadImage(final String url, final int id) {
//		// ���������ͻ�ӻ�����ȡ��ͼ��ImageCallback�ӿ��з���Ҳ���ᱻִ��
////    	ImageView imageView = (ImageView)findViewById(id);
//    	CallbackImpl callbackImpl = new CallbackImpl(imageView);
//    	Drawable cacheImage = 
//    		loader.loadDrawable(url, callbackImpl);
//		if (cacheImage != null) {
//			imageView.setImageDrawable(cacheImage);
//		}
//	}
}

//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage (Message msg) {
//            switch (msg.what) {
//            case 0://接到从线程内传来的图片bitmap和imageView.
//                //这里只是将bitmap传到imageView中就行了。只所以不在线程中做是考虑到线程的安全性。
////                ArrayList<Bitmap> list = (ArrayList<Bitmap>) msg.obj;
////                Bitmap bitmap = (Bitmap) list.get (0);
////                ImageView iv = (ImageView) list.get (1);
//            	List list = (List)msg.obj;
//            	Bitmap bmm = (Bitmap)list.get(0);
//            	ImageView iv = (ImageView)list.get(1);
//            	iv.setImageBitmap(bmm);
//                break;
//            default:
//                super.handleMessage (msg);
//            }
//        }
//    };

//	private Bitmap getDiskBitmap(String pathString)  
//	{  
//	    Bitmap bitmap = null;  
//	    try  
//	    {  
//	        File file = new File(pathString);  
//	        if(file.exists())  
//	        {  
//	            bitmap = BitmapFactory.decodeFile(pathString);  
//	        }  
//	    } catch (Exception e)  
//	    {  
//	        // TODO: handle exception  
//	    }  
//	      
//	      
//	    return bitmap;  
//	} 



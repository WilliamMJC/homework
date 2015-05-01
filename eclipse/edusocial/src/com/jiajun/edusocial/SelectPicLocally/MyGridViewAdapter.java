package com.jiajun.edusocial.SelectPicLocally;

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





import com.jiajun.edusocial.R;


public class MyGridViewAdapter extends BaseAdapter {

	ArrayList<String> pathList ;
	ArrayList<Bitmap> bmList = new ArrayList<Bitmap>();
	private Context mmContext;
	private AsyncImageLoader loader = new AsyncImageLoader();

	public MyGridViewAdapter(Context mmContext,ArrayList<String> alist) {

		super();
		this.mmContext = mmContext;
		this.pathList = alist;
	}	
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
		//生成实现接口对象，无返回值，直接设置传入imageview对象的图片
    	CallbackImpl callbackImpl = new CallbackImpl(holder.iv);
    	//"http://zeng.shaoning.net/edusocial/1.png"
    	//pathList.get(position)
    	Drawable cacheImage = 
        		loader.loadDrawable(pathList.get(position), callbackImpl);
    		if (cacheImage != null) {
    			holder.iv.setImageDrawable(cacheImage);
    		}
        return convertView;
	}
}




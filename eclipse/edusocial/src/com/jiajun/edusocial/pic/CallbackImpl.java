package com.jiajun.edusocial.pic;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

///CallbackImpl类实现了AsyncImageLoader类的ImageCallback接口；
public class CallbackImpl implements AsyncImageLoader.ImageCallback{
	
	private ImageView imageView ;
	//imageview参数的成员函数；就是传入一个imageview对象；
	public CallbackImpl(ImageView imageView) {
		super();
		this.imageView = imageView;
	}
	//实现接口就要实现包含的所有方法，包括这个imageLoaded；传入一个drawable对象，并把imageview控件设置这个drawable对象；
	@Override
	public void imageLoaded(Drawable imageDrawable) {
		imageView.setImageDrawable(imageDrawable);
	}

}

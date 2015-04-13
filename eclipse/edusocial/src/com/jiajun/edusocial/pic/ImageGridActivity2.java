package com.jiajun.edusocial.pic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.jiajun.edusocial.R;
import com.jiajun.edusocial.pic.ImageGridAdapter.TextCallback;

public class ImageGridActivity2 extends Activity {
	public static final String EXTRA_IMAGE_LIST = "imagelist";

	// ArrayList<Entity> dataList;//鐢ㄦ潵瑁呰浇鏁版嵁婧愮殑鍒楄〃
	List<ImageItem> dataList;
	GridView gridView;
	ImageGridAdapter adapter;// 鑷畾涔夌殑閫傞厤鍣�
	AlbumHelper helper;

	String LaiZi= null;
	int maxL;


	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(ImageGridActivity2.this, "最多选择1张图片", 400).show();
				break;

			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_image_grid2);


		Intent in = getIntent();
		
		LaiZi = in.getStringExtra("LaiZi");
		System.out.println(LaiZi);
		if (LaiZi.equals("pubPhoto")) {
			maxL = 9;
		}else {
			maxL = 1;

		}
		
//		helper = AlbumHelper.getHelper();
//		helper.init(getApplicationContext());
		//得到某相册里面的照片的imagelist，强制转换成imageitem的list对象，
		dataList = (List<ImageItem>) in.getSerializableExtra(
				EXTRA_IMAGE_LIST);
		//初始化控件函数。
		initView();


		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
//		String result = data.getStringExtra("picPathPassBack");
//		System.out.println(result);
//		
		if (resultCode == RESULT_OK) {
			finish();
		}
		
	}
	/**
	 * 鍒濆鍖杤iew瑙嗗浘
	 */
	private void initView() {
		gridView = (GridView) findViewById(R.id.gridview);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new ImageGridAdapter(ImageGridActivity2.this, dataList,
				mHandler,maxL);
		gridView.setAdapter(adapter);
		adapter.setTextCallback(new TextCallback() {
			public void onListen(int count) {
//				bt.setText("完成" + "(" + count + ")");
			}
		});
		System.out.println("start");
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				/**
				 * 鏍规嵁position鍙傛暟锛屽彲浠ヨ幏寰楄窡GridView鐨勫瓙View鐩哥粦瀹氱殑瀹炰綋绫伙紝鐒跺悗鏍规嵁瀹冪殑isSelected鐘舵
				 * �锛� 鏉ュ垽鏂槸鍚︽樉绀洪�涓晥鏋溿� 鑷充簬閫変腑鏁堟灉鐨勮鍒欙紝涓嬮潰閫傞厤鍣ㄧ殑浠ｇ爜涓細鏈夎鏄�
				 */
				// if(dataList.get(position).isSelected()){
				// dataList.get(position).setSelected(false);
				// }else{
				// dataList.get(position).setSelected(true);
				// }
				/**
				 * 閫氱煡閫傞厤鍣紝缁戝畾鐨勬暟鎹彂鐢熶簡鏀瑰彉锛屽簲褰撳埛鏂拌鍥�
				 */
				
//				System.out.println("lalalalaalalalaal");
//				if (maxL == 1) {
//					Intent intent = new Intent(ImageGridActivity2.this,
//							PhotoActivity.class);
//					//把你点的是adapter中第几张图片传过去，
//					intent.putExtra("ID", position);
					adapter.notifyDataSetChanged();
//					startActivity(intent);
//				}else {
//					adapter.notifyDataSetChanged();
//				}
			}

		});

	}
}
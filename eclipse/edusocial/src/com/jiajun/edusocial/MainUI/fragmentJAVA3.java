package com.jiajun.edusocial.MainUI;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jiajun.edusocial.R;
import com.jiajun.edusocial.Search4me;
import com.jiajun.edusocial.personalInfo;
import com.jiajun.edusocial.pic.AsyncImageLoader;
import com.jiajun.edusocial.pic.CallbackImpl;

//一个添加好友行+一个listview实现通讯录，可以看通讯录的视频以后再做。

public class fragmentJAVA3 extends Fragment{
	

	SimpleAdapter adapter_listContact;
	ListView list_contact;
	TextView text_addContact;
	LinearLayout ll4addcontactordialog;
	EditText edit4inputnickname;
	ImageView tx4contact;
	//需要添加已经添加此人为好友的验证吗	？
	String ifExistThisNick;
	
	String uGotTheFriendsId;
	String newuGotTheFriendsID;
	String StringuGotTheFriendsId;
	String StringnewuGotTheFriendsId;
	
    List<Map<String, Object>> data;
    LinearLayout loadLL;
	
	String userNickName;
	String [] array4contact = new String[]{};
	
	List<Map<String, Object>> listItems;
	Map<String, Object> listItem;
	

	SimpleAdapter adapter4cl;
	ArrayList<String> ConNmList;
	
	ArrayList<String> tempTX;
	ArrayList<String> tempName;
	ArrayList<String> listTX;
	ArrayList<String> listName = new ArrayList<String>();
	ArrayList<String> arrUtx = new ArrayList<String>();
	ArrayList<String> arrUtx2 = new ArrayList<String>();
	
	ArrayList<String> tempAllFromUser;
	ArrayList<String> listAllFromUser = new ArrayList<String>();
	MyFSAdapter myFSAdapter ;

	HttpEntity httpEntity;
	HttpResponse httpResponse;
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();
			listName = bundle.getStringArrayList("listName");
			listAllFromUser = bundle.getStringArrayList("listAllFromUser");
			arrUtx = bundle.getStringArrayList("Utx");

//			if (listName.equals(null)) {
//				
//			}else 
			if (listName.size() == 0) {
				loadLL.setVisibility(View.GONE);
				Toast.makeText(getActivity(), "没有好友哟！", Toast.LENGTH_SHORT).show();
			}else {
				getListView();
				loadLL.setVisibility(View.GONE);
				list_contact.setAdapter(myFSAdapter);
			}
//			getListView();
			
		}
	};
	
	static fragmentJAVA3 newInstance() {
		fragmentJAVA3 newFragment = new fragmentJAVA3();
		

		
        return newFragment;
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view1 = inflater.inflate(R.layout.fragment3, container,false);
		text_addContact = (TextView)view1.findViewById(R.id.text_addContact);
		loadLL = (LinearLayout)view1.findViewById(R.id.loadLL);
		list_contact = (ListView)view1.findViewById(R.id.list_contact);
		tx4contact = (ImageView)view1.findViewById(R.id.tx4contact);
		
		Intent intent1 = getActivity().getIntent();
		Bundle bundle = intent1.getExtras();
		userNickName = bundle.getString("userName");

		myFSAdapter = new MyFSAdapter(getActivity());
		
//		ConNmList = new ArrayList<String>();
		
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpGet httpGet = new HttpGet("http://zeng.shaoning.net/edusocial/FriendShip.json");
				HttpGet httpGet2 = new HttpGet("http://zeng.shaoning.net/edusocial/User.json");
				HttpClient httpClient = new DefaultHttpClient();
				Message msg2 = new Message();
				//Looper.loop();
				InputStream inputStream = null;
				InputStream inputStream2 = null;
				try {
					//zheli 这里
					httpResponse = httpClient.execute(httpGet);
					httpEntity = httpResponse.getEntity();
					inputStream = httpEntity.getContent();
					//GB2312
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"GB2312"));
					String result = "";
					String line = "";
					while((line = reader.readLine()) != null){
						result = result + line;
					}
//					System.out.println("1111" + result);
					
					JSONObject JO4FriendShipJson = new JSONObject(result);
					JSONArray JA4FriendShip = JO4FriendShipJson.getJSONArray("FriendShip");
//					System.out.println(JA4FriendShip.length());
					tempTX = new ArrayList<String>();
					tempName = new ArrayList<String>();
					for (int q = 0; q < JA4FriendShip.length(); q++) {
						JSONObject JO4SingleFriendShip = JA4FriendShip.getJSONObject(q);
						if (JO4SingleFriendShip.getString("PositiveU").equals(userNickName)
								&& JO4SingleFriendShip.getString("FSstate").equals("1")) {
//							hisTX = JO4SingleFriendShip.getString("");
							tempName.add(JO4SingleFriendShip.getString("NegativeU"));
						}
					}
					
					httpResponse = httpClient.execute(httpGet2);
					httpEntity = httpResponse.getEntity();
					inputStream2 = httpEntity.getContent();
					//GB2312
					BufferedReader reader2 = new BufferedReader(new InputStreamReader(inputStream2,"GB2312"));
					String result2 = "";
					String line2 = "";
					while((line2 = reader2.readLine()) != null){
						result2 = result2 + line2;
					}
//					System.out.println("2222" + result2);
					
					JSONObject JO4UserJson = new JSONObject(result2);
					JSONArray JA4UserShip = JO4UserJson.getJSONArray("User");
//					System.out.println(JA4UserShip.length());
					tempAllFromUser = new ArrayList<String>();
//					tempName = new ArrayList<String>();
					for (int q = 0; q < JA4UserShip.length(); q++) {
						JSONObject JO4SingleJA4UserShip = JA4UserShip.getJSONObject(q);
//						if (JO4SingleJA4UserShip.getString("positiveU").equals(userNickName)) {
//							hisTX = JO4SingleFriendShip.getString("");
							tempAllFromUser.add(JO4SingleJA4UserShip.getString("nickname"));
//						}
//						tempAllName.add(JO4SingleJA4UserShip.getString(""));
					}
					for (int r = 0; r < tempName.size(); r++) {
						for (int t = 0; t < JA4UserShip.length(); t++) {
							JSONObject JO4SingleJA4UserShip = JA4UserShip.getJSONObject(t);
//							System.out.println(JO4SingleJA4UserShip.getString("nickname"));
							if (JO4SingleJA4UserShip.getString("nickname").equals(tempName.get(r))) {
								arrUtx2.add(JO4SingleJA4UserShip.getString("tx"));
//								System.out.println("here   PangTS");
								break;
							}
						}
					}
					
					Bundle bNor = new Bundle();
					bNor.putStringArrayList("listName", tempName);
					bNor.putStringArrayList("listAllFromUser", tempAllFromUser);
					bNor.putStringArrayList("Utx", arrUtx2);
					msg2.setData(bNor);


				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					try{//这里zheli
						inputStream.close();
						inputStream2.close();
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}					
				handler.sendMessage(msg2);
			}
		}.start();
//		dp = new DatabasePart("jim/sjk", "a1.db");
//		CursoruGotTheFriendsId = dp.query(uGotTheFriendsId, new String[]{userNickName});
////		int nc  = ;
//		//CursoruGotTheFriendsId.getString(0).equals("")
//		if (CursoruGotTheFriendsId.getCount() == 0) {
//			Toast.makeText(getActivity(), "没有好友哟！", Toast.LENGTH_SHORT).show();
//		}else {
////			StringuGotTheFriendsId = CursoruGotTheFriendsId.getString(0);
////			array4contact = StringuGotTheFriendsId.split("-");
//			for (CursoruGotTheFriendsId.moveToFirst();!CursoruGotTheFriendsId.isAfterLast();CursoruGotTheFriendsId.moveToNext()) {
//				ConNmList.add(CursoruGotTheFriendsId.getString(CursoruGotTheFriendsId.getColumnIndex("NegativeU")));
//				System.out.println(ConNmList.get(CursoruGotTheFriendsId.getPosition()));
//			}
//			
//			getListView();
//		}
		
		
//		getListView();

		
//		Map<String, Object> contactItem = new HashMap<String,Object>();
//		contactItem.put("icon_contact", R.drawable.ic_launcher);
//		contactItem.put("name_contact", name);
//		contactItems.add(contactItem);
//		adapter_listContact = new SimpleAdapter(getActivity(), contactItems, R.layout.layout_list_contact, 
//				new String [] {"icon_contact","name_contact"}, new int[]{R.id.icon4contact,R.id.name4contact});
//		list_contact.setAdapter(adapter_listContact);
		
		list_contact.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				if (listName.size() == 0 || arrUtx.size() == 0) {
					Toast.makeText(getActivity(), "网络异常，请检查网络！", Toast.LENGTH_LONG).show();
				}else {
					Intent intent = new Intent();
					intent.putExtra("nick", listName.get(position));
					intent.putExtra("userTx", arrUtx.get(position));
					intent.putExtra("from", "contactList");
					intent.setClass(getActivity(), personalInfo.class);
					startActivity(intent);
				}

			}
			
		});
		
		text_addContact.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				if (listName.size() == 0 || arrUtx.size() == 0) {
//					Toast.makeText(getActivity(), "网络异常，请检查网络！", Toast.LENGTH_LONG).show();
//				}else {
					ll4addcontactordialog = (LinearLayout)getActivity().getLayoutInflater()
							.inflate(R.layout.xml4addcontactor_dialog, null);
					
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setIcon(R.drawable.ic_launcher).setTitle("请输入好友昵称：")
					.setView(ll4addcontactordialog)
					.setPositiveButton("添加好友",	 new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							edit4inputnickname = (EditText)ll4addcontactordialog.findViewById(R.id.edit4inputnickname);
							String back = edit4inputnickname.getText().toString().trim();
						
							boolean existed = false;
							boolean yourfriend = false;
							
							if (back.equals(userNickName)) {
								Toast.makeText(getActivity(), "不能添加你自己为好友哇，", Toast.LENGTH_SHORT).show();
							}else {
								for (int j = 0; j < listName.size(); j++) {
									if (back.equals(listName.get(j))) {
										Toast.makeText(getActivity(), "ta已经是你的好友了，", Toast.LENGTH_SHORT).show();
										yourfriend = true;
										break;
									}
								}
								if (yourfriend == true) {
									
								}else {
									for (int i = 0; i < listAllFromUser.size(); i++) {
										if (back.equals(listAllFromUser.get(i))) {
											existed = true;
											Toast.makeText(getActivity(), "好友请求已发送！", Toast.LENGTH_SHORT).show();
											break;
										}
									}
									if (existed == false) {
										Toast.makeText(getActivity(), "没有这个用户!", Toast.LENGTH_SHORT).show();
									}
								}
							}				
						}
					});
					builder.create().show();
//				}
				
				
			}
		});
	
		return view1;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//Toast.makeText(getActivity(), "baGa", Toast.LENGTH_SHORT).show();
	}
	public void getListView(){
		//好像这里有点问题，意思说没有朋友的用户登陆的时候过不了第二页，是因为这里查好友ID为空，然后223行却分解了查询结果，所以提示无指针异常？
		//查询无结果的情况真的好难处理。
	}
	class MyFSAdapter extends BaseAdapter{
		private Context mContext;
		LayoutInflater inflater;
		public MyFSAdapter(Context mContext){
			this.inflater = LayoutInflater.from(mContext);
			this.mContext = mContext;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listName.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			holder = new ViewHolder();
			
			convertView = inflater.inflate(R.layout.layout_list_contact, null,false);
			holder.tv4u = (TextView)convertView.findViewById(R.id.name4contact);
			holder.iv4tx = (ImageView)convertView.findViewById(R.id.tx4contact);
			
			holder.tv4u.setText(listName.get(position));
			holder.iv4tx.setImageResource(R.drawable.withouttouxiang);
			
	    	CallbackImpl callbackImpl = new CallbackImpl(holder.iv4tx);
	    	AsyncImageLoader loader = new AsyncImageLoader();
	    	Drawable cacheImage = loader.loadDrawable(arrUtx.get(position), callbackImpl);//zheli
	    	if (cacheImage != null) {
	    		holder.iv4tx.setImageDrawable(cacheImage);
	    	}
	    		
			return convertView;
		}
		private class ViewHolder {

			  TextView tv4u;

			  ImageView iv4tx;
		}
	}
}

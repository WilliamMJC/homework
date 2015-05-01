package com.jj.tmdemo.MainUI;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DatabasePart.DatabasePart;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jj.tmdemo.R;

//一个添加好友行+一个listview实现通讯录，可以看通讯录的视频以后再做。

public class fragmentJAVA3 extends Fragment{
	

	SimpleAdapter adapter_listContact;
	ListView list_contact;
	TextView text_addContact;
	LinearLayout ll4addcontactordialog;
	EditText edit4inputnickname;
	
	//需要添加已经添加此人为好友的验证吗	？
	String ifExistThisNick;
	
	String uGotTheFriendsId;
	String newuGotTheFriendsID;
	String StringuGotTheFriendsId;
	String StringnewuGotTheFriendsId;
	
	String userNickName;
	String [] array4contact = new String[]{};
	
	List<Map<String, Object>> listItems;
	Map<String, Object> listItem;
	
	Cursor CursoruGotTheFriendsId;
//	Cursor CursornewuGotTheFriendsId;
	SimpleAdapter adapter4cl;
	ArrayList<String> ConNmList;
	
	Cursor checkExist;
	
	DatabasePart dp;

	
	static fragmentJAVA3 newInstance() {
		fragmentJAVA3 newFragment = new fragmentJAVA3();
		

		
        return newFragment;
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view1 = inflater.inflate(R.layout.fragment3, container,false);
		text_addContact = (TextView)view1.findViewById(R.id.text_addContact);
		
		list_contact = (ListView)view1.findViewById(R.id.list_contact);
		ifExistThisNick = "select nickname from User where nickname = ? ";
		uGotTheFriendsId = "select NegativeU from FriendShip where PositiveU = ? and FSstate = 1";
		
		Intent intent1 = getActivity().getIntent();
		Bundle bundle = intent1.getExtras();
		userNickName = bundle.getString("userName");
		ConNmList = new ArrayList<String>();
		
		Toast.makeText(getActivity(), userNickName + " lala", Toast.LENGTH_SHORT).show();

		
		dp = new DatabasePart("jim/sjk", "a1.db");
		CursoruGotTheFriendsId = dp.query(uGotTheFriendsId, new String[]{userNickName});
//		int nc  = ;
		//CursoruGotTheFriendsId.getString(0).equals("")
		if (CursoruGotTheFriendsId.getCount() == 0) {
			Toast.makeText(getActivity(), "没有好友哟！", Toast.LENGTH_SHORT).show();
		}else {
//			StringuGotTheFriendsId = CursoruGotTheFriendsId.getString(0);
//			array4contact = StringuGotTheFriendsId.split("-");
			for (CursoruGotTheFriendsId.moveToFirst();!CursoruGotTheFriendsId.isAfterLast();CursoruGotTheFriendsId.moveToNext()) {
				ConNmList.add(CursoruGotTheFriendsId.getString(CursoruGotTheFriendsId.getColumnIndex("NegativeU")));
				System.out.println(ConNmList.get(CursoruGotTheFriendsId.getPosition()));
			}
			
			getListView();
		}
		
		
//		getListView();

		
//		Map<String, Object> contactItem = new HashMap<String,Object>();
//		contactItem.put("icon_contact", R.drawable.ic_launcher);
//		contactItem.put("name_contact", name);
//		contactItems.add(contactItem);
//		adapter_listContact = new SimpleAdapter(getActivity(), contactItems, R.layout.layout_list_contact, 
//				new String [] {"icon_contact","name_contact"}, new int[]{R.id.icon4contact,R.id.name4contact});
//		list_contact.setAdapter(adapter_listContact);
		
		
		
		text_addContact.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ll4addcontactordialog = (LinearLayout)getActivity().getLayoutInflater().inflate(R.layout.xml4addcontactor_dialog, null);
//				d4np = new Database4NewsPart("jim/sjk2", "a1.db");
//				Map<String, Object> contactItem = new HashMap<String,Object>();
//				contactItem.put("icon_contact", R.drawable.ic_launcher);
//				contactItem.put("name_contact", name);
//				contactItems.add(contactItem);
//				adapter_listContact = new SimpleAdapter(getActivity(), contactItems, R.layout.layout_list_contact, 
//						new String [] {"icon_contact","name_contact"}, new int[]{R.id.icon4contact,R.id.name4contact});
//				list_contact.setAdapter(adapter_listContact);
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setIcon(R.drawable.ic_launcher).setTitle("请输入好友昵称：")
				.setView(ll4addcontactordialog)
				.setPositiveButton("添加好友",	 new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						newuGotTheFriendsID = "";
						String targetUser;
						
						
						//Toast.makeText(getActivity(), "啦啦啦", Toast.LENGTH_SHORT).show();
						edit4inputnickname = (EditText)ll4addcontactordialog.findViewById(R.id.edit4inputnickname);
						String back = edit4inputnickname.getText().toString().trim();
//						Toast.makeText(getActivity(), back, Toast.LENGTH_SHORT).show();
						
						//dp = new DatabasePart("jim/sjk2", "a1.db");
						checkExist = dp.query(ifExistThisNick, new String[]{back});
						
						if (!checkExist.moveToFirst()) {
							Toast.makeText(getActivity(), "用户不存在，请确认用户昵称是否正确", Toast.LENGTH_SHORT).show();
							edit4inputnickname.setText("");
						}else {
							targetUser = checkExist.getString(0);
							
//							StringuGotTheFriendsId = targetUser + "-" + StringuGotTheFriendsId;
							
							
							ContentValues cv4addContact = new ContentValues();
							cv4addContact.put("PositiveU",userNickName);
							cv4addContact.put("NegativeU", targetUser);
							cv4addContact.put("FSstate", 2);
//							dp.updatebyid("User", cv4addContact, userNickName);
							dp.insert("FriendShip", cv4addContact);
							SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
							String date = sDateFormat.format(new java.util.Date());
							ContentValues cv4putInNews = new ContentValues();
							
							cv4putInNews.put("time", date);
							cv4putInNews.put("title", userNickName + "请求加你为好友！");
							cv4putInNews.put("userid", userNickName);
							cv4putInNews.put("negauserid", targetUser);
							cv4putInNews.put("type", "req");
							dp.insert("News", cv4putInNews);
							
							Toast.makeText(getActivity(), "发送请求了！", Toast.LENGTH_SHORT).show();
							
//							Toast.makeText(getActivity(), "用户存在，请确认用户昵称是否正确", Toast.LENGTH_SHORT).show();
							
//							getListView();
						}					
						
//						CursornewuGotTheFriendsId = dp.query(newuGotTheFriendsID, new String[]{back});
//						CursornewuGotTheFriendsId.moveToFirst();
//						StringnewuGotTheFriendsId = CursornewuGotTheFriendsId.getString(0);
						
							//需要在此验证“已添加此好友”吗？
							//dp.updatebyid("User", cv, targetID);
							//取出对应id的好友字段值，然后拼上EditText的值，构成新的值放回去。
							//真的用的时候不是用back，而是用操作的这个用户的昵称。

							
						
						//dp.DataClose();
					}
				});
				builder.create().show();
				
						

				
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
		dp.DataClose();
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

		//ll4addcontactordialog = (LinearLayout)view.findViewById(R.id.ll4addcontactordialog);
//		d4np = new Database4NewsPart("jim/sjk2", "a1.db");

//		if (ConNmList.size() == 0) {
//			
//		}else {
//			listItems = new ArrayList<Map<String,Object>>();
//
//			for (int i = 0; i < array4contact.length; i++) {
//				listItem = new HashMap<String, Object>();
//				listItem.put("name", ConNmList.get(i));
//				listItems.add(listItem);
//			}
//			adapter4cl = new SimpleAdapter(getActivity(), listItems, R.layout.layout_list_contact, new String[]{"name"}, new int[]{R.id.name4contact});
//			list_contact.setAdapter(adapter4cl);
//		}
//	}
		listItems = new ArrayList<Map<String,Object>>();

		for (int i = 0; i < ConNmList.size(); i++) {
			listItem = new HashMap<String, Object>();
			listItem.put("name", ConNmList.get(i));
			listItems.add(listItem);
		}
		adapter4cl = new SimpleAdapter(getActivity(), listItems, R.layout.layout_list_contact, new String[]{"name"}, new int[]{R.id.name4contact});
		list_contact.setAdapter(adapter4cl);
	}
}

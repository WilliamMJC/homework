package com.hzu.feirty.MailIM.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.hzu.feirty.MailIM.R;
import com.hzu.feirty.MailIM.db.Email;
import com.hzu.feirty.MailIM.entity.Ip;
import com.hzu.feirty.MailIM.utils.IdentityReceiver;
import com.hzu.feirty.MailIM.utils.MailReceiver;
import com.hzu.feirty.MailIM.utils.PreferencesUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment{
	//邮箱的接收数据集合
	private ArrayList<Email> mailslist = new ArrayList<Email>();
	private List<Email> list;
	private ProgressDialog pd;
	private String url = Ip.ip + "/MailIM/DoGetMail?";
	private String url3 = Ip.ip + "/MailIM/DoGetType?";
	private ArrayList<ArrayList<InputStream>> attachmentsInputStreamsList = new ArrayList<ArrayList<InputStream>>();
	private Context context;
	private String type="INBOX";
	private SQLiteDatabase db;
	private Toolbar toolbar;
	//适配器接口;连接后端数据和前端显示的适配器接口
	private MyAdapter myAdapter;
	//显示列表
	private ListView lv_box;
	//接收子线程的数据
	private Handler handler;
	//邮件接收数据数组
	private List<MailReceiver> mailReceivers;
	//进度条控件
	private ProgressBar pb_box;
	private IdentityReceiver mReceiver;
	private static final String IDENTITY = "saveidentity";
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view= inflater.inflate(R.layout.fragment_main, null);
		return view;
	}
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		context = MainFragment.this.getActivity();
		pb_box = (ProgressBar) getActivity().findViewById(R.id.pb_box);
		lv_box = (ListView) getActivity().findViewById(R.id.lv_box);
		toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
		AppCompatActivity activity = (AppCompatActivity) getActivity();
		//activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		if (activity.getSupportActionBar() != null){
			activity.getSupportActionBar().hide();
		}
		toolbar.setTitle("首页");
		toolbar.inflateMenu(R.menu.base_toolbar_menu);//设置右上角的填充菜单
		toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
					case R.id.action_insert:
						isType();
						break;
				}
				return true;
			}
		});
		myAdapter = new MyAdapter();
		lv_box.setAdapter(myAdapter);
		handler = new MyHandler(MainFragment.this);
		isType();
		/*if(str.equals("老师")||str.equals("学生")) {
			receiveEmail();*/
			/*//子线程处理
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						//MailHelper() 邮件接收功能操作方法，返回所有邮件接收的内容集合
						mailReceivers = MailHelper.getInstance(context).getMailByTeacher(type);
					} catch (Exception e) {
						e.printStackTrace();
					}
					getMailsList(mailReceivers);
					handler.obtainMessage(1).sendToTarget();
				}
			}).start();*/
		/*}else if(str.equals("学生")){
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						//MailHelper() 邮件接收功能操作方法，返回所有邮件接收的内容集合
						mailReceivers = MailHelper.getInstance(context).getMailByStudent(type);
					} catch (Exception e) {
						e.printStackTrace();
					}
					getMailsList(mailReceivers);
					handler.obtainMessage(1).sendToTarget();
				}
			}).start();
		}else{
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						//MailHelper() 邮件接收功能操作方法，返回所有邮件接收的内容集合
						 mailReceivers = MailHelper.getInstance(context).getStudentId(type);
					} catch (Exception e) {
						e.printStackTrace();
					}
					getMailsList(mailReceivers);
					handler.obtainMessage(1).sendToTarget();
				}
			}).start();
		}*/
	}
	/**
	 * 将序列化的对象赋给email ,再将email加到mailslist集合中
	 * @param
	 */
	/*private void getMailsList(List<MailReceiver> mails) {
		if(mails!=null) {
			for (MailReceiver mailReceiver : mails) {
				Email email = new Email();
				try {
					email.setMessageID(mailReceiver.getMessageID());
					email.setFrom(mailReceiver.getFrom());
					email.setTo(mailReceiver.getMailAddress("TO"));
					email.setCc(mailReceiver.getMailAddress("CC"));
					email.setBcc(mailReceiver.getMailAddress("BCC"));
					email.setSubject(mailReceiver.getSubject());
					email.setSentdata(mailReceiver.getSentData());
					email.setContent(mailReceiver.getMailContent());
					email.setReplysign(mailReceiver.getReplySign());
					email.setHtml(mailReceiver.isHtml());
					email.setNews(mailReceiver.isNew());
					email.setAttachments(mailReceiver.getAttachments());
					email.setCharset(mailReceiver.getCharset());
					//保存每个邮件的信息到mailslist<Email>
					attachmentsInputStreamsList.add(0, mailReceiver.getAttachmentsInputStreams());
					//list.add(0,email);
					mailslist.add(0, email);
					handler.obtainMessage(0).sendToTarget();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}*/

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mailslist.size();
		}

		@Override
		public Object getItem(int position) {
			return mailslist.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			convertView = LayoutInflater.from(context).inflate(R.layout.mailbox_item, null);
			TextView tv_sentdate = (TextView) convertView.findViewById(R.id.tv_sentdate);
			TextView tv_new = (TextView) convertView.findViewById(R.id.tv_new);
			TextView tv_subject = (TextView) convertView.findViewById(R.id.tv_subject);
			tv_sentdate.setText(mailslist.get(position).getSentdata());
			if (mailslist.get(position).isNews()) {
				tv_new.setVisibility(View.VISIBLE);
			}
			tv_subject.setText(mailslist.get(position).getSubject());
			//点击List的监听
			convertView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//((MailApplication)getActivity().getApplication()).setAttachmentsInputStreams(attachmentsInputStreamsList.get(position));
					final Intent intent = new Intent(getActivity(), MailContentActivity.class).putExtra("EMAIL", mailslist.get(position));
					startActivity(intent);
				}
			});
			return convertView;
		}

	}
	private static class MyHandler extends Handler {

		private WeakReference<MainFragment> wrActivity;

		public MyHandler(MainFragment activity) {
			this.wrActivity = new WeakReference<MainFragment>(activity);
		}
		@Override
		public void handleMessage(android.os.Message msg) {
			final MainFragment activity = wrActivity.get();
			switch (msg.what) {
				case 0:
					activity.myAdapter.notifyDataSetChanged();
					break;
				case 1:
					activity.pb_box.setVisibility(View.GONE);
					break;
				default:
					break;
			}
		}
	}
	public void receiveEmail() {
		RequestParams params = new RequestParams();
		String user =PreferencesUtil.getSharedStringData(MainFragment.this.getActivity(),USERNAME);
		params.put("user", user);
		params.put("action", "receive");
		AsyncHttpClient client = new AsyncHttpClient();
		client.setConnectTimeout(500000);
		pd= ProgressDialog.show(MainFragment.this.getActivity(),null, "搜索作业中…");
		client.post(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int i, org.apache.http.Header[] headers, byte[] responseBody) {
				String str = new String(responseBody);
				if (str != null) {
					try {
						//handler.obtainMessage(0).sendToTarget();
						JSONObject object = new JSONObject(str);
						if (object.getString("code").equals("success")) {
							JSONArray array = object.getJSONArray("data");
							for (int a = 0; a < array.length(); a++) {
								JSONObject item = array.getJSONObject(a);
								Email mail = new Email();
								mail.setFrom(item.getString("from"));
								mail.setAttachment(item.getString("attachment"));
								mail.setSubject(item.getString("subject"));
								mail.setContent(item.getString("content"));
								mail.setSentdata(item.getString("time"));
								//mail.setAttachments(object.getJSONArray("attachment"));
								mailslist.add(0,mail);
							}
							handler.obtainMessage(1).sendToTarget();
							pd.dismiss();
							Toast.makeText(MainFragment.this.getActivity(),"接收成功",Toast.LENGTH_SHORT).show();
						} else {
							// btn_login.setEnabled(true);
							Toast.makeText(MainFragment.this.getActivity(),"接收失败",Toast.LENGTH_SHORT).show();
							pd.dismiss();

						}
					} catch (JSONException e) {
						e.printStackTrace();
						// btn_login.setEnabled(true);
					}
				} else {
					// btn_login.setEnabled(true);
				}
			}
			@Override
			public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
				//DialogView.dismiss();
				//btn_login.setEnabled(true);
				pd.dismiss();
				Toast.makeText(MainFragment.this.getActivity(), "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();

			}
		});
	}

	private void isType(){
		RequestParams params = new RequestParams();
		params.put("action", "istype");
		String user =PreferencesUtil.getSharedStringData(MainFragment.this.getActivity(),"username");
		params.put("user", user);
		AsyncHttpClient client = new AsyncHttpClient();
		client.setConnectTimeout(5000);
		client.post(url3, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int i, org.apache.http.Header[] headers, byte[] responseBody) {
				String str = new String(responseBody);
				if (str != null) {
					try {
						JSONObject object = new JSONObject(str);
						if (object.getString("code").equals("teacher")) {
							Toast.makeText(MainFragment.this.getActivity(), "欢迎老师", Toast.LENGTH_SHORT).show();
							receiveEmail();
						} else if(object.getString("code").equals("student")){
							Toast.makeText(MainFragment.this.getActivity(), "欢迎学生", Toast.LENGTH_SHORT).show();
							receiveEmail();
						}
						else {
							// btn_login.setEnabled(true);
							Toast.makeText(MainFragment.this.getActivity(), "请先选择身份", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
				}
			}
			@Override
			public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
				Toast.makeText(MainFragment.this.getActivity(), "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
			}
		});
	}
}

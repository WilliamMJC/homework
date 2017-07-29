package com.hzu.feirty.MailIM.activity;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hzu.feirty.MailIM.R;
import com.hzu.feirty.MailIM.db.Email;
import com.hzu.feirty.MailIM.entity.Ip;
import com.hzu.feirty.MailIM.utils.PreferencesUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MyCenterFragment extends Fragment {
	private TextView my_context;
	private TextView my_works;
	private TextView my_teachers;
	private ProgressDialog pd;
	private Toolbar toolbar;
	private LinearLayout setting;
	private ArrayList<Email> mails = new ArrayList<Email>();
	private static final String PWD="teacher_pwd";
	private LinearLayout ll_1;
	private LinearLayout identity;
	private LinearLayout receivework;
	private LinearLayout checkwork;
	final String items[] = { "学生","老师"};
	private static final String IDENTITY = "saveidentity";
	private  static  final String ISNULL= "isnull";
	private String url = Ip.ip + "/MailIM/DoGetUser?";
	private String url2 = Ip.ip + "/MailIM/DoGetMail?";
	private String url3 = Ip.ip + "/MailIM/DoGetType?";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view= inflater.inflate(R.layout.fragment_mycenter, null);
		return view;
	}
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		my_context = (TextView) getActivity().findViewById(R.id.my_context);
		ll_1 = (LinearLayout) getActivity().findViewById(R.id.ll_1);
		identity = (LinearLayout) getActivity().findViewById(R.id.identity);
		receivework = (LinearLayout) getActivity().findViewById(R.id.receivework);
		checkwork = (LinearLayout) getActivity().findViewById(R.id.checkwork);
		setting = (LinearLayout)  getActivity().findViewById(R.id.setting);
		toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
		AppCompatActivity activity = (AppCompatActivity) getActivity();
		//activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		if (activity.getSupportActionBar() != null){
			activity.getSupportActionBar().hide();
		}
		ll_1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),CourseActivity.class);
				startActivity(intent);
			}
		});
		setting.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				isType();
			}
		});
		checkwork.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//receiveEmail();
			}
		});
		receivework.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Dialog dialog=new AlertDialog.Builder(MyCenterFragment.this.getActivity())
						.setTitle("提示")
						//.setIcon(R.drawable.ic_launcher)
						.setMessage("确认收作业吗？")
						//相当于点击确认按钮
						.setPositiveButton("确认", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								pd = ProgressDialog.show(MyCenterFragment.this.getActivity(), null, "正在打包发送…");
								receiveHomework();
							}
						})
						//相当于点击取消按钮
						.setNegativeButton("取消", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub

							}
						})
						.create();
				dialog.show();
				/*pd= ProgressDialog.show(MyCenterFragment.this.getActivity(), "收作业", "正在打包发送…");
				handler.sendEmptyMessage(0);*/


			}
		});
		identity.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setType();
			}
		});
	}
	private Handler handler =new Handler(){
		@Override
		//当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			//只要执行到这里就关闭对话框
			pd.dismiss();
		}
	};
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	public void receiveEmail() {
		RequestParams params = new RequestParams();
		params.put("user", "abc");
		params.put("action", "receive");
		AsyncHttpClient client = new AsyncHttpClient();
		client.setConnectTimeout(5000);
		client.post(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int i, org.apache.http.Header[] headers, byte[] responseBody) {
				String str = new String(responseBody);
				if (str != null) {
					try {
						JSONObject object = new JSONObject(str);
						if (object.getString("code").equals("success")) {
							JSONArray array = object.getJSONArray("data");
							for (int a = 0; a < array.length(); a++) {
								JSONObject item = array.getJSONObject(a);
								Email mail = new Email();
								mail.setFrom(item.getString("from"));
								mail.setSubject(item.getString("subject"));
								mail.setContent(item.getString("content"));
								mail.setSentdata(item.getString("time"));
								//mail.setAttachments(object.getJSONArray("attachment"));
								mails.add(0,mail);
							}
							Toast.makeText(MyCenterFragment.this.getActivity(),"接收成功",Toast.LENGTH_SHORT).show();
						} else {
							// btn_login.setEnabled(true);
							Toast.makeText(MyCenterFragment.this.getActivity(),"接收失败",Toast.LENGTH_SHORT).show();

						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
				}
			}
			@Override
			public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
				Toast.makeText(MyCenterFragment.this.getActivity(), "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();

			}
		});
	}
	private void downlode(){
		RequestParams params = new RequestParams();
		params.put("action", "receive2");
		AsyncHttpClient client = new AsyncHttpClient();
		client.setConnectTimeout(5000);
		client.post(url2, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int i, org.apache.http.Header[] headers, byte[] responseBody) {
				String str = new String(responseBody);
				if (str != null) {
					try {
						JSONObject object = new JSONObject(str);
						if (object.getString("code").equals("success")) {
							Toast.makeText(MyCenterFragment.this.getActivity(), "下载成功！", Toast.LENGTH_SHORT).show();
						} else {
							// btn_login.setEnabled(true);
							Toast.makeText(MyCenterFragment.this.getActivity(), "下载失败！", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
				}
			}

			@Override
			public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
				//DialogView.dismiss();
				//btn_login.setEnabled(true);
				Toast.makeText(MyCenterFragment.this.getActivity(), "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
			}
		});
	}
	private void number(){
		RequestParams params = new RequestParams();
		String user =PreferencesUtil.getSharedStringData(MyCenterFragment.this.getActivity(),"username");
		params.put("user", user);
		params.put("action", "number");
		AsyncHttpClient client = new AsyncHttpClient();
		client.setConnectTimeout(5000);
		client.post(url2, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int i, org.apache.http.Header[] headers, byte[] responseBody) {
				String str = new String(responseBody);
				if (str != null) {
					try {
						JSONObject object = new JSONObject(str);
						if (object.getString("code").equals("success")) {
							Toast.makeText(MyCenterFragment.this.getActivity(), "添加成功！", Toast.LENGTH_SHORT).show();

						} else {
							// btn_login.setEnabled(true);
							Toast.makeText(MyCenterFragment.this.getActivity(), "添加失败！", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
				}
			}

			@Override
			public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
				Toast.makeText(MyCenterFragment.this.getActivity(), "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
			}
		});
	}
	private void receiveHomework(){
		RequestParams params = new RequestParams();
		String user =PreferencesUtil.getSharedStringData(MyCenterFragment.this.getActivity(),"username");
		params.put("user", user);
		params.put("action", "RECEIVEHOMEWORK");
		AsyncHttpClient client = new AsyncHttpClient();
		client.setConnectTimeout(10000);
		//receiveHomework();
		client.post(url2, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int i, org.apache.http.Header[] headers, byte[] responseBody) {
				String str = new String(responseBody);
				if (str != null) {
					try {
						JSONObject object = new JSONObject(str);
						if(object.getString("code").equals("noidentry")){
							handler.sendEmptyMessage(0);
							Toast.makeText(MyCenterFragment.this.getActivity(), "不允许非教师操作", Toast.LENGTH_SHORT).show();
						}else if(object.getString("code").equals("nomail")){
							handler.sendEmptyMessage(0);
							Toast.makeText(MyCenterFragment.this.getActivity(), "请先设置工作邮箱", Toast.LENGTH_SHORT).show();
						}else {
							if (object.getString("code").equals("success")) {
								handler.sendEmptyMessage(0);
								Toast.makeText(MyCenterFragment.this.getActivity(), "收作业成功,请及时查收工作邮箱", Toast.LENGTH_SHORT).show();
							} else {
								handler.sendEmptyMessage(0);
								Toast.makeText(MyCenterFragment.this.getActivity(), "未知错误！", Toast.LENGTH_SHORT).show();
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
				}
			}
			@Override
			public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
				//DialogView.dismiss();
				//btn_login.setEnabled(true);
				handler.sendEmptyMessage(0);
				Toast.makeText(MyCenterFragment.this.getActivity(), "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
			}
		});
	}
	private void isType(){
		RequestParams params = new RequestParams();
		params.put("action", "istype");
		String user =PreferencesUtil.getSharedStringData(MyCenterFragment.this.getActivity(),"username");
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
							Intent intent = new Intent(getActivity(),TeacherSetActivity.class);
							startActivity(intent);

						} else if(object.getString("code").equals("student")){
							Intent intent = new Intent(getActivity(),StudentSetActivity.class);
							startActivity(intent);
						}
						else {
							// btn_login.setEnabled(true);
							Toast.makeText(MyCenterFragment.this.getActivity(), "请先选择身份", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
				}
			}

			@Override
			public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
				Toast.makeText(MyCenterFragment.this.getActivity(), "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
			}
		});
	}
	private void setType(){
		RequestParams params = new RequestParams();
		params.put("action", "settype");
		String user =PreferencesUtil.getSharedStringData(MyCenterFragment.this.getActivity(),"username");
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
						if (object.getString("code").equals("success")) {
							Toast.makeText(MyCenterFragment.this.getActivity(), "你已选择身份", Toast.LENGTH_SHORT).show();
						} else if(object.getString("code").equals("false")){
							final AlertDialog.Builder builder = new AlertDialog.Builder(MyCenterFragment.this.getActivity());
							builder.setTitle("选择身份");
							//builder.setIcon(android.R.drawable.ic_dialog_info);
							builder.setItems(items, new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									String select_item = items[which].toString();
									if(select_item.equals("学生")) {
										setType2("student");
									}else{
										setType2("teacher");
									}
								}
							});
							AlertDialog dialog = builder.create();
							dialog.show();
						}
						else {
							Toast.makeText(MyCenterFragment.this.getActivity(), "未知错误", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
				}
			}

			@Override
			public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
				Toast.makeText(MyCenterFragment.this.getActivity(), "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
			}
		});
	}
	private void setType2(String type){
		RequestParams params = new RequestParams();
		params.put("action", "settype2");
		String user =PreferencesUtil.getSharedStringData(MyCenterFragment.this.getActivity(),"username");
		params.put("user", user);
		params.put("type",type);
		AsyncHttpClient client = new AsyncHttpClient();
		client.setConnectTimeout(5000);
		client.post(url3, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int i, org.apache.http.Header[] headers, byte[] responseBody) {
				String str = new String(responseBody);
				if (str != null) {
					try {
						JSONObject object = new JSONObject(str);
						if (object.getString("code").equals("success")) {
							Toast.makeText(MyCenterFragment.this.getActivity(), "设置成功", Toast.LENGTH_SHORT).show();
						} else if(object.getString("code").equals("false")){
							Toast.makeText(MyCenterFragment.this.getActivity(), "设置失败", Toast.LENGTH_SHORT).show();
						}
						else {
							Toast.makeText(MyCenterFragment.this.getActivity(), "未知错误", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
				}
			}

			@Override
			public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
				Toast.makeText(MyCenterFragment.this.getActivity(), "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
			}
		});
	}
}

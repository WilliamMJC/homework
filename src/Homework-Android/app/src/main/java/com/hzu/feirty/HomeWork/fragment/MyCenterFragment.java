package com.hzu.feirty.HomeWork.fragment;
import android.app.Activity;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hzu.feirty.HomeWork.R;
import com.hzu.feirty.HomeWork.activity.index.MailApplication;
import com.hzu.feirty.HomeWork.activity.index.MyApplication;
import com.hzu.feirty.HomeWork.activity.mycenter.CourseActivity;
import com.hzu.feirty.HomeWork.activity.mycenter.CourseToWorkedActivity;
import com.hzu.feirty.HomeWork.activity.mycenter.SetActivity;
import com.hzu.feirty.HomeWork.activity.mycenter.StudentActivity;
import com.hzu.feirty.HomeWork.activity.mycenter.StudentSetActivity;
import com.hzu.feirty.HomeWork.activity.mycenter.TaskActivity;
import com.hzu.feirty.HomeWork.activity.mycenter.TeacherActivity;
import com.hzu.feirty.HomeWork.activity.mycenter.TeacherSetActivity;
import com.hzu.feirty.HomeWork.activity.mycenter.UserInfoActivity;
import com.hzu.feirty.HomeWork.db.Email;
import com.hzu.feirty.HomeWork.entity.Ip;
import com.hzu.feirty.HomeWork.utils.PreferencesUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyCenterFragment extends Fragment {
	private TextView my_user;
	private TextView toolbarTv;
	private TextView idname;
	private ProgressDialog pd;
	private Toolbar toolbar;
	private LinearLayout setting;
	private ArrayList<Email> mails = new ArrayList<Email>();
	private static final String PWD = "teacher_pwd";
	private LinearLayout ll_1;
	private LinearLayout teacher_show;
	private LinearLayout student_show;
	private LinearLayout no_show;
	private LinearLayout identity;
	private LinearLayout my_teacher;
	private LinearLayout my_course;
	private LinearLayout my_work;
	private LinearLayout my_students;
	private LinearLayout all_works;
	private LinearLayout data_set;
	private MailApplication mAPP = null;
	private FunctionFragment.MyHandler mHandler1 = null;
	final String items[] = {"学生", "老师","学习委员"};
	private String url3 = Ip.ip + "/HomeWork/DoGetType?";
	Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 1:
					identity.setVisibility(View.GONE);
					no_show.setVisibility(View.VISIBLE);
					student_show.setVisibility(View.GONE);
					data_set.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							startActivity(new Intent(MyCenterFragment.this.getActivity(), UserInfoActivity.class));
						}
					});
					break;
				case 0:
					identity.setVisibility(View.GONE);
					no_show.setVisibility(View.VISIBLE);
					my_course.setVisibility(View.GONE);
					teacher_show.setVisibility(View.GONE);
					data_set.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							startActivity(new Intent(MyCenterFragment.this.getActivity(),UserInfoActivity.class));
						}
					});
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_mycenter, null);
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		MyApplication.getInstance().addActivity(getActivity());
		toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_my);
		AppCompatActivity activity = (AppCompatActivity) getActivity();
		if (activity.getSupportActionBar() != null) {
			activity.getSupportActionBar().hide();
		}
		toolbar.setTitle("我的");
		toolbar.inflateMenu(R.menu.work_toolbar_menu);//设置右上角的填充菜单
		toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
					case R.id.action_settings:
						startActivity(new Intent(getActivity(), SetActivity.class));
						break;
				}
				return true;
			}
		});
		initView();
	}

	public void initView() {
		String user = PreferencesUtil.getSharedStringData(MyCenterFragment.this.getActivity(), "username");
		ll_1 = (LinearLayout) getActivity().findViewById(R.id.ll_1);
		no_show = (LinearLayout) getActivity().findViewById(R.id.no_show);
		teacher_show = (LinearLayout) getActivity().findViewById(R.id.teacher_show);
		student_show = (LinearLayout) getActivity().findViewById(R.id.student_show);
		identity = (LinearLayout) getActivity().findViewById(R.id.identity);
		my_course = (LinearLayout) getActivity().findViewById(R.id.my_course);
		my_teacher = (LinearLayout) getActivity().findViewById(R.id.my_teacher);
		my_students = (LinearLayout) getActivity().findViewById(R.id.my_students);
		all_works = (LinearLayout) getActivity().findViewById(R.id.all_works);
		my_work = (LinearLayout) getActivity().findViewById(R.id.my_works);
		my_user = (TextView) getActivity().findViewById(R.id.my_user);
		idname = (TextView) getActivity().findViewById(R.id.id_name);
		data_set = (LinearLayout) getActivity().findViewById(R.id.data_set);
		my_user.setText(user + ",你好");
		isType();
		setListener();
	}

	public void setListener() {
		my_course.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), CourseActivity.class);
				startActivity(intent);
			}
		});
		my_work.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), TaskActivity.class);
				startActivity(intent);
			}
		});
		my_teacher.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), TeacherActivity.class);
				startActivity(intent);
			}
		});
		identity.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setType();
			}
		});
		my_students.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), StudentActivity.class);
				startActivity(intent);
			}
		});
		all_works.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), CourseToWorkedActivity.class);
				startActivity(intent);
			}
		});

	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	private void setType() {
		RequestParams params = new RequestParams();
		params.put("action", "settype");
		String user = PreferencesUtil.getSharedStringData(MyCenterFragment.this.getActivity(), "username");
		params.put("user", user);
		AsyncHttpClient client = new AsyncHttpClient();
		client.setConnectTimeout(50000);
		client.post(url3, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int i, org.apache.http.Header[] headers, byte[] responseBody) {
				String str = new String(responseBody);
				if (str != null) {
					try {
						JSONObject object = new JSONObject(str);
						if (object.getString("code").equals("success")) {
							Toast.makeText(MyCenterFragment.this.getActivity(), "你已选择身份", Toast.LENGTH_SHORT).show();
						} else if (object.getString("code").equals("false")) {
							final AlertDialog.Builder builder = new AlertDialog.Builder(MyCenterFragment.this.getActivity());
							builder.setTitle("选择身份");
							//builder.setIcon(android.R.drawable.ic_dialog_info);
							builder.setItems(items, new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									String select_item = items[which].toString();
									if (select_item.equals("学生")) {
										setType2("1");
									} else if (select_item.equals("老师")){
										setType2("2");
									} else if(select_item.equals("学习委员")){
										setType2("2");
									}
								}
							});
							AlertDialog dialog = builder.create();
							dialog.show();
						} else {
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

	private void isType() {
		RequestParams params = new RequestParams();
		params.put("action", "istype");
		String user = PreferencesUtil.getSharedStringData(MyCenterFragment.this.getActivity(), "username");
		String token =PreferencesUtil.getSharedStringData(MyCenterFragment.this.getActivity(),"token");
		params.put("user", user);
		params.put("token",token);
		AsyncHttpClient client = new AsyncHttpClient();
		client.setConnectTimeout(50000);
		String urlnn="http://192.168.10.168:8080/email-homework/api/user/getIdentity";
		//url3
		client.post(urlnn, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int i, org.apache.http.Header[] headers, byte[] responseBody) {
				String str = new String(responseBody);
				if (str != null) {
					try {
						JSONObject object = new JSONObject(str);
						if (object.getString("code").equals("2")) {
							idname.setText("教师或者学习委员");
							myHandler.obtainMessage(1).sendToTarget();
						} else if (object.getString("code").equals("1")) {
							idname.setText("学生");
							myHandler.obtainMessage(0).sendToTarget();
						} else {
							idname.setText("未设置");
							no_show.setVisibility(View.GONE);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
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
		String token =PreferencesUtil.getSharedStringData(MyCenterFragment.this.getActivity(),"token");
		params.put("user", user);
		params.put("token",token);
		params.put("type",type);
		AsyncHttpClient client = new AsyncHttpClient();
		client.setConnectTimeout(5000);
		String urln="http://192.168.10.168:8080/email-homework/api/user/setIdentity";
		//url3   原来的
		client.post(urln, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int i, org.apache.http.Header[] headers, byte[] responseBody) {
				String str = new String(responseBody);
				if (str != null) {
					try {
						JSONObject object = new JSONObject(str);
						if (object.getString("code").equals("success")) {
							Toast.makeText(MyCenterFragment.this.getActivity(), "设置成功", Toast.LENGTH_SHORT).show();
							isType();
						} else if(object.getString("code").equals("false")){
							Toast.makeText(MyCenterFragment.this.getActivity(), "设置失败", Toast.LENGTH_SHORT).show();
							isType();
						}
						else {
							Toast.makeText(MyCenterFragment.this.getActivity(), "未知错误", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
			@Override
			public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
				Toast.makeText(MyCenterFragment.this.getActivity(), "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
			}
		});
	}
}

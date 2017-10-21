package com.hzu.feirty.HomeWork.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import com.hzu.feirty.HomeWork.R;
import com.hzu.feirty.HomeWork.activity.main.ItemContentActivity;
import com.hzu.feirty.HomeWork.adapter.MyAdapter;
import com.hzu.feirty.HomeWork.db.Email;
import com.hzu.feirty.HomeWork.entity.Ip;
import com.hzu.feirty.HomeWork.entity.OnRecyclerviewItemClickListener;
import com.hzu.feirty.HomeWork.utils.MyItemDecoration;
import com.hzu.feirty.HomeWork.utils.PreferencesUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class MainFragment extends Fragment{
	private ArrayList<Email> mData = new ArrayList<Email>();
	private RecyclerView recyclerView;
	private SwipeRefreshLayout swipeRefreshLayout;
	private MyAdapter recyclerAdapter;
	private String url = Ip.ip + "/HomeWork/DoGetMail?";
	private String url3 = Ip.ip + "/HomeWork/DoGetType?";
	private Toolbar toolbar;
	private static final String USERNAME = "username";
	Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 1:
					/**
					 *  初始化recyclerView
					 */
					recyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerview);
					swipeRefreshLayout.setRefreshing(false);
					recyclerView.setLayoutManager(new LinearLayoutManager(MainFragment.this.getActivity()));
					recyclerView.addItemDecoration(new MyItemDecoration());
					recyclerAdapter =  new MyAdapter(getActivity(),mData,onRecyclerviewItemClickListener);
					//recyclerAdapter = new MyAdapter(mData);
					recyclerView.setAdapter(recyclerAdapter);
					recyclerAdapter.notifyDataSetChanged();
					break;
				case 0:
					swipeRefreshLayout.setRefreshing(false);
			}
		}
	};
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
		toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
		AppCompatActivity activity = (AppCompatActivity) getActivity();
		if (activity.getSupportActionBar() != null){
			activity.getSupportActionBar().hide();
		}
		toolbar.setTitle("首页");
		toolbar.inflateMenu(R.menu.base_toolbar_menu);//设置右上角的填充菜单
		toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
					case R.id.action_search:
						Toast.makeText(MainFragment.this.getActivity(), "Search !", Toast.LENGTH_LONG).show();
						break;
					case R.id.action_notifications:
						Toast.makeText(MainFragment.this.getActivity(), "Notification !", Toast.LENGTH_LONG).show();
						break;
				}
				return true;
			}
		});
		/**
		 *  初始化swipeRefreshLayout
		 */
		swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swiperefreshlayout);
		swipeRefreshLayout.setColorSchemeResources(R.color.blue);
		swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				mData.clear();
				isType();
			}
		});
		swipeRefreshLayout.post(new Runnable() {
			@Override
			public void run() {
				swipeRefreshLayout.setRefreshing(true);
				isType();  //请求网络的线程
			}
		});
	}
	private OnRecyclerviewItemClickListener onRecyclerviewItemClickListener = new OnRecyclerviewItemClickListener() {
		@Override
		public void onItemClickListener(View v, int position) {
			final Intent intent = new Intent(getActivity(), ItemContentActivity.class).putExtra("EMAIL", mData.get(position));
			startActivity(intent);
		}
	};
	public void receiveEmail() {
		RequestParams params = new RequestParams();
		String user =PreferencesUtil.getSharedStringData(MainFragment.this.getActivity(),USERNAME);
		params.put("user", user);
		params.put("action", "receive");
		AsyncHttpClient client = new AsyncHttpClient();
		client.setConnectTimeout(50000);
		client.setTimeout(50000);
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
								mail.setMessageID(item.getString("id"));
								mail.setFrom(item.getString("from"));
								mail.setAttachment(item.getString("attachment"));
								mail.setSubject(item.getString("subject"));
								mail.setCourse(item.getString("course"));
								mail.setContent(item.getString("content"));
								mail.setSentdata(item.getString("time"));
								mail.setCc(item.getString("stu_number"));
								mData.add(0, mail);
							}
							Toast.makeText(MainFragment.this.getActivity(),"作业接收成功",Toast.LENGTH_SHORT).show();
							myHandler.obtainMessage(1).sendToTarget();
						} else {
							Toast.makeText(MainFragment.this.getActivity(),"接收失败",Toast.LENGTH_SHORT).show();
							myHandler.obtainMessage(0).sendToTarget();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
			@Override
			public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
				Toast.makeText(MainFragment.this.getActivity(), "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
				myHandler.obtainMessage(0).sendToTarget();
			}
		});
	}
	private void isType(){
		RequestParams params = new RequestParams();
		params.put("action", "istype");
		String user =PreferencesUtil.getSharedStringData(MainFragment.this.getActivity(),"username");
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
						if (object.getString("code").equals("teacher")) {
							receiveEmail();
						} else if(object.getString("code").equals("student")){
							receiveEmail();
						}
						else {
							myHandler.obtainMessage(0).sendToTarget();
							Toast.makeText(MainFragment.this.getActivity(), "请先选择身份", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						myHandler.obtainMessage(0).sendToTarget();
						e.printStackTrace();
					}
				} else {
				}
			}
			@Override
			public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
				myHandler.obtainMessage(0).sendToTarget();
				Toast.makeText(MainFragment.this.getActivity(), "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
			}
		});
	}
}

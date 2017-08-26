package com.hzu.feirty.MailIM.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hzu.feirty.MailIM.R;
import com.hzu.feirty.MailIM.entity.Ip;
import com.hzu.feirty.MailIM.utils.PreferencesUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

public class SearchFragment extends Fragment {
	private LinearLayout send;
	private Toolbar toolbar;
	private static final String IDENTITY = "saveidentity";
	private String url3 = Ip.ip + "/MailIM/DoGetType?";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view= inflater.inflate(R.layout.fragment_search, null);
		return view;
	}
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		send = (LinearLayout) getActivity().findViewById(R.id.send);
		toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
		AppCompatActivity activity = (AppCompatActivity) getActivity();
		//activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		if (activity.getSupportActionBar() != null){
			activity.getSupportActionBar().hide();
		}
		send.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				isType();
			}
		});
	}

	private void isType(){
		RequestParams params = new RequestParams();
		params.put("action", "istype");
		String user =PreferencesUtil.getSharedStringData(SearchFragment.this.getActivity(),"username");
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
							Intent intent = new Intent(getActivity(), EditMailActivity.class);
							startActivity(intent);

						} else if(object.getString("code").equals("student")){
							Toast.makeText(SearchFragment.this.getActivity(),"不允许学生操作",Toast.LENGTH_LONG).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
				}
			}

			@Override
			public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
				Toast.makeText(SearchFragment.this.getActivity(), "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
			}
		});
	}
}

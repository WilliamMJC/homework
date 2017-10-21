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
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.hzu.feirty.HomeWork.R;
import com.hzu.feirty.HomeWork.activity.mission.WorkContentActivity;
import com.hzu.feirty.HomeWork.adapter.MsAdapter;
import com.hzu.feirty.HomeWork.db.Email;
import com.hzu.feirty.HomeWork.entity.Ip;
import com.hzu.feirty.HomeWork.entity.OnRecyclerviewItemClickListener;
import com.hzu.feirty.HomeWork.utils.PreferencesUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MissionFragment extends Fragment {
    private ArrayList<Email> mData = new ArrayList<Email>();
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MsAdapter recyclerAdapter;
    private String stu_number;
    private String url = Ip.ip + "/HomeWork/DoGetMail?";
    private String url3 = Ip.ip + "/HomeWork/DoGetType?";
    private Context context;
    private String type="INBOX";
    private Toolbar toolbar;
    private static final String USERNAME = "username";
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    /**
                     *  初始化recyclerView
                     */
                    recyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerview_ms);
                    swipeRefreshLayout.setRefreshing(false);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MissionFragment.this.getActivity()));
                    recyclerAdapter =  new MsAdapter(getActivity(),mData,onRecyclerviewItemClickListener);
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
        View view= inflater.inflate(R.layout.fragment_mission, null);
        return view;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = MissionFragment.this.getActivity();
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_ms);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity.getSupportActionBar() != null){
            activity.getSupportActionBar().hide();
        }
        toolbar.setTitle("任务");
        toolbar.inflateMenu(R.menu.base_toolbar_menu);//设置右上角的填充菜单
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search:
                        Toast.makeText(MissionFragment.this.getActivity(), "Search !", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.action_notifications:
                        Toast.makeText(MissionFragment.this.getActivity(), "Notification !", Toast.LENGTH_LONG).show();
                        break;
                }
                return true;
            }
        });
        /**
         *  初始化swipeRefreshLayout
         */
        swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swiperefreshlayout_ms);
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
                //receiveEmail();
            }
        });
    }

    private void isType(){
        RequestParams params = new RequestParams();
        params.put("action", "istype");
        String user = PreferencesUtil.getSharedStringData(MissionFragment.this.getActivity(),"username");
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
                            receive();
                        } else if(object.getString("code").equals("student")){
                            receive();
                        }
                        else {
                            myHandler.obtainMessage(0).sendToTarget();
                            Toast.makeText(MissionFragment.this.getActivity(), "请先选择身份", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        myHandler.obtainMessage(0).sendToTarget();
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                myHandler.obtainMessage(0).sendToTarget();
                Toast.makeText(MissionFragment.this.getActivity(), "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void receive() {
        RequestParams params = new RequestParams();
        String user =PreferencesUtil.getSharedStringData(MissionFragment.this.getActivity(),USERNAME);
        params.put("user", user);
        params.put("action", "receivework");
        AsyncHttpClient client = new AsyncHttpClient();
        client.setConnectTimeout(5000000);
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
                                mail.setCourse(item.getString("course"));
                                mail.setSubject(item.getString("subject"));
                                mail.setContent(item.getString("content"));
                                mail.setSentdata(item.getString("time"));
                                mail.setCc(item.getString("stu_number"));
                                mData.add(0, mail);
                            }
                            Toast.makeText(MissionFragment.this.getActivity(),"任务接收成功",Toast.LENGTH_SHORT).show();
                            myHandler.obtainMessage(1).sendToTarget();
                        } else {
                            Toast.makeText(MissionFragment.this.getActivity(),"接收失败",Toast.LENGTH_SHORT).show();
                            myHandler.obtainMessage(0).sendToTarget();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(MissionFragment.this.getActivity(), "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
                myHandler.obtainMessage(0).sendToTarget();
            }
        });
    }
    private OnRecyclerviewItemClickListener onRecyclerviewItemClickListener = new OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
            //这里的view就是我们点击的view  position就是点击的position
            final Intent intent = new Intent(getActivity(), WorkContentActivity.class).putExtra("EMAIL", mData.get(position));
            startActivity(intent);
        }
    };

}


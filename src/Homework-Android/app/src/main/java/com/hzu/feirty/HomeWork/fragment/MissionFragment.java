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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.hzu.feirty.HomeWork.R;
import com.hzu.feirty.HomeWork.activity.MainActivityDemo;
import com.hzu.feirty.HomeWork.activity.index.LoginActivity;
import com.hzu.feirty.HomeWork.activity.index.MyApplication;
import com.hzu.feirty.HomeWork.activity.mission.WorkContentActivity;
import com.hzu.feirty.HomeWork.activity.mycenter.UserInfoActivity;
import com.hzu.feirty.HomeWork.adapter.MsAdapter;
import com.hzu.feirty.HomeWork.adapter.MsAdapter2;
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
    private MsAdapter2 recyclerAdapter2;
    private String stu_number;
    private String url = Ip.ip + "/HomeWork/DoGetHomeWork?";
    private String url3 = Ip.ip + "/HomeWork/DoGetType?";
    private Context context;
    private LinearLayout ll_noid;
    private String type="INBOX";
    private long mExitTime;
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
                case 2:
                    /**
                     *  初始化recyclerView
                     */
                    recyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerview_ms);
                    swipeRefreshLayout.setRefreshing(false);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MissionFragment.this.getActivity()));
                    recyclerAdapter2 =  new MsAdapter2(getActivity(),mData,onRecyclerviewItemClickListener);
                    //recyclerAdapter = new MyAdapter(mData);
                    recyclerView.setAdapter(recyclerAdapter2);
                    recyclerAdapter2.notifyDataSetChanged();
                    break;
                case 0:
                    ll_noid.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
            }
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        boolean state = PreferencesUtil.getSharedBooleanData(MissionFragment.this.getActivity(),"loginstate");
        if(!state){
            startActivity(new Intent(MissionFragment.this.getActivity(), LoginActivity.class));
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_mission, null);
        return view;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MyApplication.getInstance().addActivity(getActivity());
        boolean state = PreferencesUtil.getSharedBooleanData(MissionFragment.this.getActivity(),"loginstate");
        if(!state){
            startActivity(new Intent(MissionFragment.this.getActivity(), LoginActivity.class));
        }
        context = MissionFragment.this.getActivity();
        ll_noid = (LinearLayout) getActivity().findViewById(R.id.ll_noid);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_ms);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity.getSupportActionBar() != null){
            activity.getSupportActionBar().hide();
        }
        toolbar.setTitle("作业");
        //toolbar.inflateMenu(R.menu.base_toolbar_menu);//设置右上角的填充菜单
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
                mData.clear();
                swipeRefreshLayout.setRefreshing(true);
                isTypeFirst();  //请求网络的线程
                //receiveEmail();
            }
        });
    }
    private void isType(){
        RequestParams params = new RequestParams();
        params.put("action", "istype");
        String user = PreferencesUtil.getSharedStringData(MissionFragment.this.getActivity(), "username");
        String token =PreferencesUtil.getSharedStringData(MissionFragment.this.getActivity(),"token");
        params.put("user", user);
        params.put("token",token);
        AsyncHttpClient client = new AsyncHttpClient();
        client.setConnectTimeout(5000);
        String urlnn="http://192.168.10.168:8080/email-homework/api/user/getIdentity";
        client.post(urlnn, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, org.apache.http.Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if (str != null) {
                    try {
                        JSONObject object = new JSONObject(str);
                        if (object.getString("code").equals("2")) {
                            receive();
                        } else if(object.getString("code").equals("1")){
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
    private void isTypeFirst(){
        RequestParams params = new RequestParams();
        params.put("action", "istype");
        String user = PreferencesUtil.getSharedStringData(MissionFragment.this.getActivity(), "username");
        String token =PreferencesUtil.getSharedStringData(MissionFragment.this.getActivity(),"token");
        params.put("user", user);
        params.put("token",token);
        AsyncHttpClient client = new AsyncHttpClient();
        client.setConnectTimeout(5000);
        String urlnn="http://192.168.10.168:8080/email-homework/api/user/getIdentity";
        client.post(urlnn, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, org.apache.http.Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if (str != null) {
                    try {
                        JSONObject object = new JSONObject(str);
                        if (object.getString("code").equals("2")) {
                            fristReceive();
                        } else if(object.getString("code").equals("1")){
                            fristReceive();
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
        String token =PreferencesUtil.getSharedStringData(MissionFragment.this.getActivity(),"token");
        params.put("token",token);
        AsyncHttpClient client = new AsyncHttpClient();
        client.setConnectTimeout(5000000);
        client.setTimeout(5000000);
        String url2 = "http://192.168.10.168:8080/email-homework/api/homework/updateHomework";
        client.post(url2, params, new AsyncHttpResponseHandler() {
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
                                mail.setSubject(item.getString("title"));
                                mail.setContent(item.getString("content"));
                                mail.setSentdata(item.getString("issue_time"));
                                mail.setCc(item.getString("students"));
                                mail.setUpdate_time(item.getString("update_time"));
                                mail.setWork_state(item.getString("receive_state"));
                                mail.setWork_number(item.getString("submitted"));
                                mail.setHomeworkUuid(item.getString("homeworkUuid"));
                                mail.setUserUuid(item.getString("teacherUuid"));
                                mail.setUserName(item.getString("teacherName"));
                                mData.add(0, mail);
                            }
                            //Toast.makeText(MissionFragment.this.getActivity(),"接收成功",Toast.LENGTH_SHORT).show();
                            myHandler.obtainMessage(1).sendToTarget();
                        } else {
                            Toast.makeText(MissionFragment.this.getActivity(),"接收失败",Toast.LENGTH_SHORT).show();
                            //myHandler.obtainMessage(0).sendToTarget();
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
    public void fristReceive(){
        RequestParams params = new RequestParams();
        String user =PreferencesUtil.getSharedStringData(MissionFragment.this.getActivity(),USERNAME);
        params.put("user", user);
        String token =PreferencesUtil.getSharedStringData(MissionFragment.this.getActivity(),"token");
        params.put("token", token);
        params.put("action", "first_receive");
        AsyncHttpClient client = new AsyncHttpClient();
        client.setConnectTimeout(5000000);
        client.setTimeout(5000000);
        String url2 = "http://192.168.10.168:8080/email-homework/api/homework/getHomework";
        client.post(url2, params, new AsyncHttpResponseHandler() {
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
                                mail.setSubject(item.getString("title"));
                                mail.setContent(item.getString("content"));
                                mail.setSentdata(item.getString("issue_time"));
                                mail.setCc(item.getString("students"));
                                mail.setUpdate_time(item.getString("update_time"));
                                mail.setWork_state(item.getString("receive_state"));
                                mail.setWork_number(item.getString("submitted"));
                                mail.setHomeworkUuid(item.getString("homeworkUuid"));
                                mail.setUserUuid(item.getString("teacherUuid"));
                                mail.setUserName(item.getString("teacherName"));
                                mData.add(0, mail);
                            }
                           // Toast.makeText(MissionFragment.this.getActivity(),"接收成功",Toast.LENGTH_SHORT).show();
                            myHandler.obtainMessage(2).sendToTarget();
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

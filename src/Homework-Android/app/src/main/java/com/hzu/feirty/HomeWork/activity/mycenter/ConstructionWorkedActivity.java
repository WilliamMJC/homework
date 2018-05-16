package com.hzu.feirty.HomeWork.activity.mycenter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.hzu.feirty.HomeWork.R;
import com.hzu.feirty.HomeWork.adapter.HomeWorkAdapter;
import com.hzu.feirty.HomeWork.adapter.StudentAdapter;
import com.hzu.feirty.HomeWork.db.HomeWork;
import com.hzu.feirty.HomeWork.db.Student;
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

/**
 * Created by Administrator on 2017-10-23.
 */

public class ConstructionWorkedActivity extends AppCompatActivity {
    private ArrayList<HomeWork> mData = new ArrayList<HomeWork>();
    private SwipeRefreshLayout swiperefreshlayout;
    private RecyclerView recyclerview;
    private HomeWorkAdapter recyclerAdapter;
    private String course;
    private String url= Ip.ip + "/HomeWork/DoGetStudent?";
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    /**
                     *  初始化recyclerView
                     */
                    recyclerview = (RecyclerView)findViewById(R.id.recyclerview_show);
                    swiperefreshlayout.setRefreshing(false);
                    recyclerview.setLayoutManager(new LinearLayoutManager(ConstructionWorkedActivity.this));
                    recyclerview.addItemDecoration(new MyItemDecoration());
                    recyclerAdapter =  new HomeWorkAdapter(ConstructionWorkedActivity.this,mData,onRecyclerviewItemClickListener);
                    recyclerview.setAdapter(recyclerAdapter);
                    recyclerAdapter.notifyDataSetChanged();
                    break;
                case 0:
                    swiperefreshlayout.setRefreshing(false);
            }
        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_construction_show);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("统计列表");
        //设置导航图标、添加菜单点击事件要在setSupportActionBar方法之后
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        course = (String) getIntent().getSerializableExtra("course");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initView();
    }

    public void initView(){
        /**
         *  初始化swipeRefreshLayout
         */
        swiperefreshlayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout_show);
        swiperefreshlayout.setColorSchemeResources(R.color.blue);
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                findHomeWork();//请求网络的线程
            }
        });
        swiperefreshlayout.post(new Runnable() {
            @Override
            public void run() {
                swiperefreshlayout.setRefreshing(true);
                findHomeWork();//请求网络的线程
            }
        });
    }
    private OnRecyclerviewItemClickListener onRecyclerviewItemClickListener = new OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
            //这里的view就是我们点击的view  position就是点击的position
        }
    };
    public void findHomeWork() {
        RequestParams params = new RequestParams();
        String user = PreferencesUtil.getSharedStringData(ConstructionWorkedActivity.this,"username");
        params.put("user", user);
        params.put("action", "findhomework");
        params.put("course", course);
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
                            JSONArray arraya = object.getJSONArray("homework");
                            for (int a = 0; a < arraya.length(); a++) {
                                JSONObject item = arraya.getJSONObject(a);
                                HomeWork homework = new HomeWork();
                                homework.setStu_id(item.getString("stu_id"));
                                homework.setFile_name(item.getString("file_name"));
                                homework.setFile_size(item.getString("file_size"));
                                mData.add(0, homework);
                            }
                            myHandler.obtainMessage(1).sendToTarget();
                            Toast.makeText(ConstructionWorkedActivity.this,"统计信息接收成功",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ConstructionWorkedActivity.this,"统计信息接收失败",Toast.LENGTH_SHORT).show();
                            myHandler.obtainMessage(0).sendToTarget();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(ConstructionWorkedActivity.this, "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
                myHandler.obtainMessage(0).sendToTarget();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

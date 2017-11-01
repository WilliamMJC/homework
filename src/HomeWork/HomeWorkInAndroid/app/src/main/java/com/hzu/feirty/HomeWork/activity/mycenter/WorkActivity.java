package com.hzu.feirty.HomeWork.activity.mycenter;

import android.content.Intent;
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
import com.hzu.feirty.HomeWork.activity.index.MailApplication;
import com.hzu.feirty.HomeWork.activity.main.ItemContentActivity;
import com.hzu.feirty.HomeWork.adapter.MyAdapter;
import com.hzu.feirty.HomeWork.db.Course;
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

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017-10-12.
 */

public class WorkActivity extends AppCompatActivity {
    private ArrayList<Email> mData = new ArrayList<Email>();
    private ArrayList<Course> cData = new ArrayList<Course>();
    private ArrayList<InputStream> attachmentsInputStreams;
    private SwipeRefreshLayout swiperefreshlayout;
    private RecyclerView recyclerview;
    private MyAdapter recyclerAdapter;
    private String course;
    private Handler handler;
    private String stu_number;
    private String work_number;
    private String url= Ip.ip + "/HomeWork/DoGetCourse?";
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    /**
                     *  初始化recyclerView
                     */
                    recyclerview = (RecyclerView)findViewById(R.id.recyclerview);
                    swiperefreshlayout.setRefreshing(false);
                    recyclerview.setLayoutManager(new LinearLayoutManager(WorkActivity.this));
                    recyclerview.addItemDecoration(new MyItemDecoration());
                    recyclerAdapter =  new MyAdapter(WorkActivity.this,mData,onRecyclerviewItemClickListener);
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
        setContentView(R.layout.activiry_works);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("待收取作业");
        //设置导航图标、添加菜单点击事件要在setSupportActionBar方法之后
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        course = (String) getIntent().getSerializableExtra("course");
        attachmentsInputStreams = ((MailApplication) getApplication()).getAttachmentsInputStreams();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initView();
    }

    public void initView(){
        handler = new MyHandler(this);
        /**
         *  初始化swipeRefreshLayout
         */
        swiperefreshlayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
        swiperefreshlayout.setColorSchemeResources(R.color.blue);
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                findWorks();//请求网络的线程
            }
        });
        swiperefreshlayout.post(new Runnable() {
            @Override
            public void run() {
                swiperefreshlayout.setRefreshing(true);
                findWorks();//请求网络的线程
            }
        });
    }

    private OnRecyclerviewItemClickListener onRecyclerviewItemClickListener = new OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
            //这里的view就是我们点击的view  position就是点击的position
            final Intent intent = new Intent(WorkActivity.this, ItemContentActivity.class).putExtra("EMAIL", mData.get(position));
            startActivity(intent);
        }
    };
    public void findWorks() {
        RequestParams params = new RequestParams();
        String user = PreferencesUtil.getSharedStringData(WorkActivity.this,"username");
        params.put("user", user);
        params.put("action", "findwork");
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
                        stu_number = object.getString("number");
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
                                mail.setCc(stu_number);
                                mData.add(0, mail);
                            }
                            Toast.makeText(WorkActivity.this,"课程接收成功",Toast.LENGTH_SHORT).show();
                            myHandler.obtainMessage(1).sendToTarget();
                        } else {
                            Toast.makeText(WorkActivity.this,"课程接收失败",Toast.LENGTH_SHORT).show();
                            myHandler.obtainMessage(0).sendToTarget();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(WorkActivity.this, "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
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

    private static class MyHandler extends Handler {

        private WeakReference<WorkActivity > wrActivity;

        public MyHandler(WorkActivity activity) {
            this.wrActivity = new WeakReference<WorkActivity>(activity);
        }
        @Override
        public void handleMessage(android.os.Message msg) {
            final WorkActivity  activity = wrActivity.get();
            switch (msg.what) {
                case 0:
                    Toast.makeText(activity.getApplicationContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }
}

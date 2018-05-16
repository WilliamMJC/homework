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
import com.hzu.feirty.HomeWork.adapter.StudentAdapter;
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
 * Created by Administrator on 2017-10-15.
 */

public class StudentTableActivity extends AppCompatActivity {
    private ArrayList<Student> mData = new ArrayList<Student>();
    private SwipeRefreshLayout swiperefreshlayout;
    private RecyclerView recyclerview;
    private StudentAdapter recyclerAdapter;
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
                    recyclerview.setLayoutManager(new LinearLayoutManager(StudentTableActivity.this));
                    recyclerview.addItemDecoration(new MyItemDecoration());
                    recyclerAdapter =  new StudentAdapter(StudentTableActivity.this,mData,onRecyclerviewItemClickListener);
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
        setContentView(R.layout.activity_student_show);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("学生列表");
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
                findStudent();//请求网络的线程
            }
        });
        swiperefreshlayout.post(new Runnable() {
            @Override
            public void run() {
                swiperefreshlayout.setRefreshing(true);
                findStudent();//请求网络的线程
            }
        });
    }
    private OnRecyclerviewItemClickListener onRecyclerviewItemClickListener = new OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
            //这里的view就是我们点击的view  position就是点击的position
        }
    };
    public void findStudent() {
        RequestParams params = new RequestParams();
        String user = PreferencesUtil.getSharedStringData(StudentTableActivity.this,"username");
        params.put("user", user);
        params.put("action", "findstudent");
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
                            JSONArray arraya = object.getJSONArray("student");
                            for (int a = 0; a < arraya.length(); a++) {
                                JSONObject item = arraya.getJSONObject(a);
                                Student student = new Student();
                                //student.setName(item.getString("name"));
                                student.setNumber(item.getString("number"));
                                mData.add(0, student);
                            }
                            myHandler.obtainMessage(1).sendToTarget();
                            Toast.makeText(StudentTableActivity.this,"学生信息接收成功",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(StudentTableActivity.this,"学生信息接收失败",Toast.LENGTH_SHORT).show();
                            myHandler.obtainMessage(0).sendToTarget();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(StudentTableActivity.this, "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
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

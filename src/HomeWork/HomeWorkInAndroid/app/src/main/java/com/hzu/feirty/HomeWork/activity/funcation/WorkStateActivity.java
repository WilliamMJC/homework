package com.hzu.feirty.HomeWork.activity.funcation;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hzu.feirty.HomeWork.R;
import com.hzu.feirty.HomeWork.db.Course;
import com.hzu.feirty.HomeWork.entity.Ip;
import com.hzu.feirty.HomeWork.utils.PreferencesUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-10-20.
 */

public class WorkStateActivity extends AppCompatActivity {
    private ProgressDialog pd;
    private SwipeRefreshLayout swiperefreshlayout;
    private ListView lv_selete_course;
    private String course;
    private List<String> courses = new ArrayList<>();
    private String url= Ip.ip + "/HomeWork/DoGetStudent?";
    private String url2 = Ip.ip + "/HomeWork/DoGetMail?";
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    swiperefreshlayout.setRefreshing(false);
                    initView2();
                    break;
                case 0:
                    swiperefreshlayout.setRefreshing(false);
                    break;
                case 2:
                    pd.dismiss();
            }
        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workstate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("选择检查的课程");
        //设置导航图标、添加菜单点击事件要在setSupportActionBar方法之后
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
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
        swiperefreshlayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout_selete_course);
        swiperefreshlayout.setColorSchemeResources(R.color.blue);
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                findCourse();//请求网络的线程
            }
        });
        swiperefreshlayout.post(new Runnable() {
            @Override
            public void run() {
                swiperefreshlayout.setRefreshing(true);
                findCourse();//请求网络的线程
            }
        });
    }

    public void initView2(){
        //添加底部按钮
        View bottomView=getLayoutInflater().inflate(R.layout.activity_bottom2, null);
        Button loadMore=(Button)bottomView.findViewById(R.id.load);
        loadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new AlertDialog.Builder(WorkStateActivity.this)
                        .setTitle("提示")
                        //.setIcon(R.drawable.ic_launcher)
                        .setMessage("确认检查该课程作业状态吗？")
                        //相当于点击确认按钮
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                pd = ProgressDialog.show(WorkStateActivity.this, null, "正在检查,你的作业收取情况...");
                                checkHomework(course);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create();
                dialog.show();

            }
        });
        lv_selete_course = (ListView) findViewById(R.id.lv_selete_course);
        lv_selete_course.setAdapter(new ArrayAdapter(WorkStateActivity.this,android.R.layout.simple_list_item_single_choice,courses));
        lv_selete_course.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int headerViewsCount = lv_selete_course.getHeaderViewsCount();
                if (position >= headerViewsCount) {
                    Log.d("Test", courses.get(position - headerViewsCount));
                    course =courses.get(position - headerViewsCount);
                }
                Toast.makeText(getApplicationContext(), "onItemClick position = " + (position + 1) + " , Current Checked item position = " + lv_selete_course.getCheckedItemPosition(), Toast.LENGTH_SHORT).show();
            }
        });
        lv_selete_course.addFooterView(bottomView);
    }

    public void findCourse() {
        RequestParams params = new RequestParams();
        String user = PreferencesUtil.getSharedStringData(WorkStateActivity.this,"username");
        params.put("user", user);
        params.put("action", "findcourse");
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
                            JSONArray arrayc = object.getJSONArray("course");
                            for(int b=0;b< arrayc.length();b++){
                                JSONObject itemb = arrayc.getJSONObject(b);
                                courses.add(itemb.getString("course"));
                            }
                            Toast.makeText(WorkStateActivity.this,"课程接收成功",Toast.LENGTH_SHORT).show();
                            myHandler.obtainMessage(1).sendToTarget();
                        } else {
                            Toast.makeText(WorkStateActivity.this,"课程接收失败",Toast.LENGTH_SHORT).show();
                            myHandler.obtainMessage(0).sendToTarget();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(WorkStateActivity.this, "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
                myHandler.obtainMessage(0).sendToTarget();
            }
        });
    }

    private void checkHomework(String course){
        RequestParams params = new RequestParams();
        String user =PreferencesUtil.getSharedStringData(WorkStateActivity.this,"username");
        params.put("user", user);
        params.put("course",course);
        params.put("action", "checkhomework");
        AsyncHttpClient client = new AsyncHttpClient();
        client.setConnectTimeout(500000);
        client.setTimeout(500000);
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, org.apache.http.Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if (str != null) {
                    try {
                        JSONObject object = new JSONObject(str);
                            if (object.getString("code").equals("success")) {
                                myHandler.obtainMessage(2).sendToTarget();
                                Toast.makeText(WorkStateActivity.this, "你的作业已收取", Toast.LENGTH_SHORT).show();
                            } else if(object.getString("code").equals("false")){
                                myHandler.obtainMessage(2).sendToTarget();
                                Toast.makeText(WorkStateActivity.this, "你的作业未收取或者你未上传作业", Toast.LENGTH_SHORT).show();
                            }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                myHandler.obtainMessage(2).sendToTarget();
                Toast.makeText(WorkStateActivity.this, "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
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

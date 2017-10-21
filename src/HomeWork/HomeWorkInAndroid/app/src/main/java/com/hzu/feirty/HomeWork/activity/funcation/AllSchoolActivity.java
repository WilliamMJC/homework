package com.hzu.feirty.HomeWork.activity.funcation;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.hzu.feirty.HomeWork.R;
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
 * Created by Administrator on 2017-10-7.
 */

public class AllSchoolActivity extends AppCompatActivity {
    private ListView listview;
    private SwipeRefreshLayout swiperefreshlayout;
    private List<String> school = new ArrayList<String>();
    private String url= Ip.ip + "/HomeWork/DoGetSchool?";
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    /**
                     *  初始化ListView
                     */
                    swiperefreshlayout.setRefreshing(false);
                    listview = (ListView) findViewById(R.id.list_view_school);
                    listview.setAdapter(new ArrayAdapter<String>(AllSchoolActivity.this, android.R.layout.simple_expandable_list_item_1,school));
                    break;
                case 0:
                    swiperefreshlayout.setRefreshing(false);
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allschool);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("所有学校");
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
        swiperefreshlayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout_allschool);
        swiperefreshlayout.setColorSchemeResources(R.color.blue);
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                findAllSchool();//请求网络的线程
            }
        });
        swiperefreshlayout.post(new Runnable() {
            @Override
            public void run() {
                swiperefreshlayout.setRefreshing(true);
                findAllSchool();//请求网络的线程
            }
        });
    }

    public void findAllSchool() {
        RequestParams params = new RequestParams();
        String user = PreferencesUtil.getSharedStringData(AllSchoolActivity.this,"username");
        params.put("user", user);
        params.put("action", "querySchool");
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
                            JSONArray array = object.getJSONArray("schools");
                            for(int b=0;b< array.length();b++){
                                JSONObject itemb = array.getJSONObject(b);
                                school.add(itemb.getString("school"));
                            }
                            Toast.makeText(AllSchoolActivity.this,"学校接收成功",Toast.LENGTH_SHORT).show();
                            myHandler.obtainMessage(1).sendToTarget();
                        } else {
                            Toast.makeText(AllSchoolActivity.this,"学校接收失败",Toast.LENGTH_SHORT).show();
                            myHandler.obtainMessage(0).sendToTarget();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(AllSchoolActivity.this, "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
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

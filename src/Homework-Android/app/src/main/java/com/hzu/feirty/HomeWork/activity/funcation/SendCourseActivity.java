package com.hzu.feirty.HomeWork.activity.funcation;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hzu.feirty.HomeWork.R;
import com.hzu.feirty.HomeWork.activity.mycenter.UserInfoActivity;
import com.hzu.feirty.HomeWork.entity.Ip;
import com.hzu.feirty.HomeWork.utils.PreferencesUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017-10-10.
 */

public class SendCourseActivity extends AppCompatActivity {
    private Button btn_insert;
    private Button btn_update;
    private ProgressDialog pd;
    private String url = Ip.ip + "/HomeWork/DoGetHomeWork?";
    private static final String USERNAME = "username";
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    myHandler.obtainMessage(0).sendToTarget();
                    break;
                case 0:
            }
        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("发布课程");
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
        initListener();
    }
    public void initView(){
        btn_insert = (Button) findViewById(R.id.btn_insert);
        btn_update = (Button) findViewById(R.id.btn_update);
    }
    public void initListener(){
        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCourse();
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCourse();
            }
        });
    }

    public void doCourse(){
        RequestParams params = new RequestParams();
        String user =PreferencesUtil.getSharedStringData(SendCourseActivity.this, USERNAME);
        params.put("user",user);
        params.put("action", "COURSE");
        String token =PreferencesUtil.getSharedStringData(SendCourseActivity.this,"token");
        params.put("token", token);
        AsyncHttpClient client = new AsyncHttpClient();
        client.setConnectTimeout(5000);
        String url2 ="http://192.168.10.168:8080/email-homework/api/course/addCourse";
        pd= ProgressDialog.show(SendCourseActivity.this,null, "正在添加，请稍候...");
        client.post(url2, params, new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int i, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if (str != null) {
                    try {
                        JSONObject object = new JSONObject(str);
                        if (object.getString("code").equals("success")) {
                            pd.dismiss();
                            Toast.makeText(SendCourseActivity.this, "课程添加成功", Toast.LENGTH_LONG).show();
                        } else {
                            pd.dismiss();
                            Toast.makeText(SendCourseActivity.this, "课程添加失败", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        pd.dismiss();
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                pd.dismiss();
                Toast.makeText(SendCourseActivity.this, "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void updateCourse(){
        RequestParams params = new RequestParams();
        String user =PreferencesUtil.getSharedStringData(SendCourseActivity.this, USERNAME);
        params.put("user",user);
        params.put("action", "UPDATECOURSE");
        AsyncHttpClient client = new AsyncHttpClient();
        client.setConnectTimeout(5000);
        pd= ProgressDialog.show(SendCourseActivity.this,null, "正在操作，请稍候...");
        client.post(url, params, new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int i, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if (str != null) {
                    try {
                        JSONObject object = new JSONObject(str);
                        if (object.getString("code").equals("success")) {
                            pd.dismiss();
                            Toast.makeText(SendCourseActivity.this, "课程更新成功", Toast.LENGTH_LONG).show();
                        } else {
                            pd.dismiss();
                            Toast.makeText(SendCourseActivity.this, "课程更新失败", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        pd.dismiss();
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                pd.dismiss();
                Toast.makeText(SendCourseActivity.this, "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

package com.hzu.feirty.HomeWork.activity.mycenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hzu.feirty.HomeWork.R;
import com.hzu.feirty.HomeWork.fragment.MyCenterFragment;
import com.hzu.feirty.HomeWork.utils.PreferencesUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/4/23 0023.
 */

public class UserInfoActivity extends AppCompatActivity {
    private LinearLayout ll_userName;
    private LinearLayout ll_schoolName;
    private LinearLayout ll_mailName;
    private LinearLayout ll_mailPwd;
    private LinearLayout ll_studentNo;
    private LinearLayout ll_stu_show;
    private LinearLayout ll_tea_show;
    private TextView userName;
    private TextView schoolName;
    private TextView mailName;
    private TextView studentNo;
    private TextView mailPwd;

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    ll_tea_show.setVisibility(View.VISIBLE);
                    break;
                case 0:
                    ll_stu_show.setVisibility(View.VISIBLE);

            }
        }
    };


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_my);
        toolbar.setTitle("个人信息");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        initView();
        setListener();
        getUserInfo();

    }

    public void initView() {
        ll_userName = (LinearLayout)findViewById(R.id.ll_userName);
        ll_schoolName = (LinearLayout)findViewById(R.id.ll_schoolName);
        ll_mailName = (LinearLayout)findViewById(R.id.ll_emailName);
        ll_mailPwd = (LinearLayout)findViewById(R.id.ll_emailPwd);
        ll_studentNo = (LinearLayout)findViewById(R.id.ll_studentNo);
        ll_stu_show = (LinearLayout)findViewById(R.id.ll_stu_show);
        ll_tea_show = (LinearLayout)findViewById(R.id.ll_tea_show);
        userName = (TextView) findViewById(R.id.loginName);
        schoolName = (TextView) findViewById(R.id.schoolName);
        mailName = (TextView) findViewById(R.id.emailName);
        studentNo = (TextView) findViewById(R.id.studentNo);
        mailPwd = (TextView) findViewById(R.id.emailPwd);
        setListener();
    }

    public void setListener() {
        ll_userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this, UserNameUpdateActivity.class);
                intent.putExtra("DATA",userName.getText().toString());
                startActivity(intent);
            }
        });
        ll_schoolName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this, UserSchoolUpdateActivity.class);
                intent.putExtra("DATA",schoolName.getText().toString());
                startActivity(intent);
            }
        });
        ll_mailName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this, UserEmailUpdateActivity.class);
                intent.putExtra("DATA",mailName.getText().toString());
                startActivity(intent);
            }
        });

        ll_mailPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this, UserEmialPwdUpdateActivity.class);
                intent.putExtra("DATA",mailPwd.getText().toString());
                startActivity(intent);
            }
        });
        ll_studentNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this, UserNoUpdateActivity.class);
                intent.putExtra("DATA",studentNo.getText().toString());
                startActivity(intent);
            }
        });
    }

    private void getUserInfo(){
        RequestParams params = new RequestParams();
        String token =PreferencesUtil.getSharedStringData(UserInfoActivity.this,"token");
        params.put("token", token);
        AsyncHttpClient client = new AsyncHttpClient();
        client.setConnectTimeout(5000);
        String url = "http://192.168.10.168:8080/email-homework/api/user/getUserInfo";
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, org.apache.http.Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if (str != null) {
                    try {
                        JSONObject object = new JSONObject(str);
                        String userType =object.getString("userType");
                        userName.setText(object.getString("userName"));
                        schoolName.setText(object.getString("schoolName"));
                        if(userType.equals("1")){
                            myHandler.obtainMessage(0).sendToTarget();
                            studentNo.setText(object.getString("studentNo"));

                        }else if(userType.equals("2")){
                            myHandler.obtainMessage(1).sendToTarget();
                            mailName.setText(object.getString("emailName"));
                            mailPwd.setText(object.getString("emailPwd"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                }
            }
            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(UserInfoActivity.this, "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        getUserInfo();
        super.onResume();
    }
}

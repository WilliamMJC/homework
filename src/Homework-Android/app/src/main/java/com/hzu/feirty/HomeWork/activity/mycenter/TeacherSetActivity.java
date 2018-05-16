package com.hzu.feirty.HomeWork.activity.mycenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hzu.feirty.HomeWork.R;
import com.hzu.feirty.HomeWork.entity.Ip;
import com.hzu.feirty.HomeWork.utils.ConnUtil;
import com.hzu.feirty.HomeWork.utils.PreferencesUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import javax.mail.Store;

public class TeacherSetActivity extends AppCompatActivity{
    private EditText et_name;
    private EditText et_school;
    private EditText et_workmail;
    private EditText et_mail_pwd;
    private EditText et_peasonmail;
    private Button btn_ok;
    private String url = Ip.ip + "/HomeWork/DoGetTeacher?";
    private String url3 = Ip.ip + "/HomeWork/DoGetType?";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tea_set);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("修改信息");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        et_name = (EditText) findViewById(R.id.et_name);
        et_school = (EditText) findViewById(R.id.et_school);
        et_workmail = (EditText) findViewById(R.id.et_work_email);
        et_mail_pwd = (EditText) findViewById(R.id.et_email_pwd);
        et_peasonmail = (EditText) findViewById(R.id.et_peason_email);
        btn_ok = (Button) findViewById(R.id.btn_tea_ok);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString();
                String school = et_school.getText().toString();
                String workmail = et_workmail.getText().toString();
                String peasonmail = et_peasonmail.getText().toString();
                String pwd = et_mail_pwd.getText().toString();
                Store store = ConnUtil.login("pop.qq.com",workmail,pwd);
                if(store!=null) {
                    saveSet(name, school, workmail, pwd, peasonmail);
                }else{
                    Toast.makeText(TeacherSetActivity.this, "邮箱账号密码验证不符", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void saveSet(String name,String school,String workmail,String pwd,String peasonmail){
        RequestParams params = new RequestParams();
        String user =PreferencesUtil.getSharedStringData(TeacherSetActivity.this,"username");
        params.put("user", user);
        params.put("name",name );
        params.put("school",school);
        params.put("workmail",workmail);
        params.put("pwd",pwd);
        params.put("peasonmail",peasonmail);
        params.put("action", "SAVESET");
        AsyncHttpClient client = new AsyncHttpClient();
        client.setConnectTimeout(5000);
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, org.apache.http.Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if (str != null) {
                    try {
                        JSONObject object = new JSONObject(str);
                        if (object.getString("code").equals("success")) {
                            Toast.makeText(TeacherSetActivity.this, "验证成功，学号信息上传成功，请返回", Toast.LENGTH_SHORT).show();
                            //setType2("teacher");
                            btn_ok.setEnabled(false);
                        } else {
                          //  btn_login.setEnabled(true);
                            Toast.makeText(TeacherSetActivity.this, "未搜索到学号信息！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                }
            }

            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(TeacherSetActivity.this, "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setType2(String type){
        RequestParams params = new RequestParams();
        params.put("action", "settype2");
        String user =PreferencesUtil.getSharedStringData(TeacherSetActivity.this,"username");
        params.put("user", user);
        params.put("type",type);
        AsyncHttpClient client = new AsyncHttpClient();
        client.setConnectTimeout(5000);
        client.post(url3, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, org.apache.http.Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if (str != null) {
                    try {
                        JSONObject object = new JSONObject(str);
                        if (object.getString("code").equals("success")) {
                            Toast.makeText(TeacherSetActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
                        } else if(object.getString("code").equals("false")){
                            Toast.makeText(TeacherSetActivity.this, "设置失败", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(TeacherSetActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                }
            }
            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(TeacherSetActivity.this, "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
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
}

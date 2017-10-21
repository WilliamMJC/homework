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
import com.hzu.feirty.HomeWork.utils.PreferencesUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017-6-27.
 */

public class StudentSetActivity extends AppCompatActivity {
    private EditText et_school;
    private EditText et_teacher;
    private EditText et_number;
    private EditText et_stu_email;
    private EditText et_course;
    private Button btn_ok;
    private static final String MAIL = "mail";
    private static final String PASSWORD = "password";
    private String url = Ip.ip + "/HomeWork/DoGetStudent?";
    private String url3 = Ip.ip + "/HomeWork/DoGetType?";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_set);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("学生信息填写");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        et_school = (EditText) findViewById(R.id.et_school);
        et_teacher = (EditText) findViewById(R.id.et_teacher);
        et_number = (EditText) findViewById(R.id.et_number);
        et_stu_email = (EditText) findViewById(R.id.et_stu_email);
        et_course = (EditText) findViewById(R.id.et_course);
        btn_ok = (Button) findViewById(R.id.btn_stu_ok);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String teacher = et_teacher.getText().toString();
                String school = et_school.getText().toString();
                String mail = et_stu_email.getText().toString();
                String number = et_number.getText().toString();
                String course = et_course.getText().toString();
                saveSet(teacher,school,mail,number,course);
            }
        });
    }

    public void saveSet(String teacher,String school,String mail,String number,String course){
        RequestParams params = new RequestParams();
        params.put("teacher",teacher);
        params.put("school",school);
        params.put("mail",mail);
        params.put("number",number);
        params.put("course",course);
        String user =PreferencesUtil.getSharedStringData(StudentSetActivity.this,"username");
        params.put("user", user);
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
                            Toast.makeText(StudentSetActivity.this, "验证成功!", Toast.LENGTH_SHORT).show();
                            //setType2("student");
                            btn_ok.setEnabled(false);
                        } else {
                            //  btn_login.setEnabled(true);
                            Toast.makeText(StudentSetActivity.this, "未找到相关信息！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //  btn_login.setEnabled(true);
                    }
                } else {
                    // btn_login.setEnabled(true);
                }
            }

            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                //DialogView.dismiss();
                Toast.makeText(StudentSetActivity.this, "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setType2(String type){
        RequestParams params = new RequestParams();
        params.put("action", "settype2");
        String user =PreferencesUtil.getSharedStringData(StudentSetActivity.this,"username");
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
                            Toast.makeText(StudentSetActivity.this, "设置成功,请返回刷新", Toast.LENGTH_SHORT).show();
                        } else if(object.getString("code").equals("false")){
                            Toast.makeText(StudentSetActivity.this, "设置失败", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(StudentSetActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                }
            }
            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(StudentSetActivity.this, "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
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

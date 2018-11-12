package com.hzu.feirty.HomeWork.activity.index;

import android.content.Intent;
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
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017-10-29.
 */

public class ResetPwdActivity extends AppCompatActivity {
    private EditText et_pwd;
    private EditText et_pwd1;
    private Button btn_reset;
    private String mail_name;
    private String url = Ip.ip + "/HomeWork/DoGetUser?";
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(ResetPwdActivity.this);
        setContentView(R.layout.activity_reset_pwd);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("重置密码");
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
    private void initView(){
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        et_pwd1 = (EditText) findViewById(R.id.et_pwd1);
        btn_reset = (Button) findViewById(R.id.btn_reset);
        mail_name =getIntent().getStringExtra("mail_name");
    }
    private void initListener(){
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNull()){
                    resetPwd(mail_name,et_pwd.getText().toString());
                }
            }
        });
    }
    private boolean isNull(){
        if(et_pwd.getText().toString().equals("")|| et_pwd1.getText().toString().equals("")){
            Toast.makeText(this, "输入不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!et_pwd.getText().toString().equals(et_pwd1.getText().toString())){
            Toast.makeText(this, "前后密码不一致", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return  true;
        }
    }

    private void resetPwd(String mail_name,String code) {
        RequestParams params = new RequestParams();
        params.put("mail_name", mail_name);
        params.put("pwd", code);
        params.put("action", "reset_pwd");
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
                            MyApplication.getInstance().exitApp();
                            startActivity(new Intent(ResetPwdActivity.this, LoginActivity.class));
                            Toast.makeText(ResetPwdActivity.this, "重置成功", Toast.LENGTH_SHORT).show();
                        } else if(object.getString("code").equals("false")){
                            Toast.makeText(ResetPwdActivity.this, "邮箱未注册", Toast.LENGTH_SHORT).show();
                        }else if(object.getString("code").equals("upcode")){
                            Toast.makeText(ResetPwdActivity.this, "获取次数超过限制", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                //DialogView.dismiss();
                Toast.makeText(ResetPwdActivity.this, "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.drawable.ic_home1) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

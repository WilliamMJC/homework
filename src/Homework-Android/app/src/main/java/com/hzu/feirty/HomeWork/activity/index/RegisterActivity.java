package com.hzu.feirty.HomeWork.activity.index;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.hzu.feirty.HomeWork.R;
import com.hzu.feirty.HomeWork.entity.Ip;
import com.hzu.feirty.HomeWork.wedgit.ClearEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONException;
import org.json.JSONObject;

import static com.hzu.feirty.HomeWork.R.color.blue;
import static com.hzu.feirty.HomeWork.R.color.border_clo;

public class RegisterActivity extends AppCompatActivity {
    private ClearEditText username;
    private ClearEditText pwd;
    private ClearEditText pwd1;
    private ClearEditText et_mail;
    private ClearEditText et_yzm;
    private Button btn_djs;
    private Button btn_register;
    private String url = Ip.ip + "/HomeWork/DoGetUser?";
    private String format = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(RegisterActivity.this);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("注册");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        username = (ClearEditText) findViewById(R.id.et_username);
        pwd = (ClearEditText) findViewById(R.id.et_pwd);
        pwd1 = (ClearEditText) findViewById(R.id.et_pwd1);
        et_mail = (ClearEditText) findViewById(R.id.et_email);
        et_yzm  = (ClearEditText) findViewById(R.id.et_yzm);
        btn_register = (Button) findViewById(R.id.btn_register);
        // 对EditText内容的实时监听
        et_mail.addTextChangedListener(new TextWatcher() {
            // 第二个执行
            @Override
            public void onTextChanged(CharSequence s, int start, int before,int count) {

            }

            // 第一个执行
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {

            }

            // 第三个执行
            @Override
            public void afterTextChanged(Editable s) { // Edittext中实时的内容
                System.out.println("afterTextChanged:" + s);
                String okdata = s.toString();
                if (okdata.matches(format)) {
                    btn_djs.setBackgroundColor(getResources().getColor(blue));
                    btn_djs.setEnabled(true);
                }else{
                    btn_djs.setBackgroundColor(getResources().getColor(border_clo));
                    btn_djs.setEnabled(false);
                }
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String password = pwd.getText().toString();
                String pwd = pwd1.getText().toString();
                String mail = et_mail.getText().toString();
                String yzm = et_yzm.getText().toString();
                if (user.equals("") || password.equals("") ||pwd.equals("")||mail.equals("")||yzm.equals("")) {
                    Toast.makeText(RegisterActivity.this, "输入不能为空",Toast.LENGTH_LONG).show();
                    return;
                }else if(!pwd.equals(password)){
                    Toast.makeText(RegisterActivity.this, "前后密码不一致",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(!mail.matches(format)){
                    Toast.makeText(RegisterActivity.this, "邮箱格式不正确",Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(RegisterActivity.this, "注册成功",Toast.LENGTH_LONG).show();
                Register(user, password,yzm,mail);
            }
        });
        btn_djs = (Button) findViewById(R.id.btn_djs);
        //new倒计时对象,总共的时间,每隔多少秒更新一次时间
        MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,1000);
        //给Button设置点击时间,触发倒计时
        btn_djs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = et_mail.getText().toString();
                if (mail.equals("")) {
                    Toast.makeText(RegisterActivity.this, "邮箱不能为空",Toast.LENGTH_LONG).show();
                    return;
                } else if(!mail.matches(format)){
                    Toast.makeText(RegisterActivity.this, "邮箱格式不正确",Toast.LENGTH_LONG).show();
                    return;
                }
                getCode(mail);
                //myCountDownTimer.start();
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    //复写倒计时
    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            btn_djs.setClickable(false);
            btn_djs.setText("已发送("+l/1000+"s)");
        }
        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            btn_djs.setText("重新获取");
            //设置可点击
            btn_djs.setClickable(true);
        }
    }

    private void getCode(String mail_name) {
        RequestParams params = new RequestParams();
        params.put("mail_name", mail_name);
        params.put("action", "getcode");
        AsyncHttpClient client = new AsyncHttpClient();
        client.setConnectTimeout(5000);
        String url3 = "http://192.168.10.168:8080/email-homework/api/user/getVerifyCode";
        client.post(url3, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, org.apache.http.Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if (str != null) {
                    try {
                        JSONObject object = new JSONObject(str);
                        if (object.getString("code").equals("success")) {
                            new MyCountDownTimer(60000,1000).start();
                            Toast.makeText(RegisterActivity.this, "获取成功", Toast.LENGTH_SHORT).show();
                        } else if(object.getString("code").equals("false")){
                            Toast.makeText(RegisterActivity.this, "邮箱已注册", Toast.LENGTH_SHORT).show();
                        }else if(object.getString("code").equals("upcode")){
                            Toast.makeText(RegisterActivity.this, "获取次数超过限制", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                //DialogView.dismiss();
                btn_register.setEnabled(true);
                Toast.makeText(RegisterActivity.this, "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void Register(String user, String password,String code,String mail_name) {
        RequestParams params = new RequestParams();
        params.put("user", user);
        params.put("password", password);
        params.put("mail_name", mail_name);
        params.put("mail_code", code);
        params.put("action", "save");
        String url2 = "http://192.168.10.168:8080/email-homework/api/user/register";
        AsyncHttpClient client = new AsyncHttpClient();
        client.setConnectTimeout(5000);
        client.post(url2, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, org.apache.http.Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if (str != null) {
                    try {
                        JSONObject object = new JSONObject(str);
                        if (object.getString("code").equals("success")) {
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                            MyApplication.getInstance().exitApp();
                            startActivity(intent);
                        } else if(object.getString("code").equals("same_name")){
                            Toast.makeText(RegisterActivity.this, "用户名已存在，请重新注册", Toast.LENGTH_SHORT).show();
                        } else if(object.getString("code").equals("false")){
                            Toast.makeText(RegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                //DialogView.dismiss();
                btn_register.setEnabled(true);
                Toast.makeText(RegisterActivity.this, "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
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


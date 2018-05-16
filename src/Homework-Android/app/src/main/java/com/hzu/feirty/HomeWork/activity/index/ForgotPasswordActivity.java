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
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import static com.hzu.feirty.HomeWork.R.color.blue;
import static com.hzu.feirty.HomeWork.R.color.border_clo;

/**
 * Created by Administrator on 2017-10-29.
 */

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText et_mail;
    private EditText et_identify_code;
    private Button btn_identify;
    private Button btn_djs;
    private String url = Ip.ip + "/HomeWork/DoGetUser?";
    private String format = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(ForgotPasswordActivity.this);
        setContentView(R.layout.activity_forgot_pwd);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("验证邮箱");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,1000);
        initView();
        initListener();
    }
    private void initView(){
        et_mail = (EditText) findViewById(R.id.et_mail);
        et_identify_code = (EditText) findViewById(R.id.et_yzm);
        btn_identify = (Button) findViewById(R.id.btn_identify);
        btn_djs = (Button) findViewById(R.id.btn_djs);
    }
    private void initListener(){
        final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,1000);
        btn_djs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!et_mail.getText().toString().equals("")){
                    getCode(et_mail.getText().toString());
                }
                //myCountDownTimer.start();
            }
        });
        btn_identify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNull()){
                    forgotOk(et_mail.getText().toString(),et_identify_code.getText().toString());
                }
            }
        });
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
    }
    private boolean isNull(){
        if(et_mail.getText().toString().equals("")||et_identify_code.getText().toString().equals("")){
            Toast.makeText(this, "输入不为空", Toast.LENGTH_SHORT).show();
            return false;
        }else if(et_mail.getText().toString().matches(format)){
            return true;
        }else{
            Toast.makeText(this, "邮箱格式不正确", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    private void getCode(String mail_name) {
        RequestParams params = new RequestParams();
        params.put("mail_name", mail_name);
        params.put("action", "reset_code");
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
                            new MyCountDownTimer(60000,1000).start();
                            Toast.makeText(ForgotPasswordActivity.this, "获取成功", Toast.LENGTH_SHORT).show();
                        } else if(object.getString("code").equals("false")){
                            Toast.makeText(ForgotPasswordActivity.this, "邮箱未注册", Toast.LENGTH_SHORT).show();
                        }else if(object.getString("code").equals("upcode")){
                            Toast.makeText(ForgotPasswordActivity.this, "获取次数超过限制", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                //DialogView.dismiss();
                Toast.makeText(ForgotPasswordActivity.this, "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void forgotOk(final String mail_name, String code) {
        RequestParams params = new RequestParams();
        params.put("mail_name", mail_name);
        params.put("mail_code", code);
        params.put("action", "reset_ok");
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
                            startActivity(new Intent(ForgotPasswordActivity.this, ResetPwdActivity.class).putExtra("mail_name",mail_name));
                        } else if(object.getString("code").equals("false")){
                            Toast.makeText(ForgotPasswordActivity.this, "邮箱未注册", Toast.LENGTH_SHORT).show();
                        }else if(object.getString("code").equals("upcode")){
                            Toast.makeText(ForgotPasswordActivity.this, "获取次数超过限制", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                //DialogView.dismiss();
                Toast.makeText(ForgotPasswordActivity.this, "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.drawable.ic_home1) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

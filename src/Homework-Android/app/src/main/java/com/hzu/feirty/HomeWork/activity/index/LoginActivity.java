package com.hzu.feirty.HomeWork.activity.index;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hzu.feirty.HomeWork.R;
import com.hzu.feirty.HomeWork.activity.MainActivityDemo;
import com.hzu.feirty.HomeWork.entity.Ip;
import com.hzu.feirty.HomeWork.utils.PreferencesUtil;
import com.hzu.feirty.HomeWork.wedgit.ClearEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.ref.WeakReference;


public class LoginActivity extends AppCompatActivity {
    private ClearEditText et_un, et_pw;
    private Button btn_login;
    private TextView btn_register;
    private TextView btn_forgot;
    private CheckBox cb_password;
    private Context context;
    private Handler handler;
    private ProgressDialog pd;
    private static final String USERNAME = "username";
    private static final String LOGINSTATE = "loginstate";
    private String url = Ip.ip + "/HomeWork/DoGetUser?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
       /* boolean state = PreferencesUtil.getSharedBooleanData(context,"loginstate");
        if(state){
            startActivity(new Intent(context, MainActivityDemo.class));
        }*/
        MyApplication.getInstance().addActivity(LoginActivity.this);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("登录");
        //设置导航图标、添加菜单点击事件要在setSupportActionBar方法之后
        setSupportActionBar(toolbar);

        initView();
        handler = new MyHandler(this);
        setListener();
    }
    private void setListener() {
		//登陆按钮的监听
        btn_login.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {   //先判断输入合法性
                if (TextUtils.isEmpty(et_un.getText()) || TextUtils.isEmpty(et_pw.getText())) {
                    Toast.makeText(getApplicationContext(), "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
				//保存属性信息到PreferencesUtil.setSharedBooleanData()方法
                login();
            }
        });
        btn_register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, RegisterActivity.class));
            }
        });
        btn_forgot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ForgotPasswordActivity.class));
            }
        });
        cb_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int passwordLength = et_pw.getText().length();
                et_pw.setInputType(isChecked ?
                        (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) :
                        (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD));
                et_pw.setSelection(passwordLength);
            }
        });
    }

    private void login() {
        RequestParams params = new RequestParams();
        params.put("user", et_un.getText().toString());
        params.put("password", et_pw.getText().toString());
        params.put("action", "login");
        AsyncHttpClient client = new AsyncHttpClient();
        client.setConnectTimeout(1000);
        PersistentCookieStore myCookieStore = new PersistentCookieStore(LoginActivity.this);
        client.setCookieStore(myCookieStore);
        String url2 = "http://192.168.10.168:8080/email-homework/api/user/login";
        pd= ProgressDialog.show(LoginActivity.this,null, "请稍候");
        client.post(url2, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, org.apache.http.Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if (str != null) {
                    try {
                        JSONObject object = new JSONObject(str);
                        if (object.getString("code").equals("success")) {
                            String token =object.getString("token");
                            pd.dismiss();
                            PreferencesUtil.setSharedStringData(context, USERNAME, et_un.getText().toString());
                            PreferencesUtil.setSharedBooleanData(context,LOGINSTATE,true);
                            PreferencesUtil.setSharedStringData(context, "token", token);
                            Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                            Toast.makeText(LoginActivity.this, "token:"+token, Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(context, MainActivityDemo.class));

                        } else {
                            pd.dismiss();
                            Toast.makeText(LoginActivity.this, "账号或密码有误，请重新输入", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        btn_login.setEnabled(true);
                    }
                } else {
                    btn_login.setEnabled(true);
                }
            }

            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                pd.dismiss();
                Toast.makeText(LoginActivity.this, "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
            }
        });
    }
	//控件定义方法
    private void initView() {
        et_un = (ClearEditText) findViewById(R.id.et_un);
        et_pw = (ClearEditText) findViewById(R.id.et_pw);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (TextView) findViewById(R.id.btn_register);
        btn_forgot = (TextView) findViewById(R.id.btn_forgot);
        cb_password = (CheckBox) findViewById(R.id.cb_password);
        String username = PreferencesUtil.getSharedStringData(context,USERNAME);
        et_un.setText(username);
    }
     //接受子线程发送的数据， 并用此数据配合主线程更新UI
    private static class MyHandler extends Handler {

        private WeakReference<LoginActivity> wrActivity;

        public MyHandler(LoginActivity activity) {
            this.wrActivity = new WeakReference<LoginActivity>(activity);
        }

        @Override
        public void handleMessage(android.os.Message msg) {
            final LoginActivity activity = wrActivity.get();
            switch (msg.what) {
                case 0:
                    Toast.makeText(activity.getApplicationContext(), "用户名或密码不正确", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            MyApplication.getInstance().exitApp();
            System.exit(0);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

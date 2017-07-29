package com.hzu.feirty.MailIM.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

import com.hzu.feirty.MailIM.R;
import com.hzu.feirty.MailIM.entity.Ip;
import com.hzu.feirty.MailIM.utils.CookieUtil;
import com.hzu.feirty.MailIM.utils.FinalAsyncHttpClient;
import com.hzu.feirty.MailIM.utils.PreferencesUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import org.apache.http.cookie.Cookie;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.ref.WeakReference;
import java.util.List;


public class LoginActivity extends AppCompatActivity {

    private EditText et_un, et_pw;        //账号密码输入框
    private Button btn_login;           //登陆按钮
    private Button btn_register;
    private CheckBox cb_saveuser, cb_autologin;  //记住密码、自动登陆
    private Context context;            
    private Handler handler;           //Handler:接受子线程发送的数据， 并用此数据配合主线程更新UI
    private Toolbar toolbar;
    private MyApplication myapplication;
    private static final String POP3HOST = "pop3host";
    private static final String SMTPHOST = "smtphost";
    private static final String SAVEUSER = "saveuser";
    private static final String AUTOLOGIN = "autologin";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private String url = Ip.ip + "/MailIM/DoGetUser?";
    private String url2 = Ip.ip + "/MailIM/DoGetMail?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //MyApplication myapplication = new MyApplication();
        //myapplication.addActivity(this);
       /* if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }*/
        initView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        context = this;
        handler = new MyHandler(this);
		//判断自动登陆的历史操作
        if (PreferencesUtil.getSharedBooleanData(context, AUTOLOGIN)) {
			//TRUE则直接登陆
            login();
        } else {
            //判断SVAEUSER的设置
            if (PreferencesUtil.getSharedBooleanData(context, SAVEUSER)) {
                //TRUE则将历史用户信息显示
                //用户信息保存在PreferencesUtil()
                cb_saveuser.setChecked(PreferencesUtil.getSharedBooleanData(context, SAVEUSER));
                et_un.setText(PreferencesUtil.getSharedStringData(context, USERNAME));
                et_pw.setText(PreferencesUtil.getSharedStringData(context, PASSWORD));
            }
            setListener();    //监听集合

        }
    }
       //自动登陆Checkbox控件的监听
    private void setListener() {
        cb_autologin.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cb_saveuser.setEnabled(!isChecked);
                if (isChecked) {      
                    cb_saveuser.setChecked(isChecked);   //勾自动登陆的操作，同时勾上保存用户Checkbox
                }
            }
        });
		//登陆按钮的监听
        btn_login.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {   //先判断输入合法性
                if (TextUtils.isEmpty(et_un.getText()) || TextUtils.isEmpty(et_pw.getText())) {
                    Toast.makeText(getApplicationContext(), "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
				//保存属性信息到PreferencesUtil.setSharedBooleanData()方法
                PreferencesUtil.setSharedBooleanData(context, SAVEUSER, cb_saveuser.isChecked());
                PreferencesUtil.setSharedBooleanData(context, AUTOLOGIN, cb_autologin.isChecked());
                PreferencesUtil.setSharedStringData(context, USERNAME, et_un.getText().toString());
                PreferencesUtil.setSharedStringData(context, PASSWORD, et_pw.getText().toString());
                PreferencesUtil.setSharedStringData(context, POP3HOST,"pop.qq.com");
                PreferencesUtil.setSharedStringData(context, SMTPHOST, "smtp.qq.com");
                //PreferencesUtil.setSharedStringData(context, POP3HOST, ConnUtil.getPOP3Host(et_un.getText().toString()));
               // PreferencesUtil.setSharedStringData(context, SMTPHOST, ConnUtil.getSMTPHost(et_un.getText().toString()));
				//执行login()方法
                login();
            }
        });
        btn_register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, RegisterActivity.class));
            }
        });
    }

    private void login() {
        RequestParams params = new RequestParams();
        params.put("user", et_un.getText().toString());
        params.put("password", et_pw.getText().toString());
        params.put("action", "login");
        FinalAsyncHttpClient finalAsyncHttpClient = new FinalAsyncHttpClient();
        AsyncHttpClient client = finalAsyncHttpClient.getAsyncHttpClient();
        saveCookie(client);
        PersistentCookieStore myCookieStore = new PersistentCookieStore(LoginActivity.this);
        client.setCookieStore(myCookieStore);
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, org.apache.http.Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if (str != null) {
                    try {
                        JSONObject object = new JSONObject(str);
                        if (object.getString("code").equals("success")) {
                            CookieUtil.setCookies(getCookie());
                            Toast.makeText(LoginActivity.this, "登陆成功！", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(context, MainActivity.class));
                        } else {
                            btn_login.setEnabled(true);
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
                //DialogView.dismiss();
                btn_login.setEnabled(true);
                Toast.makeText(LoginActivity.this, "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();

            }
        });
		/*//子线程处理
        new Thread(new Runnable() {
            @Override
			  //PreferencesUtil() 用户设置有关信息保存提取JAVABEAN类
			  //ConnUtil()    邮箱发送的有关信息保存提取JAVABEAN类和邮箱协议的连接方法
            public void run() {
				//调用ConnUtil中的login()方法，设置pop3host和邮箱地址、登陆密码  :pop3用于邮件接收的协议
                Store store = ConnUtil.login(PreferencesUtil.getSharedStringData(context, POP3HOST), PreferencesUtil.getSharedStringData(context, USERNAME),
                        PreferencesUtil.getSharedStringData(context, PASSWORD));
                if (store != null) {
					//调用成功，保存store
                    //((MailApplication) getApplication()).setStore(store);
                    ((MailApplication)context.getApplicationContext()).setStore(store);
                    MailApplication a=new MailApplication();
                    a.setStore(store);
					//跳转到HomeActivity
                    startActivity(new Intent(context, MainActivity.class));
                    finish();
                } else {
					//不成功显示提示信息
                    handler.obtainMessage(0).sendToTarget();
                }
            }
        }).start();*/
    }
	//控件定义方法
    private void initView() {
        et_un = (EditText) findViewById(R.id.et_un);
        et_pw = (EditText) findViewById(R.id.et_pw);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);
        cb_saveuser = (CheckBox) findViewById(R.id.cb_savepw);
        cb_autologin = (CheckBox) findViewById(R.id.cb_autologin);
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
        };
    };

    protected void saveCookie(AsyncHttpClient client) {
        PersistentCookieStore cookieStore = new PersistentCookieStore(this);
        client.setCookieStore(cookieStore);
    }

    protected List<Cookie> getCookie(){
        PersistentCookieStore cookieStore = new PersistentCookieStore(this);
        List<Cookie> cookies = cookieStore.getCookies();
        return cookies;
    }

    public void clearCookie(){
        PersistentCookieStore cookieStore = new PersistentCookieStore(this);
        cookieStore.clear();
    }
}

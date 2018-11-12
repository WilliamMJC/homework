package com.hzu.feirty.HomeWork.activity.mycenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hzu.feirty.HomeWork.R;
import com.hzu.feirty.HomeWork.utils.PreferencesUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/4/23 0023.
 */

public class UserNameUpdateActivity extends AppCompatActivity {
    private EditText loginName;
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                   UserNameUpdateActivity.this.finish();
                    break;

            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_my);
        if (this.getSupportActionBar() != null) {
            this.getSupportActionBar().hide();
        }
        toolbar.setTitle("编辑姓名");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.inflateMenu(R.menu.info_update_toolbar_menu);//设置右上角的填充菜单
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_update:
                        String userName = loginName.getText().toString();
                        if(!userName.equals("")){
                            updateUserName(userName);
                        }
                        break;
                }
                return true;
            }
        });
        initView();
        getIntentValue(loginName);
    }
    public void getIntentValue(EditText position){
        Intent _intent = getIntent();
        //从Intent当中根据key取得value
        if (_intent != null) {
            String _value = _intent.getStringExtra("DATA");
            position.setText(_value);
        }
    }

    public void initView() {
        loginName = (EditText) findViewById(R.id.loginName);
    }

    private void updateUserName(final String data){
        RequestParams params = new RequestParams();
        String token = PreferencesUtil.getSharedStringData(UserNameUpdateActivity.this,"token");
        params.put("token", token);
        params.put("userName", data);
        AsyncHttpClient client = new AsyncHttpClient();
        client.setConnectTimeout(5000);
        String url = "http://192.168.10.168:8080/email-homework/api/user/updateUserName";
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, org.apache.http.Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if (str != null) {
                    try {
                        JSONObject object = new JSONObject(str);
                        String code =object.getString("code");
                        if(code.equals("success")){
                            PreferencesUtil.setSharedStringData(UserNameUpdateActivity.this,"username",data);
                            Toast.makeText(UserNameUpdateActivity.this,"修改成功",Toast.LENGTH_LONG).show();
                            myHandler.obtainMessage(1).sendToTarget();
                        }else{
                            Toast.makeText(UserNameUpdateActivity.this,"用户名已存在",Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                }
            }
            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(UserNameUpdateActivity.this, "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_update:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_update_toolbar_menu, menu);
        return true;
    }

}

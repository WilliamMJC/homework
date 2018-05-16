package com.hzu.feirty.HomeWork.activity.mycenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
 * Created by Administrator on 2018/4/24 0024.
 */

public class UserNoUpdateActivity extends AppCompatActivity {
    private EditText studentNo;


    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    UserNoUpdateActivity.this.finish();
                    break;
            }
        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_update);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_my);
        studentNo = (EditText) findViewById(R.id.studentNo);
        studentNo.getText().toString();
        if (this.getSupportActionBar() != null) {
            this.getSupportActionBar().hide();
        }
        toolbar.setTitle("编辑学号");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.inflateMenu(R.menu.info_update_toolbar_menu);//设置右上角的填充菜单
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_update:
                        String no =studentNo.getText().toString();
                        if(!no.equals("")){
                            updateStudentNo(no);
                        }
                        break;
                }
                return true;
            }
        });
        getIntentValue(studentNo);

    }

    private void updateStudentNo(String data){
        RequestParams params = new RequestParams();
        String token = PreferencesUtil.getSharedStringData(UserNoUpdateActivity.this,"token");
        params.put("token", token);
        params.put("studentNo", data);
        AsyncHttpClient client = new AsyncHttpClient();
        client.setConnectTimeout(5000);
        String url = "http://192.168.10.168:8080/email-homework/api/user/updateStudentNo";
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, org.apache.http.Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if (str != null) {
                    try {
                        JSONObject object = new JSONObject(str);
                        String code =object.getString("code");
                        if(code.equals("success")){
                            Toast.makeText(UserNoUpdateActivity.this,"修改成功",Toast.LENGTH_LONG).show();
                            myHandler.obtainMessage(1).sendToTarget();
                        }else{
                            Toast.makeText(UserNoUpdateActivity.this,"学号绑定失败，请检查学号是否正确！",Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                }
            }
            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(UserNoUpdateActivity.this, "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getIntentValue(EditText position){
        Intent _intent = getIntent();
        //从Intent当中根据key取得value
        if (_intent != null) {
            String _value = _intent.getStringExtra("DATA");
            position.setText(_value);
        }
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

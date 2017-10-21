package com.hzu.feirty.HomeWork.activity.index;
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
import com.hzu.feirty.HomeWork.utils.ToastUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    private EditText username;
    private EditText pwd;
    private EditText pwd1;
    private Button btn_register;
    private String url = Ip.ip + "/HomeWork/DoGetUser?";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("注册");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        username = (EditText) findViewById(R.id.et_username);
        pwd = (EditText) findViewById(R.id.et_pwd);
        pwd1 = (EditText) findViewById(R.id.et_pwd1);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String password = pwd.getText().toString();
                String pwd = pwd1.getText().toString();
                if (user.equals("") || password.equals("")) {
                    ToastUtil.show(RegisterActivity.this, "用户名或密码不能为空");
                    return;
                }else if(!pwd.equals(password)){
                    ToastUtil.show(RegisterActivity.this, "前后密码不一致");
                    return;
                }
                Register(user, password);
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void Register(String user, String password) {
        RequestParams params = new RequestParams();
        params.put("user", user);
        params.put("password", password);
        params.put("action", "save");
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
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "用户名已存在，请重新注册", Toast.LENGTH_SHORT).show();
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


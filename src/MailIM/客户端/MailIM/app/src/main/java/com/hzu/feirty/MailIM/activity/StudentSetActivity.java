package com.hzu.feirty.MailIM.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hzu.feirty.MailIM.R;
import com.hzu.feirty.MailIM.entity.Ip;
import com.hzu.feirty.MailIM.utils.PreferencesUtil;
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
    private Button btn_ok;
    private static final String MAIL = "mail";
    private static final String PASSWORD = "password";
    private String url = Ip.ip + "/MailIM/DoGetStudent?";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_set);
        et_school = (EditText) findViewById(R.id.et_school);
        et_teacher = (EditText) findViewById(R.id.et_teacher);
        et_number = (EditText) findViewById(R.id.et_number);
        et_stu_email = (EditText) findViewById(R.id.et_stu_email);
        btn_ok = (Button) findViewById(R.id.btn_stu_ok);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String teacher = et_teacher.getText().toString();
                String school = et_school.getText().toString();
                String mail = et_stu_email.getText().toString();
                String number = et_number.getText().toString();
                saveSet(teacher,school,mail,number);
            }
        });
    }

    public void saveSet(String teacher,String school,String mail,String number){
        RequestParams params = new RequestParams();
        params.put("teacher",teacher);
        params.put("school",school);
        params.put("mail",mail);
        params.put("number",number);
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
                        /*    String tea_mail =object.getString("mail");
                            String pwd = object.getString("pwd");
                            PreferencesUtil.setSharedStringData(StudentSetActivity.this, MAIL, tea_mail);
                            PreferencesUtil.setSharedStringData(StudentSetActivity.this, PASSWORD, pwd);
                            Store store = ConnUtil.login("pop.qq.com",tea_mail,pwd);*/
                            Toast.makeText(StudentSetActivity.this, "验证成功!", Toast.LENGTH_SHORT).show();
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
}

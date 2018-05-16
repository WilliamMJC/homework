package com.hzu.feirty.HomeWork.activity.funcation;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hzu.feirty.HomeWork.R;
import com.hzu.feirty.HomeWork.activity.mycenter.UserInfoActivity;
import com.hzu.feirty.HomeWork.entity.Ip;
import com.hzu.feirty.HomeWork.utils.PreferencesUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.angmarch.views.NiceSpinner;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SendWorkActivity extends AppCompatActivity {
    private static final String USERNAME = "username";
    private SwipeRefreshLayout swiperefreshlayout;
    private String url = Ip.ip + "/HomeWork/DoGetHomeWork?";
    private String url2 = Ip.ip + "/HomeWork/DoGetCourse?";
    private EditText et_mailsubject, et_mailcontent;
    private TextView tv_date_time;
    private Button btn_sent;
    private Handler handler;
    private ProgressDialog pd;
    private Spinner sp;
    private String course1;
    private List<String> course = new ArrayList<String>();
    private Context context;   //-[1,9]:
    private String format="-[1-9]:";
    private  List<String> dataset=new  ArrayList<String>();
    private NiceSpinner niceSpinner;
    private String selectString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_send);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_sendwork);
        toolbar.setTitle("发布作业");
        //设置导航图标、添加菜单点击事件要在setSupportActionBar方法之后
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        init();
        initView();
    }
    private void init() {
        et_mailsubject = (EditText) findViewById(R.id.et_mailsubject);
        et_mailcontent = (EditText) findViewById(R.id.et_mailcontent);
        //sp=(Spinner) findViewById(R.id.spinner);
        btn_sent = (Button) findViewById(R.id.btn_sent);
        btn_sent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject=et_mailsubject.getText().toString();
                String content = et_mailcontent.getText().toString();
                if(subject.equals("")||content.equals("")){
                    Toast.makeText(SendWorkActivity.this, "输入不能为空", Toast.LENGTH_LONG).show();
                }else{   //subject.matches(format)
                    send(selectString,subject,content);
                }/*else{
                    Toast.makeText(SendWorkActivity.this, "标题格式不符合要求", Toast.LENGTH_LONG).show();
                }*/
            }
        });
    }
    public void initView(){
        /**
         *  初始化swipeRefreshLayout
         */
        swiperefreshlayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
        swiperefreshlayout.setColorSchemeResources(R.color.blue);
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                course.clear();
                findCourse();//请求网络的线程
            }
        });
        swiperefreshlayout.post(new Runnable() {
            @Override
            public void run() {
                //swiperefreshlayout.setRefreshing(true);
               findCourse();//请求网络的线程
            }
        });

    }

    public void send(String course1,String subject,String content){
        RequestParams params = new RequestParams();
        String token =PreferencesUtil.getSharedStringData(SendWorkActivity.this,"token");
        params.put("token", token);
        params.put("homeworkTitle", subject);
        params.put("content",content);
        params.put("courseName", course1);
        pd = ProgressDialog.show(SendWorkActivity.this,"","正在发布....", true, false);
        AsyncHttpClient client = new AsyncHttpClient();
        client.setConnectTimeout(5000);
        String url = "http://192.168.10.168:8080/email-homework/api/homework/addHomework";
        client.post(url, params, new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int i, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if (str != null) {
                    try {
                        JSONObject object = new JSONObject(str);
                        if (object.getString("code").equals("success")) {
                            myHandler.obtainMessage(1).sendToTarget();
                            Toast.makeText(SendWorkActivity.this, "发布成功！", Toast.LENGTH_LONG).show();
                        } else {
                            myHandler.obtainMessage(1).sendToTarget();
                            Toast.makeText(SendWorkActivity.this, "发布失败！", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(SendWorkActivity.this, "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void findCourse() {
        RequestParams params = new RequestParams();
        String token = PreferencesUtil.getSharedStringData(SendWorkActivity.this,"token");
        params.put("token", token);
        AsyncHttpClient client = new AsyncHttpClient();
        client.setConnectTimeout(50000);
        client.setTimeout(50000);
        String url2 = "http://192.168.10.168:8080/email-homework/api/course/getMyCourse";
        client.post(url2, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, org.apache.http.Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if (str != null) {
                    try {
                        JSONObject object = new JSONObject(str);
                        course.add("--请选择--");
                        if (object.getString("code").equals("success")) {
                            JSONArray arrayc = object.getJSONArray("course");
                            for(int b=0;b< arrayc.length();b++){
                                JSONObject itemb = arrayc.getJSONObject(b);
                                course.add(itemb.getString("course"));
                            }
                            Toast.makeText(SendWorkActivity.this,"课程接收成功",Toast.LENGTH_SHORT).show();
                            myHandler.obtainMessage(2).sendToTarget();
                        } else {
                            Toast.makeText(SendWorkActivity.this,"课程接收失败",Toast.LENGTH_SHORT).show();
                            myHandler.obtainMessage(2).sendToTarget();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(SendWorkActivity.this, "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
                handler.obtainMessage(1).sendToTarget();
            }
        });
    }
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    pd.dismiss();
                    break;
                case 2:
                    swiperefreshlayout.setRefreshing(false);
                    niceSpinner = (NiceSpinner) findViewById(R.id.nice_spinner);
                    niceSpinner.setTextColor(Color.BLACK);
                    niceSpinner.attachDataSource(course);
                    niceSpinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Log.e("什么数据",String.valueOf(course.get(i)));
                            selectString = String.valueOf(course.get(i));
                            Toast.makeText(SendWorkActivity.this,String.valueOf(course.get(i)),Toast.LENGTH_LONG).show();
                        }
                    });

                    break;
                case 3:
                    swiperefreshlayout.setRefreshing(false);
                    break;

                default:
                    Toast.makeText(SendWorkActivity.this, "发送出现错误！", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

}

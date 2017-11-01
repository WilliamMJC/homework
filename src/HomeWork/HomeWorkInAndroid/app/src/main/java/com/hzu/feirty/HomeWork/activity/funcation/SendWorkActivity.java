package com.hzu.feirty.HomeWork.activity.funcation;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hzu.feirty.HomeWork.R;
import com.hzu.feirty.HomeWork.adapter.NumericWheelAdapter;
import com.hzu.feirty.HomeWork.entity.Ip;
import com.hzu.feirty.HomeWork.utils.PreferencesUtil;
import com.hzu.feirty.HomeWork.widget.WheelView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SendWorkActivity extends AppCompatActivity {

    private static final String USERNAME = "username";
    private SwipeRefreshLayout swiperefreshlayout;
    private String url = Ip.ip + "/HomeWork/DoGetMail?";
    private String url2 = Ip.ip + "/HomeWork/DoGetCourse?";
    private EditText et_mailsubject, et_mailcontent;
    private TextView tv_date_time;
    private Button btn_sent;
    private Handler handler;
    private ProgressDialog pd;
    private Spinner sp;
    private String course1;
    private ArrayList<String> attachments;
    private List<String> course = new ArrayList<String>();
    private Context context;
    private LayoutInflater inflater = null;
    private WheelView year;
    private WheelView month;
    private WheelView day;
    private WheelView hour;
    private WheelView mins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_send);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_sendwork);
        toolbar.setTitle("发布任务");
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
        tv_date_time = (TextView) findViewById(R.id.tv_date_time);
        sp=(Spinner) findViewById(R.id.spinner);
        btn_sent = (Button) findViewById(R.id.btn_sent);
        tv_date_time.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateAndTime();
            }
        });
        btn_sent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject=et_mailsubject.getText().toString();
                String content = et_mailcontent.getText().toString();
                send(course1,subject,content,tv_date_time.getText().toString());
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
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(SendWorkActivity.this, android.R.layout.simple_spinner_item,course);
                sp.setAdapter(adapter);
                findCourse();//请求网络的线程
            }
        });
        swiperefreshlayout.post(new Runnable() {
            @Override
            public void run() {
                swiperefreshlayout.setRefreshing(true);
                findCourse();//请求网络的线程
            }
        });
    }

    public void send(String course1,String subject,String content,String endtime){
        RequestParams params = new RequestParams();
        String user =PreferencesUtil.getSharedStringData(context, USERNAME);
        params.put("subject", subject);
        params.put("content",content);
        params.put("endtime",endtime);
        params.put("user",user);
        params.put("course", course1);
        params.put("action", "send");
        pd = ProgressDialog.show(SendWorkActivity.this, "操作","正在发布....", true, false);
        AsyncHttpClient client = new AsyncHttpClient();
        client.setConnectTimeout(5000);
        client.post(url, params, new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int i, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if (str != null) {
                    try {
                        JSONObject object = new JSONObject(str);
                        if (object.getString("code").equals("success")) {
                            myHandler.obtainMessage(1).sendToTarget();
                            Toast.makeText(SendWorkActivity.this, "发送成功！", Toast.LENGTH_LONG).show();
                        } else {
                            myHandler.obtainMessage(1).sendToTarget();
                            Toast.makeText(SendWorkActivity.this, "发送失败！", Toast.LENGTH_LONG).show();
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
        String user = PreferencesUtil.getSharedStringData(SendWorkActivity.this,"username");
        params.put("user", user);
        params.put("action", "findcourse");
        AsyncHttpClient client = new AsyncHttpClient();
        client.setConnectTimeout(50000);
        client.setTimeout(50000);
        client.post(url2, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, org.apache.http.Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if (str != null) {
                    try {
                        JSONObject object = new JSONObject(str);
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

    private int getDay(int year, int month) {
        int day = 30;
        boolean flag = false;
        switch (year % 4) {
            case 0:
                flag = true;
                break;
            default:
                flag = false;
                break;
        }
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            case 2:
                day = flag ? 29 : 28;
                break;
            default:
                day = 30;
                break;
        }
        return day;
    }

    /**
     * 初始化年
     */
    private void initYear() {
        NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(this,1950, 2050);
        numericWheelAdapter.setLabel(" 年");
        		//numericWheelAdapter.setTextSize(15);  //设置字体大小
        year.setViewAdapter(numericWheelAdapter);
        year.setCyclic(true);
    }

    /**
     * 初始化月
     */
    private void initMonth() {
        NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(this,1, 12, "%02d");
        numericWheelAdapter.setLabel(" 月");
        //		numericWheelAdapter.setTextSize(15);  设置字体大小
        month.setViewAdapter(numericWheelAdapter);
        month.setCyclic(true);
    }

    /**
     * 初始化天
     */
    private void initDay(int arg1, int arg2) {
        NumericWheelAdapter numericWheelAdapter=new NumericWheelAdapter(this,1, getDay(arg1, arg2), "%02d");
        numericWheelAdapter.setLabel(" 日");
        //		numericWheelAdapter.setTextSize(15);  设置字体大小
        day.setViewAdapter(numericWheelAdapter);
        day.setCyclic(true);
    }

    /**
     * 初始化时
     */
    private void initHour() {
        NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(this,0, 23, "%02d");
        numericWheelAdapter.setLabel(" 时");
        //		numericWheelAdapter.setTextSize(15);  设置字体大小
        hour.setViewAdapter(numericWheelAdapter);
        hour.setCyclic(true);
    }
    /**
     * 初始化分
     */
    private void initMins() {
        NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(this,0, 59, "%02d");
        numericWheelAdapter.setLabel(" 分");
//		numericWheelAdapter.setTextSize(15);  设置字体大小
        mins.setViewAdapter(numericWheelAdapter);
        mins.setCyclic(true);
    }


    /**
     * 显示全部日期
     */
    private void showDateAndTime(){
        Calendar c = Calendar.getInstance();
        int curYear = c.get(Calendar.YEAR);
        int curMonth = c.get(Calendar.MONTH) + 1;//通过Calendar算出的月数要+1
        int curDate = c.get(Calendar.DATE);
        int curHour = c.get(Calendar.HOUR_OF_DAY);
        int curMin = c.get(Calendar.MINUTE);


        final AlertDialog dialog = new AlertDialog.Builder(SendWorkActivity.this)
                .create();
        dialog.show();
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.date_time_picker_layout);
        // 设置宽高
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.AnimBottom);

        year = (WheelView) window.findViewById(R.id.new_year);
        initYear();
        month = (WheelView) window.findViewById(R.id.new_month);
        initMonth();
        day = (WheelView) window.findViewById(R.id.new_day);
        initDay(curYear,curMonth);
        hour = (WheelView) window.findViewById(R.id.new_hour);
        initHour();
        mins = (WheelView) window.findViewById(R.id.new_mins);
        initMins();

        // 设置当前时间
        year.setCurrentItem(curYear - 1950);
        month.setCurrentItem(curMonth - 1);
        day.setCurrentItem(curDate - 1);
        hour.setCurrentItem(curHour);
        mins.setCurrentItem(curMin);

        month.setVisibleItems(7);
        day.setVisibleItems(7);
        hour.setVisibleItems(7);
        mins.setVisibleItems(7);

        // 设置监听
        TextView ok = (TextView) window.findViewById(R.id.set);
        TextView cancel = (TextView) window.findViewById(R.id.cancel);
        ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = String.format(Locale.CHINA,"%04d-%02d-%02d %02d:%02d",year.getCurrentItem()+1950,
                        month.getCurrentItem()+1,day.getCurrentItem()+1,hour.getCurrentItem(),mins.getCurrentItem());
                tv_date_time.setText(time);
                Toast.makeText(SendWorkActivity.this, time, Toast.LENGTH_LONG).show();
                dialog.cancel();
            }
        });
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        LinearLayout cancelLayout = (LinearLayout) window.findViewById(R.id.view_none);
        cancelLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                dialog.cancel();
                return false;
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
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(SendWorkActivity.this, android.R.layout.simple_spinner_item,course);
                    adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                    sp.setAdapter(adapter);
                    sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ArrayAdapter<String> adapter = (ArrayAdapter<String>) parent.getAdapter();
                            course1 = adapter.getItem(position);
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            Toast.makeText(SendWorkActivity.this,"请选择课程",Toast.LENGTH_LONG).show();
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

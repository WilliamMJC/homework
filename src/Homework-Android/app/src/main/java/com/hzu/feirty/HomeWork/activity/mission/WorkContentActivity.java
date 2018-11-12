package com.hzu.feirty.HomeWork.activity.mission;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.hzu.feirty.HomeWork.R;
import com.hzu.feirty.HomeWork.activity.information.ChatActivity;
import com.hzu.feirty.HomeWork.adapter.ChatUserAdaper;
import com.hzu.feirty.HomeWork.adapter.CommentAdapter;
import com.hzu.feirty.HomeWork.db.Comment;
import com.hzu.feirty.HomeWork.db.Email;
import com.hzu.feirty.HomeWork.db.MsgModel;
import com.hzu.feirty.HomeWork.entity.Ip;
import com.hzu.feirty.HomeWork.entity.OnRecyclerviewItemClickListener;
import com.hzu.feirty.HomeWork.fragment.InformationFragment;
import com.hzu.feirty.HomeWork.utils.PreferencesUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.rockerhieu.emojicon.EmojiconEditText;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;


public class WorkContentActivity extends AppCompatActivity {
    private PieChart pieChart;
    private LinearLayout legendLayout,ll_tubiao,ll_submit_state;
    private int[] colors = {R.color.PIE_PINK,R.color.PIE_ORANGE};//颜色集合
    private String[] datas={"已提交","未提交"};//数据，可以是任何类型的数据，如String,int
    private String student_number;
    private ArrayList<Comment> mData = new ArrayList<Comment>();
    private String works_number;
    private String else_number;
    private int student_int;
    private int works_int;
    private float works_bfb;
    private float else_bfb;
    private TextView tv_title, tv_content,tv_course_name,tv_submit_state;
    private ArrayList<InputStream> attachmentsInputStreams;
    private Button btn_receive_works;
    private String workmade_number;
    private Email email;
    private Toolbar toolbar;
    private long mExitTime;
    private ProgressDialog pd;
    private String url3 = Ip.ip + "/HomeWork/DoGetType?";
    private String url2 = Ip.ip + "/HomeWork/DoGetHomeWork?";
    private TextView sendMsg;
    private EmojiconEditText etContent;
    private ImageView ivFace;
    FrameLayout faceLayout;
    boolean bFace = false;
    boolean bClickFirst = true;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CommentAdapter recyclerAdapter;
    private TextView tv_noData;
    private TextView likeNum;
    private ImageView likeImg;


    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    recyclerAdapter.addAll(mData);
                    break;
                case 0:
                    recyclerAdapter =  new CommentAdapter(WorkContentActivity.this,mData,onRecyclerviewItemClickListener);
                    //recyclerAdapter = new MyAdapter(mData);
                    recyclerView.setAdapter(recyclerAdapter);
                    recyclerAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    pd.dismiss();
                    break;
                case 3:
                    recyclerAdapter =  new CommentAdapter(WorkContentActivity.this,mData,onRecyclerviewItemClickListener);
                    //recyclerAdapter = new MyAdapter(mData);
                    recyclerView.setAdapter(recyclerAdapter);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        toolbar = (Toolbar) findViewById(R.id.toolbar_item);
        toolbar.setTitle("作业要求");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.inflateMenu(R.menu.share_toolbar_menu);//设置右上角的填充菜单
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_chat:
                        final Intent intent = new Intent(WorkContentActivity.this, ChatTeaActivity.class).putExtra("EMAIL", email);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
        init();
        initLikeNum();
       // testData();
        initComment();
        initChart();
    }
    private void init() {
        //handler = new MyHandler(this);
        email = (Email) getIntent().getSerializableExtra("EMAIL");
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_course_name = (TextView) findViewById(R.id.tv_course_name);
        tv_submit_state = (TextView) findViewById(R.id.tv_submit_state);
        btn_receive_works = (Button) findViewById(R.id.btn_receive_work);
        pieChart = (PieChart) findViewById(R.id.pie_chart);
        legendLayout = (LinearLayout) findViewById(R.id.legendLayout);
        ll_tubiao = (LinearLayout) findViewById(R.id.ll_tubiao);
        ll_submit_state = (LinearLayout) findViewById(R.id.ll_submit_state);
        sendMsg = (TextView)findViewById(R.id.sendMsg) ;
        etContent = (EmojiconEditText)findViewById(R.id.etContent);
        ivFace = (ImageView)findViewById(R.id.ivFace);
        faceLayout = (FrameLayout)findViewById(R.id.emojicons);
        tv_noData = (TextView) findViewById(R.id.tv_noData) ;
        likeNum = (TextView)findViewById(R.id.likeNum);
        likeImg = (ImageView)findViewById(R.id.likeImg) ;
        student_number =email.getCc();
        works_number = email.getWork_number();
        if(works_number.equals("0")){
            pieChart.setVisibility(View.GONE);
            legendLayout.setVisibility(View.GONE);
        }
        tv_title.setText(email.getSubject());
        tv_content.setText(email.getContent());
        tv_course_name.setText(email.getCourse());
        /**
         *  初始化recyclerView
         */
        recyclerView = (RecyclerView)findViewById(R.id.recyclerviewComment);
        // swipeRefreshLayout.setRefreshing(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(WorkContentActivity.this));
        isType();
        btn_receive_works.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new AlertDialog.Builder(WorkContentActivity.this)
                        .setTitle("提示")
                        //.setIcon(R.drawable.ic_launcher)
                        .setMessage("确认收取该课程作业吗？")
                        //相当于点击确认按钮
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                 pd = ProgressDialog.show(WorkContentActivity.this, null, "正在检查,下载发送作业文件...");
                                receiveHomework(email.getCourse(),"1");
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create();
                dialog.show();
                //这里设置收取作业操作
            }
        });
        likeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateLikeNum();
            }
        });

        /**
         *  初始化swipeRefreshLayout
         */
       /* swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshComment);
        swipeRefreshLayout.setColorSchemeResources(R.color.blue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mData.clear();
                testData();

            }
        });
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mData.clear();
                swipeRefreshLayout.setRefreshing(true);
                testData();
                //receiveEmail();
            }
        });*/

        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String content=etContent.getText().toString();
                if(!content.equals("")){
                    sendMsg.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (bClickFirst) {
                    bFace = false;
                    faceLayout.setVisibility(View.GONE);
                    bClickFirst = false;
                }

            }
        });
        etContent.addTextChangedListener(mTextWatcher);


        etContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bFace = false;
                faceLayout.setVisibility(View.GONE);
            }
        });

        ivFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bFace) {
                    bFace = false;
                    faceLayout.setVisibility(View.GONE);
                    hideKeyBorad(etContent);
                } else {
                    bFace = true;
                    faceLayout.setVisibility(View.VISIBLE);
                    hideKeyBorad(etContent);
                }
            }
        });

        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment(etContent.getText().toString());
                etContent.setText("");
                etContent.setHint("回复...");
                bFace = false;
                hideKeyBorad(etContent);
            }
        });
    }


    private void initLikeNum(){
        RequestParams params = new RequestParams();
        String token = PreferencesUtil.getSharedStringData(WorkContentActivity.this,"token");
        params.put("token", token);
        params.put("homeworkUuid", email.getHomeworkUuid());
        AsyncHttpClient client = new AsyncHttpClient();
        client.setConnectTimeout(5000);
        client.setTimeout(5000);
        String url_comment="http://192.168.10.168:8080/email-homework/api/like/getLikeNum";
        client.post(url_comment, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if(str!=null){
                    try{
                        JSONObject object = new JSONObject(str);
                        if (object.getString("code").equals("success")) {
                            String isLike =object.getString("isLike");
                            String Num = object.getString("likeNum");
                            if(isLike.equals("1")){
                                likeImg.setImageResource(R.drawable.ic_like);
                            }else{
                                likeImg.setImageResource(R.drawable.ic_unlike);
                            }
                            likeNum.setText(Num);
                        }else{

                        }

                    }catch(JSONException e){

                    }
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(WorkContentActivity.this, "网络有问题", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateLikeNum(){
        RequestParams params = new RequestParams();
        String token = PreferencesUtil.getSharedStringData(WorkContentActivity.this,"token");
        params.put("token", token);
        params.put("homeworkUuid", email.getHomeworkUuid());
        AsyncHttpClient client = new AsyncHttpClient();
        client.setConnectTimeout(5000);
        client.setTimeout(5000);
        String url_like="http://192.168.10.168:8080/email-homework/api/like/updateLikeNum";
        client.post(url_like, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if(str!=null){
                    try{
                        JSONObject object = new JSONObject(str);
                        if (object.getString("code").equals("success")) {
                            String isLike =object.getString("isLike");
                            String Num = object.getString("likeNum");
                            if(isLike.equals("1")){
                                likeImg.setImageResource(R.drawable.ic_like);
                            }else{
                                likeImg.setImageResource(R.drawable.ic_unlike);
                            }
                            likeNum.setText(Num);
                        }else{

                        }

                    }catch(JSONException e){
                        Toast.makeText(WorkContentActivity.this, "网络有问题", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }

    private void initComment(){
        RequestParams params = new RequestParams();
        String token = PreferencesUtil.getSharedStringData(WorkContentActivity.this,"token");
        params.put("token", token);
        params.put("homeworkUuid", email.getHomeworkUuid());
        AsyncHttpClient client = new AsyncHttpClient();
        client.setConnectTimeout(5000);
        client.setTimeout(5000);
        String url_comment="http://192.168.10.168:8080/email-homework/api/comment/getComment";
        client.post(url_comment, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if(str!=null){
                    try{
                        JSONObject object = new JSONObject(str);
                        if (object.getString("code").equals("success")) {
                            mData.clear();
                            JSONArray array = object.getJSONArray("data");
                            for (int a = 0; a < array.length(); a++) {
                                JSONObject item = array.getJSONObject(a);
                                String userName = item.getString("userName");
                                String userUuid = item.getString("userUuid");
                                String createTime = item.getString("createTime");
                                String content = item.getString("content");
                                Comment comment =new Comment();
                                comment.setUserName(userName);
                                comment.setUserUuid(userUuid);
                                comment.setCreateTime(createTime);
                                comment.setContext(content);
                                mData.add(comment);
                            }
                            myHandler.obtainMessage(0).sendToTarget();
                        }else{
                            tv_noData.setVisibility(View.VISIBLE);
                            myHandler.obtainMessage(3).sendToTarget();
                        }

                    }catch(JSONException e){
                        myHandler.obtainMessage(0).sendToTarget();
                    }
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                myHandler.obtainMessage(0).sendToTarget();
            }
        });
    }


    private void addComment(String content){
        RequestParams params = new RequestParams();
        String token = PreferencesUtil.getSharedStringData(WorkContentActivity.this,"token");
        params.put("token", token);
        params.put("homeworkUuid", email.getHomeworkUuid());
        params.put("content",content);
        AsyncHttpClient client = new AsyncHttpClient();
        client.setConnectTimeout(5000);
        client.setTimeout(5000);
        String url_comment="http://192.168.10.168:8080/email-homework/api/comment/addComment";
        client.post(url_comment, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if(str!=null){
                    try{
                        JSONObject object = new JSONObject(str);
                        if (object.getString("code").equals("success")) {
                            mData.clear();
                            String userName = object.getString("userName");
                            String userUuid = object.getString("userUuid");
                            String createTime = object.getString("createTime");
                            String content = object.getString("content");
                            Comment comment =new Comment();
                            comment.setUserName(userName);
                            comment.setUserUuid(userUuid);
                            comment.setCreateTime(createTime);
                            comment.setContext(content);
                            mData.add(comment);
                            myHandler.obtainMessage(1).sendToTarget();
                        }else{

                        }

                    }catch(JSONException e){

                    }
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }




    private void receiveHomework(String course, String work_number){
        RequestParams params = new RequestParams();
        String token = PreferencesUtil.getSharedStringData(WorkContentActivity.this,"token");
        params.put("token", token);
        params.put("homeworkUuid", email.getHomeworkUuid());
        AsyncHttpClient client = new AsyncHttpClient();
        client.setConnectTimeout(500000);
        client.setTimeout(500000);
        String url_="http://192.168.10.168:8080/email-homework/api/homework/collectHomework";
        client.post(url_, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, org.apache.http.Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if (str != null) {
                    try {
                        JSONObject object = new JSONObject(str);
                        if(object.getString("code").equals("noidentry")){
                            myHandler.obtainMessage(2).sendToTarget();
                            Toast.makeText(WorkContentActivity.this, "不允许非教师操作", Toast.LENGTH_SHORT).show();
                        }else if(object.getString("code").equals("nomail")){
                            myHandler.obtainMessage(2).sendToTarget();
                            Toast.makeText(WorkContentActivity.this, "请先设置工作邮箱", Toast.LENGTH_SHORT).show();
                        }else if(object.getString("code").equals("nonew")){
                            myHandler.obtainMessage(2).sendToTarget();
                            Toast.makeText(WorkContentActivity.this, "没有待收取的作业", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (object.getString("code").equals("success")) {
                                myHandler.obtainMessage(2).sendToTarget();
                                String filename = object.getString("zip_name");
                                String fileSize = object.getString("zip_size");
                                String number = object.getString("amount");
                                AlertDialog.Builder builder = new AlertDialog.Builder(WorkContentActivity.this);
                                View view1 = View.inflate(WorkContentActivity.this,R.layout.activity_dialog,null);
                                TextView tv_filename = (TextView) view1.findViewById(R.id.tv_filename);
                                TextView tv_filesize = (TextView) view1.findViewById(R.id.tv_fileSize);
                                TextView tv_number = (TextView) view1.findViewById(R.id.tv_number);
                                tv_filename.setText(filename);
                                tv_filesize.setText(fileSize);
                                tv_number.setText(number);
                                builder.setTitle("作业已成功收取");
                                builder.setView(view1);
                                builder.setPositiveButton("确定", null);
                                builder.show();
                                Toast.makeText(WorkContentActivity.this, "收作业成功,请及时查收工作邮箱", Toast.LENGTH_SHORT).show();
                            } else if(object.getString("code").equals("SizeIsTOBig")){
                                myHandler.obtainMessage(2).sendToTarget();
                                String filename = object.getString("zip_name");
                                String fileSize = object.getString("zip_size");
                                String number = object.getString("amount");
                                String url = object.getString("url");
                                AlertDialog.Builder builder = new AlertDialog.Builder(WorkContentActivity.this);
                                View view1 = View.inflate(WorkContentActivity.this,R.layout.activity_url_dialog,null);
                                TextView tv_filename = (TextView) view1.findViewById(R.id.tv_filename);
                                TextView tv_filesize = (TextView) view1.findViewById(R.id.tv_fileSize);
                                TextView tv_number = (TextView) view1.findViewById(R.id.tv_number);
                                TextView tv_url = (TextView) view1.findViewById(R.id.tv_url);
                                tv_filename.setText(filename);
                                tv_filesize.setText(fileSize);
                                tv_number.setText(number);
                                tv_url.setText(url);
                                builder.setTitle("作业已成功收取");
                                builder.setView(view1);
                                builder.setPositiveButton("确定", null);
                                builder.show();
                                Toast.makeText(WorkContentActivity.this, "文件超出限制,请复制链接自行下载", Toast.LENGTH_SHORT).show();

                            } else{
                                myHandler.obtainMessage(2).sendToTarget();
                                Toast.makeText(WorkContentActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                myHandler.obtainMessage(2).sendToTarget();
                Toast.makeText(WorkContentActivity.this, "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
            }
        });
    }

    TextWatcher mTextWatcher = new TextWatcher() {
        private CharSequence temp;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            try {
                int retainCount = temp.length();
                if (retainCount > 0) {
                    sendMsg.setBackgroundResource(R.drawable.button_blue);
                } else if (retainCount == 0) {
                    sendMsg.setBackgroundResource(R.drawable.button);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    private void isType(){
        RequestParams params = new RequestParams();
        String token =PreferencesUtil.getSharedStringData(WorkContentActivity.this,"token");
        params.put("token",token);
        AsyncHttpClient client = new AsyncHttpClient();
        client.setConnectTimeout(50000);
        String urlnn="http://192.168.10.168:8080/email-homework/api/user/getIdentity";
        client.post(urlnn, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, org.apache.http.Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if (str != null) {
                    try {
                        JSONObject object = new JSONObject(str);
                        if (object.getString("code").equals("2")) {
                            btn_receive_works.setVisibility(View.VISIBLE);
                            ll_tubiao.setVisibility(View.VISIBLE);
                        } else if(object.getString("code").equals("1")){
                            tv_submit_state.setText(email.getSubmit_state());
                            ll_submit_state.setVisibility(View.VISIBLE);
                        } else {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                }
            }

            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                myHandler.obtainMessage(0).sendToTarget();
                Toast.makeText(WorkContentActivity.this, "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initChart() {
        // 设置饼图是否接收点击事件，默认为true
        pieChart.setTouchEnabled(true);
        //设置饼图是否使用百分比
        pieChart.setUsePercentValues(true);
        //设置饼图右下角的文字描述
        pieChart.setDescription("");
        //设置饼图右下角的文字描述   文字位置
        pieChart.setDescriptionPosition(460,730);
        //设置饼图右下角的文字大小
        pieChart.setDescriptionTextSize(16);
        pieChart.setExtraOffsets(15, -20, 15, 0);//left   top  right  bottom

        //是否显示圆盘中间文字，默认显示
        pieChart.setDrawCenterText(true);
        pieChart.setHoleColor(Color.WHITE);//设置中间圆盘的颜色
        //设置圆盘中间文字
        pieChart.setCenterText(generateCenterSpannableText());
        pieChart.setTransparentCircleColor(getResources().getColor(R.color.bantouming));
//        pieChart.setTransparentCircleAlpha(100);

        pieChart.setHoleRadius(60); //设置中间圆盘的半径,值为所占饼图的百分比
        pieChart.setTransparentCircleRadius(62); //设置中间透明圈的半径,值为所占饼图的百分比

        pieChart.setDrawCenterText(true);//饼状图中间可以添加文字

        //设置圆盘是否转动，默认转动
        pieChart.setRotationEnabled(true);
        //设置初始旋转角度
        pieChart.setRotationAngle(100);

//        //设置比例图
//        Legend mLegend = pieChart.getLegend();
//        //设置比例图显示在饼图的哪个位置
//        mLegend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
//        //设置比例图的形状，默认是方形,可为方形、圆形、线性
//        mLegend.setForm(Legend.LegendForm.SQUARE);
//        mLegend.setXEntrySpace(0f);//设置距离饼图的距离，防止与饼图重合
//        mLegend.setYEntrySpace(0f);
//        mLegend.setYOffset(0f);
//        //设置比例块换行...
//        mLegend.setWordWrapEnabled(true);
        //绑定数据
        bindData();
        customizeLegend();

        //设置X轴动画
//        pieChart.animateX(1800);
//        //设置y轴动画
//        pieChart.animateY(1800);
//        //设置xy轴一起的动画
//        pieChart.animateXY(1800, 1800);

        // 设置一个选中区域监听
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                //pieChart.setDescription(datas[e.getXIndex()] + "比例" + (int)e.getVal() + "%");
            }
            @Override
            public void onNothingSelected() {
            }
        });
    }
    /**
     *
     * 分成两部分
     */
    private void bindData(){
        /**
         * nameList用来表示每个饼块上的文字内容
         *
         */
        ArrayList<String> nameList = new ArrayList<String>();
        student_int=Integer.parseInt(student_number);
        works_int = Integer.parseInt(works_number);
        int else_int = student_int-works_int;
        else_number = else_int+"";
        works_bfb = (float) accuracy(works_int,student_int, 1);
        nameList.add((int)works_bfb+"%");
        else_bfb = (float) accuracy(else_int,student_int, 1);
        nameList.add((int)else_bfb+"%");

        /**
         * valueList将一个饼形图分成三部分，各个区域的百分比的值
         * Entry构造函数中
         * 第一个值代表所占比例，
         * 第二个值代表区域位置
         * （可以有第三个参数，表示携带的数据object）这里没用到
         */
        ArrayList<Entry> valueList = new ArrayList<Entry>();
        valueList.add(new Entry(works_bfb, 0));
        valueList.add(new Entry(else_bfb, 1));
        // valueList.add(new Entry(10, 2));
        // valueList.add(new Entry(4, 3));
        // valueList.add(new Entry(6, 4));

        //显示在比例图上
        PieDataSet dataSet = new PieDataSet(valueList, "");
        //设置各个饼状图之间的距离
        dataSet.setSliceSpace(0f);
        // 部分区域被选中时多出的长度
        dataSet.setSelectionShift(10f);

        // 设置饼图各个区域颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(getResources().getColor(R.color.PIE_PINK));
        colors.add(getResources().getColor(R.color.PIE_ORANGE));
        //colors.add(getResources().getColor(R.color.PIE_YELLOW));
        //colors.add(getResources().getColor(R.color.PIE_ORANGE));
        //colors.add(getResources().getColor(R.color.PIE_PINK));
        dataSet.setColors(colors);

        PieData data = new PieData(nameList, dataSet);
        //设置以百分比显示
        data.setValueFormatter(new PercentFormatter());
        //区域文字的大小
        data.setValueTextSize(11f);
        //设置区域文字的颜色
        data.setValueTextColor(Color.WHITE);
        //设置区域文字的字体
        data.setValueTypeface(Typeface.DEFAULT);

        pieChart.setData(data);

        //设置是否显示区域文字内容
        pieChart.setDrawSliceText(pieChart.isDrawSliceTextEnabled());
        //设置是否显示区域百分比的值
        for (IDataSet<?> set : pieChart.getData().getDataSets()){
            set.setDrawValues(!set.isDrawValuesEnabled());
        }
        // undo all highlights
        pieChart.highlightValues(null);
        pieChart.invalidate();
    }

    //中间显示的文字数据
    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("提交情况统计");
        s.setSpan(new RelativeSizeSpan(1.2f), 0, 4, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 0, 4, 0);
        s.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 4, 0);
        s.setSpan(new RelativeSizeSpan(1.2f), 4, s.length(), 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 4, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(Color.BLACK), 4, s.length(), 0);
        return s;
    }

    /**
     * 定制图例，通过代码生成布局
     */
    private void customizeLegend(){
        Legend legend=pieChart.getLegend();//设置比例图
        legend.setEnabled(false);//图例不显示
        String[] data = {works_number+"人",else_number+"人"};
        for(int i=0;i<datas.length;i++){
            LinearLayout.LayoutParams lp=new LinearLayout.
                    LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            lp.weight=1;//设置比重为1
            LinearLayout layout=new LinearLayout(this);//单个图例的布局
            layout.setOrientation(LinearLayout.HORIZONTAL);//水平排列
            layout.setGravity(Gravity.CENTER_VERTICAL);//垂直居中
            layout.setLayoutParams(lp);

            //添加color
            LinearLayout.LayoutParams colorLP=new LinearLayout.
                    LayoutParams(20,20);
            colorLP.setMargins(30, 10, 10, 10);//int left, int top, int right, int bottom
            LinearLayout colorLayout=new LinearLayout(this);
            colorLayout.setLayoutParams(colorLP);
            colorLayout.setBackgroundColor(getResources().getColor(colors[i]));
            layout.addView(colorLayout);
            //添加名称data
            TextView dataTV=new TextView(this);
            dataTV.setText(datas[i]+"");
            dataTV.setTextSize(12f);
            layout.addView(dataTV);
            //添加数量data
            TextView datam=new TextView(this);
            datam.setText("-"+data[i]+"");
            datam.setTextSize(12f);
            layout.addView(datam);
            legendLayout.addView(layout);//legendLayout为外层布局即整个图例布局，是在xml文件中定义
        }
    }
    public  double accuracy(double num, double total, int scale){
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
        //可以设置精确几位小数
        df.setMaximumFractionDigits(scale);
        //模式 例如四舍五入
        df.setRoundingMode(RoundingMode.HALF_UP);
        double accuracy_num = num / total * 100;
        //return df.format(accuracy_num);
        return accuracy_num;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_toolbar_menu, menu);
        return true;
    }
    private void hideKeyBorad(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }
    private OnRecyclerviewItemClickListener onRecyclerviewItemClickListener = new OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
            //这里的view就是我们点击的view  position就是点击的position
           // final Intent intent = new Intent(WorkContentActivity.this, ChatActivity.class).putExtra("toUserName",mData.get(position).getUserName());
           // startActivity(intent);
        }
    };
}

package com.hzu.feirty.HomeWork.activity.mission;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hzu.feirty.HomeWork.R;
import com.hzu.feirty.HomeWork.activity.mission.WorkContentActivity;
import com.hzu.feirty.HomeWork.adapter.ChatUserAdaper;
import com.hzu.feirty.HomeWork.adapter.MsgAdapter;
import com.hzu.feirty.HomeWork.db.Comment;
import com.hzu.feirty.HomeWork.db.Email;
import com.hzu.feirty.HomeWork.db.Message;
import com.hzu.feirty.HomeWork.db.MsgModel;
import com.hzu.feirty.HomeWork.entity.OnRecyclerviewItemClickListener;
import com.hzu.feirty.HomeWork.fragment.InformationFragment;
import com.hzu.feirty.HomeWork.utils.PreferencesUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.rockerhieu.emojicon.EmojiconEditText;
import com.rockerhieu.emojicon.EmojiconsFragment;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2018/4/25 0025.
 */

public class ChatTeaActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Email email;
    private RecyclerView recyclerView;
    private MsgAdapter adapter;
    private TextView sendMsg;
    private EmojiconEditText etContent;
    private ImageView ivFace;
    FrameLayout faceLayout;
    boolean bFace = false;
    boolean bClickFirst = true;
    private ArrayList<MsgModel> mData = new ArrayList<MsgModel>();
    private Runnable runnable=null;


    //定时获取最新消息
    Timer timer = new Timer( );
    TimerTask task = new TimerTask( ) {

        public void run ( ) {
            myHandler.obtainMessage(2).sendToTarget();
        }

    };

    Handler myHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    adapter.replaceAll(mData);
                    recyclerView.scrollToPosition(adapter.getItemCount()-1);
                    break;
                case 2:
                    getNewMessage();
                    break;
                case 3:
                    adapter.addAll(mData);
                    recyclerView.scrollToPosition(adapter.getItemCount()-1);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_tea);
        email = (Email) getIntent().getSerializableExtra("EMAIL");
        sendMsg = (TextView)findViewById(R.id.sendMsg) ;
        etContent = (EmojiconEditText)findViewById(R.id.etContent);
        ivFace = (ImageView)findViewById(R.id.ivFace);
        faceLayout = (FrameLayout)findViewById(R.id.emojicons);
        toolbar = (Toolbar) findViewById(R.id.toolbar_chat);
        toolbar.setTitle(email.getUserName());
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.inflateMenu(R.menu.chat_toolbar_menu);//设置右上角的填充菜单
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_info:

                        break;
                }
                return true;
            }
        });
        /**
         *  初始化recyclerView
         */
        recyclerView = (RecyclerView)findViewById(R.id.recylerViewMsg);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ChatTeaActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter = new MsgAdapter());
        //getData();
        getMessage();
        initSendMsg();
        timer.schedule(task,1000,3000);
    }



    private void initSendMsg(){
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
                //setEmojiconFragment(bFace);
            }
        });

        sendMsg.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                String content = etContent.getText().toString();
                if(!content.equals("")){
                    sendMsg();
                    bFace = false;
                    hideKeyBorad(etContent);
                }
            }
        });
    }

    private void setEmojiconFragment(boolean useSystemDefault) {
        this.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.emojicons, EmojiconsFragment.newInstance(useSystemDefault))
                .commit();
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

    public void getMessage(){
        RequestParams params = new RequestParams();
        String token = PreferencesUtil.getSharedStringData(ChatTeaActivity.this,"token");
        params.put("token", token);
        params.put("toUuid", email.getUserUuid());
        AsyncHttpClient client = new AsyncHttpClient();
        client.setConnectTimeout(5000);
        client.setTimeout(5000);
        String url_comment="http://192.168.10.168:8080/email-homework/api/message/getMessage";
        client.post(url_comment, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if (str != null) {
                    try {
                        JSONObject object = new JSONObject(str);
                        if (object.getString("code").equals("success")) {
                            mData.clear();
                            JSONArray array = object.getJSONArray("msg");
                            for (int a = 0; a < array.length(); a++) {
                            JSONObject item = array.getJSONObject(a);
                            String content = item.getString("content");
                            String createTime = item.getString("createTime");
                            String state = item.getString("type");
                            Message message = new Message(content, createTime, state);
                                Toast.makeText(ChatTeaActivity.this, state, Toast.LENGTH_SHORT).show();
                            if(state.equals("1")){
                                mData.add(new MsgModel(MsgModel.MSG_TO, message));
                            }else{
                                mData.add(new MsgModel(MsgModel.MSG_FORM, message));
                            }
                           }
                            myHandler.obtainMessage(1).sendToTarget();
                        } else {

                        }

                    } catch (JSONException e) {

                    }
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(ChatTeaActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void getNewMessage(){
        RequestParams params = new RequestParams();
        String token = PreferencesUtil.getSharedStringData(ChatTeaActivity.this,"token");
        params.put("token", token);
        params.put("formUuid", email.getUserUuid());
        AsyncHttpClient client = new AsyncHttpClient();
        client.setConnectTimeout(5000);
        client.setTimeout(5000);
        String url_comment="http://192.168.10.168:8080/email-homework/api/message/getNowMessage";
        client.post(url_comment, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if (str != null) {
                    try {
                        JSONObject object = new JSONObject(str);
                        if (object.getString("code").equals("success")) {
                            mData.clear();
                            String content = object.getString("content");
                            String createTime = object.getString("createTime");
                            Message message = new Message(content, createTime, "-1");
                            mData.add(new MsgModel(MsgModel.MSG_FORM, message));
                            myHandler.obtainMessage(3).sendToTarget();
                        } else {

                        }

                    } catch (JSONException e) {

                    }
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(ChatTeaActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void sendMsg(){
        RequestParams params = new RequestParams();
        String token = PreferencesUtil.getSharedStringData(ChatTeaActivity.this,"token");
        params.put("token", token);
        params.put("toUuid", email.getUserUuid());
        params.put("content", etContent.getText().toString());
        AsyncHttpClient client = new AsyncHttpClient();
        client.setConnectTimeout(5000);
        client.setTimeout(5000);
        String url_comment="http://192.168.10.168:8080/email-homework/api/message/addMessage";
        client.post(url_comment, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if (str != null) {
                    try {
                        JSONObject object = new JSONObject(str);
                        if (object.getString("code").equals("success")) {
                            String content = object.getString("content");
                            String createTime = object.getString("createTime");
                            Message message = new Message(content, createTime, "1");
                            mData.add(new MsgModel(MsgModel.MSG_TO, message));
                            //   }
                            myHandler.obtainMessage(3).sendToTarget();
                        } else {

                        }

                    } catch (JSONException e) {

                    }
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(ChatTeaActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_toolbar_menu, menu);
        return true;
    }

    private OnRecyclerviewItemClickListener onRecyclerviewItemClickListener = new OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
            //这里的view就是我们点击的view  position就是点击的position
            // final Intent intent = new Intent(ChatActivity.this, WorkContentActivity.class).putExtra("EMAIL", mData.get(position));
            //  startActivity(intent);
        }
    };

    private void hideKeyBorad(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }

    protected void onDestroy ( ) {
        if (timer != null) {
            timer.cancel( );
            timer = null;
        }
        super.onDestroy( );
    }

}

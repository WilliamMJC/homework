package com.hzu.feirty.HomeWork.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hzu.feirty.HomeWork.R;
import com.hzu.feirty.HomeWork.activity.funcation.AllCourseActivity;
import com.hzu.feirty.HomeWork.activity.funcation.AllSchoolActivity;
import com.hzu.feirty.HomeWork.activity.funcation.SendCourseActivity;
import com.hzu.feirty.HomeWork.activity.funcation.SendWorkActivity;
import com.hzu.feirty.HomeWork.activity.index.MyApplication;
import com.hzu.feirty.HomeWork.activity.information.ChatActivity;
import com.hzu.feirty.HomeWork.activity.mission.ChatTeaActivity;
import com.hzu.feirty.HomeWork.activity.mission.WorkContentActivity;
import com.hzu.feirty.HomeWork.adapter.ChatUserAdaper;
import com.hzu.feirty.HomeWork.adapter.MsAdapter;
import com.hzu.feirty.HomeWork.adapter.MsAdapter2;
import com.hzu.feirty.HomeWork.db.ChatUser;
import com.hzu.feirty.HomeWork.db.Email;
import com.hzu.feirty.HomeWork.entity.Ip;
import com.hzu.feirty.HomeWork.entity.OnRecyclerviewItemClickListener;
import com.hzu.feirty.HomeWork.utils.PreferencesUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/25 0025.
 */

public class InformationFragment extends Fragment {
    private RelativeLayout chat;
    private Toolbar toolbar;
    private TextView toolbarTv;
    private ArrayList<ChatUser> mData = new ArrayList<ChatUser>();
    private FunctionFragment.MyHandler handler1 = null;
    private String url3 = Ip.ip + "/HomeWork/DoGetType?";
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ChatUserAdaper recyclerAdapter;

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    swipeRefreshLayout.setRefreshing(false);
                    recyclerAdapter =  new ChatUserAdaper(getActivity(),mData,onRecyclerviewItemClickListener);
                    //recyclerAdapter = new MyAdapter(mData);
                    recyclerView.setAdapter(recyclerAdapter);
                    recyclerAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    swipeRefreshLayout.setRefreshing(false);
                    break;
                case 0:

            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_information, null);
        return view;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MyApplication.getInstance().addActivity(getActivity());
        toolbarTv = (TextView) getActivity().findViewById(R.id.toolbarTv04);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity.getSupportActionBar() != null){
            activity.getSupportActionBar().hide();
        }
        toolbarTv.setText("消息");
       /* chat = (RelativeLayout) getActivity().findViewById(R.id.chat_show);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ChatActivity.class));
            }
        });*/
        /**
         *  初始化swipeRefreshLayout
         */
        swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.refreshInformation);
        swipeRefreshLayout.setColorSchemeResources(R.color.blue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mData.clear();
               // getData();
                getChatUser();
            }
        });
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mData.clear();
                swipeRefreshLayout.setRefreshing(true);
                //getData();
                getChatUser();
                //receiveEmail();
            }
        });/**
         *  初始化recyclerView
         */
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerviewInformation);
        recyclerView.setLayoutManager(new LinearLayoutManager(InformationFragment.this.getActivity()));

    }



    public void getChatUser(){
        RequestParams params = new RequestParams();
        String token = PreferencesUtil.getSharedStringData(InformationFragment.this.getActivity(),"token");
        params.put("token",token);
        AsyncHttpClient client = new AsyncHttpClient();
        client.setConnectTimeout(50000);
        client.setTimeout(50000);
        String url2 = "http://192.168.10.168:8080/email-homework/api/message/getChatUserList";
        client.post(url2, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, org.apache.http.Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if (str != null) {
                    try {
                        JSONObject object = new JSONObject(str);
                        if (object.getString("code").equals("success")) {
                            mData.clear();
                            JSONArray array = object.getJSONArray("chatUser");
                            for (int a = 0; a < array.length(); a++) {
                                JSONObject item = array.getJSONObject(a);
                                ChatUser chatUser = new ChatUser();
                                chatUser.setUserUuid(item.getString("userUuid"));
                                chatUser.setUserName(item.getString("userName"));
                                chatUser.setLastMsgContext(item.getString("lastMessage"));
                                chatUser.setLastMsgTime(item.getString("createTime"));
                                chatUser.setUnReadNum(item.getString("unReadNum"));
                                mData.add(0, chatUser);
                            }
                            myHandler.obtainMessage(1).sendToTarget();
                        } else if (object.getString("code").equals("false")){
                            myHandler.obtainMessage(2).sendToTarget();
                            Toast.makeText(InformationFragment.this.getActivity(),"无消息",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(InformationFragment.this.getActivity(), "网络连接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
                myHandler.obtainMessage(2).sendToTarget();
            }
        });
    }


    private OnRecyclerviewItemClickListener onRecyclerviewItemClickListener = new OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
            //这里的view就是我们点击的view  position就是点击的position
            final Intent intent = new Intent(InformationFragment.this.getActivity(), ChatActivity.class).putExtra("user", mData.get(position));
            startActivity(intent);
        }
    };
    /**
     * 自己实现 Handler 处理消息更新UI
     *
     * @author mark
     */
    final public  class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:// 更新UI

                    break;
                case 0:

            }
        }
    }

    @Override
    public void onResume() {
        getChatUser();
        super.onResume();
    }
}

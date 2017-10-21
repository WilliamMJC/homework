package com.hzu.feirty.HomeWork.activity.main;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.hzu.feirty.HomeWork.R;
import com.hzu.feirty.HomeWork.activity.index.MailApplication;
import com.hzu.feirty.HomeWork.db.Email;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class ItemContentActivity extends AppCompatActivity {
    private TextView tv_addr, tv_mailsubject, tv_mailcontent,tv_mailattachment;
    private ArrayList<InputStream> attachmentsInputStreams;
    private Email email;
    private Handler handler;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mailcontent);
        toolbar = (Toolbar) findViewById(R.id.toolbar_item);
        toolbar.setTitle("作业详情");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        email = (Email) getIntent().getSerializableExtra("EMAIL");
        attachmentsInputStreams = ((MailApplication) getApplication()).getAttachmentsInputStreams();
        init();

    }
    private void init() {
        handler = new MyHandler(this);
        tv_addr = (TextView) findViewById(R.id.tv_addr);
        tv_mailsubject = (TextView) findViewById(R.id.tv_mailsubject);
        tv_mailcontent = (TextView) findViewById(R.id.tv_mailcontent);
        tv_mailattachment = (TextView) findViewById(R.id.tv_mailattachment);
        tv_addr.setText(email.getFrom());
        tv_mailsubject.setText(email.getSubject());
        tv_mailcontent.setText(email.getContent());
        tv_mailattachment.setText(email.getAttachment());
    }

    private static class MyHandler extends Handler {

        private WeakReference<ItemContentActivity> wrActivity;

        public MyHandler(ItemContentActivity activity) {
            this.wrActivity = new WeakReference<ItemContentActivity>(activity);
        }
        @Override
        public void handleMessage(android.os.Message msg) {
            final ItemContentActivity activity = wrActivity.get();
            switch (msg.what) {
                case 0:
                    Toast.makeText(activity.getApplicationContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }
}

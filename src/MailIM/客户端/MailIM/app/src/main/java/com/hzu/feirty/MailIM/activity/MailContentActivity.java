package com.hzu.feirty.MailIM.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.hzu.feirty.MailIM.R;
import com.hzu.feirty.MailIM.db.Email;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MailContentActivity extends AppCompatActivity {

    private TextView tv_addr, tv_mailsubject, tv_mailcontent,tv_mailattachment;
    private ListView lv_mailattachment;
    private ArrayList<InputStream> attachmentsInputStreams;
    private Email email;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mailcontent);
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
/*        if (email.getAttachments().size() > 0) {
            lv_mailattachment = (ListView) findViewById(R.id.lv_mailattachment);
            lv_mailattachment.setVisibility(View.VISIBLE);
            lv_mailattachment.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, email.getAttachments()));
        }*/
        tv_addr.setText(email.getFrom());
        tv_mailsubject.setText(email.getSubject());
        tv_mailcontent.setText(email.getContent());
        tv_mailattachment.setText(email.getAttachment());
    }

    private static class MyHandler extends Handler {

        private WeakReference<MailContentActivity> wrActivity;

        public MyHandler(MailContentActivity activity) {
            this.wrActivity = new WeakReference<MailContentActivity>(activity);
        }
        @Override
        public void handleMessage(android.os.Message msg) {
            final MailContentActivity activity = wrActivity.get();
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

package com.hzu.feirty.MailIM.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hzu.feirty.MailIM.R;

/**
 * Created by Administrator on 2017-6-27.
 */

public class CourseActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}

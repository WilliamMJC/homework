package com.hzu.feirty.HomeWork.activity.mycenter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.hzu.feirty.HomeWork.R;
import com.hzu.feirty.HomeWork.activity.MainActivityDemo;
import com.hzu.feirty.HomeWork.activity.index.AboutActivity;
import com.hzu.feirty.HomeWork.activity.index.LoginActivity;
import com.hzu.feirty.HomeWork.activity.index.MyApplication;
import com.hzu.feirty.HomeWork.activity.mission.WorkContentActivity;
import com.hzu.feirty.HomeWork.fragment.MyCenterFragment;
import com.hzu.feirty.HomeWork.utils.DataCleanManager;
import com.hzu.feirty.HomeWork.utils.PreferencesUtil;

/**
 * Created by Administrator on 2017-9-8.
 */

public class SetActivity extends AppCompatActivity {
    private LinearLayout clear;
    private LinearLayout about;
    private LinearLayout exit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        MyApplication.getInstance().addActivity(SetActivity.this);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("设置");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        clear = (LinearLayout) findViewById(R.id.clear);
        about =(LinearLayout) findViewById(R.id.about);
        exit = (LinearLayout) findViewById(R.id.out);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCleanManager.cleanApplicationData(SetActivity.this,new String[0]);
                Snackbar.make(clear,"清除缓存成功",Snackbar.LENGTH_SHORT).show();
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SetActivity.this,AboutActivity.class));
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new AlertDialog.Builder(SetActivity.this)
                        .setTitle("提示")
                        .setMessage("确认退出吗？")
                        //相当于点击确认按钮
                        .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                PreferencesUtil.setSharedBooleanData(SetActivity.this,"loginstate",false);
                                MyApplication.getInstance().exitApp();
                                Intent intent = new Intent(SetActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create();
                dialog.show();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

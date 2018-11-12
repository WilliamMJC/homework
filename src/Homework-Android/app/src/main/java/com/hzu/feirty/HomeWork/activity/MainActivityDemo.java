package com.hzu.feirty.HomeWork.activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import io.github.leibnik.gradualradiobar.GradualRadioGroup;
import com.hzu.feirty.HomeWork.R;
import com.hzu.feirty.HomeWork.activity.index.LoginActivity;
import com.hzu.feirty.HomeWork.activity.index.MyApplication;
import com.hzu.feirty.HomeWork.fragment.FunctionFragment;
import com.hzu.feirty.HomeWork.fragment.InformationFragment;
import com.hzu.feirty.HomeWork.fragment.MissionFragment;
import com.hzu.feirty.HomeWork.fragment.MyCenterFragment;
import com.hzu.feirty.HomeWork.utils.PreferencesUtil;

public class MainActivityDemo extends AppCompatActivity {
    private ViewPager viewPager;
    private long mExitTime;
    private GradualRadioGroup gradualRadioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean state = PreferencesUtil.getSharedBooleanData(MainActivityDemo.this,"loginstate");
        if(!state){
            startActivity(new Intent(MainActivityDemo.this, LoginActivity.class));
        }
        MyApplication.getInstance().addActivity(MainActivityDemo.this);
        setContentView(R.layout.activity_maindemo);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        gradualRadioGroup = (GradualRadioGroup) findViewById(R.id.radiobar);
        List<Fragment> list = new ArrayList<>();
        list.add(new MissionFragment());
        list.add(new FunctionFragment());
        list.add(new InformationFragment());
        list.add(new MyCenterFragment());
        viewPager.setAdapter(new DemoPagerAdapter(getSupportFragmentManager(), list));
        viewPager.setOffscreenPageLimit(2);
        gradualRadioGroup.setViewPager(viewPager);
    }
    class DemoPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> mData;
        public DemoPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        public DemoPagerAdapter(FragmentManager fm, List<Fragment> data) {
            super(fm);
            mData = data;
        }
        @Override
        public Fragment getItem(int position) {
            return mData.get(position);
        }
        @Override
        public int getCount() {
            return mData.size();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(MainActivityDemo.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            MyApplication.getInstance().exitApp();
            System.exit(0);
        }
    }
}

package com.hzu.feirty.HomeWork.activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import io.github.leibnik.gradualradiobar.GradualRadioGroup;
import com.hzu.feirty.HomeWork.R;
import com.hzu.feirty.HomeWork.fragment.FunctionFragment;
import com.hzu.feirty.HomeWork.fragment.MainFragment;
import com.hzu.feirty.HomeWork.fragment.MissionFragment;
import com.hzu.feirty.HomeWork.fragment.MyCenterFragment;

public class MainActivityDemo extends AppCompatActivity {
    private ViewPager viewPager;
    private GradualRadioGroup gradualRadioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maindemo);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        gradualRadioGroup = (GradualRadioGroup) findViewById(R.id.radiobar);
        List<Fragment> list = new ArrayList<>();
        list.add(new MainFragment());
        list.add(new MissionFragment());
        list.add(new FunctionFragment());
        list.add(new MyCenterFragment());
        viewPager.setAdapter(new DemoPagerAdapter(getSupportFragmentManager(), list));
        viewPager.setOffscreenPageLimit(3);
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
}

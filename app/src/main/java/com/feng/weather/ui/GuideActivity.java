package com.feng.weather.ui;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;

import com.feng.weather.R;

import java.util.ArrayList;
import java.util.List;

/**
 * author 丰
 * date   2018/12/12
 * desc
 */
public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,
        RadioGroup.OnCheckedChangeListener {

    private List<Fragment> fragments = new ArrayList<>();

    private ViewPager vpGuide;
    private RadioGroup rgIndicator;

    private FragmentPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        setStatusBar();

        vpGuide = findViewById(R.id.vp_guide);
        rgIndicator = findViewById(R.id.rg_indicator);

        fragments.add(new Fragment0());
        fragments.add(new Fragment1());
        fragments.add(new Fragment2());
        fragments.add(new Fragment3());

        adapter = new GuideFragmentPagerAdapter(getSupportFragmentManager());
        vpGuide.setAdapter(adapter);
        vpGuide.addOnPageChangeListener(this);
        rgIndicator.setOnCheckedChangeListener(this);
    }

    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                rgIndicator.check(R.id.rb_dot_1);
                break;
            case 1:
                rgIndicator.check(R.id.rb_dot_2);
                break;
            case 2:
                rgIndicator.check(R.id.rb_dot_3);
                break;
            case 3:
                rgIndicator.check(R.id.rb_dot_4);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_dot_1:
                vpGuide.setCurrentItem(0);
                break;
            case R.id.rb_dot_2:
                vpGuide.setCurrentItem(1);
                break;
            case R.id.rb_dot_3:
                vpGuide.setCurrentItem(2);
                break;
            case R.id.rb_dot_4:
                vpGuide.setCurrentItem(3);
                break;
        }
    }

    class GuideFragmentPagerAdapter extends FragmentPagerAdapter {
        public GuideFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}

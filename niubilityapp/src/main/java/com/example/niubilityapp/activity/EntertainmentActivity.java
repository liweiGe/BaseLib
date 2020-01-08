package com.example.niubilityapp.activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.niubilityapp.R;
import com.example.niubilityapp.fragment.JokeFragment;
import com.example.niubilityapp.fragment.NewsFragment;
import com.example.niubilityapp.fragment.ZhiHuFragment;
import com.google.android.material.tabs.TabLayout;
import com.kongzue.baseframework.BaseActivity;
import com.kongzue.baseframework.interfaces.DarkStatusBarTheme;
import com.kongzue.baseframework.interfaces.Layout;
import com.kongzue.baseframework.other.JumpParameter;

import java.util.ArrayList;
import java.util.List;

/**
 * 娱乐页面
 */
@Layout(R.layout.activity_entertainment)
@DarkStatusBarTheme(true)           //开启顶部状态栏图标、文字暗色模式
public class EntertainmentActivity extends BaseActivity {
    List<Fragment> fragments = new ArrayList<>();
    List<String> titles = new ArrayList<>();
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    public void initViews() {
        tabLayout = findViewById(R.id.tl_tabs);
        viewPager = findViewById(R.id.vp_content);


    }

    @Override
    public void initDatas(JumpParameter paramer) {

    }

    @Override
    public void setEvents() {
        fragments.add(NewsFragment.newInstance());
        fragments.add(JokeFragment.newInstance());
        fragments.add(ZhiHuFragment.newInstance());

        titles.add("网易新闻");
        titles.add("段子");
        titles.add("知乎精选");

        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }
        });
        tabLayout.setupWithViewPager(viewPager);
    }
}

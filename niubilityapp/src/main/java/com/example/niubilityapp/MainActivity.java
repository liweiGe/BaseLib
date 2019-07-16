package com.example.niubilityapp;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.niubilityapp.fragment.JokeFragment;
import com.example.niubilityapp.fragment.NewsFragment;
import com.example.niubilityapp.fragment.RvFragment;
import com.google.android.material.tabs.TabLayout;
import com.kongzue.baseframework.base.BaseActivity;
import com.kongzue.baseframework.interfaces.DarkStatusBarTheme;
import com.kongzue.baseframework.interfaces.Layout;
import com.kongzue.baseframework.util.JumpParameter;

import java.util.ArrayList;
import java.util.List;

@Layout(R.layout.activity_main)
@DarkStatusBarTheme(true)           //开启顶部状态栏图标、文字暗色模式
public class MainActivity extends BaseActivity {
    List<Fragment> fragments = new ArrayList<>();
    List<String> titles = new ArrayList<>();
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    public void initViews() {
        tabLayout = findViewById(R.id.tl_tabs);
        viewPager = findViewById(R.id.vp_content);

        fragments.add(RvFragment.newInstance());
        fragments.add(NewsFragment.newInstance());
        fragments.add(JokeFragment.newInstance());

        titles.add("电视直播列表");
        titles.add("网易新闻");
        titles.add("段子");
    }

    @Override
    public void initDatas(JumpParameter paramer) {

    }

    @Override
    public void setEvents() {
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

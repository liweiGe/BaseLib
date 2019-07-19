package com.example.niubilityapp;

import android.app.UiModeManager;
import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.niubilityapp.fragment.CCTVFragment;
import com.example.niubilityapp.fragment.CityTvFragment;
import com.example.niubilityapp.fragment.OtherTvFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.kongzue.baseframework.base.BaseActivity;
import com.kongzue.baseframework.interfaces.DarkStatusBarTheme;
import com.kongzue.baseframework.interfaces.Layout;
import com.kongzue.baseframework.util.activityParam.JumpParameter;

import java.util.ArrayList;
import java.util.List;

@Layout(R.layout.activity_main)
@DarkStatusBarTheme(true)           //开启顶部状态栏图标、文字暗色模式
public class MainActivity extends BaseActivity {
    List<Fragment> fragments = new ArrayList<>();
    List<String> titles = new ArrayList<>();
    TabLayout tabLayout;
    ViewPager viewPager;
    private NavigationView navigationView;

    @Override
    public void initViews() {
        tabLayout = findViewById(R.id.tl_tabs);
        viewPager = findViewById(R.id.vp_content);
        navigationView = findViewById(R.id.left_layout);

    }

    @Override
    public void initDatas(JumpParameter paramer) {

    }

    @Override
    public void setEvents() {
        fragments.add(CCTVFragment.newInstance());
        fragments.add(CityTvFragment.newInstance());
        fragments.add(OtherTvFragment.newInstance());

        titles.add("央视频道");
        titles.add("地方频道");
        titles.add("其他频道");
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

        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            switch (itemId) {
                case R.id.play:
                    break;
                case R.id.night:
                    final UiModeManager uiModeManager = (UiModeManager) getSystemService(Context.UI_MODE_SERVICE);
                    int nightMode = uiModeManager.getNightMode();
                    if (nightMode == UiModeManager.MODE_NIGHT_YES) {
                        uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
                    } else {
                        uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_YES);
                    }
                    break;
                case R.id.web_video1:
                    String url1 = "http://www.2myy.com";
                    WebActivity.start(this, url1);
                    break;
                case R.id.web_video2:
                    String url2 = "http://m.kkkkmao.com";
                    WebActivity.start(this, url2);
                    break;
                case R.id.web_video3:
                    String url3 = "http://m.gutime.net";
                    WebActivity.start(this, url3);
                    break;
                case R.id.web_video4:
                    String url4 = "http://m.yinyuetai.com";
                    WebActivity.start(this, url4);
                    break;
            }
            return false;
        });
    }
}

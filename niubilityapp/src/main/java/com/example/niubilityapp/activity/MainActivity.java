package com.example.niubilityapp.activity;

import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.niubilityapp.R;
import com.example.niubilityapp.fragment.CCTVFragment;
import com.example.niubilityapp.fragment.CityTvFragment;
import com.example.niubilityapp.fragment.OtherTvFragment;
import com.example.niubilityapp.live_TV.IntentKeys;
import com.example.niubilityapp.live_TV.LivePlayerActivity;
import com.example.niubilityapp.live_TV.LiveUrl;
import com.example.niubilityapp.live_TV.VideoBean;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.kongzue.baseframework.BaseActivity;
import com.kongzue.baseframework.interfaces.Layout;
import com.kongzue.baseframework.other.JumpParameter;

import java.util.ArrayList;
import java.util.List;

@Layout(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    List<Fragment> fragments = new ArrayList<>();
    List<String> titles = new ArrayList<>();
    TabLayout tabLayout;
    ViewPager viewPager;
    private NavigationView navigationView;
    private SearchView searchView;

    @Override
    public void initViews() {
        tabLayout = findViewById(R.id.tl_tabs);
        viewPager = findViewById(R.id.vp_content);
        navigationView = findViewById(R.id.left_layout);
        searchView = findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                goSearch(query);
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void goSearch(String query) {
        VideoBean hasTv = null;
        for (VideoBean videoBean : list) {
            if (videoBean.getTitle().contains(query)) {
                hasTv = videoBean;
            }
        }
        if (hasTv != null) {
            toastS("有该频道,立马跳转播放");
            VideoBean finalHasTv = hasTv;
            searchView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    skipLive(finalHasTv);
                }
            }, 1000);
        } else {
            toastS("未收录该频道");
        }
    }

    private void skipLive(VideoBean videoBean) {
        Intent intent = new Intent(this, LivePlayerActivity.class);
        intent.putExtra(IntentKeys.URL, videoBean.getUrl());
        intent.putExtra(IntentKeys.IS_LIVE, true);
        intent.putExtra(IntentKeys.TITLE, "直播");
        startActivity(intent);
    }

    ArrayList<VideoBean> list = new ArrayList<>(128);

    @Override
    public void initDatas(JumpParameter paramer) {
        List<VideoBean> cctvVideoList = LiveUrl.getCCTVVideoList();
        list.addAll(cctvVideoList);
        List<VideoBean> cityVideoList = LiveUrl.getCityVideoList();
        list.addAll(cityVideoList);
        List<VideoBean> otherVideoList = LiveUrl.getOtherVideoList();
        list.addAll(otherVideoList);
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
                    jump(EntertainmentActivity.class);
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
                case R.id.web_video5:
                    String url5 = "http://www.80ys.net";
                    WebActivity.start(this, url5);
                    break;
                case R.id.web_video6:
                    String url6 = "http://70bt.cn/";
                    WebActivity.start(this, url6);
                    break;
                case R.id.web_video7:
                    //http://so.448521.com/
                    String url7 = "http://so.448521.com";
                    WebActivity.start(this, url7);
                    break;
                case R.id.web_video8:
                    String url8 = "http://www.dy6080.com";
                    WebActivity.start(this, url8);
                    break;
            }
            return false;
        });
    }
}

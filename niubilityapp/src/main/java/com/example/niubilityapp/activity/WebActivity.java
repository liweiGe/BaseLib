package com.example.niubilityapp.activity;


import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.FrameLayout;

import com.example.niubilityapp.R;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.kongzue.baseframework.BaseActivity;
import com.kongzue.baseframework.interfaces.DarkStatusBarTheme;
import com.kongzue.baseframework.interfaces.Layout;
import com.kongzue.baseframework.interfaces.SwipeBack;
import com.kongzue.baseframework.other.JumpParameter;

@Layout(R.layout.activity_web)

@DarkStatusBarTheme(true)
@SwipeBack(true)
public class WebActivity extends BaseActivity {

    private AgentWeb mAgentWeb;
    public static final String KEY_URL = "URL";

    public static void start(Context context, String url) {
        Intent starter = new Intent(context, WebActivity.class);
        starter.putExtra(KEY_URL, url);
        context.startActivity(starter);
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onPause();
        }
    }

    @Override
    public void initViews() {
        ViewGroup webContent = findViewById(R.id.web_content);
        String url = getIntent().getStringExtra(KEY_URL);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(webContent,
                        new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator()
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
//                .setWebChromeClient(mWebChromeClient)
                .createAgentWeb()
                .ready()
                .go(url);

        mAgentWeb.getAgentWebSettings().getWebSettings().setLoadWithOverviewMode(true);
        mAgentWeb.getAgentWebSettings().getWebSettings().setUseWideViewPort(true);

        mAgentWeb.getAgentWebSettings().getWebSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }




    @Override
    public void initDatas(JumpParameter paramer) {

    }

    @Override
    public void setEvents() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAgentWeb != null) {
            mAgentWeb.destroy();
            mAgentWeb.getWebLifeCycle().onDestroy();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mAgentWeb.handleKeyEvent(keyCode, event) || super.onKeyDown(keyCode, event);
    }

}

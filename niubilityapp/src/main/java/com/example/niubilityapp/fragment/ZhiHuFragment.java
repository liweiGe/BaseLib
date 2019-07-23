package com.example.niubilityapp.fragment;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.niubilityapp.R;
import com.example.niubilityapp.adapter.NewsAdapter;
import com.example.niubilityapp.http.HttpApi;
import com.example.niubilityapp.model.NewsBean;
import com.kongzue.baseframework.interfaces.Layout;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.List;

@Layout(R.layout.fragment_list_view)
public class ZhiHuFragment extends ListFragment {

    public static Fragment newInstance() {
        return new ZhiHuFragment();
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new NewsAdapter();
    }

    @Override
    protected void httpNet(boolean isRefresh) {
        EasyHttp.get(HttpApi.wangyi)

                .execute(new SimpleCallBack<List<NewsBean>>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(List<NewsBean> newsBeans) {

                    }
                });
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
}

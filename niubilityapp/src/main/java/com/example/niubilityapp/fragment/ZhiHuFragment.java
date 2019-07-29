package com.example.niubilityapp.fragment;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.niubilityapp.R;
import com.example.niubilityapp.adapter.NewsAdapter;
import com.kongzue.baseframework.interfaces.Layout;

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

//        EasyHttp.get(HttpApi.api_news)
//                .baseUrl(HttpApi.base_url)
//                .params("type","0")
//                .params("page","20")
//                .execute(new SimpleCallBack<Object>() {
//                    @Override
//                    public void onError(ApiException e) {
//
//                    }
//
//                    @Override
//                    public void onSuccess(Object o) {
//
//                    }
//                });

    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
}

package com.example.niubilityapp.fragment;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.niubilityapp.R;
import com.example.niubilityapp.activity.WebActivity;
import com.example.niubilityapp.adapter.NewsAdapter;
import com.example.niubilityapp.http.HttpApi;
import com.example.niubilityapp.model.JokeBean;
import com.kongzue.baseframework.interfaces.Layout;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.List;

@Layout(R.layout.fragment_list_view)
public class NewsFragment extends ListFragment<JokeBean> {

    public static Fragment newInstance() {
        return new NewsFragment();
    }

    @Override
    protected BaseQuickAdapter<JokeBean, BaseViewHolder> getAdapter() {
        return new NewsAdapter();
    }

    @Override
    protected void httpNet(boolean isRefresh) {
        EasyHttp.get(HttpApi.news)
                .params("page", index + "")
                .params("count", "20")
                .cacheKey("wangyi_news")
                .execute(new SimpleCallBack<List<JokeBean>>() {
                    @Override
                    public void onError(ApiException e) {
                        swipe.setRefreshing(false);
                    }

                    @Override
                    public void onSuccess(List<JokeBean> jokeBeans) {
                        if (isRefresh) {
                            adapter.setNewData(jokeBeans);
                        } else {
                            adapter.addData(jokeBeans);
                            adapter.loadMoreComplete();
                        }
                        index++;
                        swipe.setRefreshing(false);
                    }
                });
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        JokeBean bean = (JokeBean) adapter.getData().get(position);
        WebActivity.start(me, bean.path);
    }
}

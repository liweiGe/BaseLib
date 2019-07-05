package com.example.niubilityapp.fragment;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.niubilityapp.R;
import com.example.niubilityapp.WebActivity;
import com.example.niubilityapp.adapter.NewsAdapter;
import com.example.niubilityapp.http.HttpApi;
import com.example.niubilityapp.model.JokeBean;
import com.kongzue.baseframework.BaseFragment;
import com.kongzue.baseframework.interfaces.Layout;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.List;

@Layout(R.layout.fragment_list_view)
public class NewsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private SwipeRefreshLayout swipe;

    public static Fragment newInstance() {
        return new NewsFragment();
    }

    @Override
    public void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        newsAdapter = new NewsAdapter();
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        swipe.setRefreshing(true);
    }

    int index = 1;

    @Override
    public void initDatas() {
        getDate(true);
    }

    private void getDate(boolean isRefresh) {
        if (isRefresh) {
            index = 1;
        }
        EasyHttp.get(HttpApi.news)
                .params("page", index + "")
                .params("count", "8")
                .execute(new SimpleCallBack<List<JokeBean>>() {
                    @Override
                    public void onError(ApiException e) {
                        swipe.setRefreshing(false);
                    }

                    @Override
                    public void onSuccess(List<JokeBean> jokeBeans) {
                        if (isRefresh) {
                            newsAdapter.setNewData(jokeBeans);
                        } else {
                            newsAdapter.addData(jokeBeans);
                            newsAdapter.loadMoreComplete();
                        }
                        index++;
                        swipe.setRefreshing(false);

                    }
                });
    }

    @Override
    public void setEvents() {
        recyclerView.setAdapter(newsAdapter);
        swipe.setOnRefreshListener(this);
        newsAdapter.setOnLoadMoreListener(this, recyclerView);
        newsAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onRefresh() {
        getDate(true);
    }

    @Override
    public void onLoadMoreRequested() {
        getDate(false);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        JokeBean bean = newsAdapter.getData().get(position);
        WebActivity.start(me, bean.path);
    }
}

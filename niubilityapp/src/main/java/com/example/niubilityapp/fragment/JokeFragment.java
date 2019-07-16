package com.example.niubilityapp.fragment;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.niubilityapp.R;
import com.example.niubilityapp.adapter.JokeAdapter;
import com.example.niubilityapp.http.HttpApi;
import com.example.niubilityapp.model.DuanziBean;
import com.kongzue.baseframework.base.BaseFragment;
import com.kongzue.baseframework.interfaces.Layout;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.List;

@Layout(R.layout.fragment_list_view)
public class JokeFragment extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener {

    private RecyclerView recyclerView;
    private JokeAdapter newsAdapter;
    private SwipeRefreshLayout swipe;

    public static Fragment newInstance() {
        return new JokeFragment();
    }

    @Override
    public void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        newsAdapter = new JokeAdapter();
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
        EasyHttp.get(HttpApi.joke)
                .params("type", "text")
                .params("page", index + "")
                .params("count", "10")
                .execute(new SimpleCallBack<List<DuanziBean>>() {
                    @Override
                    public void onError(ApiException e) {
                        swipe.setRefreshing(false);
                    }

                    @Override
                    public void onSuccess(List<DuanziBean> jokeBeans) {
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
    }

    @Override
    public void onRefresh() {
        getDate(true);
    }

    @Override
    public void onLoadMoreRequested() {
        getDate(false);
    }


}

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
import com.zhouyou.http.cache.model.CacheResult;
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
        //网络缓存的使用,真香:几个点注意,一个是缓存模式设置,第二个是cacheKey的设置
        EasyHttp.get(HttpApi.joke)
                .params("type", "text")
                .params("page", index + "")
                .cacheKey("weige")
                .params("count", "10")
                .execute(new SimpleCallBack<CacheResult<List<DuanziBean>>>() {
                    @Override
                    public void onError(ApiException e) {
                        swipe.setRefreshing(false);
                    }

                    @Override
                    public void onSuccess(CacheResult<List<DuanziBean>> listCacheResult) {
                        boolean isFromCache = listCacheResult.isFromCache;

                        if (isRefresh) {
                            newsAdapter.setNewData(listCacheResult.data);
                        } else {
                            newsAdapter.addData(listCacheResult.data);
                            newsAdapter.loadMoreComplete();
                        }
                        if (isFromCache) {
                            toastS("来自缓存");
                            newsAdapter.setEnableLoadMore(false);
                        }else {
                            newsAdapter.setEnableLoadMore(true);
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

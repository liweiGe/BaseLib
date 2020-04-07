package com.example.niubilityapp.fragment;

import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.niubilityapp.R;
import com.kongzue.baseframework.BaseFragment;
import com.kongzue.baseframework.interfaces.Layout;

@Layout(R.layout.fragment_list_view)
public abstract class ListFragment<T> extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    public RecyclerView recyclerView;
    public BaseQuickAdapter<T, BaseViewHolder> adapter;
    public SwipeRefreshLayout swipe;

//    public abstract static Fragment newInstance();

    @Override
    public void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.rv);
//        newsAdapter = new JokeAdapter();
        adapter = getAdapter();
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        swipe.setRefreshing(true);
    }

    protected abstract BaseQuickAdapter<T,BaseViewHolder> getAdapter();

    int index = 1;

    @Override
    public void initDatas() {
        getDate(true);
    }

    private void getDate(boolean isRefresh) {
        if (isRefresh) {
            index = 1;
        }
        httpNet(isRefresh);
        //网络缓存的使用,真香:几个点注意,一个是缓存模式设置,第二个是cacheKey的设置
//        EasyHttp.get(HttpApi.joke)
//                .params("type", "text")
//                .params("page", index + "")
//                .cacheKey("duanzi")
//                .params("count", "10")
//                .execute(new SimpleCallBack<CacheResult<List<DuanziBean>>>() {
//                    @Override
//                    public void onError(ApiException e) {
//                        swipe.setRefreshing(false);
//                    }
//
//                    @Override
//                    public void onSuccess(CacheResult<List<DuanziBean>> listCacheResult) {
//                        if (isRefresh) {
//                            newsAdapter.setNewData(listCacheResult.data);
//                        } else {
//                            newsAdapter.addData(listCacheResult.data);
//                            newsAdapter.loadMoreComplete();
//                        }
//
//                        index++;
//                        swipe.setRefreshing(false);
//                    }
//                });
    }

    protected abstract void httpNet(boolean isRefresh);

    @Override
    public void setEvents() {
        recyclerView.setAdapter(adapter);
        swipe.setOnRefreshListener(this);
        adapter.setOnLoadMoreListener(this, recyclerView);
        adapter.setOnItemClickListener(this);
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

    }
}

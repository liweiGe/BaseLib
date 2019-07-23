package com.example.niubilityapp.fragment;

import androidx.fragment.app.Fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.niubilityapp.R;
import com.example.niubilityapp.adapter.JokeAdapter;
import com.example.niubilityapp.http.HttpApi;
import com.example.niubilityapp.model.DuanziBean;
import com.kongzue.baseframework.interfaces.Layout;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.model.CacheResult;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.List;

@Layout(R.layout.fragment_list_view)
public class JokeFragment extends ListFragment {

    public static Fragment newInstance() {
        return new JokeFragment();
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new JokeAdapter();
    }

    @Override
    protected void httpNet(boolean isRefresh) {
//网络缓存的使用,真香:几个点注意,一个是缓存模式设置,第二个是cacheKey的设置
        EasyHttp.get(HttpApi.joke)
                .params("type", "text")
                .params("page", index + "")
                .cacheKey("duanzi")
                .params("count", "10")
                .execute(new SimpleCallBack<CacheResult<List<DuanziBean>>>() {
                    @Override
                    public void onError(ApiException e) {
                        swipe.setRefreshing(false);
                    }

                    @Override
                    public void onSuccess(CacheResult<List<DuanziBean>> listCacheResult) {
                        if (isRefresh) {
                            adapter.setNewData(listCacheResult.data);
                        } else {
                            adapter.addData(listCacheResult.data);
                            adapter.loadMoreComplete();
                        }

                        index++;
                        swipe.setRefreshing(false);
                    }
                });
    }
}

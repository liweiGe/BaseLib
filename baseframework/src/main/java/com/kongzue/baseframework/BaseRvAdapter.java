package com.kongzue.baseframework;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * 省去viewholder绑定数据到view更直白
 *
 * @param <T>
 */
public abstract class BaseRvAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

    public BaseRvAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {
        bindView(helper, item);
    }

    protected abstract void bindView(BaseViewHolder helper, T item);
}

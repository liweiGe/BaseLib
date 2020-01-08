package com.example.niubilityapp.adapter;


import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.niubilityapp.R;
import com.example.niubilityapp.model.JokeBean;
import com.kongzue.baseframework.BaseRvAdapter;

public class NewsAdapter extends BaseRvAdapter<JokeBean> {

    public NewsAdapter() {
        super(R.layout.item_news_layout);
    }

    @Override
    protected void bindView(BaseViewHolder helper, JokeBean item) {
        ImageView imageView = helper.getView(R.id.new_image);
        Glide.with(imageView).load(item.image).into(imageView);
        helper.setText(R.id.new_title, item.title);
    }
}

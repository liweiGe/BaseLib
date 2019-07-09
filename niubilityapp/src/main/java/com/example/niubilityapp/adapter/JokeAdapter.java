package com.example.niubilityapp.adapter;


import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.niubilityapp.R;
import com.example.niubilityapp.model.DuanziBean;

public class JokeAdapter extends BaseQuickAdapter<DuanziBean, BaseViewHolder> {

    public JokeAdapter() {
        super(R.layout.item_just_text_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, DuanziBean item) {
        ImageView imageView = helper.getView(R.id.user_image);
        Glide.with(imageView).load(item.header).into(imageView);
        helper.setText(R.id.new_title, item.text);
        helper.setText(R.id.user_name,item.name);
    }
}

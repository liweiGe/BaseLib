package com.example.niubilityapp.fragment;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.niubilityapp.R;
import com.example.niubilityapp.live_TV.LiveAdapter;
import com.example.niubilityapp.live_TV.LiveUrl;
import com.kongzue.baseframework.base.BaseFragment;
import com.kongzue.baseframework.interfaces.Layout;

@Layout(R.layout.activity_recycler_view)
public class CCTVFragment extends BaseFragment {

    private RecyclerView recyclerView;

    public static Fragment newInstance() {
        return new CCTVFragment();
    }

    @Override
    public void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.rv);

    }

    @Override
    public void initDatas() {
        recyclerView.setAdapter(new LiveAdapter(LiveUrl.getCCTVVideoList(), me));
    }

    @Override
    public void setEvents() {

    }
}

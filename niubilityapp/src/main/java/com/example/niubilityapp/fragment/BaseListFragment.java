package com.example.niubilityapp.fragment;

import androidx.recyclerview.widget.RecyclerView;

import com.example.niubilityapp.R;
import com.kongzue.baseframework.BaseFragment;
import com.kongzue.baseframework.interfaces.Layout;

@Layout(R.layout.activity_recycler_view)
public abstract class BaseListFragment extends BaseFragment {

    public RecyclerView recyclerView;



    @Override
    public void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.rv);

    }



    @Override
    public void setEvents() {

    }

}

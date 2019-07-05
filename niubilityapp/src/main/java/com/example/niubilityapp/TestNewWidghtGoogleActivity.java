package com.example.niubilityapp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.ChipGroup;
import com.google.android.material.internal.FlowLayout;

import java.util.ArrayList;
import java.util.List;

public class TestNewWidghtGoogleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_new_widght_google);
        FlowLayout layout = findViewById(R.id.flowlayout);
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String s = "item" + i;
            strings.add(s);
        }
        showTagView(layout, strings);
        // TODO: 2019-07-04 傻屌谷歌,用这个控件还得必须配着theme才可以用,不然就崩了直接
        // https://www.jianshu.com/p/d64a75ec7c74
        ChipGroup chipGroup = findViewById(R.id.chip_group);

        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, @IdRes int checkedId) {
                String hintStr = " ";
                switch (checkedId) {
                    case R.id.chipInGroup2_1:
                        hintStr = "被选中的是 chipInGroup2_1 ";
                        break;
                    case R.id.chipInGroup2_2:
                        hintStr = "被选中的是 chipInGroup2_2 ";
                        break;
                    case R.id.chipInGroup2_3:
                        hintStr = "被选中的是 chipInGroup2_3 ";
                        break;
                    default:
                        hintStr = "没有选中任何chip";
                        break;
                }
                Toast.makeText(TestNewWidghtGoogleActivity.this, hintStr, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showTagView(FlowLayout flowLayout, final List<String> beanList) {
        flowLayout.removeAllViews();
        for (int i = 0; i < beanList.size(); i++) {
            TextView textView = new TextView(flowLayout.getContext());
            textView.setText(beanList.get(i));
            flowLayout.addView(textView);
        }
    }
}

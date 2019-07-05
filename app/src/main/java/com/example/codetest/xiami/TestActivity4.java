package com.example.codetest.xiami;


import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codetest.R;
import com.example.codetest.mainpage.TypeAdapter;

import java.util.ArrayList;
import java.util.List;

public class TestActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test4);

        RecyclerView commentList = findViewById(R.id.comment_list);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i + 1 + "");
        }
        TypeAdapter adapter = new TypeAdapter(this, list, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        commentList.setLayoutManager(layoutManager);
        commentList.setAdapter(adapter);
    }
}

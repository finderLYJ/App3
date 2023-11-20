package com.example.app3.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.app3.R;
import com.example.app3.adapter.LVAdapter1;
import com.example.app3.adapter.LVAdapter2;
import com.example.app3.bean.Item2;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class NewsActivity extends AppCompatActivity {
    private List<Item2> data;
    private ListView listView;
    private LVAdapter2 adapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        listView=findViewById(R.id.news_list);
        data=new ArrayList<>();
        Intent intent=getIntent();
        String title=intent.getStringExtra("title");
        String content=intent.getStringExtra("content");
        int img=intent.getIntExtra("img",0);
        data.add(new Item2(title,content,img));
        adapter2=new LVAdapter2(data,this);
        listView.setAdapter(adapter2);
    }
}
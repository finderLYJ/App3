package com.example.app3.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.app3.R;
import com.example.app3.adapter.MyAdapter;
import com.example.app3.bean.Word;
import com.example.app3.helper.MyDBhelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowDatabaseActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private List<Word> data;
    private ListView lv_info;
    private MyAdapter adapter;
    private MyDBhelper myDBhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_database);
        myDBhelper=new MyDBhelper(ShowDatabaseActivity.this,"trahistory.db",null,1);
        lv_info=findViewById(R.id.lv_info);
        data = myDBhelper.queryTraHistory();
        adapter=new MyAdapter(data,ShowDatabaseActivity.this);
        lv_info.setAdapter(adapter);
        lv_info.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ListView listView = (ListView) parent;
        Word word= (Word) listView.getItemAtPosition(position);
        String personid = word.getWord();
        Intent intent =  new Intent(ShowDatabaseActivity.this,VoicetraActivity.class);
        intent.putExtra("index",personid);
        startActivity(intent);
    }
}
package com.example.app3.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app3.R;
import com.example.app3.activity.VideoPlayActivity;
import com.example.app3.adapter.MyFragmentAdapter;
import com.example.app3.bean.Item1;

import java.util.ArrayList;
import java.util.List;


public class Fragment1 extends Fragment {
    private MyFragmentAdapter myAdapter;
    private ListView listView;
    private List<Item1> list=new ArrayList<>();
    private String[] titles={"送你一朵小红花 手语舞","星辰大海手语舞|让我们一起奔向星辰与大海","好想爱这个世界啊（手语版）",
            "看我们聋人用手语如何诠释送你一朵小红花的","五月天《玫瑰少年》手语版～ 生而无罪 做你自己","《国家》手语舞教学"};
    private int[] imgs={R.mipmap.list1_1,R.mipmap.list1_4,R.mipmap.list1_3,R.mipmap.list1_2,R.mipmap.list1_5,R.mipmap.list1_6};
    private String uri;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout1,null);
        listView=view.findViewById(R.id.listView1);
        for(int i=0;i<titles.length;i++){
            list.add(new Item1(titles[i],imgs[i]));
        }
        myAdapter=new MyFragmentAdapter(list,getActivity());
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getActivity(), VideoPlayActivity.class);
                switch(i){
                    case 0:
                        uri="android.resource://"+getActivity().getPackageName()+"/"+R.raw.shouyuwu1;
                        break;
                    case 1:
                        uri="android.resource://"+getActivity().getPackageName()+"/"+R.raw.shouyuwu2;
                        break;
                }
                intent.putExtra("uri",uri);
                intent.putExtra("title","兴趣培养");
                startActivity(intent);
            }
        });
        return view;
    }
}

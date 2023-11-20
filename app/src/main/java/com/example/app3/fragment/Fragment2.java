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
import com.example.app3.bean.Item2;

import java.util.ArrayList;
import java.util.List;


public class Fragment2 extends Fragment {
    private MyFragmentAdapter myAdapter;
    private ListView listView;
    private List<Item1> list=new ArrayList<>();
    private String uri;
    private String[] titles={"《一起向未来》冬奥会和冬残奥会主题口号推广歌曲手语表演鉴赏","手语微课堂第二期|日常手语教学及场景表演鉴赏",
            "《当你老了》杜银铃手语音乐鉴赏","【大鱼海棠】印象曲《大鱼》手语音乐鉴赏","【风犬少年的天空】同名主题曲“风犬少年的天空”手语版鉴赏","《声声慢》手语音乐欣赏 小透明制作"};
    private int[] imgs={R.mipmap.list2_1,R.mipmap.list2_2,R.mipmap.list2_3,R.mipmap.list2_4,R.mipmap.list2_5,R.mipmap.list2_6};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout2,null);
        listView=view.findViewById(R.id.listView2);
        for(int i=0;i<titles.length;i++){
            list.add(new Item1(titles[i],imgs[i]));
        }
        myAdapter=new MyFragmentAdapter(list,getActivity());
        listView.setAdapter(myAdapter);
        //uri="android.resource://"+getActivity().getPackageName()+"/"+R.raw.math1;
        uri="https://haokan.baidu.com/v?pd=wisenatural&vid=6010409228019705725";
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getActivity(), VideoPlayActivity.class);
                intent.putExtra("uri",uri);
                intent.putExtra("title","素质教育");
                startActivity(intent);
            }
        });
        return view;
    }
}


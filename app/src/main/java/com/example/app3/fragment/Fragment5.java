package com.example.app3.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.app3.R;
import com.example.app3.activity.VideoPlayActivity;
import com.example.app3.adapter.MyFragmentAdapter1;
import com.example.app3.bean.Item1;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment5#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment5 extends Fragment implements AdapterView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private GridView gv;
    private List<Item1> data;
    private MyFragmentAdapter1 adapter;
    private String[] titles={"身边的手语--零基础手语入门·数字手语教学（三）","常用的称谓手语怎么说","手语|有关冬奥会的手语表达","【教程】手语基础教学"};
    private int[] imgs={R.drawable.grid1,R.drawable.grid2,R.drawable.grid3,R.drawable.grid4};
    private int[] uris={R.raw.grid1,R.raw.grid2,R.raw.grid3,R.raw.grid4};
    public Fragment5() {
        // Required empty public constructor
    }
    public static Fragment5 newInstance(String param1, String param2) {
        Fragment5 fragment = new Fragment5();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.layout5, container, false);
        initView(view);
        return view;
    }
    private void initView(View view){
        gv=view.findViewById(R.id.func_grid);
        data=new ArrayList<>();
        for(int i=0;i<titles.length;i++){
            data.add(new Item1(titles[i],imgs[i]));
        }
        adapter=new MyFragmentAdapter1(data,getContext());
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent=new Intent(getActivity(), VideoPlayActivity.class);
        String url;
        url="android.resource://"+getActivity().getPackageName()+"/"+uris[i];
        intent.putExtra("uri",url);
        intent.putExtra("title","手语学堂");
        startActivity(intent);
    }
}
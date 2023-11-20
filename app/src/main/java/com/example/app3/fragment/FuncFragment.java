package com.example.app3.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.app3.R;
import com.example.app3.activity.LuntanActivity;
import com.example.app3.activity.SigntraActivity;
import com.example.app3.activity.StudyActivity;
import com.example.app3.activity.UsualTextActivity;
import com.example.app3.activity.VideoPlayActivity;
import com.example.app3.activity.VoicetraActivity;
import com.example.app3.adapter.LVAdapter;
import com.example.app3.bean.Item;
import com.example.app3.utils.myAddTab;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FuncFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FuncFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView listView;
    private LVAdapter lvAdapter;
    private List<Item> list;//数据源
    private EditText search_et;
    private ImageView searchbtn;
    private ImageView signtra,voicetra,study;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<String> titles;
    private List<Fragment> fragments;
    private String text;
    //private final int[] tubiaos={R.drawable.signlag,R.drawable.voicetra,R.drawable.text,R.drawable.study,R.drawable.luntan};
    //private final String[] titles={"手语翻译","语音转义","常用文字","学习专区","家长论坛"};
    //private final int arrow=R.drawable.right_arrow;
    public FuncFragment() {
        // Required empty public constructor
    }

    public static FuncFragment newInstance(String param1, String param2) {
        FuncFragment fragment = new FuncFragment();
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
        View view=inflater.inflate(R.layout.fragment_func, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        mTabLayout=view.findViewById(R.id.func_tablayout);
        mViewPager=view.findViewById(R.id.func_vp);

        titles=new ArrayList<>();
        titles.add("手语学堂");//手语舞，手语操
        titles.add("常用文字");

        fragments=new ArrayList<>();
        fragments.add(new Fragment5());
        fragments.add(new Fragment6());

        myAddTab.addTab(mTabLayout, mViewPager, fragments, titles, getActivity().getSupportFragmentManager());

        signtra=view.findViewById(R.id.signtra);
        voicetra=view.findViewById(R.id.voicetra);
        study=view.findViewById(R.id.study);

        signtra.setOnClickListener(this);
        voicetra.setOnClickListener(this);
        study.setOnClickListener(this);

        search_et=view.findViewById(R.id.search_et);
        searchbtn=view.findViewById(R.id.search_iv);

        searchbtn.setOnClickListener(this);
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //i：表示被点击的项在适配器中的位置，即对应数据源的索引位置
                //点击不同的条目跳转到相应的页面
                Intent intent=new Intent();
                switch (i){
                    case 0:
                        intent.setClass(getActivity(), SigntraActivity.class);
                        break;
                    case 1:
                        intent.setClass(getActivity(), VoicetraActivity.class);
                        break;
                    case 2:
                        intent.setClass(getActivity(), UsualTextActivity.class);
                        break;
                    case 3:
                        intent.setClass(getActivity(), StudyActivity.class);
                        break;
                    case 4:
                        intent.setClass(getActivity(), LuntanActivity.class);
                        break;
                }
                startActivity(intent);
            }
        });*/
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent();
        text=search_et.getText().toString().trim();
        String uri="";
        switch (view.getId()){
            case R.id.signtra:
                intent.setClass(getActivity(),SigntraActivity.class);
                break;
            case R.id.voicetra:
                intent.setClass(getActivity(),VoicetraActivity.class);
                break;
            case R.id.study:
                intent.setClass(getActivity(),StudyActivity.class);
                break;
            case R.id.search_iv:
                Log.e("MY",text);
                intent.setClass(getActivity(), VideoPlayActivity.class);
                switch(text){
                    case "你好":
                        uri="android.resource://"+getActivity().getPackageName()+"/"+R.raw.hello;
                        break;
                    case "喝茶":
                        uri="android.resource://"+getActivity().getPackageName()+"/"+R.raw.drink;
                        break;
                    case "早上好":
                        uri = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.goodmorning;
                        break;
                    case "朋友":
                        uri = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.friend;
                        break;
                    case "漂亮":
                        uri = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.beautiful;
                        break;
                    case "谢谢":
                        uri = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.thanks;
                        break;
                }
                intent.putExtra("uri",uri);
                break;
        }
        startActivity(intent);
    }
}
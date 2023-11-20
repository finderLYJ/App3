package com.example.app3.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.app3.CircleImageView;
import com.example.app3.R;
import com.example.app3.activity.AboutUsActivity;
import com.example.app3.activity.ChoseModifyActivity;
import com.example.app3.activity.HelpActivity;
import com.example.app3.activity.InitActivity;
import com.example.app3.adapter.LVAdapter;
import com.example.app3.bean.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private final int[] tubiaos={R.drawable.func_list1,R.drawable.func_list2,R.drawable.func_list3,R.drawable.func_list4,R.drawable.func_list6,R.drawable.func_list5};
    private final int arrow=R.drawable.baseline_arrow_forward_ios_24;
    private final String[] titles={"我的消息","我的喜欢","我的收藏","历史记录","使用帮助","意见反馈"};
    private ListView listView;
    private LVAdapter lvAdapter;
    private List<Item> list;//数据源
    private Toolbar toolbar;
    private RelativeLayout blank;
    public MyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyFragment newInstance(String param1, String param2) {
        MyFragment fragment = new MyFragment();
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
        setHasOptionsMenu(true);//加上这句话，menu才会显示出来
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_my, container, false);
        initView(view);
        return view;
    }
    private void initView(View view) {
        list=new ArrayList<>();
        for(int i=0;i<titles.length;i++){
            list.add(new Item(tubiaos[i],arrow,titles[i]));
        }

        blank=view.findViewById(R.id.blank);
        blank.setOnClickListener(this);
        toolbar=view.findViewById(R.id.my_toolbar);
        toolbar.setTitle("");
        //设置toolbar对象
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new androidx.appcompat.widget.Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.logout:
                        showDialog();
                }
                return false;
            }
        });
        listView=view.findViewById(R.id.my_list);
        lvAdapter=new LVAdapter(list,getContext());
        listView.setAdapter(lvAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //i：表示被点击的项在适配器中的位置，即对应数据源的索引位置
                //点击不同的条目跳转到相应的页面
                Intent intent=new Intent();
                switch (i){
                    case 0:
                        intent.setClass(getActivity(), HelpActivity.class);
                        break;
                    case 1:
                        intent.setClass(getActivity(), AboutUsActivity.class);
                        break;
                    case 2:
                        intent.setClass(getActivity(), AboutUsActivity.class);
                        break;
                    case 3:
                        intent.setClass(getActivity(), AboutUsActivity.class);
                        break;
                    case 4:
                        intent.setClass(getActivity(), HelpActivity.class);
                        break;
                    case 5:
                        intent.setClass(getActivity(), AboutUsActivity.class);
                        break;
                }
                startActivity(intent);
            }
        });
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_my_logout,menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.logout:
                showDialog();
        }
        return true;
    }
    private void showDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle("退出登录");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                logout();
                getActivity().finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //dialog.dismiss();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }
    private void logout(){
        Intent intent=new Intent(getActivity(), InitActivity.class);
        startActivity(intent);
        //获取SharedPreferences对象
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("data",MODE_PRIVATE);
        //获取Editor对象的引用
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //将获取过来的值放入文件
        editor.remove("login");
        // 提交数据
        editor.commit();
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent();
        switch(view.getId()){
            case R.id.blank:
                intent.setClass(getContext(),ChoseModifyActivity.class);
                break;
        }
        startActivity(intent);
    }
}
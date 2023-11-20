package com.example.app3.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.app3.R;
import com.example.app3.activity.AddSomethingActivity;
import com.example.app3.activity.UsualTextActivity;
import com.example.app3.activity.VideoPlayActivity;
import com.example.app3.adapter.MyAdapter;
import com.example.app3.bean.Word;
import com.example.app3.helper.MyDBhelper;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment6#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment6 extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MyAdapter adapter;
    private List<Word> list;
    private ImageView add;
    private MyDBhelper myDBhelper;
    private ListView listView;
    public Fragment6() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment6.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment6 newInstance(String param1, String param2) {
        Fragment6 fragment = new Fragment6();
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
        View view=inflater.inflate(R.layout.layout6, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        myDBhelper=new MyDBhelper(getActivity(),"word.db",null,1);
        add=view.findViewById(R.id.add_btn);
        listView=view.findViewById(R.id.func_usual_list);

        refresh();

        add.setOnClickListener(this);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(getActivity(), AddSomethingActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String text,uri="";
        Word item=(Word) listView.getItemAtPosition(i);
        text=item.getWord().trim();
        switch (text){
            case "你好":
                uri= "android.resource://" + getActivity().getPackageName() + "/" + R.raw.hello;
                break;
            case "喝茶":
                uri= "android.resource://" + getActivity().getPackageName() + "/" + R.raw.drink;
                break;
            case"早上好":
                uri= "android.resource://" + getActivity().getPackageName() + "/" + R.raw.goodmorning;
                break;
            case"朋友":
                uri= "android.resource://" + getActivity().getPackageName() + "/" + R.raw.friend;
                break;
            case"漂亮":
                uri= "android.resource://" + getActivity().getPackageName() + "/" + R.raw.beautiful;
                break;
            case"谢谢":
                uri= "android.resource://" + getActivity().getPackageName() + "/" + R.raw.thanks;
                break;
            default:
                break;
        }
        Intent intent=new Intent(getActivity(), VideoPlayActivity.class);
        intent.putExtra("uri",uri);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        AlertDialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("删除记录")
                .setMessage("你确定要删除这条记录吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //确定要删除记录是哪一条
                        Word word = (Word) adapter.getItem(position);
                        String deleteId = word.getId();
                        //不友好，应该用对话框的形式
                        if (myDBhelper.deleteWord(deleteId)) {
                            //resultList.remove(deleteId);
                            //myAdapter.notifyDataSetChanged();
                            //删除成功就刷新一次
                            refresh();
                            Toast.makeText(getContext(), "删除成功！", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "删除失败！", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        dialog = builder.create();
        dialog.show();
        return true;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            refresh();
        }
    }
    private void refresh() {
        list = myDBhelper.queryWord();
        adapter = new MyAdapter(list, getActivity());
        listView.setAdapter(adapter);
    }
}
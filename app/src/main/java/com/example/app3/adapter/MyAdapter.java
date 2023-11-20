package com.example.app3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.app3.R;
import com.example.app3.bean.Word;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    //使用List<Note> list会存储数据库中的note表所有记录
    private List<Word> list;
    //LayoutInflater将用户布局文件转成View对象
    private LayoutInflater layoutInflater;
    //参数context可以指明在那个页面使用了该适配器
    public MyAdapter(List<Word> list, Context context){
        this.list=list;
        layoutInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view==null){
            view=layoutInflater.inflate(R.layout.text_item_layout,null,false);
            viewHolder=new ViewHolder(view);
            view.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) view.getTag();
        }
        //将数据库中内容加载到控件上
        Word word=(Word)getItem(position);
        viewHolder.t_content.setText(word.getWord());
        viewHolder.t_time.setText(word.getAdd_time());
        return view;
    }
    //ViewHolder用于给item视图加载数据内容
    class ViewHolder{
        TextView t_content,t_time;
        public ViewHolder(View view){
            t_content=view.findViewById(R.id.it_content);
            t_time=view.findViewById(R.id.item_time);
        }
    }
}

package com.example.app3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app3.R;
import com.example.app3.bean.Item2;

import java.util.List;

public class LVAdapter2 extends BaseAdapter {
    private List<Item2> list;
    private LayoutInflater layoutInflater;
    public LVAdapter2(List<Item2> list, Context context) {
        this.list = list;
        layoutInflater= LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LVAdapter2.ViewHolder viewHolder;
        if(view==null){
            view=layoutInflater.inflate(R.layout.item_news_list,null,false);
            viewHolder=new LVAdapter2.ViewHolder(view);
            view.setTag(viewHolder);
        }else{
            viewHolder=(LVAdapter2.ViewHolder) view.getTag();
        }
        Item2 item=list.get(i);
        viewHolder.tv1.setText(item.getTitle());
        viewHolder.tv2.setText(item.getContent());
        viewHolder.iv.setImageResource(item.getImg());
        return view;
    }
    class ViewHolder{
        TextView tv1,tv2;
        ImageView iv;

        public ViewHolder(View view){
            iv=view.findViewById(R.id.news_img);
            tv1=view.findViewById(R.id.news_title);
            tv2=view.findViewById(R.id.news_content);
        }
    }
}

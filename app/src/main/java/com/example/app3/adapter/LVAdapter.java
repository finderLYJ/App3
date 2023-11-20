package com.example.app3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app3.R;
import com.example.app3.bean.Item;

import java.util.List;

public class LVAdapter extends BaseAdapter {
    private List<Item> list;
    private LayoutInflater layoutInflater;
    //参数context可以指明在那个页面使用了该适配器
    public LVAdapter(List<Item> list, Context context) {
        this.list = list;
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
            view=layoutInflater.inflate(R.layout.func_item_layout,null,false);
            viewHolder=new ViewHolder(view);
            view.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) view.getTag();
        }
        Item item=list.get(position);
        viewHolder.iv.setImageResource(item.getTubiao());
        viewHolder.title.setText(item.getTitle());
        viewHolder.arrow.setImageResource(item.getArrow());
        return view;
    }
    class ViewHolder{
        ImageView iv,arrow;
        TextView title;
        public ViewHolder(View view){
            iv=view.findViewById(R.id.func_iv);
            arrow=view.findViewById(R.id.func_arrow);
            title=view.findViewById(R.id.func_tv);
        }
    }
}

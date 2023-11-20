package com.example.app3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app3.R;
import com.example.app3.bean.Item1;

import java.util.List;

public class LVAdapter1 extends BaseAdapter {
    private List<Item1> list;
    private LayoutInflater layoutInflater;
    public LVAdapter1(List<Item1> list, Context context) {
        this.list = list;
        layoutInflater=LayoutInflater.from(context);
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
        ViewHolder viewHolder;
        if(view==null){
            view=layoutInflater.inflate(R.layout.item_home_list,null,false);
            viewHolder=new ViewHolder(view);
            view.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) view.getTag();
        }
        Item1 item=list.get(i);
        viewHolder.iv.setImageResource(item.getImgPath());
        viewHolder.text.setText(item.getText());
        return view;
    }
    class ViewHolder{
        ImageView iv;
        TextView text;
        public ViewHolder(View view){
            iv=view.findViewById(R.id.home_list_iv);
            text=view.findViewById(R.id.home_list_tv);
        }
    }
}

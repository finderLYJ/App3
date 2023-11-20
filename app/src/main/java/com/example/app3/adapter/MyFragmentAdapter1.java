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

public class MyFragmentAdapter1 extends BaseAdapter {
    List<Item1> data;
    private LayoutInflater layoutInflater;
    public MyFragmentAdapter1(List<Item1> list, Context context) {
        this.data = list;
        layoutInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(view==null){
            view=layoutInflater.inflate(R.layout.item_grid,null);
            viewHolder=new ViewHolder(view);
            view.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) view.getTag();
        }
        Item1 item=data.get(i);
        viewHolder.iv.setImageResource(item.getImgPath());
        viewHolder.tv.setText(item.getText());
        return view;
    }
    class ViewHolder{
        ImageView iv;
        TextView tv;
        public ViewHolder(View view){
            iv=view.findViewById(R.id.func_grid_img);
            tv=view.findViewById(R.id.func_grid_title);
        }
    }
}

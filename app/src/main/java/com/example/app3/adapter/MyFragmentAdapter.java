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

public class MyFragmentAdapter extends BaseAdapter {
    private List<Item1> list;
    private LayoutInflater layoutInflater;
    public MyFragmentAdapter(List<Item1> list, Context context){
        this.list = list;
        this.layoutInflater =LayoutInflater.from(context);
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
        ViewHolder holder;
        if(view==null){
            view=layoutInflater.inflate(R.layout.layout_item_video,null,false);
            holder=new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder=(ViewHolder) view.getTag();
        }
        //将数据库中内容加载到控件上
        Item1 item=list.get(i);
        holder.textView.setText(item.getText());
        holder.imageView.setImageResource(item.getImgPath());
        return view;
    }
    class ViewHolder{
        TextView textView;
        ImageView imageView;
        public ViewHolder(View view){
            textView=view.findViewById(R.id.tv_item);
            imageView=view.findViewById(R.id.iv_item);
        }
    }
}

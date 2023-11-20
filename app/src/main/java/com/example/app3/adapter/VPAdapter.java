package com.example.app3.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

public class VPAdapter extends PagerAdapter {
    List<View> viewList;

    public VPAdapter(List<View> viewList) {
        this.viewList = viewList;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;//保证无限滑动
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view=viewList.get(position%viewList.size());
        ViewPager parent=(ViewPager) view.getParent();
        if(parent!=null){
            parent.removeView(view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view=viewList.get(position%viewList.size());
        container.removeView(view);
    }
}

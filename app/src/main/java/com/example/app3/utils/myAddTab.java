package com.example.app3.utils;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

/**
 * @author ithuangqing
 * @data 2017/10/17.
 */

public class myAddTab {
    public static void addTab(TabLayout tabLayout, ViewPager viewPager, final List<Fragment> fragment, final List<String> title, FragmentManager fragmentManager) {
        /**
         * 预加载
         */
        viewPager.setOffscreenPageLimit(fragment.size());

        viewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return fragment.get(position);
            }

            @Override
            public int getCount() {
                return fragment.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return title.get(position);
            }
        });
        // TabLayout关联ViewPager
        tabLayout.setupWithViewPager(viewPager);

    }

}

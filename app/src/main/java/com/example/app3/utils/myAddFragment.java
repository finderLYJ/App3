package com.example.app3.utils;


import androidx.fragment.app.Fragment;

import java.util.Arrays;
import java.util.List;

/**
 * @author ithuangqing
 * @data 2017/10/18.
 */

public class myAddFragment {
    public static List<Fragment> addFragment(Fragment... fragments) {

        Fragment[] m = fragments;

        List<Fragment> fragmentList = Arrays.asList(m);

        return fragmentList;
    }
}

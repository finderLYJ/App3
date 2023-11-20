package com.example.app3.utils;

import java.util.Arrays;
import java.util.List;

/**
 * @author ithuangqing
 * @data 2017/10/18.
 */

public class myAddTitle {

    public static List<String> addTitle(String ... a){

        String[] title = a;

        List<String> list = Arrays.asList(title);

        return list;

    }
}

package com.example.wintertraining_lab;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 郭朕 on 2018/1/20.
 */

public class ActivityCollector {

    private static List<Activity> mActivity = new ArrayList<>();

    public static void add(Activity activity){
        mActivity.add(activity);
    }

    public static void remove(Activity activity){
        mActivity.remove(activity);
    }
}

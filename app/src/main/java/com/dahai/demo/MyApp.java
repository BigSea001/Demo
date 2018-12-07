package com.dahai.demo;

import android.app.Application;
import android.content.Context;

/**
 * 作者： 大海
 * 时间： 2018/11/29
 * 描述：
 */
public class MyApp extends Application {

    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
    }
}

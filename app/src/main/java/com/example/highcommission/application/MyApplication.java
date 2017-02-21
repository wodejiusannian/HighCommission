package com.example.highcommission.application;

import android.app.Application;

import org.xutils.x;

/**
 * Created by 小五 on 2017/2/21.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}

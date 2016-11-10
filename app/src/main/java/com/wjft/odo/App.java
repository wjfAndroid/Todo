package com.wjft.odo;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.wjft.odo.util.UncaughtExceptionHandler;

/**
 * Created by Administrator on 2016/11/1.
 */
public class App extends Application {
    boolean b = false;
    Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;
    }

}

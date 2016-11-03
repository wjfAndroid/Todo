package com.wjf.leakcanary;

import android.app.Activity;
import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by Administrator on 2016/11/1.
 */
public class App extends Application {
    private RefWatcher mRefWatcher;

    public static RefWatcher getRefWatcher(Activity activity) {
        App app = (App) activity.getApplicationContext();
        return app.mRefWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mRefWatcher = LeakCanary.install(this);
    }
}

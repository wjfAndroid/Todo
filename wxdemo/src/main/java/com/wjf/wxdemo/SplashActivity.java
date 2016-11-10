package com.wjf.wxdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.wjf.wxdemo.base.BaseActivity;
import com.wjf.wxdemo.util.ThreadUtils;


public class SplashActivity extends BaseActivity {

    @Override
    public int getLayoutRes() {
        return R.layout.activity_splash;
    }

    protected void init() {
        if (EMClient.getInstance().isLoggedInBefore() && EMClient.getInstance().isConnected()) {
            postDelay(new Runnable() {
                @Override
                public void run() {
                    startActivity(MainActivity.class);
                }
            }, 2000);

        } else {
            startActivity(LoginActivity.class);
        }
    }


}

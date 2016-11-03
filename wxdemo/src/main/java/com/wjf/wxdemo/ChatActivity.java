package com.wjf.wxdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wjf.wxdemo.base.BaseActivity;

public class ChatActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_chat;
    }
}

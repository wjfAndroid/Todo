package com.wjf.wxdemo.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hyphenate.chat.EMClient;
import com.wjf.wxdemo.R;
import com.wjf.wxdemo.util.ThreadUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThirdFragment extends BaseFragment {


    @Bind(R.id.bt_logout)
    Button btLogout;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_third;
    }

    @Override
    protected void init() {
        super.init();
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                String name = EMClient.getInstance().getCurrentUser();
                btLogout.setText("退(" + name + ")出");

            }
        });

        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThreadUtils.runOnBackgroundThread(new Runnable() {
                    @Override
                    public void run() {
                        EMClient.getInstance().logout(true);
                    }
                });
            }
        });

    }

}

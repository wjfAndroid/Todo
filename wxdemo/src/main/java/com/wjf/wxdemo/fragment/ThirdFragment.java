package com.wjf.wxdemo.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.wjf.wxdemo.LoginActivity;
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

        String name = EMClient.getInstance().getCurrentUser();
        btLogout.setText("退(" + name + ")出");

        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EMClient.getInstance().logout(true, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        Log.e(TAG, "退出成功 ");
                        startActivity(LoginActivity.class);
                        mActivity.finish();
                    }

                    @Override
                    public void onError(int i, String s) {
                        Log.e(TAG, "退出失败 s:" + s);
                    }

                    @Override
                    public void onProgress(int i, String s) {
                    }
                });
            }
        });

    }

}

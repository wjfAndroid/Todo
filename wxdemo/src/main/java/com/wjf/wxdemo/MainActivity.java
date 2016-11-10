package com.wjf.wxdemo;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectListener;
import com.wjf.wxdemo.adapter.EMMessageListenerAdapter;
import com.wjf.wxdemo.base.BaseActivity;
import com.wjf.wxdemo.fragment.FragmentFactory;
import com.wjf.wxdemo.util.ThreadUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.frame_container)
    FrameLayout frameContainer;
    @Bind(R.id.bottom_bar)
    BottomBar bottomBar;
    FragmentManager mFragmentManager;


    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        super.init();
        mFragmentManager = getSupportFragmentManager();
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                transaction.replace(R.id.frame_container, FragmentFactory.getInstance().getFragment(tabId)).commit();
            }
        });

        EMClient.getInstance().chatManager().addMessageListener(mEMMessageListenerAdapter);
        EMClient.getInstance().addConnectionListener(mEMConnectionListener);

    }

    private EMMessageListenerAdapter mEMMessageListenerAdapter = new EMMessageListenerAdapter() {
        @Override
        public void onMessageReceived(List<EMMessage> list) {
            super.onMessageReceived(list);
            updateUnreadCount();

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        updateUnreadCount();
    }

    private void updateUnreadCount() {
//        ThreadUtils.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                BottomBarTab tab = bottomBar.getTabWithId(R.id.conversations);
//                int count = EMClient.getInstance().chatManager().getUnreadMsgsCount();
//                tab.setBadgeCount(count);
//            }
//        });
    }

    private EMConnectionListener mEMConnectionListener = new EMConnectionListener() {
        @Override
        public void onConnected() {

        }

        @Override
        public void onDisconnected(int i) {
            if (i == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                ThreadUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(LoginActivity.class,true);
                        toast("用户登录其他设备");
                    }
                });
            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().removeConnectionListener(mEMConnectionListener);
        EMClient.getInstance().chatManager().removeMessageListener(mEMMessageListenerAdapter);
    }

}

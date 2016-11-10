package com.wjf.wxdemo.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.wjf.wxdemo.AddFriendActivity;
import com.wjf.wxdemo.ChatActivity;
import com.wjf.wxdemo.R;
import com.wjf.wxdemo.adapter.SecondLVAdapter;
import com.wjf.wxdemo.util.ThreadUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends BaseFragment {


    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.add)
    ImageView add;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    List<String> contacts;
    @Bind(R.id.lv_second)
    RecyclerView mRecyclerView;
    private String tag = "SecondFragment";

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_second;
    }

    @Override
    protected void init() {
        super.init();
        title.setText("联系人");
        add.setVisibility(View.VISIBLE);

        notifyList();


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                notifyList();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AddFriendActivity.class, false);
            }
        });
    }

    public void notifyList() {
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                try {
                    String user = EMClient.getInstance().getCurrentUser();
                    contacts = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    System.out.println("user = " + user + "  contacts = " + contacts);
                    ThreadUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
                            SecondLVAdapter adapter = new SecondLVAdapter(mActivity, contacts);
                            mRecyclerView.setAdapter(adapter);

                            adapter.setOnClickListener(new SecondLVAdapter.onClickListener() {
                                @Override
                                public void onClick(int pos, View view) {
                                    Intent intent = new Intent(mActivity, ChatActivity.class);
                                    intent.putExtra("name", contacts.get(pos));
                                    startActivity(intent);


                                }
                            });
                            refreshLayout.setRefreshing(false);
                        }
                    });

                } catch (HyphenateException e) {
                    e.printStackTrace();
                    toast("获取失败 e:" + e);
                    Log.e(tag, "获取失败 e:" + e);
                }
            }
        });
    }
}

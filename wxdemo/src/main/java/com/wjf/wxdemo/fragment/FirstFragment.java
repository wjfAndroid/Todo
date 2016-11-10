package com.wjf.wxdemo.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.wjf.wxdemo.ChatActivity;
import com.wjf.wxdemo.R;
import com.wjf.wxdemo.adapter.FirstLVAdapter;
import com.wjf.wxdemo.util.ThreadUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


public class FirstFragment extends BaseFragment {


    @Bind(R.id.lv_first)
    ListView lvFirst;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.add)
    ImageView add;
    List<EMConversation> mConversations = new ArrayList<>();
    FirstLVAdapter adapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_first;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter!=null){
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void init() {
        super.init();
        title.setText("消息");
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
                mConversations.clear();
                mConversations.addAll(conversations.values());
                Collections.sort(mConversations, new Comparator<EMConversation>() {
                    @Override//消息排序，比较时间大的就在上面
                    public int compare(EMConversation lhs, EMConversation rhs) {
                        int i = (int) (rhs.getLastMessage().getMsgTime() - rhs.getLastMessage().getMsgTime());
                        return i;
                    }
                });
                ThreadUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new FirstLVAdapter(mConversations, mActivity);
                        lvFirst.setAdapter(adapter);
                        lvFirst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                EMConversation conversation = EMClient.getInstance().chatManager().getConversation(mConversations.get(position).getUserName());
                                //指定会话消息未读数清零
                                conversation.markAllMessagesAsRead();

                                Intent intent = new Intent(mActivity, ChatActivity.class);
                                intent.putExtra("name", mConversations.get(position).getUserName());
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        });
    }


}

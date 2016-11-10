package com.wjf.wxdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.wjf.wxdemo.adapter.ChatLVAdapter;
import com.wjf.wxdemo.base.BaseActivity;
import com.wjf.wxdemo.util.ThreadUtils;
import com.wjf.wxdemo.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChatActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.add)
    ImageView add;
    @Bind(R.id.lv_chat)
    ListView lvChat;
    @Bind(R.id.et_msg)
    EditText etMsg;
    @Bind(R.id.bt_send)
    Button btSend;
    List<EMMessage> mEMMessages = new ArrayList<>();
    ChatLVAdapter chatLVAdapter;
    String name;
    boolean hasMore = true;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    Activity mActivity;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_chat;
    }

    @Override
    protected void init() {
        super.init();
        mActivity = this;
        name = getIntent().getStringExtra("name");
        title.setText("与" + name + "进行聊天");
        getMessage();
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMsg();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadMore();

            }
        });
    }

    private void sendMsg() {
        final String msg = etMsg.getText().toString().trim();
        etMsg.setText("");

        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                final EMMessage message = EMMessage.createTxtSendMessage(msg, name);
                message.setStatus(EMMessage.Status.INPROGRESS);
                message.setMessageStatusCallback(new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        // getMessage(name);
                        System.out.println("发送成功：");
                        ToastUtil.showToast(mActivity, "发送成功");
//                        mEMMessages.add(message);
//                        chatLVAdapter.notifyDataSetChanged();
                        getMessage();
                    }

                    @Override
                    public void onError(int i, String s) {
                        System.out.println("发送失败：");
                        ToastUtil.showToast(mActivity, "发送失败");
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
                EMClient.getInstance().chatManager().sendMessage(message);
            }
        });
    }

    private void getMessage() {
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                EMConversation emConversation = EMClient.getInstance().chatManager().getConversation(name);
                if (emConversation != null) {
                    List<EMMessage> emMessages = emConversation.getAllMessages();
                    mEMMessages = emMessages;
                }
                System.out.println("获取聊天记录成功  mEMMessages.size：" + mEMMessages.size());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        chatLVAdapter = new ChatLVAdapter(mEMMessages, ChatActivity.this);
                        lvChat.setAdapter(chatLVAdapter);
                        lvChat.setSelection(lvChat.getBottom());
                    }
                });
            }
        });
    }

    public void loadMore() {
        if (hasMore) {
            EMConversation emConversation = EMClient.getInstance().chatManager().getConversation(name);
            EMMessage firstMessage = mEMMessages.get(0);
            //SDK初始化加载的聊天记录为20条，到顶时需要去DB里获取更多
            //获取startMsgId之前的pagesize条消息，此方法获取的messages SDK会自动存入到此会话中，APP中无需再次把获取到的messages添加到会话中
            List<EMMessage> messages = emConversation.loadMoreMsgFromDB(firstMessage.getMsgId(), 20);
            hasMore = (messages.size() == 20);
            mEMMessages.addAll(0, messages);
            System.out.println("加载更多数据成功  mEMMessages.size：" + mEMMessages.size());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    chatLVAdapter = new ChatLVAdapter(mEMMessages, ChatActivity.this);
                    lvChat.setAdapter(chatLVAdapter);
                    lvChat.setSelection(lvChat.getBottom());
                }
            });
        } else {
            ToastUtil.showToast(this, "没有更多了");
            Log.e(TAG, "没有更多了");
        }
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }

    }

    public void backToMain() {

    }


}

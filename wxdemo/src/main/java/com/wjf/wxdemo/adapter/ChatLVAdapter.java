package com.wjf.wxdemo.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.DateUtils;
import com.wjf.wxdemo.R;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */
public class ChatLVAdapter extends BaseAdapter {
    List<EMMessage> mEMMessages;
    Activity mActivity;
    LayoutInflater mInflater;
    public static final int SEND = 0;
    public static final int RECEIVE = 1;
//    View view_send,view_receive;

    public ChatLVAdapter(List<EMMessage> EMMessages, Activity activity) {
        mEMMessages = EMMessages;
        mActivity = activity;
        mInflater = mActivity.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderReceive holderReceive;
        ViewHolderSend holderSend;

        EMMessage message = mEMMessages.get(position);
        switch (getItemViewType(position)) {
            case SEND:
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.item_chat_send, parent, false);
                    holderSend = new ViewHolderSend();
                    holderSend.tv_msg_send = (TextView) convertView.findViewById(R.id.tv_msg);
                    holderSend.tv_name_send = (TextView) convertView.findViewById(R.id.tv_name);
                    holderSend.tv_time_send = (TextView) convertView.findViewById(R.id.tv_time);
                    convertView.setTag(holderSend);
                } else {
                    holderSend = (ViewHolderSend) convertView.getTag();
                }

                EMMessageBody body = message.getBody();
                if (body instanceof EMTextMessageBody) {
                    holderSend.tv_msg_send.setText(((EMTextMessageBody) body).getMessage());
                } else {
                    holderSend.tv_msg_send.setText("非文本信息");
                }
                String time = DateUtils.getTimestampString(new Date(message.getMsgTime()));
                holderSend.tv_time_send.setText(time);

                return convertView;
            case RECEIVE:
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.item_chat_receive, parent, false);
                    holderReceive = new ViewHolderReceive();
                    holderReceive.tv_msg_receive = (TextView) convertView.findViewById(R.id.tv_msg);
                    holderReceive.tv_name_receive = (TextView) convertView.findViewById(R.id.tv_name);
                    holderReceive.tv_time_receive = (TextView) convertView.findViewById(R.id.tv_time);
                    convertView.setTag(holderReceive);
                } else {
                    holderReceive = (ViewHolderReceive) convertView.getTag();
                }

                EMMessageBody body1 = message.getBody();
                if (body1 instanceof EMTextMessageBody) {
                    holderReceive.tv_msg_receive.setText(((EMTextMessageBody) body1).getMessage());
                } else {
                    holderReceive.tv_msg_receive.setText("非文本信息");
                }
                String time1 = DateUtils.getTimestampString(new Date(message.getMsgTime()));
                holderReceive.tv_time_receive.setText(time1);
                return convertView;
        }
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        System.out.println("position = [" + position + "]"+"mEMMessages.get(position).direct() = [" + mEMMessages.get(position).direct() + "]");
        switch (mEMMessages.get(position).direct()) {
            case SEND:
                return SEND;
            case RECEIVE:
                return RECEIVE;
        }
        return -1;
    }

    @Override
    public int getCount() {
        return mEMMessages.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    class ViewHolderSend {
        TextView tv_time_send;
        TextView tv_msg_send;
        TextView tv_name_send;
    }

    class ViewHolderReceive {
        TextView tv_time_receive;
        TextView tv_msg_receive;
        TextView tv_name_receive;
    }
}

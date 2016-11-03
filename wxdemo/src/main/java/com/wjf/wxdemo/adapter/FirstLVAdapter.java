package com.wjf.wxdemo.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.DateUtils;
import com.wjf.wxdemo.R;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */
public class FirstLVAdapter extends BaseAdapter {
    List<EMConversation> mConversations;
    Activity mActivity;
    LayoutInflater mInflater;

    public FirstLVAdapter(List<EMConversation> conversations, Activity activity) {
        mConversations = conversations;
        mActivity = activity;
        mInflater = mActivity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return mConversations.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_lv_first, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
            viewHolder.tv_msg = (TextView) convertView.findViewById(R.id.tv_msg);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        EMConversation emConversation = mConversations.get(position);

        viewHolder.tv_name.setText(emConversation.getUserName());

        EMMessage message = emConversation.getLastMessage();
        if (message.getBody() instanceof EMTextMessageBody) {
            viewHolder.tv_msg.setText(((EMTextMessageBody) message.getBody()).getMessage());
        } else {
            viewHolder.tv_msg.setText("非文本消息");
        }

        viewHolder.tv_time.setText(DateUtils.getTimestampString(new Date(message.getMsgTime())));

        int count = emConversation.getUnreadMsgCount();
        if (count > 1) {
            viewHolder.tv_count.setVisibility(View.VISIBLE);
            viewHolder.tv_count.setText(count + "");
        } else {
            viewHolder.tv_count.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }


    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class ViewHolder {
        TextView tv_name;
        TextView tv_msg;
        TextView tv_time;
        TextView tv_count;
    }

}

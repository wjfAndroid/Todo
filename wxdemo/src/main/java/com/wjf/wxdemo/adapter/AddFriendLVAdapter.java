package com.wjf.wxdemo.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wjf.wxdemo.R;
import com.wjf.wxdemo.base.AddFriendItem;
import com.wjf.wxdemo.base.BaseActivity;

import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */
public class AddFriendLVAdapter extends BaseAdapter {
    List<AddFriendItem> mAddFriendItems;
    Activity mActivity;
    LayoutInflater mInflater;

    public AddFriendLVAdapter(List<AddFriendItem> addFriendItems, Activity activity) {
        mAddFriendItems = addFriendItems;
        mActivity = activity;
        mInflater = mActivity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return mAddFriendItems.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VH vh;
        if (convertView==null){
            convertView =mInflater.inflate(R.layout.item_add_friend_lv,parent,false);
            vh = new VH();
            vh.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            vh.tv_time= (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(vh);
        }else {
            vh = (VH) convertView.getTag();
        }
        vh.tv_time.setText(mAddFriendItems.get(position).getCreateTime());
        vh.tv_name.setText(mAddFriendItems.get(position).getName());

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

    class VH {
        TextView tv_name;
        TextView tv_time;
    }
}

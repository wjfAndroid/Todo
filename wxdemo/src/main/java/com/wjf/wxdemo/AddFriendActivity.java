package com.wjf.wxdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.wjf.wxdemo.adapter.AddFriendLVAdapter;
import com.wjf.wxdemo.base.AddFriendItem;
import com.wjf.wxdemo.base.BaseActivity;
import com.wjf.wxdemo.bean.User;
import com.wjf.wxdemo.util.ThreadUtils;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

public class AddFriendActivity extends BaseActivity {
    ArrayList<AddFriendItem> mAddFriendItems = new ArrayList<>();
    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.bt_search)
    Button btSearch;
    @Bind(R.id.lv_search)
    ListView lvSearch;


    @Override
    public int getLayoutRes() {
        return R.layout.activity_add_friend;
    }

    @Override
    protected void init() {
        super.init();

        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = etSearch.getText().toString().trim();
                searchFriend(s);
            }
        });
        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                addFriendInEase(position);
            }
        });
    }


    public void searchFriend(String key) {
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereContains("userNmae", key).addWhereNotEqualTo("userNmae", EMClient.getInstance().getCurrentUser());
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null && list.size() > 0) {
                    System.out.println("list = " + list);
                    for (int i = 0; i < list.size(); i++) {
                        AddFriendItem item = new AddFriendItem();
                        item.setCreateTime(list.get(i).getCreatedAt());
                        item.setName(list.get(i).getUserNmae());
                        item.setBombID(list.get(i).getObjectId());
                        mAddFriendItems.add(item);
                        lvSearch.setAdapter(new AddFriendLVAdapter(mAddFriendItems, AddFriendActivity.this));
                    }
                }
            }
        });
    }

    public void addFriendInEase(final int pos) {
                try {
                    EMClient.getInstance().contactManager().addContact(mAddFriendItems.get(pos).getName(), "");
                    Log.e(TAG, "添加好友成功");
                    toast("添加好友成功");


                } catch (HyphenateException e) {
                    e.printStackTrace();
                    Log.e(TAG, "添加好友失败 e:" + e);
                }
    }

    public void addFriendInBomb(String name) {
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("userNmae", EMClient.getInstance().getCurrentUser());

    }


}

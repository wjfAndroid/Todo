package com.wjf.wxdemo.base;

/**
 * Created by Administrator on 2016/11/4.
 */
public class AddFriendItem {
    String createTime;
    String name;
    String BombID;

    public String getBombID() {
        return BombID;
    }

    public void setBombID(String bombID) {
        BombID = bombID;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

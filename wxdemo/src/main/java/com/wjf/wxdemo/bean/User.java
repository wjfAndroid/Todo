package com.wjf.wxdemo.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/11/3.
 */
public class User extends BmobObject {
    String userNmae;
    String userPsw;

    public String getUserNmae() {
        return userNmae;
    }

    public void setUserNmae(String userNmae) {
        this.userNmae = userNmae;
    }

    public String getUserPsw() {
        return userPsw;
    }

    public void setUserPsw(String userPsw) {
        this.userPsw = userPsw;
    }

    @Override
    public String toString() {
        return "User{" +
                "userNmae='" + userNmae + '\'' +
                ", userPsw='" + userPsw + '\'' +
                '}';
    }
}

package com.wjf.leakcanary;

import android.content.Context;

/**
 * Created by Administrator on 2016/11/1.
 */
public class LoginManager {
    private static LoginManager mLoginManager;
   private Context mContext;

    private LoginManager(Context context) {
        mContext = context;
    }
    public static LoginManager getInstance(Context context){
        if (mLoginManager==null){
            synchronized (LoginManager.class){
                if (mLoginManager==null){
                    mLoginManager = new LoginManager(context);
                }
            }
        }
        return mLoginManager;
    }
    public void doSomeThing(){

    }
}

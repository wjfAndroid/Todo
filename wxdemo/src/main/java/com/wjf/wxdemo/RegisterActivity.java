package com.wjf.wxdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.wjf.wxdemo.base.BaseActivity;
import com.wjf.wxdemo.bean.User;
import com.wjf.wxdemo.util.ThreadUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.et_id)
    EditText etId;
    @Bind(R.id.et_psw)
    EditText etPsw;
    @Bind(R.id.bt_regist)
    Button btRegist;
    public static final String TAG = "RegisterActivity";


    @Override
    public int getLayoutRes() {
        return R.layout.activity_register;
    }

    @Override
    protected void init() {
        super.init();
        btRegist.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        regist();
    }

    //regist  bomb
    private void regist() {
        final String id = etId.getText().toString().trim();
        final String psw = etPsw.getText().toString().trim();

        User user = new User();
        user.setUserNmae(id);
        user.setUserPsw(psw);


        user.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Log.e(TAG, "bomb 注册成功 s：" + s);
                    registEase(id, psw);
                } else {
                    toast("注册失败");
                    Log.e(TAG, "bomb 注册失败 e：" + e);
                }
            }
        });
    }
    //regist  huanxin
    private void registEase(final String id, final String psw) {
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(id, psw);//同步方法
                    ThreadUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e(TAG, "ease 注册成功 " );
                        }
                    });
                    startActivity(LoginActivity.class);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    ThreadUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e(TAG, "ease 注册失败 " );
                        }
                    });
                }
            }
        });

    }
}

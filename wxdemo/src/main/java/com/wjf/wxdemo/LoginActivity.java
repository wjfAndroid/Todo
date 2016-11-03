package com.wjf.wxdemo;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.wjf.wxdemo.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    public static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    @Bind(R.id.et_id)
    EditText etId;
    @Bind(R.id.et_psw)
    EditText etPsw;
    @Bind(R.id.bt_login)
    Button btLogin;
    @Bind(R.id.tv_regist)
    TextView tvRegist;
    public static final String TAG="LoginActivity";

    @Override
    public int getLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        super.init();
        btLogin.setOnClickListener(this);
        tvRegist.setOnClickListener(this);

    }

    private void login() {
        String userName = etId.getText().toString().trim();
        String password = etPsw.getText().toString().trim();
        EMClient.getInstance().login(userName,password,new EMCallBack() {//回调
            @Override
            public void onSuccess() {
//                EMClient.getInstance().groupManager().loadAllGroups();
//                EMClient.getInstance().chatManager().loadAllConversations();
                startActivity(MainActivity.class);
                Log.d(TAG, "登录聊天服务器成功！");
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "登录聊天服务器失败！");
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                login();
                break;
            case R.id.tv_regist:
                startActivity(RegisterActivity.class);
                break;
        }

    }

    /**
     * 是否有写磁盘权限
     */
    private boolean hasWriteExternalStoragePermission() {
        int result = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PermissionChecker.PERMISSION_GRANTED;
    }

    /**
     * 申请权限
     */
    private void applyPermission() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(this, permissions, REQUEST_WRITE_EXTERNAL_STORAGE);
    }

    /**
     * 申请权限回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_WRITE_EXTERNAL_STORAGE:
                if (grantResults[0] == PermissionChecker.PERMISSION_GRANTED) {
                  //  login();
                    toast("权限允许");
                } else {
                    toast("权限拒绝");
                }
                break;
        }
    }


}

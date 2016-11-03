package com.wjf.wxdemo.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/17 21:52
 * 描述：    TODO
 */
public abstract class BaseFragment extends Fragment {
    public static final String TAG = "BaseFragment";

    private ProgressDialog mProgressDialog;
    Activity mActivity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(getLayoutRes(), null);
        ButterKnife.bind(this, root);
        init();
        return root;
    }

    protected void init() {
        mActivity = getActivity();
        Log.d(TAG, "init: ");
    }

    ;

    protected abstract int getLayoutRes();

    protected void showProgress(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setCancelable(true);
        }
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    protected void hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    protected void toast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void startActivity(Class activity) {
        startActivity(activity, false);
    }

    protected void startActivity(Class activity, String key, String extra) {
        Intent intent = new Intent(getContext(), activity);
        intent.putExtra(key, extra);
        startActivity(intent);
    }

    protected void startActivity(Class activity, boolean finish) {
        Intent intent = new Intent(getContext(), activity);
        startActivity(intent);
        if (finish) {
            getActivity().finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mProgressDialog = null;
        ButterKnife.unbind(this);
    }
}

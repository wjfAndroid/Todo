package com.wjf.wxdemo.fragment;

import android.support.v4.app.Fragment;

import com.wjf.wxdemo.R;

/**
 * Created by Administrator on 2016/11/3.
 */
public class FragmentFactory {
    private static FragmentFactory mFragmentFactory;
    FirstFragment mFirstFragment;
    SecondFragment mSecondFragment;
    ThirdFragment mThirdFragment;

    private FragmentFactory() {
    }

    public static FragmentFactory getInstance() {
        if (mFragmentFactory == null) {
            synchronized (FragmentFactory.class) {
                if (mFragmentFactory == null) {
                    mFragmentFactory = new FragmentFactory();
                }
            }
        }
        return mFragmentFactory;
    }

    public Fragment getFragment(int id) {
        switch (id) {
            case R.id.conversations:
                getFirstFragment();
                return mFirstFragment;
            case R.id.contacts:
                getSecondFragment();
                return mSecondFragment;
            case R.id.dynamic:
                getThirdFragment();
                return mThirdFragment;
        }
        return null;
    }

    private void getFirstFragment() {
        if (mFirstFragment == null) {
            mFirstFragment = new FirstFragment();
        }

    }

    private void getSecondFragment() {
        if (mSecondFragment == null) {
            mSecondFragment = new SecondFragment();
        }
    }

    private void getThirdFragment() {
        if (mThirdFragment == null) {
            mThirdFragment = new ThirdFragment();
        }
    }

}

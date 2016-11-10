package com.wjft.odo.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wjft.odo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/8.
 */
public class CommomPagerAdapter extends PagerAdapter {
    Activity mActivity;
    LayoutInflater mInflater;
    List<String> mStrings;

    public CommomPagerAdapter(Activity activity, List<String> mStrings) {
        mActivity = activity;
        this.mStrings = mStrings;
        mInflater = mActivity.getLayoutInflater();

    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        RecyclerView recyclerView = (RecyclerView) mInflater.inflate(R.layout.item_vp, null);
        List<String> strings = new ArrayList<>();
        for (int j = 0; j < 100; j++) {
            strings.add(" kkk " + j);
        }
        CommonAdapter adapter = new CommonAdapter(mActivity, strings);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        container.addView(recyclerView);

        return recyclerView;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return mStrings.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}

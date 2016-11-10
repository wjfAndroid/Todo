package com.wjft.odo.view.design;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import com.wjft.odo.R;
import com.wjft.odo.adapter.CommomPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CoordinatorLayout2Activity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.vp)
    ViewPager vp;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.container)
    CoordinatorLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_coordinator_layout2);
        ButterKnife.bind(this);

        initToolbar();
        init();

    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("tablayout");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
//        toolbar.setNavigationIcon();
//        toolbar.setOverflowIcon();

    }

    ArrayList<RecyclerView> mRecyclerViews = new ArrayList<>();
    List<String> mStrings = new ArrayList<>();

    private void init() {
        for (int i = 0; i < 5; i++) {
            mStrings.add("item" + i);
        }
        CommomPagerAdapter pagerAdapter = new CommomPagerAdapter(this, mStrings);
        vp.setAdapter(pagerAdapter);
        tablayout.setTabTextColors(Color.parseColor("#ffffff"), Color.parseColor("#ffffff"));
        tablayout.setupWithViewPager(vp);
    }


}

package com.wjft.odo.view.design;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.widget.LinearLayout;

import com.wjft.odo.R;
import com.wjft.odo.adapter.CommonAdapter;
import com.wjft.odo.view.recyclerview_pullpush.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CoordinatorLayout3Activity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.recycler)
    RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_coordinator_layout3);
        ButterKnife.bind(this);
        initToolbar();
        initRecycler();
    }


    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("CoordInatorLayout");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
    }

    private void initRecycler() {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 150; i++) {
            strings.add("i+" + i);

        }
        CommonAdapter adapter = new CommonAdapter(this, strings);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(adapter);

    }
}

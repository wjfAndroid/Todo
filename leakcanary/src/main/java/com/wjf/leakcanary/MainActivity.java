package com.wjf.leakcanary;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.squareup.leakcanary.RefWatcher;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.leak_canary)
    Button leakCanary;

    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        leakCanary.setOnClickListener(this);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                leakCanary.setText("aaa");
            }
        }, 3 * 60 * 1000);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = App.getRefWatcher(this);
        refWatcher.watch(this);
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, LeakCanaryActivity.class));
        this.finish();
    }
}

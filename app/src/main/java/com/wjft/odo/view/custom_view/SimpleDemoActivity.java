package com.wjft.odo.view.custom_view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wjft.odo.R;

public class SimpleDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_demo2);
    }

    public void click_viewgraghelper(View v) {
        startActivity(ViewDragHelperActivity.class);
    }

    public void startActivity(Class c) {
        startActivity(new Intent(this, c));
    }

}

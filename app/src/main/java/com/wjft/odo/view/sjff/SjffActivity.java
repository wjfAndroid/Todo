package com.wjft.odo.view.sjff;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.wjft.odo.R;

public class SjffActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjff);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("SjffActivity", "dispatchTouchEvent ev.getAction():" + ev.getAction());
        boolean b = super.dispatchTouchEvent(ev);
        Log.d("SjffActivity", "dispatchTouchEvent b:" + b+"  ev.getAction():" + ev.getAction());
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("SjffActivity", "onTouchEvent event.getAction():" + event.getAction());
        boolean b = super.onTouchEvent(event);
        Log.d("SjffActivity", "onTouchEvent b:" + b+"  ev.getAction():" + event.getAction());
        return b;
    }
}

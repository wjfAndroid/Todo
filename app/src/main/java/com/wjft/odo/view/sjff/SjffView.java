package com.wjft.odo.view.sjff;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/11/15.
 */
public class SjffView extends View {

    public SjffView(Context context) {
        super(context);
    }

    public SjffView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SjffView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d("SjffView", "dispatchTouchEvent event.getAction():" + event.getAction());
        boolean b = super.dispatchTouchEvent(event);
        Log.d("SjffView", "dispatchTouchEvent b:" + b+"  ev.getAction():" + event.getAction());
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("SjffView", "onTouchEvent event.getAction():" + event.getAction());
        boolean b = super.onTouchEvent(event);
        Log.d("SjffView", "onTouchEvent b:" + b+"  ev.getAction():" + event.getAction());
        return b;
    }
}

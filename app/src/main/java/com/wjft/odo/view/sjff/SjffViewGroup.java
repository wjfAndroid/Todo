package com.wjft.odo.view.sjff;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2016/11/15.
 */
public class SjffViewGroup extends RelativeLayout {
    public SjffViewGroup(Context context) {
        super(context);
    }

    public SjffViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SjffViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("SjffViewGroup", "dispatchTouchEvent ev.getAction():" + ev.getAction());
        boolean b = super.dispatchTouchEvent(ev);
        Log.d("SjffViewGroup", "dispatchTouchEvent b:" + b+"  ev.getAction():" + ev.getAction());
        return b ;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d("SjffViewGroup", "onInterceptTouchEvent ev.getAction():" + ev.getAction());
        boolean b = super.onInterceptTouchEvent(ev);
        Log.d("SjffViewGroup", "onInterceptTouchEvent b:" + b+"  ev.getAction():" + ev.getAction());
//        if (ev.getAction()==2){
//            return true;
//        }
     return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("SjffViewGroup", "onTouchEvent ev.getAction():" + event.getAction());
        boolean b = super.onTouchEvent(event);
        Log.d("SjffViewGroup", "onTouchEvent b:" + b+"  ev.getAction():" + event.getAction());
        return b;
    }
}

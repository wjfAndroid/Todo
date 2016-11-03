package com.wjft.odo.view.pull_customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.wjft.odo.R;


public class QQRefreshHeader extends FrameLayout implements RefreshHeader {

    ImageView imgvArrow;
    ImageView imgvOk;
    ImageView imgvProgress;
    TextView tv;
    Animation rotate_down;
    Animation rotate_up;
    Animation rotate;


    public QQRefreshHeader(Context context) {
        this(context, null);
    }

    public QQRefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.qq_header, this);
        imgvArrow = (ImageView) findViewById(R.id.imgv_arrow);
        imgvOk = (ImageView) findViewById(R.id.imgv_ok);
        imgvProgress = (ImageView) findViewById(R.id.imgv_progress);
        tv = (TextView) findViewById(R.id.tv);

        rotate = AnimationUtils.loadAnimation(context, R.anim.rotate_infinite);
        rotate_up = AnimationUtils.loadAnimation(context, R.anim.rotate_up);
        rotate_down = AnimationUtils.loadAnimation(context, R.anim.rotate_down);


    }


    @Override
    public void reset() {
        imgvArrow.setVisibility(VISIBLE);
        imgvProgress.setVisibility(INVISIBLE);
        imgvOk.setVisibility(INVISIBLE);
        imgvArrow.clearAnimation();//保证隐藏
        imgvProgress.clearAnimation();//保证隐藏
        tv.setText("下拉刷新");
    }

    @Override
    public void pull() {

    }

    @Override
    public void refreshing() {
        imgvArrow.setVisibility(INVISIBLE);
        imgvProgress.setVisibility(VISIBLE);
        imgvProgress.clearAnimation();
        imgvArrow.clearAnimation();//保证隐藏
        imgvProgress.startAnimation(rotate);
        tv.setText("正在刷新...");
    }

    @Override
    public void onPositionChange(float currentPos, float lastPos, float refreshPos, boolean isTouch, RefreshLayout.State state) {
        imgvProgress.setVisibility(INVISIBLE);
        imgvOk.setVisibility(INVISIBLE);
        System.out.println("currentPos = [" + currentPos + "], lastPos = [" + lastPos + "], refreshPos = [" + refreshPos + "], isTouch = [" + isTouch + "], state = [" + state + "]");
        if (currentPos > refreshPos && lastPos <= refreshPos) {
            if (isTouch && state == RefreshLayout.State.PULL){
                tv.setText("释放立即刷新");
                imgvArrow.clearAnimation();
                imgvArrow.startAnimation(rotate_up);
            }
        }
        if (currentPos < refreshPos && lastPos >=refreshPos) {
            if (isTouch && state == RefreshLayout.State.PULL) {
                tv.setText("下拉刷新1");
                imgvArrow.clearAnimation();
                imgvArrow.startAnimation(rotate_down);
            }
        }
    }

    @Override
    public void complete() {
        imgvArrow.setVisibility(INVISIBLE);
        imgvProgress.setVisibility(INVISIBLE);
        imgvOk.setVisibility(VISIBLE);
        imgvArrow.clearAnimation();//
        imgvProgress.clearAnimation();//保证隐藏
        tv.setText("刷新成功");
    }
}

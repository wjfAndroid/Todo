package com.wjft.odo.view.custom_view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wjft.odo.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/11.
 * 使用ViewDragHelper实现卡片的移动删除
 */
public class VDHLayout extends RelativeLayout {
    Context mContext;
    ViewDragHelper mViewDragHelper;
    private String TAG = "VDHLayout";
    Point mPoint = new Point();
    int childWidth, childHeight;
    int currentLeft, currentTop;
    int width, height;


    private ArrayList<View> mViews = new ArrayList<>();


    public VDHLayout(Context context) {
        super(context);
    }

    public VDHLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return true;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                //   Log.d(TAG, "clampViewPositionHorizontal() called with: " + "], left = [" + left + "], dx = [" + dx + "]");
                currentLeft = left;
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                //      Log.d(TAG, "clampViewPositionVertical() called with: " + "], top = [" + top + "], dy = [" + dy + "]");
                currentTop = top;
                return top;
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                //        Log.d(TAG, "onViewReleased() called with: " + "releasedChild = [" + releasedChild + "], xvel = [" + xvel + "], yvel = [" + yvel + "]");


                if (currentLeft >= 0) {//上 右 下
                    if (currentTop <= 0) {
                        if (currentTop + childHeight / 4 < 0) {//上
                            if ((currentLeft + childWidth - width) > childWidth / 4) {//右上
                                mViewDragHelper.settleCapturedViewAt(width, -childHeight);

                            } else {//上
                                mViewDragHelper.settleCapturedViewAt(currentLeft, -childHeight);

                            }
                        } else {//回
                            mViewDragHelper.settleCapturedViewAt(mPoint.x, mPoint.y);
                        }
                    } else {//右 和 下
                        if ((currentLeft + childWidth) - width > childWidth / 4) {//右
                            if ((currentTop + childHeight - height > childHeight / 4)) {//右下
                                mViewDragHelper.settleCapturedViewAt(width, height);

                            } else {//右
                                mViewDragHelper.settleCapturedViewAt(width, currentTop);

                            }
                        } else if (currentTop + childHeight - height > childHeight / 4) {//下
                            mViewDragHelper.settleCapturedViewAt(currentLeft, height);

                        } else {//回
                            mViewDragHelper.settleCapturedViewAt(mPoint.x, mPoint.y);
                        }
                    }
                } else {//左
                    if (currentTop + childHeight - height > childHeight / 4 && Math.abs(currentLeft) > childWidth / 4) {//左下
                        mViewDragHelper.settleCapturedViewAt(-childWidth, height);

                    } else if (currentLeft + childWidth / 4 < 0) {//左
                        if (currentTop + childHeight / 4 < 0) {//左上
                            mViewDragHelper.settleCapturedViewAt(-childWidth, -childHeight);

                        } else {//左
                            mViewDragHelper.settleCapturedViewAt(-childWidth, currentTop);

                        }
                    } else {//回
                        mViewDragHelper.settleCapturedViewAt(mPoint.x, mPoint.y);
                    }
                }
                postInvalidate();
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                int range = getMeasuredWidth() - child.getMeasuredWidth();
                //       Log.d(TAG, "getViewHorizontalDragRange() called with: " + "range = [" + range + "]");
                return range;
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                int range = getMeasuredHeight() - child.getMeasuredHeight();
                //    Log.d(TAG, "getViewVerticalDragRange() called with: " + "range = [" + range + "]");
                return range;
            }
        });

    }

    public void setViews(ArrayList<View> views) {
        mViews = views;
        int count = mViews.size();
        for (int i = 0; i < count; i++) {
            addView(mViews.get(i));
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        View child = getChildAt(0);
        if (child != null) {
            childWidth = child.getMeasuredWidth();
            childHeight = child.getMeasuredHeight();
            width = getMeasuredWidth();
            height = getMeasuredHeight();
            mPoint.x = child.getLeft();
            mPoint.y = child.getTop();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }
}

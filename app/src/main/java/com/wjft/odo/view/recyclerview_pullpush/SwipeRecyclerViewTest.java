package com.wjft.odo.view.recyclerview_pullpush;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.wjft.odo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by Administrator on 2016/8/26.
 *
 * 下面操作
 */
public class SwipeRecyclerViewTest extends Activity {

    private SwipeRecyclerView swipeRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private RecycleViewAdapter recycleViewAdapter;
    private List<String> list = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swiperecycleviewtest);
        swipeRecyclerView = (SwipeRecyclerView) findViewById(R.id.swiperefreshlayout);
        swipeRefreshLayout = swipeRecyclerView.getSwipeRefreshLayout();
        recyclerView = swipeRecyclerView.getRecyclerView();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        initData();
        recycleViewAdapter = new RecycleViewAdapter(this, list);
        recyclerView.setAdapter(recycleViewAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        swipeRecyclerView.setOnSwipeRecyclerViewListener(new SwipeRecyclerView.OnSwipeRecyclerViewListener() {
            @Override
            public void onRefresh() {

                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        list.clear();
                        initData();
                        recycleViewAdapter.notifyDataSetChanged();
                    }
                }, 2000);
            }

            @Override
            public void onLoadNext() {
//                (new Handler()).postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
                initData();
                recycleViewAdapter.notifyDataSetChanged();
                swipeRecyclerView.onLoadFinish();
//                    }
//                },2000);

            }
        });
//        View view = LayoutInflater.from(this).inflate(R.layout.content_headview,null);
//        recycleViewAdapter.setHeaderView(view);
    }

    int i = 0;

    private void initData() {
        for (i=0;i<20;i++){
            list.add(i + "");
        }

    }

    //0则不执行拖动或者滑动
    ItemTouchHelper.Callback mCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT) {
        /**
         * @param recyclerView
         * @param viewHolder 拖动的ViewHolder
         * @param target 目标位置的ViewHolder
         * @return
         */
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();//得到拖动ViewHolder的position
            int toPosition = target.getAdapterPosition();//得到目标ViewHolder的position
            if (fromPosition < toPosition) {
                //分别把中间所有的item的位置重新交换
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(list, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(list, i, i - 1);
                }
            }
            recycleViewAdapter.notifyItemMoved(fromPosition, toPosition);
            //返回true表示执行拖动
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            list.remove(position);
            recycleViewAdapter.notifyItemRemoved(position);
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            super.onSelectedChanged(viewHolder, actionState);
            System.out.println("viewHolder = [" + viewHolder + "], actionState = [" + actionState + "]");

        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            System.out.println("recyclerView = [" + recyclerView + "], viewHolder = [" + viewHolder + "]");
        }
    };



}

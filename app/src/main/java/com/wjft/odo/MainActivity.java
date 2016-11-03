package com.wjft.odo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.leakcanary.RefWatcher;
import com.wjft.odo.bean.Bean;
import com.wjft.odo.util.OnClickListener;
import com.wjft.odo.util.UncaughtExceptionHandler;
import com.wjft.odo.util.VirtualKeyUtil;
import com.wjft.odo.view.pull_customview.PullActivity;
import com.wjft.odo.view.recyclerview_pullpush.DividerItemDecoration;
import com.wjft.odo.view.recyclerview_pullpush.SwipeRecyclerViewTest;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends Activity implements OnClickListener {

    @Bind(R.id.recy_main)
    RecyclerView recyMain;
    ArrayList<Bean> mBeens = new ArrayList<>();
    ArrayList<String> mStrings;
    Activity mActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mActivity = this;
        init();

        testPermissions();

        recyMain.setLayoutManager(new LinearLayoutManager(this));
        recyMain.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        BeanAdapter beanAdapter = new BeanAdapter();
        recyMain.setAdapter(beanAdapter);
        beanAdapter.setListener(this);

        mStrings.add("a");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        int l1 = VirtualKeyUtil.getNoHasVirtualKey(this);
        int l2 = VirtualKeyUtil.getHasVirtualKey(this);
        System.out.println("l1 = [" + l1 + "]" + "   l2 = [" + l2 + "]");
    }

    private void init() {
        mBeens.add(new Bean("Recyclerview的下拉刷新和上推加载+侧滑删除，长按拖拽", "下拉刷新采用SwipeRefreshLayout，上推加载采用自定义view，判断是否到达底部，并且判断是否是下拉。拖拽使用ItemTouchHelper"));
        mBeens.add(new Bean("可以填充任何view的下拉刷新布局，下拉刷新的头布局可以是自定义", "----------------------"));
    }


    @Override
    public void onClick(View v, int pos) {
        switch (pos) {
            case 0:
                startActivity(new Intent(this, SwipeRecyclerViewTest.class));
                break;
            case 1:
                startActivity(new Intent(this, PullActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


    class BeanAdapter extends RecyclerView.Adapter<BeanAdapter.VH> {
        OnClickListener mListener;

        public void setListener(OnClickListener listener) {
            mListener = listener;
        }

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = MainActivity.this.getLayoutInflater().inflate(R.layout.item_main, parent, false);
            VH vh = new VH(view);
            return vh;
        }

        @Override
        public void onBindViewHolder(final VH holder, final int position) {
            holder.bt.setText(mBeens.get(position).getTitle());
            holder.tv.setText(mBeens.get(position).getContent());
            if (mListener != null) {
                holder.bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onClick(holder.bt, position);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return mBeens.size();
        }

        class VH extends RecyclerView.ViewHolder {
            View mView;
            TextView tv;
            Button bt;

            public VH(View itemView) {
                super(itemView);
                mView = itemView;
                tv = (TextView) mView.findViewById(R.id.tv_main);
                bt = (Button) itemView.findViewById(R.id.bt_item);
            }
        }
    }

    public void testPermissions() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                System.out.println("拒绝但是没有选择不再提示");
                ActivityCompat.requestPermissions(mActivity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        0x1);
            } else {
                System.out.println(" 第一次   或者    拒绝并且不再提示");
                ActivityCompat.requestPermissions(mActivity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        0x1);
            }
        } else {
            System.out.println(" 允许之后  再次调用 ");
            setErrorHandler();
        }
    }

    private void setErrorHandler() {

        Thread.setDefaultUncaughtExceptionHandler(
                new UncaughtExceptionHandler(getApplicationContext(), Thread.getDefaultUncaughtExceptionHandler()));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0x1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setErrorHandler();
                } else {
                    Toast.makeText(this, "PERMISSION_deny", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}

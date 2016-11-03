package com.wjf.wxdemo.adapter;

import android.app.Activity;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wjf.wxdemo.R;

import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */
public class SecondLVAdapter extends RecyclerView.Adapter<SecondLVAdapter.VH> {
    List<String> mList;
    Activity mActivity;
    LayoutInflater mInflater;
    onClickListener mOnClickListener;

    public void setOnClickListener(onClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public SecondLVAdapter(Activity activity, List<String> list) {
        mActivity = activity;
        mList = list;
        mInflater = mActivity.getLayoutInflater();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_lv_second, parent, false);
        VH vh = new VH(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final VH holder, final int position) {
        holder.tv.setText(mList.get(position));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickListener.onClick(position, holder.mView);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class VH extends RecyclerView.ViewHolder {
        View mView;
        TextView tv;

        public VH(View itemView) {
            super(itemView);
            mView = itemView;
            tv = (TextView) mView.findViewById(R.id.tv_name);
        }
    }

    public interface onClickListener {
        void onClick(int pos, View view);
    }
}

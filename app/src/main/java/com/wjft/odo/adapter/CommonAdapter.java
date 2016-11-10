package com.wjft.odo.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wjft.odo.R;

import java.util.List;

/**
 * Created by Administrator on 2016/11/8.
 */
public class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.ViewHolder> {
    Activity mActivity;
    List<String> mStrings;
    LayoutInflater mInflater;

    public CommonAdapter(Activity activity, List<String> strings) {
        mActivity = activity;
        mStrings = strings;
        mInflater = mActivity.getLayoutInflater();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View View =  mInflater.inflate(R.layout.item_recy_vp, parent, false);
        ViewHolder viewHolder = new ViewHolder(View);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv.setText(mStrings.get(position));
    }

    @Override
    public int getItemCount() {
        return mStrings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            tv = (TextView) mView.findViewById(R.id.textView);
        }
    }
}

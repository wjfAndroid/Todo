package com.wjft.odo.view.multitype;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wjft.odo.R;

import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by Administrator on 2016/11/15.
 */
public class CateGoryViewProvider extends ItemViewProvider<CateGory, CateGoryViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_category, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull CateGory cateGory) {
        holder.tv.setText(cateGory.txt);

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        View mView;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            tv = (TextView) itemView.findViewById(R.id.tv_category);
        }
    }
}

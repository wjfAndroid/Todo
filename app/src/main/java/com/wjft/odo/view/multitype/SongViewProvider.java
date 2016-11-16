package com.wjft.odo.view.multitype;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wjft.odo.R;

import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by Administrator on 2016/11/15.
 */
public class SongViewProvider extends ItemViewProvider<Song, SongViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Song song) {
        holder.tv.setText(song.name);
        Glide.with(holder.mContext).load(song.img).into(holder.iv);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView iv;
        View mView;
        Context mContext;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            tv = (TextView) mView.findViewById(R.id.tv);
            iv = (ImageView) mView.findViewById(R.id.iv);
            mContext = mView.getContext();
        }
    }
}

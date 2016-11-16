package com.wjft.odo.view.multitype;

import android.support.annotation.NonNull;

import me.drakeet.multitype.Item;

/**
 * Created by Administrator on 2016/11/15.
 */
public class CateGory implements Item {
    @NonNull
    public String txt;

    public CateGory(@NonNull String txt) {
        this.txt = txt;
    }
}

package com.wjft.odo.view.multitype;

import me.drakeet.multitype.Item;

/**
 * Created by Administrator on 2016/11/15.
 */
public class Song implements Item {
   public String name;
   public int img;

    public Song(String name, int img) {
        this.name = name;
        this.img = img;
    }
}

package com.wjft.odo.bean;

import android.widget.TextView;

/**
 * Created by Administrator on 2016/10/24.
 */
public class Bean {
    String title;
    String content;

    public Bean(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}

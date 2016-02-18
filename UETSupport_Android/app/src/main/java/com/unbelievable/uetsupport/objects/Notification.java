package com.unbelievable.uetsupport.objects;

import java.util.Date;

/**
 * Created by Nam on 11/22/2015.
 */
public class Notification {
    public String title;
    public String content;
    public boolean important;
    public Date createTime;

    public Notification(String title, String content, boolean important, Date createTime) {
        this.title = title;
        this.content = content;
        this.important = important;
        this.createTime = createTime;
    }
}

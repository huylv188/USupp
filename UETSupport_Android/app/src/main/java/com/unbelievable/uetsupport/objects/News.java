package com.unbelievable.uetsupport.objects;

import com.unbelievable.uetsupport.common.CommonUtils;

import org.json.JSONObject;

/**
 * Created by huylv on 20/11/2015.
 */
public class News {
    public String title;
    public String content;
    public String photo;
    public String createdTime;

    public News() {}

    public static News getNews(JSONObject jData) {
        try {
            News news = new News();
            news.title = CommonUtils.getValidString(jData.getString("title"));
            news.content = CommonUtils.getValidString(jData.getString("content"));
            news.photo = CommonUtils.getValidString(jData.getString("photo"));
            news.createdTime = CommonUtils.getValidString(jData.getString("createdTime"));

            return news;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

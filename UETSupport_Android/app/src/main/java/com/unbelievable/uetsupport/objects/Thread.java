package com.unbelievable.uetsupport.objects;

import android.util.Log;

import com.unbelievable.uetsupport.R;
import com.unbelievable.uetsupport.common.CommonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Nam on 11/21/2015.
 */
public class Thread {
    public long threadId;
    public long userId;

    public String title;
    public String content;
    public String status;
    public Photo[] photos;
    public String totalLike;
    public String totalUnlike;
    public String comment;
    public String userName;
    public String avatar;
    public ArrayList<Comment> comments;
    public String createdTime;
    public String modifiedTime;

    public Thread() {}
    public static Thread getThread(JSONObject jData){
        try {
            Thread thread = new Thread();
            thread.threadId = jData.getLong("threadId");
            thread.title = CommonUtils.getValidString(jData.getString("title"));
            thread.content = CommonUtils.getValidString(jData.getString("content"));
            thread.status = CommonUtils.getValidString(jData.getString("status"));
                JSONArray jArray = jData.getJSONArray("photos");
                thread.photos = new Photo[jArray.length()];
                for (int i = 0; i < jArray.length(); i++) {
                    thread.photos[i] = Photo.getPhoto(jArray.getJSONObject(i));
                }
            thread.totalLike = (jData.getString("totalLike").equals("null"))? "0": jData.getString("totalLike");
            thread.totalUnlike = (jData.getString("totalUnlike").equals("null"))? "0":jData.getString("totalUnlike");
            thread.userName = CommonUtils.getValidString(jData.getString("username"));
            thread.avatar = CommonUtils.getValidString(jData.getString("avatar"));
            thread.createdTime = CommonUtils.getValidString(jData.getString("createdTime"));
            thread.modifiedTime = CommonUtils.getValidString(jData.getString("modifiedTime"));
            return thread;
        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }
}

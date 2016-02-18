package com.unbelievable.uetsupport.objects;

import com.unbelievable.uetsupport.common.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Nam on 11/21/2015.
 */
public class Comment {
    public long commentId;
    public long threadId;
    public long userID;

    public String content;
    public String photo;
    public boolean isCorrect;
    public String  username;
    public String avatar;
    public String createdTime;
    public String modifiedTime;

    public Comment() {}

    public static Comment getComment(JSONObject jData){
        try {
            Comment comment = new Comment();
            comment.commentId = jData.getLong("commentId");
            comment.content = CommonUtils.getValidString(jData.getString("content"));
            comment.photo = jData.getString("photo");
            comment.username = jData.getString("username");
            comment.isCorrect = jData.getBoolean("isCorrect");
            comment.avatar = jData.getString("avatar");
            comment.createdTime = jData.getString("createdTime");
            comment.modifiedTime = jData.getString("modifiedTime");
            return comment;
        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }
}

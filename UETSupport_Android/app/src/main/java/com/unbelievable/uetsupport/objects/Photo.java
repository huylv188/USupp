package com.unbelievable.uetsupport.objects;

import com.unbelievable.uetsupport.common.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by DucAnhZ on 22/11/2015.
 */
public class Photo {
    public String photoUrl;
    public String photoDescription;

    public Photo() {}
    public static Photo getPhoto(JSONObject jData){
        try {
            Photo photo = new Photo();
            photo.photoUrl = CommonUtils.getValidString(jData.getString("photoUrl"));
            photo.photoDescription = CommonUtils.getValidString(jData.getString("photoDescription"));
            return photo;
        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }
}

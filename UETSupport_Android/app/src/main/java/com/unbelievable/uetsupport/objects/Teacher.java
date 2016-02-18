package com.unbelievable.uetsupport.objects;

import android.util.Log;

import com.unbelievable.uetsupport.common.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by DucAnhZ on 20/11/2015.
 */
public class Teacher {

    public Long teacherId;
    public int avatar;

    public String fullname;
    public String email;
    public String phone;
    public String address;
    public String description;

    public String facultyName;

    public Teacher(String fullname, String phone) {
        this.fullname = fullname;
        this.phone = phone;
    }
    public Teacher(){};
    public static Teacher getTeacher(JSONObject jObject){
        try{
            Teacher teacher = new Teacher();
            teacher.teacherId = jObject.getLong("teacherId");
            teacher.fullname = CommonUtils.getValidString(jObject.getString("fullname"));
            teacher.email = CommonUtils.getValidString(jObject.getString("email"));
            teacher.phone = CommonUtils.getValidString(jObject.getString("phone"));
            teacher.address = CommonUtils.getValidString(jObject.getString("address"));
            teacher.description = CommonUtils.getValidString(jObject.getString("description"));
            teacher.facultyName = CommonUtils.getValidString(jObject.getString("faculty"));
            return teacher;
        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    public int getAvatar() {

        return avatar;
    }

    public String getFullname() {
        return fullname;
    }

    public String getPhone() {
        return phone;
    }
}

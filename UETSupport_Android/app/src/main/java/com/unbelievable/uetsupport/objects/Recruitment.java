package com.unbelievable.uetsupport.objects;

import com.unbelievable.uetsupport.common.CommonUtils;

import org.json.JSONObject;

/**
 * Created by Nam on 11/21/2015.
 */
public class Recruitment {
    public String title;
    public String content;
    public int logo;
    public String jobType;
    public int quantity;
    public String salary;
    public String createdTime;

    public Recruitment(String title, int logo, String jobType, int quantity, String salary) {
        this.title = title;
//        this.content = content;
        this.logo = logo;
        this.jobType = jobType;
        this.quantity = quantity;
        this.salary = salary;
//        this.createdTime = createdTime;
    }

    //    public static Recruitment getNews(JSONObject jData) {
//        try {
//            Recruitment recruitment = new Recruitment();
//            recruitment.title = CommonUtils.getValidString(jData.getString("title"));
//            recruitment.content = CommonUtils.getValidString(jData.getString("content"));
//            recruitment.logo = CommonUtils.getValidString(jData.getString("photo"));
//            recruitment.createdTime = CommonUtils.getValidString(jData.getString("createdTime"));
//            return recruitment;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getLogo() {
        return logo;
    }

    public String getJobType() {
        return jobType;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getSalary() {
        return salary;
    }

    public String getCreatedTime() {
        return createdTime;
    }
}

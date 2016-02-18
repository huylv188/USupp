package com.unbelievable.uetsupport.objects;

import com.unbelievable.uetsupport.common.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nam on 11/22/2015.
 */
public class Department {
    private long departmentId;
    public String name;
    public String description;
    public String address;

    public Department() {}

    public static Department getDepartment(JSONObject jObject){
        try {
            Department department = new Department();
            department.departmentId = jObject.getLong("departmentId");
            department.name = CommonUtils.getValidString(jObject.getString("name"));
            department.description = CommonUtils.getValidString(jObject.getString("description"));
            department.address = CommonUtils.getValidString(jObject.getString("address"));
            return department;
        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

}

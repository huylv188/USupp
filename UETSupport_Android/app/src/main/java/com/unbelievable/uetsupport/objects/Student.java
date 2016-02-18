package com.unbelievable.uetsupport.objects;

import java.util.ArrayList;

/**
 * Created by DucAnhZ on 20/11/2015.
 */
public class Student  {

    public Long studentId;
    public String username;
    public String password;
    public String fullname;
    public String gender;
    public String email;
    public String otherEmail;
    public String avatar;
    public String phone;
    public String birthday;
    public String description;

    public String address;
    public String placeOfBirth;
    public String role;
    public String ethnic;			// Dan toc
    public String religion;			// Ton giao
    public String country;			// Quoc gia
    public String nationality;		// 	Quoc tich
    public String indentityCard;	//	So CMT
    public String daysForIdentityCards; // Ngay cap CMT
    public String placeForIdentityCards; // Noi cap CMT

    public String courseName;

    public String className;

    public ArrayList<Schedule> schedules;

    public ArrayList<Reminder> reminders;

}
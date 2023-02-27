package com.java_new.newjava.request.user;

import java.util.Date;

import org.springframework.data.annotation.Id;

import com.java_new.newjava.model.user.Gender;

public class UserCreateReq {
    public String id;
    public String name;
    public String mail;
    public String password;
    public String rePassword;
    public String gender;
    public String dob;
    public Double height;
    public Double weight;


}

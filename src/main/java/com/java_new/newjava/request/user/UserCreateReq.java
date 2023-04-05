package com.java_new.newjava.request.user;

import java.util.Date;

import org.springframework.data.annotation.Id;

import com.java_new.newjava.model.user.Gender;

import jakarta.validation.constraints.NotNull;

public class UserCreateReq {
    public String id;
    @NotNull(message="name isnamenull")
    public String name;
    @NotNull(message="mail is null")
    public String mail;
    @NotNull(message="password is null")
    public String password;
    @NotNull(message="password-re is null")
    public String rePassword;
    @NotNull(message="gender is null")
    public String gender;
    @NotNull(message="dob is null")
    public Double dob;
    @NotNull(message="height is null")
    public Double height;
    @NotNull(message="weight is null")
    public Double weight;
    @NotNull(message="level is null")
    public String level;


}

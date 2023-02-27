package com.java_new.newjava.model.user;

import org.springframework.data.mongodb.core.mapping.Document;

import com.java_new.newjava.request.user.UserCreateReq;
import com.java_new.newjava.service.SequenceGeneratorService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "user")
public class User {
    @Transient
    public static final String userShortcode = "USR";

    @Id
    public String id;
    public String name;
    public String mail;
    public String password;
    public String rePassword;
    public Gender gender;
    public String dob;
    public Double height;
    public Double weight;
    public Date created;
    public Date updated;
    public Boolean exist;




    public User(UserCreateReq userCreateReq) {
        this.id = SequenceGeneratorService.generateSequence(userShortcode);
        this.name = userCreateReq.name;
        this.mail = userCreateReq.mail;
        this.password = userCreateReq.password;
        this.rePassword = userCreateReq.rePassword;
        this.gender = Gender.valueOf(userCreateReq.gender);
        this.dob = userCreateReq.dob;
        this.height = userCreateReq.height;
        this.weight = userCreateReq.weight;
        if(userCreateReq.id!=null){
            this.updated=new Date();
        }
        this.created=new Date();
    }

}

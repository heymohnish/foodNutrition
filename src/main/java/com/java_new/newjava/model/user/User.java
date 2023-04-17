package com.java_new.newjava.model.user;

import org.springframework.data.mongodb.core.mapping.Document;

import com.java_new.newjava.model.food.FoodCal;
import com.java_new.newjava.model.food.FoodModel;
import com.java_new.newjava.request.user.UserCreateReq;
import com.java_new.newjava.service.SequenceGeneratorService;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lombok.*;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
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
    public Double dob;
    public Double height;
    public Double weight;
    public Double neck;
    public Double waist;
    public Double bmr;
    public Double bmi;
    public Double activeBMR;
    public String level;
    public String bmiNote;
    public Double lossCal;
    public Double gainCal;
    public Date created;
    public Date updated;
    public Boolean exist;
    public Integer totalCal;
    public Double total_ENERC_KCAL;
    public Double total_PROCNT;
    public Double total_FAT;
    public Double total_CHOCDF;
    public Double total_FIBTG;
    public List<FoodCal> foodList;
    public List<Object> tryd;
    public List<FoodModel> foodModel;
    // created



    public User(UserCreateReq userCreateReq) {
        if (userCreateReq.id == null)
            this.id = SequenceGeneratorService.generateSequence(userShortcode);
        this.name = userCreateReq.name;
        this.mail = userCreateReq.mail;
        this.password = userCreateReq.password;
        this.rePassword = userCreateReq.rePassword;
        this.gender = Gender.valueOf(userCreateReq.gender);
        this.dob = userCreateReq.dob;
        this.height = userCreateReq.height;
        this.weight = userCreateReq.weight;
        if (userCreateReq.id != null) {
            this.updated = new Date();
        }
        this.bmi = caculateBMI(userCreateReq.height, userCreateReq.weight);
        if (this.bmi < 18.5) {
            this.bmiNote = "You are Under Wight";
        }
        if (this.bmi >= 18.5 && this.bmi <= 24.9) {
            this.bmiNote = "You are Normal Wight";
        }
        if (this.bmi >= 25 && this.bmi <= 29.9) {
            this.bmiNote = "You are Over Wight";
        }
        if (this.bmi >= 30 && this.bmi <= 39.9) {
            this.bmiNote = "You are Obese Wight";
        }
        if (this.bmi >= 40) {
            this.bmiNote = "You are Over Obese";
        }
        this.level = userCreateReq.level;
        this.bmr = calculateBMR(userCreateReq.height, userCreateReq.weight, userCreateReq.gender, userCreateReq.dob);
        this.activeBMR = calculatActiveBMR(userCreateReq.level, this.bmr);
        this.created = new Date();
    }

    private Double calculatActiveBMR(String level2, Double bmr2) {
        Double activeBMR = 0.00;
        if (level2.equals( "Level-1 (Sedentary)")) {
            activeBMR = bmr2 * 1.2;
        }
        if (level2.equals( "Level-2 (Lightly active)")) {
            activeBMR = bmr2 * 1.375;
        }
        if (level2.equals( "Level-3 (Moderately active)")) {
            activeBMR = bmr2 * 1.55;
        }
        if (level2.equals( "Level-4 (Very active)")) {
            activeBMR = bmr2 * 1.725;
        }
        if (level2.equals( "Level-5 (Extra active)")) {
            activeBMR = bmr2 * 1.9;
        }
        return activeBMR;
    }

    private Double calculateBMR(Double height2, Double weight2, String gender2, Double dob) {
        Double BMR = 0.00;
        if (gender2.equals("male")) {
            BMR = 88.362 + (13.397 * weight2) + (4.799 * height2) - (5.677 * dob);
        } else {
            BMR = 447.593 + (9.247 * weight2) + (3.098 * height2) - (4.330 * dob);

        }
        return BMR;
    }

    private Double caculateBMI(Double height, Double weight) {
        DecimalFormat df = new DecimalFormat("0.00");
        Double BMI = Double.valueOf(df.format((100 * 100 * weight) / (height * height)));
        // df.format(BMI);
        return BMI;

    }

}

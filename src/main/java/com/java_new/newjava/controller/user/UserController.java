package com.java_new.newjava.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestTemplate;

import com.java_new.newjava.model.DataResponse;
import com.java_new.newjava.model.Food;
import com.java_new.newjava.model.arithmeticOperator;
import com.java_new.newjava.model.calculator;
import com.java_new.newjava.model.user.User;
import com.java_new.newjava.repository.FoodNutritionRep;
import com.java_new.newjava.repository.UserRepository;
import com.java_new.newjava.request.calculatorReq;
import com.java_new.newjava.request.user.UserCreateReq;
import com.java_new.newjava.service.user.UserService;

import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    FoodNutritionRep fodNutritionRep;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReactiveMongoTemplate mongoOperations;

    @GetMapping(value = "/check")
    public ResponseEntity<?> hello() {
        try {
            return new ResponseEntity<>(DataResponse.builder().data("hello").build(), HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(DataResponse.builder().error(ex.getMessage()).build(), HttpStatus.OK);
        }
    }
    @PostMapping(value = "/upsert")
    public ResponseEntity<?> userCreate(@RequestBody UserCreateReq userCreateReq) {
        try {
            UserService userService =new UserService(fodNutritionRep,userRepository,mongoOperations);
            User user = new User(userCreateReq);
            userService.checkuser(user.mail,null);
            userRepository.save(user).subscribe();
            // DataResponse.builder().data(attendanceService.getAllAttendances()).build(), HttpStatus.OK
            return new ResponseEntity<>(DataResponse.builder().data(user.id).build(), HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(DataResponse.builder().error(ex.getMessage()).build(), HttpStatus.OK);
        }
    }
    @GetMapping(value = "/exist")
    public ResponseEntity<?> userExist(@RequestParam(required = true, name = "mail") String mail,
    @RequestParam(required = false, name = "code") String code) {
        try {
            UserService userService =new UserService(fodNutritionRep,userRepository,mongoOperations);
            // userService.();
            User single =userService.checkuser(mail, code);
            return new ResponseEntity<>(DataResponse.builder().data(single).build(), HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(DataResponse.builder().error(ex.getMessage()).build(), HttpStatus.OK);
        }
    }
   
}


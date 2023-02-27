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

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping(value = "/upsert")
    public ResponseEntity<?> userCreate(@RequestBody UserCreateReq userCreateReq) {
        try {
            User user = new User(userCreateReq);
            userRepository.save(user).subscribe();
            return new ResponseEntity<>(user.id,
                    HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(DataResponse.builder().error(ex.getMessage()).build(), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/exist")
    public ResponseEntity<?> userExist(@RequestParam(required = true, name = "mail") String mail,
    @RequestParam(required = false, name = "code") String code) {
        try {
            Query query = new Query();
            List<Criteria> andCriterias = new ArrayList<>();
            Criteria criteria = new Criteria();
            mail = mail.replaceAll("\\s", "");
            andCriterias.add(Criteria.where("mail").in(mail));
            if (andCriterias.size() > 0)
                criteria.andOperator(andCriterias.toArray(new Criteria[andCriterias.size()]));

            query.addCriteria(criteria);
            List<User> user = mongoOperations.find(query, User.class)
                    .collectList()
                    .blockOptional().orElse(null);
            User single = new User();
            if(code!=null){
                if (user != null && user.size() > 0) {
                    single=user.get(0);
                    single.exist=true;
                    if(!single.password.equals(code)){
                        throw new RuntimeException("password is invalid for this mail");
                    }
                } else {
                    single.exist=false;
                }

            }
            else{
                if (user != null && user.size() > 0) {
                    throw new RuntimeException("mail exist");
                } else {
                    single.exist=false;
                }
            }
            
            return new ResponseEntity<>(single,
                    HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(DataResponse.builder().error(ex.getMessage()).build(), HttpStatus.OK);
        }
    }

}

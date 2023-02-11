package com.java_new.newjava.controller;
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
import com.java_new.newjava.repository.FoodNutritionRep;
import com.java_new.newjava.request.calculatorReq;
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
@RequestMapping("/calculator")
public class FoodNutritionController {
    @Autowired
    FoodNutritionRep fodNutritionRep;
    @Value("${foodNutrient.edamam.acces_url}")
    private String acces_url;
    
    @GetMapping(value = "/food/{foodName}")
    public ResponseEntity<?> test (@PathVariable(name = "foodName") String foodName) {
        try {
            RestTemplate restTemplate=new RestTemplate();
           String url=acces_url+foodName;
           Food food=restTemplate.getForObject(url,Food.class);
        fodNutritionRep.save(food).subscribe();
            return new ResponseEntity<>(food,
                    HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(DataResponse.builder().error(ex.getMessage()).build(), HttpStatus.OK);
        }
    }
}

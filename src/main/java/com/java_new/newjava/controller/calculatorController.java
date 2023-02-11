package com.java_new.newjava.controller;
import org.springframework.beans.factory.annotation.Autowired;
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
public class calculatorController {
    @Autowired
    FoodNutritionRep fodNutritionRep;
    
    // RestTemplate restTemplate;

    
    @GetMapping(value = "/hello")
    public ResponseEntity<?> calculate (@RequestBody calculatorReq cal) {
        try {
            // if(calculate.mode.equals(arithmeticOperator.addition)){
            //     // return 
            // }
            calculator calc=new  calculator(cal);
            System.out.println(calc.result);
            // name mm=new name();
            // mm.moni="mohnish;";
            return new ResponseEntity<>(calc,
                    HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(DataResponse.builder().error(ex.getMessage()).build(), HttpStatus.OK);
        }
    }
    @GetMapping(value = "/food/{foodName}")
    public ResponseEntity<?> test (@PathVariable(name = "foodName") String foodName) {
        try {
           RestTemplate restTemplate=new RestTemplate();
           String url="https://api.edamam.com/api/food-database/v2/parser?app_id=5c0445e4&app_key=afbfae66102861e2893849edd07c2c4b&ingr="+foodName;
           Food food=restTemplate.getForObject(url,Food.class);
        //    food
        fodNutritionRep.save(food).subscribe();
            return new ResponseEntity<>(food,
                    HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(DataResponse.builder().error(ex.getMessage()).build(), HttpStatus.OK);
        }
    }


    @GetMapping(value = "/joji")
    public ResponseEntity<calculator> calculater (@RequestBody calculatorReq cal) {
        HttpHeaders headers = new HttpHeaders();
        calculator user=new calculator(cal);
        ResponseEntity<calculator> entity = new ResponseEntity<>(user,headers,HttpStatus.OK);
        return entity;
    }
    // @GetMapping(value = "/food/{type}")
    // public ResponseEntity<calculator> calculaterFood (@PathVariable(name = "orderId") String orderId) {
    //     HttpHeaders headers = new HttpHeaders();
    //     // calculator user=new calculator();
    //     // getForObject 
    //     // ResponseEntity<calculator> entity = restTemplate.getForObject("https://api.edamam.com/api/food-database/v2/parser?app_id=5c0445e4&app_key=afbfae66102861e2893849edd07c2c4b&ingr=tomato", HttpMethod.POST,null,
    //     // calculator.class);
    //     return "";
    // }
}

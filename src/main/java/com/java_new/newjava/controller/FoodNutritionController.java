package com.java_new.newjava.controller;

// import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
// import org.springframework.boot.configurationprocessor.json.JSONObject;
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

import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.Gson;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import com.java_new.newjava.model.EdmanPost;
import com.java_new.newjava.model.DataResponse;
import com.java_new.newjava.model.Food;
import com.java_new.newjava.model.FoodHints;
import com.java_new.newjava.model.FoodMain;
import com.java_new.newjava.model.Ingredients;
import com.java_new.newjava.model.arithmeticOperator;
import com.java_new.newjava.model.calculator;
import com.java_new.newjava.model.food.NutritionDataResponse;
import com.java_new.newjava.model.food.NutritionResponse;
import com.java_new.newjava.repository.FoodNutritionRep;
import com.java_new.newjava.request.calculatorReq;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/food")
public class FoodNutritionController {
    @Autowired
    FoodNutritionRep fodNutritionRep;
    @Value("${foodNutrient.edamam.base_url}")
    private String base_url;
    @Value("${foodNutrient.edamam.access_key}")
    private String access_key;

    @GetMapping(value = "/name/{foodName}")
    public ResponseEntity<?> test(@PathVariable(name = "foodName") String foodName) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = base_url + "parser?" + access_key + foodName;
            Food food = restTemplate.getForObject(url, Food.class);
            List<FoodHints> hints = new ArrayList<>();
            List<FoodMain> listFood = new ArrayList<>();
            if (food != null && food.hints.size() > 0) {
                hints = food.hints;
                int p = 0;
                System.out.println(hints.size());
                hints.forEach(each -> {
                    Boolean check = false;
                    for (int i = 0; i < each.measures.size(); i++) {
                        if (each.measures.get(i).label.equals("Gram")) {
                            check = true;
                        }
                    }
                    if (check) {
                        listFood.add(each.food);
                        // System.out.println("-----" + p + 1);
                    }

                });
            }

            return new ResponseEntity<>(DataResponse.builder().data(listFood).build(), HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(DataResponse.builder().error(ex.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/post")
    public ResponseEntity<?> userCreate(@RequestParam(required = false, name = "mail") String mail,
            @RequestParam(required = false, name = "code") String code) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            EdmanPost token = new EdmanPost();
            token.ingredients = new ArrayList<>();
            Ingredients ingredientsModel = new Ingredients();
            ingredientsModel.quantity = 100;
            ingredientsModel.foodId = "food_bjsfxtcaidvmhaa3afrbna43q3hu";
            ingredientsModel.measureURI = "http://www.edamam.com/ontologies/edamam.owl#Measure_gram";
            token.ingredients.add(ingredientsModel);
            System.out.println(token);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<EdmanPost> requestEntity = new HttpEntity<EdmanPost>(token, headers);
            String url = base_url + "nutrients?" + access_key;
            // try {
            ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
                    Object.class);
            Gson gson = new Gson();
            String json = gson.toJson(result);
            // System.out.println(json);
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(json).getAsJsonObject();
            NutritionDataResponse jj = gson.fromJson(json, NutritionDataResponse.class);
            System.out.println(jj);
            // } catch (Exception e) {
            // System.out.println(e);
            // // TODO: handle exception
            // }

            return new ResponseEntity<>(jj.body, HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(DataResponse.builder().error(ex.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/postFood")
    public ResponseEntity<?> postFood(@RequestParam(required = true, name = "userId") String userId,
            @RequestParam(required = true, name = "qty") Integer qty,
            @RequestParam(required = true, name = "foodId") String foodId) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            EdmanPost token = new EdmanPost();
            token.ingredients = new ArrayList<>();
            Ingredients ingredientsModel = new Ingredients();
            ingredientsModel.quantity = qty;
            ingredientsModel.foodId = foodId;
            ingredientsModel.measureURI = "http://www.edamam.com/ontologies/edamam.owl#Measure_gram";
            token.ingredients.add(ingredientsModel);
            System.out.println(token);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<EdmanPost> requestEntity = new HttpEntity<EdmanPost>(token, headers);
            String url = base_url + "nutrients?" + access_key;
            ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
                    Object.class);
            return new ResponseEntity<>(result.getBody(), HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(DataResponse.builder().error(ex.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }
}

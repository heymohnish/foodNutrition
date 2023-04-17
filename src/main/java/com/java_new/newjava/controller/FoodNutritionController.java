package com.java_new.newjava.controller;

// import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Transient;
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
import com.java_new.newjava.model.Nutrients;
import com.java_new.newjava.model.arithmeticOperator;
import com.java_new.newjava.model.calculator;
import com.java_new.newjava.model.food.FoodCal;
import com.java_new.newjava.model.food.FoodModel;
import com.java_new.newjava.model.food.NutritionDataResponse;
import com.java_new.newjava.model.food.NutritionResponse;
import com.java_new.newjava.model.food.TotalNutrients;
import com.java_new.newjava.model.user.User;
import com.java_new.newjava.repository.FoodCalRepository;
import com.java_new.newjava.repository.FoodNutritionRep;
import com.java_new.newjava.repository.UserRepository;
import com.java_new.newjava.request.calculatorReq;
import com.java_new.newjava.request.food.FoodMainReq;
import com.java_new.newjava.service.SequenceGeneratorService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/food")
public class FoodNutritionController {
    @Autowired
    FoodNutritionRep fodNutritionRep;
    @Autowired
    FoodCalRepository foodCalRepository;
    @Autowired
    UserRepository userRepository;
    @Value("${foodNutrient.edamam.base_url}")
    private String base_url;
    @Value("${foodNutrient.edamam.access_key}")
    private String access_key;
    @Transient
    public static final String foodShortcode = "FD";

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
                        if (each.measures.get(i) != null && each.measures.get(i).label != null
                                && each.measures.get(i).label.equals("Gram")) {
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
            ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
                    Object.class);
            Gson gson = new Gson();
            String json = gson.toJson(result);
            NutritionDataResponse jj = gson.fromJson(json, NutritionDataResponse.class);
            FoodCal foodCal = new FoodCal();
            foodCal.nutrition = jj.body;
            foodCal.id = "ds";

            foodCalRepository.save(foodCal).subscribe();
            return new ResponseEntity<>(jj.body, HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(DataResponse.builder().error(ex.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    // @PutMapping(value = "/postFood")
    // public ResponseEntity<?> postFood(
    //         @RequestParam(required = true, name = "qty") Integer qty,
    //         @RequestParam(required = true, name = "foodId") String foodId) {
    //     try {
    //         RestTemplate restTemplate = new RestTemplate();
    //         EdmanPost token = new EdmanPost();
    //         token.ingredients = new ArrayList<>();
    //         Ingredients ingredientsModel = new Ingredients();
    //         ingredientsModel.quantity = 100;
    //         ingredientsModel.foodId = foodId;
    //         ingredientsModel.measureURI = "http://www.edamam.com/ontologies/edamam.owl#Measure_gram";
    //         token.ingredients.add(ingredientsModel);
    //         System.out.println(token);
    //         HttpHeaders headers = new HttpHeaders();
    //         headers.setContentType(MediaType.APPLICATION_JSON);
    //         HttpEntity<EdmanPost> requestEntity = new HttpEntity<EdmanPost>(token, headers);
    //         String url = base_url + "nutrients?" + access_key;
    //         ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
    //                 Object.class);
    //         Gson gson = new Gson();
    //         String json = gson.toJson(result);
    //         NutritionDataResponse nutritionDataResponse = gson.fromJson(json, NutritionDataResponse.class);
    //         FoodCal foodCal = new FoodCal();
    //         foodCal.created = new Date();
    //         foodCal.id = SequenceGeneratorService.generateSequence(foodShortcode);
    //         // foodCal.nutrition = nutritionDataResponse.body;
    //         // nutritionDataResponse.body.
    //         // foodCal
    //         Map<String, TotalNutrients> tn = new HashMap<>();
    //         for (Map.Entry<String, TotalNutrients> entry : nutritionDataResponse.body.totalNutrients.entrySet()) {
    //             if (entry.getKey().contains(".")) {
    //                 System.out.println("-----------------");
    //             } else {
    //                 tn.put(entry.getKey(), entry.getValue());
    //             }
    //         }
    //         Map<String, TotalNutrients> dn = new HashMap<>();
    //         for (Map.Entry<String, TotalNutrients> entry : nutritionDataResponse.body.totalDaily.entrySet()) {
    //             if (entry.getKey().contains(".")) {
    //                 System.out.println("-----------------");
    //             } else {
    //                 dn.put(entry.getKey(), entry.getValue());
    //             }
    //         }
    //         nutritionDataResponse.body.totalDaily = dn;
    //         nutritionDataResponse.body.totalNutrients = tn;
    //         foodCal.nutrition = nutritionDataResponse.body;
    //         if (single.foodList == null || single.foodList.size() < 0) {
    //             single.foodList = new ArrayList<FoodCal>();
    //             single.foodList.add(foodCal);
    //         } else {
    //             single.foodList.add(foodCal);
    //         }
    //         single.totalCal += nutritionDataResponse.body.calories;
    //         userRepository.save(single).subscribe();
    //         return new ResponseEntity<>(DataResponse.builder().data(single).build(), HttpStatus.OK);
    //     } catch (Exception ex) {
    //         System.out.println(ex.getMessage());
    //         return new ResponseEntity<>(DataResponse.builder().error(ex.getMessage()).build(), HttpStatus.BAD_REQUEST);
    //     }
    // }

    @PostMapping(value = "/minipost")
    public ResponseEntity<?> postMinFood(@RequestBody FoodMainReq foodMainReq,
            @RequestParam(required = true, name = "userId") String userId,
            @RequestParam(required = true, name = "qty") Double qty) {
        try {
            User single = userRepository.findById(userId).blockOptional()
                    .orElseThrow(() -> new RuntimeException("Invalid trans Id " + userId));
            FoodModel fssai = new FoodModel();
            fssai.id = foodMainReq.foodId;
            fssai.qty = qty;
            fssai.lable = foodMainReq.label;
            fssai.time = new Date();
            if (single.foodModel == null || single.foodModel.size() < 0) {
                single.foodModel = new ArrayList<>();
                single.foodModel.add(fssai);
            } else {
                single.foodModel.add(fssai);
            }
            if (foodMainReq.nutrients != null) {
                Nutrients nutrients = foodMainReq.nutrients;
                if (nutrients.CHOCDF != null) {
                    if (single.total_CHOCDF == null) {
                        single.total_CHOCDF = 0.00;
                    }
                    single.total_CHOCDF += (nutrients.CHOCDF * (qty / 100));
                }
                if (nutrients.ENERC_KCAL != null) {
                    if (single.total_ENERC_KCAL == null) {
                        single.total_ENERC_KCAL = 0.00;
                    }
                    single.total_ENERC_KCAL += (nutrients.ENERC_KCAL * (qty / 100));
                }
                if (nutrients.PROCNT != null) {
                    if (single.total_PROCNT == null) {
                        single.total_PROCNT = 0.00;
                    }
                    single.total_PROCNT += (nutrients.PROCNT * (qty / 100));
                }
                if (nutrients.FAT != null) {
                    if (single.total_FAT == null) {
                        single.total_FAT = 0.00;
                    }
                    single.total_FAT += (nutrients.FAT * (qty / 100));
                }
                if (nutrients.FIBTG != null) {
                    if (single.total_FIBTG == null) {
                        single.total_FIBTG = 0.00;
                    }
                    single.total_FIBTG += (nutrients.FIBTG * (qty / 100));
                }

            }
            userRepository.save(single).subscribe();
            return new ResponseEntity<>(DataResponse.builder().data(single).build(), HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(DataResponse.builder().error(ex.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

}

package com.java_new.newjava.repository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.java_new.newjava.model.Food;

public interface FoodNutritionRep extends ReactiveMongoRepository<Food, String>{
    
    
}
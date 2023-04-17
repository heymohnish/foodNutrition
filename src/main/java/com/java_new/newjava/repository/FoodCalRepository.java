package com.java_new.newjava.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.java_new.newjava.model.food.FoodCal;

public interface FoodCalRepository extends ReactiveMongoRepository<FoodCal, String>{
    
    
}
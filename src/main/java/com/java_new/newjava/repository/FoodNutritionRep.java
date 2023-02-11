package com.java_new.newjava.repository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.java_new.newjava.model.Food;

import reactor.core.publisher.Mono;
public interface FoodNutritionRep extends ReactiveMongoRepository<Food, String>{
    
    
}
// public interface UserRepository extends ReactiveMongoRepository<User, String> {
//     Mono<User> findByUserName(String userName);

//     Mono<User> findByEmailId(String emailId);
// }
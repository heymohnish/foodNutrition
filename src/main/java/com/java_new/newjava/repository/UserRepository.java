package com.java_new.newjava.repository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.java_new.newjava.model.user.User;
public interface UserRepository extends ReactiveMongoRepository<User, String> {
    
}

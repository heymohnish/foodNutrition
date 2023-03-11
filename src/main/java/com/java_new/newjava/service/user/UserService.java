package com.java_new.newjava.service.user;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.java_new.newjava.model.user.User;
import com.java_new.newjava.repository.FoodNutritionRep;
import com.java_new.newjava.repository.UserRepository;
import com.java_new.newjava.request.user.UserCreateReq;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
public class UserService {
    private FoodNutritionRep fodNutritionRep;
    private UserRepository userRepository;
    private ReactiveMongoTemplate mongoOperations;

    public UserService(FoodNutritionRep fodNutritionRep, UserRepository userRepository,
            ReactiveMongoTemplate mongoOperations) {
            this.fodNutritionRep=fodNutritionRep;
            this.userRepository=userRepository;
            this.mongoOperations=mongoOperations;
    }

    public User checkuser(String mail,String code) {
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
        return single;
    }

    public User upsert(UserCreateReq userCreateReq) {
        User user = new User(userCreateReq);
        if(userCreateReq.id==null){
            checkuser(user.mail,null);
            userRepository.save(user).subscribe();
        }
        else{
            user.id=userCreateReq.id;
            userRepository.save(user).subscribe();
        }
        
       return user;

       
    }

    
}

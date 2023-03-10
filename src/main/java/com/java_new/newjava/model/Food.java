package com.java_new.newjava.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@Document(collection = "food")
public class Food {
    public String text;
    public List<FoodMainParsed> parsed;
    public List<FoodHints> hints;
    public LinkNext _links;
    // wfsvwv
}

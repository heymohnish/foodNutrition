package com.java_new.newjava.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
public class FoodMain {
    public String foodId;
    public String label;
    public String knownAs;
    public String category;
    public Nutrients nutrients;
    public String categoryLabel;
    public String image;
    //parsepost
    public Double quantity;
    public String measure;
    public Double weight;
    public Double retainedWeight;
    public String food;
    public String measureURI;
    public String status;


    
}

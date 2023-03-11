package com.java_new.newjava.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
public class FoodHints {

    public FoodMain food;
    public List<Measures> measures;
}

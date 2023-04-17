package com.java_new.newjava.request.food;

import com.java_new.newjava.model.Nutrients;

import jakarta.validation.constraints.NotNull;

public class FoodMainReq {
    @NotNull(message="id shoud not null")
    public String foodId;
    @NotNull(message="label shoud not null")
    public String label;
    public String knownAs;
    public String category;
    // @NotNull(message="label shoud not null")
    public Nutrients nutrients;
    public String categoryLabel;
    public String image;
}

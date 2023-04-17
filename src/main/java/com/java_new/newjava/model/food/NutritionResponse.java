package com.java_new.newjava.model.food;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class NutritionResponse {
    // public String status;
    public String uri;
    public Integer calories;
    public Integer totalWeight;
    public List<String> dietLabels;
    public List<String> healthLabels;
    public List<String> cautions;
    public Map<String,TotalNutrients> totalNutrients;
    public Map<String,TotalNutrients> totalDaily;
    public List<IngredientsObject> ingredients;
    
}

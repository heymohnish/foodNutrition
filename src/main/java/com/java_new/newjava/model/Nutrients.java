package com.java_new.newjava.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
public class Nutrients {

    public Double ENERC_KCAL;
    public Double PROCNT;
    public Double FAT;
    public Double CHOCDF;
    public Double FIBTG;
    
}

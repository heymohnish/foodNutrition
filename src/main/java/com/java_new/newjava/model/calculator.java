package com.java_new.newjava.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import com.java_new.newjava.request.calculatorReq;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class calculator {
    public String id;
    
    public Double a;
    public Double b;
    public Double c;
    public Double result;
    public arithmeticOperator mode;
    
    public calculator(calculatorReq calculate) {
        this.id = calculate.id;
        this.a = calculate.a;
        this.b = calculate.b;
        this.c = calculate.c;
        this.result=calculate.a+calculate.b;
        this.mode = calculate.mode;
    }

    // public calculator(calculatorReq calculate) {
    // }
    
}

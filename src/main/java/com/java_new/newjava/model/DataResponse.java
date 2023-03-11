package com.java_new.newjava.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
@JsonInclude(Include.NON_NULL)
public class DataResponse {
	public Object data;
	public Object error;
	public Object errors;

}
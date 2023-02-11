package com.java_new.newjava.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class DataResponse {
	public Object data;
	public Object error;
	public Object errors;

}
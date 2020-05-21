package com.demo.model;

import lombok.Data;

@Data
public class User {
	private Long id;
	private String name;
	private Integer age;
	private String idCard;

	public static UserDTO convert(User var){
		UserDTO dto = new UserDTO();
		dto.setName(var.getName());
		dto.setAge(var.getAge());
		return dto;
	}

	public final boolean finalMethod(){
		return true;
	}
}
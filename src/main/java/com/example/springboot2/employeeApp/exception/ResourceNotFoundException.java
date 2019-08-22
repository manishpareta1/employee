package com.example.springboot2.employeeApp.exception;

public class ResourceNotFoundException extends Throwable{
	
	public ResourceNotFoundException(String message){
		super(message);
	}

}

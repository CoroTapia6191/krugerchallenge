package com.bcoronel.inventory.util;

public class PersonNotFounException extends RuntimeException{

	private String id;
	
	public PersonNotFounException(String id) {
		super("Person not found: "+id);
	}
}

package com.bcoronel.inventory.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationReq implements Serializable{
	private String username;
	private String password;
	

}

package com.bcoronel.inventory.user.service;

import org.springframework.http.ResponseEntity;

import com.bcoronel.inventory.models.AuthenticationReq;
import com.bcoronel.inventory.models.TokenJWT;
import com.bcoronel.inventory.models.UserDtoChPasswor;
import com.bcoronel.inventory.models.Userkrug;


public interface IUserService {
	
	public boolean createuser(Userkrug usr);
	public void addUserRole(String username, String rolname);
	public TokenJWT authenticateUSer(AuthenticationReq credentials);
	public void changepassword(UserDtoChPasswor user);
	

}

package com.bcoronel.inventory.user.controller;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bcoronel.inventory.models.AuthenticationReq;
import com.bcoronel.inventory.models.TokenJWT;
import com.bcoronel.inventory.models.UserDtoChPasswor;
import com.bcoronel.inventory.models.UserkrugRoles;
import com.bcoronel.inventory.user.service.IUserService;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@RequestMapping("/userservice")
@Slf4j
public class UserController {
	@Autowired
	private IUserService userservice;

	

	@PostMapping("/addRoleToUser")
	@ApiOperation(value = "Set new user rol by username and rol name object. Allowed rol name options:'ADMINISTRADOR' or 'EMPLEADO' (Role administrador required)")
	@PreAuthorize("hasAuthority('ADMINISTRADOR')")
	public ResponseEntity addRole(@RequestBody UserkrugRoles userrol) {
		userservice.addUserRole(userrol.getIduser(), userrol.getRolname());
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/changepassword")
	@ApiOperation(value = "Change default user password (Role administrador required)")
	@PreAuthorize("hasAuthority('ADMINISTRADOR')")
	public ResponseEntity changePassword(@RequestBody UserDtoChPasswor userp) {
		userservice.changepassword(userp);
		return ResponseEntity.ok().build();
		
	}


	@PostMapping("/login")
	@ApiOperation(value = "Request authentication by user credentials, token acces is generated if credentials are correct")
	public ResponseEntity<TokenJWT> loginUser(@RequestBody AuthenticationReq credentials) {
		log.info("validate user "+credentials.getUsername());
		TokenJWT token = userservice.authenticateUSer(credentials);
		return ResponseEntity.ok(token);//
	}

}

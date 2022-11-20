package com.bcoronel.inventory.role.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bcoronel.inventory.user.service.UserServiceImp;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roleservice")
public class RoleControllerImp {
	private final UserServiceImp userservice;
	@PostMapping("/roletouser")
	@ApiOperation(value = "Set new user rol by username and rol name, allowed rol name options:'ADMINISTRADOR' or 'EMPLEADO' (Role administrador required)")
	@PreAuthorize("hasAuthority('ADMINISTRADOR')")
	public ResponseEntity addRolUSer(String username, String rolname) {
		userservice.addUserRole(username, rolname);
		return new ResponseEntity("User role updated",HttpStatus.OK);
	}

}

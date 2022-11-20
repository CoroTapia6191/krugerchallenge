package com.bcoronel.inventory.role.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcoronel.inventory.models.Roles;
import com.bcoronel.inventory.person.repo.IPersonRepository;
import com.bcoronel.inventory.role.repo.IRoleRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleServiceImp implements IRoleService{
	
	IRoleRepository rolerepo;

	@Override
	public Roles findRoleByName(String name) {
		// TODO Auto-generated method stub
		return rolerepo.findByName(name);
	}

}

package com.bcoronel.inventory.role.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bcoronel.inventory.models.Roles;

public interface IRoleRepository extends JpaRepository<Roles, Long>{
	public Roles findByName(String name);
}

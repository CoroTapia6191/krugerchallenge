package com.bcoronel.inventory.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bcoronel.inventory.models.Userkrug;

public interface IUserRepository extends JpaRepository<Userkrug,String>{

}

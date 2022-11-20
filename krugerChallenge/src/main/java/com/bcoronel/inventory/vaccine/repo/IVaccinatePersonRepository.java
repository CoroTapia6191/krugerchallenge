package com.bcoronel.inventory.vaccine.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bcoronel.inventory.models.Vaccineperson;
import com.bcoronel.inventory.models.VaccinepersonPk;

public interface IVaccinatePersonRepository extends JpaRepository<Vaccineperson, VaccinepersonPk> {
	

}

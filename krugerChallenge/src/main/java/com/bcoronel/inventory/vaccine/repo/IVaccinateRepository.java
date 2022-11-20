package com.bcoronel.inventory.vaccine.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bcoronel.inventory.models.Vaccine;

public interface IVaccinateRepository extends JpaRepository<Vaccine, Integer>{

}

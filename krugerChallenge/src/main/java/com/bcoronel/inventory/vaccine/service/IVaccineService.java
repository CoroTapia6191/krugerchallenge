package com.bcoronel.inventory.vaccine.service;

import java.util.List;

import com.bcoronel.inventory.models.Vaccine;
import com.bcoronel.inventory.models.Vaccineperson;

public interface IVaccineService {

	boolean saveVaccinePerson(Vaccineperson vacineperson);
	public List<Vaccine> getAllVaccines();
	
}

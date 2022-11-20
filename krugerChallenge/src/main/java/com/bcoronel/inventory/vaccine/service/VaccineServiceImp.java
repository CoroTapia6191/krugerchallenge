package com.bcoronel.inventory.vaccine.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bcoronel.inventory.models.Vaccine;
import com.bcoronel.inventory.models.Vaccineperson;
import com.bcoronel.inventory.vaccine.repo.IVaccinatePersonRepository;
import com.bcoronel.inventory.vaccine.repo.IVaccinateRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class VaccineServiceImp implements IVaccineService{
	@Autowired
	private IVaccinatePersonRepository vaccinatePersonRepository;
	@Autowired
	private IVaccinateRepository vaccinateRepository;
	private static Logger log = LoggerFactory.getLogger(VaccineServiceImp.class);


	@Override
	public boolean saveVaccinePerson(Vaccineperson vaccineperson) {
		try {
			vaccinatePersonRepository.save(vaccineperson);
			return true;
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			return false;
		}	
	}


	@Override
	public List<Vaccine> getAllVaccines() {
		 return vaccinateRepository.findAll();
	}
	
	

}

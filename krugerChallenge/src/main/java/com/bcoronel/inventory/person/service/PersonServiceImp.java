package com.bcoronel.inventory.person.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bcoronel.inventory.models.Person;
import com.bcoronel.inventory.models.Userkrug;
import com.bcoronel.inventory.person.controller.PersonController;
import com.bcoronel.inventory.person.repo.IPersonRepository;
import com.bcoronel.inventory.role.repo.IRoleRepository;
import com.bcoronel.inventory.user.repo.IUserRepository;
import com.bcoronel.inventory.vaccine.repo.IVaccinatePersonRepository;
import com.bcoronel.inventory.vaccine.service.IVaccineService;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PersonServiceImp implements IPersonaService {
	@Autowired
	private BCryptPasswordEncoder encoder;
	private IPersonRepository personRepository;
	private IUserRepository userrepo;
	private IRoleRepository rolereposi;
	private IVaccinatePersonRepository vaccineperson;
	private IRoleRepository rolerepository;
	@Override
	public void insertPerson(Person person) {
		personRepository.save(person);
		Userkrug user = new Userkrug();
		user.setUsername(person.getId());
		user.setPassword(encoder.encode(person.getId()));
		user.getRoles().add(rolereposi.findByName("EMPLEADO"));
		userrepo.save(user);

	}

	@Override
	public List<Person> findAll() {

		return personRepository.findAll();
	}

	@Override
	public Person findById(String id) {

		return personRepository.findById(id).orElse(null);

	}

	@Override
	public List<Person> findVaccinatedList() {

		return null;
	}

	@Override
	public List<Person> findNoVaccinatedByState(String state) {
		if (state.equals("YES")) {
			return personRepository.getAllVaccinated();

		} else if (state.equals("NO")) {
			return personRepository.getAllNotVaccinated();
		} else {
			return new ArrayList<Person>();
		}

	}

	@Override
	public List<Person> findByVaccineType(Integer type) {

		return personRepository.findByVaccinateType(type);
	}

	@Override
	public List<Person> findByVaccineDate(Date finit, Date ffin) {
		return personRepository.findByVaccinateDate(finit, ffin);
	}

	@Override
	public void updatePErson(Person person) {
		personRepository.save(person);
		
	}

	@Override
	public void insertPersonAdmin(Person person) {
		personRepository.save(person);
		Userkrug user = new Userkrug();
		user.setUsername(person.getId());
		user.setPassword(encoder.encode(person.getId()));
		user.getRoles().add(rolereposi.findByName("ADMINISTRADOR"));
		userrepo.save(user);
		
	}

//	@Override
//	public void deleteData(String id) {
//		//remover rreferencias
//		vaccineperson.deleteVaccineDataByIdPErson(id);
//		Userkrug usr = userrepo.findById(id).get();
//		usr.setRoles(null);
//		userrepo.save(usr);
//		//
//		personRepository.deleteById(id);
//		userrepo.deleteById(id);
//		
//	}

}

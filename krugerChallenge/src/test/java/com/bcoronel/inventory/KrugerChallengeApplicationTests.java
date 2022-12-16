package com.bcoronel.inventory;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bcoronel.inventory.models.Person;
import com.bcoronel.inventory.models.Userkrug;
import com.bcoronel.inventory.person.repo.IPersonRepository;
import com.bcoronel.inventory.person.service.IPersonaService;
import com.bcoronel.inventory.user.repo.IUserRepository;

@SpringBootTest
class KrugerChallengeApplicationTests {
// 0103818399 0105682082 0107144578 0104072616  0106564867
	@Autowired
	IPersonaService personservice;
	
	@Autowired
	IUserRepository userrepository;
	
	@Autowired
	IPersonRepository personrepository;
	
	@Test
	public void createPersonUser() {
		Person person = new Person("0106564867","JUAN", "EMPLEADO", "challenge@bcoronel.com", null, null, null);
		personservice.insertPerson(person);
		Userkrug user = userrepository.findById(person.getId()).get();
		
		assertTrue(user.getUsername().equals(person.getId()));
		//mi comentario desde gitglow feature
	}
	
/*	@Test
	public void updatePErson() {
		
		Person person = new Person("0106564867","PEDRO", "EMPLEADO", "challenge@bcoronel.com", null, null, "09842758585");
		personservice.updatePErson(person);
		assertTrue(person.getPhonenumber().equals(personrepository.findById(person.getId()).get().getPhonenumber()));
	}*/

}

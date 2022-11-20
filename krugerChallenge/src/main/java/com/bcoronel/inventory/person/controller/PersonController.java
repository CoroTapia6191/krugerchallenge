package com.bcoronel.inventory.person.controller;

import java.sql.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bcoronel.inventory.models.Person;
import com.bcoronel.inventory.models.Userkrug;
import com.bcoronel.inventory.person.service.IPersonaService;
import com.bcoronel.inventory.person.service.PersonServiceImp;
import com.bcoronel.inventory.person.service.UtilPersonaService;
import com.bcoronel.inventory.role.service.IRoleService;
import com.bcoronel.inventory.security.SecurityConfig;
import com.bcoronel.inventory.security.util.JwtUtilService;
import com.bcoronel.inventory.user.service.UserServiceImp;
import com.bcoronel.inventory.util.PersonNotFounException;
import com.bcoronel.inventory.util.ValidateStrings;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/personservice")
public class PersonController {

	private IPersonaService personservice;
	private IRoleService roleservice;
	private ValidateStrings auxstrings;
	private UtilPersonaService utilPersonaService;
	private UserServiceImp userseriImp;
	private JwtUtilService jwtservice;
	private static Logger LOG = LoggerFactory.getLogger(PersonController.class);

	@GetMapping("/getPersonCurrentState/{state}")
	@PreAuthorize("hasAuthority('ADMINISTRADOR')")
	@ApiOperation(value = "Get persons by state: vaccinated or not, use flag 'YES' or 'NOT' (Role administrador required)")
	public ResponseEntity<List<Person>> getPersonByState(@PathVariable String state) {
		return new ResponseEntity<List<Person>>(personservice.findNoVaccinatedByState(state), HttpStatus.OK);
	}

	@GetMapping("/getAll")
	@PreAuthorize("hasAuthority('ADMINISTRADOR')")
	@ApiOperation(value = "Get all persons on database (Role administrador required)")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "List of persons") })
	public ResponseEntity<List<Person>> getall() {
		return new ResponseEntity<>(personservice.findAll(), HttpStatus.OK);
	}

	@PostMapping("/new")
	@PreAuthorize("hasAuthority('ADMINISTRADOR')")
	@ApiOperation(value = "Create a new person in database and a new user with required fields (id, name, lastname,email),created user has username=person id and default password= person id,(Role administrador required)")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Person created"),
			@ApiResponse(code = 400, message = "Validation error in object")

	})
	public ResponseEntity<Boolean> newPerson(@RequestBody Person person) {
		LOG.info("new person event");
		if (!utilPersonaService.validateRequieredFields(person.getId(), person.getName(), person.getLastname(),
				person.getEmail())) {
			return new ResponseEntity("Required Fields(id, name, lastname, email)", HttpStatus.BAD_REQUEST);
		} else if (!utilPersonaService.validarCedEcuador(person.getId())) {
			return new ResponseEntity("Incorrect id (go to documentation) ", HttpStatus.BAD_REQUEST);
		} else if (!utilPersonaService.validateEmail(person.getEmail())) {
			return new ResponseEntity("Incorrect email ", HttpStatus.BAD_REQUEST);
		} else if (!auxstrings.validChar(person.getName())) {
			return new ResponseEntity("Name cannot contains numbers or special characters ", HttpStatus.BAD_REQUEST);
		} else if (!auxstrings.validChar(person.getLastname())) {
			return new ResponseEntity("Lastname cannot contains numbers or special characters ",
					HttpStatus.BAD_REQUEST);
		}
		LOG.info("validations OK");
		personservice.insertPerson(person);
		// create user
		/*
		 * Userkrug user = new Userkrug(); user.setUsername(person.getId());
		 * user.setPassword(encoder.encode(person.getId()));
		 * user.getRoles().add(roleservice.findRoleByName("EMPLEADO"));
		 * userseriImp.createuser(user);
		 */
		return new ResponseEntity("Person created", HttpStatus.CREATED);
	}

	@PutMapping("update")
	@ApiOperation(value = "Update a personal information of a person in database")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Person updated"),
			@ApiResponse(code = 400, message = "Person not found or id is not provided") })
	public ResponseEntity updatePerson(@RequestBody Person person) {
		if (person.getId() != null) {
			Person currentperson = personservice.findById(person.getId());
			if (currentperson != null) {
				currentperson.setBirthdate(person.getBirthdate());
				currentperson.setAddress(person.getAddress());
				currentperson.setPhonenumber(person.getPhonenumber());
				personservice.updatePErson(currentperson);
				return new ResponseEntity("Person updated", HttpStatus.OK);
			} else {
				return new ResponseEntity("Person not found", HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity("Id not found", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/getPErsonByVaccineType/{type}")
	@ApiOperation(value = "Get a list of person filtered by type of vaccine (Role administrador required)", notes = "Example: http://localhost:8080/personservice/getPErsonByVaccineType/YES" )
	@PreAuthorize("hasAuthority('ADMINISTRADOR')")
	public ResponseEntity<List<Person>> getPersonByVaccine(@PathVariable Integer type) {
		return new ResponseEntity<List<Person>>(personservice.findByVaccineType(type), HttpStatus.OK);
	}
	//http://localhost:8080/personservice/getPersonByVaccineDate/2022-11-18/2022-11-30
	@GetMapping("/getPersonByVaccineDate/{finit}/{ffin}")
	@ApiOperation(value = "Get a list of person filtered by date of vaccination (Role administrador required)", notes = "Example: http://localhost:8080/personservice/getPersonByVaccineDate/2022-11-18/2022-11-30 ")
	@PreAuthorize("hasAuthority('ADMINISTRADOR')")
	public ResponseEntity<List<Person>> getPersonByVaccineDate(@PathVariable Date finit, @PathVariable Date ffin) {
		return new ResponseEntity<List<Person>>(personservice.findByVaccineDate(finit, ffin), HttpStatus.OK);
	}
	
	@GetMapping("/getPersonById/{id}")
	@ApiOperation(value = "Get information of current user session (session token is veryfied with entered id)")
	public ResponseEntity <Person>getPErsonById(@PathVariable String id,@RequestHeader (name="Authorization") String token) {
		String username= jwtservice.extractUsername(token.replace("Bearer", "").trim());
		if (username.equals(id)) {
		Person person = personservice.findById(id);
		if (person != null) {
			return new ResponseEntity<Person>(person,HttpStatus.OK);
		}else {
			throw new PersonNotFounException(id);
		}
		}else {
			return new ResponseEntity("Failed to verify your id session",HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/newAdmin")
	@ApiOperation(value = "ONLY FOR DEVELOPMENT ENVIRONMENT, DO NOT DEPLOY THIS WS IN PRODUCTION ENVIRONMENT."
			+ " CREATES A NEW USER:9999999999 AND PASSWORD:9999999999  ")
	public ResponseEntity<Boolean> newAdmin() {
		LOG.info("new person event");
		Person admin = new Person("9999999999", "ADMINISTRADOR", "KRUGGER", "EXAMPLE@GMAIL.COM", null, null, null);
		LOG.info("validations OK");
		personservice.insertPersonAdmin(admin);
		// create user
		/*
		 * Userkrug user = new Userkrug(); user.setUsername(person.getId());
		 * user.setPassword(encoder.encode(person.getId()));
		 * user.getRoles().add(roleservice.findRoleByName("EMPLEADO"));
		 * userseriImp.createuser(user);
		 */
		return new ResponseEntity("Person created", HttpStatus.CREATED);
	}
	
	
	

}

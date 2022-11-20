package com.bcoronel.inventory.vaccine.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bcoronel.inventory.models.Vaccine;
import com.bcoronel.inventory.models.Vaccineperson;
import com.bcoronel.inventory.vaccine.service.IVaccineService;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/vaccinateservice")
public class VaccinatePersonController {
	
	private final IVaccineService vaccineservice;
	@ApiOperation(value = "AAllows you to record a person's vaccination")
	@PostMapping("/registerVaccination")
	public ResponseEntity registerVaccinate(@RequestBody Vaccineperson vaccineperson) {
		if(vaccineservice.saveVaccinePerson(vaccineperson)) {
			return new ResponseEntity("Vaccine registered",HttpStatus.CREATED);
		}else {
			return new ResponseEntity("Vaccine not registered",HttpStatus.BAD_REQUEST);
		}
		
	}
	@GetMapping("/getAll")
	@ApiOperation(value = "Get all available vaccines in database")
	public ResponseEntity<List<Vaccine>> registerEntity() {
	
		return new ResponseEntity<>(vaccineservice.getAllVaccines(),HttpStatus.OK);
	}

}

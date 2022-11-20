package com.bcoronel.inventory.models;

import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vaccineperson {
	@EmbeddedId
	private VaccinepersonPk vaccinepersonPk;
	
	private Date vaccinationdate;
	

}

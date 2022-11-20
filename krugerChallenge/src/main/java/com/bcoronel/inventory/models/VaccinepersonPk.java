package com.bcoronel.inventory.models;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VaccinepersonPk implements Serializable {
	
	private Integer idvaccine;
	private String idperson;
	private Integer vaccinenumber; 
	
	

}

package com.bcoronel.inventory.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
	
	@Id
	@Column(name="id", length=10)
	private String id;
	

	@Column(name="name", length=40  )
	private String name;
	
	
	@Column(name="lastname", length=40  )
	private String lastname;
	

	@Column(name="email", length=50  )
	private String email;
	
	@Column(name="birthdate")
	private Date birthdate;
	
	@Column(name="address",length=70)
	private String address;
	
	@Column(name="phonenumber",length=20)
	private String phonenumber;

}

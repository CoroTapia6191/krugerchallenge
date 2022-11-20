package com.bcoronel.inventory.person.service;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bcoronel.inventory.models.Person;


@Service
public interface IPersonaService {
    public void insertPerson(Person person);
    public void  insertPersonAdmin(Person person);
    public void updatePErson(Person person);
    public List<Person> findAll();
    public Person findById(String id);
    public List<Person> findVaccinatedList();
    public List<Person> findNoVaccinatedByState(String state);
    public List<Person> findByVaccineType(Integer type);
    public List<Person> findByVaccineDate(Date finit, Date ffin);
   
}

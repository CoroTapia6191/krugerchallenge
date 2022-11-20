package com.bcoronel.inventory.person.repo;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.bcoronel.inventory.models.Person;

public interface IPersonRepository extends JpaRepository<Person, String> {

	@Query(value = "select * from person where id in (select distinct(idperson) from vaccineperson)", nativeQuery = true)
	public List<Person> getAllVaccinated();

	@Query(value = "select * from person where id not in (select distinct(idperson) from vaccineperson)", nativeQuery = true)
	public List<Person> getAllNotVaccinated();

	@Query(value = "select * from person where id in (select distinct(idperson) from vaccineperson where idvaccine = :type )", nativeQuery = true)
	public List<Person> findByVaccinateType(@Param("type") Integer type);

	@Query(value = "select * from person where id in (select distinct(idperson) from vaccineperson where vaccinationdate between :finit and :ffin)", nativeQuery = true)
	public List<Person> findByVaccinateDate(@Param("finit") Date finit, @Param("ffin") Date ffin);

}

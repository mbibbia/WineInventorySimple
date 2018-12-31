package ch.bibbias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.bibbias.bean.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

	Country findByCode(String code);

	Country findByName(String name);

}

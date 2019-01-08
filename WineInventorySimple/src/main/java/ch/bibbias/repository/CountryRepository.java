package ch.bibbias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.bibbias.bean.Country;

/**
 * 
 * @author Marco Bibbia
 * 
 *         Repository interface for country beans extends JpaRepository
 *         and adds bean specific methods
 *
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

	Country findByCode(String code);

	Country findByName(String name);

}

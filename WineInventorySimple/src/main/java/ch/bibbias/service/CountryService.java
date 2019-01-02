package ch.bibbias.service;

import org.springframework.stereotype.Service;

import ch.bibbias.bean.Country;
import ch.bibbias.generic.GenericService;

/**
 * 
 * @author Marco Bibbia
 * 
 *         Service interface for country beans extends interface
 *         GenericService and adds bean specific methods
 *
 */
@Service
public interface CountryService extends GenericService<Country> {

	Country findByCode(String code);

	Country findByName(String name);

}

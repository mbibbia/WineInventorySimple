package ch.bibbias.service;

import org.springframework.stereotype.Service;

import ch.bibbias.bean.Country;
import ch.bibbias.generic.GenericService;

@Service
public interface CountryService extends GenericService<Country> {

	Country findByCode(String code);

}

package ch.bibbias.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.bibbias.bean.Country;
import ch.bibbias.repository.CountryRepository;
import ch.bibbias.service.CountryService;


/**
 * 
 * @author Marco Bibbia
 * 
 *         Persistence Service for country beans implements interface
 *         CountryService, uses CountryRepository and implements bean specific
 *         methods.
 *
 */
@Component
public class CountryServiceImpl implements CountryService {

	@Autowired
	private CountryRepository repository;

	/**
	 * Creates a new object.
	 */
	@Override
	public Country save(Country entity) {
		return repository.save(entity);
	}

	@Override
	public Country update(Country entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(Country entity) {
		repository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public void deleteInBatch(List<Country> entities) {
		repository.deleteInBatch(entities);
	}

	@Override
	public Country find(Long id) {
		return repository.getOne(id);
	}

	@Override
	public List<Country> findAll() {
		return repository.findAll();
	}

	@Override
	public Country findByCode(String code) {
		return repository.findByCode(code);
	}

	@Override
	public Country findByName(String name) {
		return repository.findByName(name);
	}

}

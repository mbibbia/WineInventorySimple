package ch.bibbias.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.bibbias.bean.Country;
import ch.bibbias.bean.Producer;
import ch.bibbias.bean.Region;
import ch.bibbias.repository.ProducerRepository;
import ch.bibbias.service.ProducerService;

/**
 * 
 * @author Marco Bibbia
 * 
 *         Persistence Service for country beans implements interface
 *         ProducerService, uses ProducerRepository and implements bean specific
 *         methods.
 *
 */
@Component
public class ProducerServiceImpl implements ProducerService {

	@Autowired
	private ProducerRepository repository;

	@Override
	public Producer save(Producer entity) {
		return repository.save(entity);
	}

	@Override
	public Producer update(Producer entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(Producer entity) {
		repository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public void deleteInBatch(List<Producer> entities) {
		repository.deleteInBatch(entities);
	}

	@Override
	public Producer find(Long id) {
		return repository.getOne(id);
	}

	@Override
	public List<Producer> findAll() {
		return repository.findAll();
	}

	@Override
	public Producer findByName(String name) {
		return repository.findByName(name);
	}

	@Override
	public Producer findByCompany(String company) {
		return repository.findByCompany(company);
	}

	@Override
	public List<Producer> findByPlace(String place) {
		return repository.findByPlace(place);
	}

	@Override
	public List<Producer> findByCountry(Country country) {
		return repository.findByCountry(country);
	}

	@Override
	public List<Producer> findByRegion(Region region) {
		return repository.findByRegion(region);
	}

}

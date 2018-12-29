package ch.bibbias.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.bibbias.bean.Classification;
import ch.bibbias.bean.Country;
import ch.bibbias.bean.Producer;
import ch.bibbias.bean.Region;
import ch.bibbias.bean.Wine;
import ch.bibbias.bean.WineType;
import ch.bibbias.repository.WineRepository;
import ch.bibbias.service.WineService;

@Component
public class WineServiceImpl implements WineService {

	@Autowired
	private WineRepository repository;

	@Override
	public Wine save(Wine entity) {
		return repository.save(entity);
	}

	@Override
	public Wine update(Wine entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(Wine entity) {
		repository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public void deleteInBatch(List<Wine> entities) {
		repository.deleteInBatch(entities);
	}

	@Override
	public Wine find(Long id) {
		return repository.getOne(id);
	}

	@Override
	public List<Wine> findAll() {
		return repository.findAll();
	}

	@Override
	public List<Wine> findByCountry(Country country) {
		return repository.findByCountry(country);
	}

	@Override
	public List<Wine> findByRegion(Region region) {
		return repository.findByRegion(region);
	}

	@Override
	public List<Wine> findByClassification(Classification classification) {
		return repository.findByClassification(classification);
	}

	@Override
	public List<Wine> findByType(WineType wineType) {
		return repository.findByType(wineType);
	}

	@Override
	public List<Wine> findByProducer(Producer producer) {
		return repository.findByProducer(producer);
	}

}

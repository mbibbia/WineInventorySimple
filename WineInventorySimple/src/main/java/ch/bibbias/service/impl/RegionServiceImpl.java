package ch.bibbias.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.bibbias.bean.Country;
import ch.bibbias.bean.Region;
import ch.bibbias.repository.RegionRepository;
import ch.bibbias.service.RegionService;

@Component
public class RegionServiceImpl implements RegionService {

	@Autowired
	private RegionRepository repository;

	@Override
	public Region save(Region entity) {
		return repository.save(entity);
	}

	@Override
	public Region update(Region entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(Region entity) {
		repository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public void deleteInBatch(List<Region> entities) {
		repository.deleteInBatch(entities);
	}

	@Override
	public Region find(Long id) {
		return repository.getOne(id);
	}

	@Override
	public List<Region> findAll() {
		return repository.findAll();
	}

	@Override
	public Region findByName(String name) {
		return repository.findByName(name);
	}

	@Override
	public List<Region> findByCountry(Country country) {
		return repository.findByCountry(country);
	}

}

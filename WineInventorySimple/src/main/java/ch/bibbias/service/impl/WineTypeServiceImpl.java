package ch.bibbias.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.bibbias.bean.WineType;
import ch.bibbias.repository.WineTypeRepository;
import ch.bibbias.service.WineTypeService;

@Component
public class WineTypeServiceImpl implements WineTypeService {

	@Autowired
	private WineTypeRepository repository;

	@Override
	public WineType save(WineType entity) {
		return repository.save(entity);
	}

	@Override
	public WineType update(WineType entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(WineType entity) {
		repository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public void deleteInBatch(List<WineType> entities) {
		repository.deleteInBatch(entities);
	}

	@Override
	public WineType find(Long id) {
		return repository.getOne(id);
	}

	@Override
	public List<WineType> findAll() {
		return repository.findAll();
	}

	@Override
	public WineType findByName(String name) {
		return repository.findByName(name);
	}

}

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
	private WineTypeRepository wineTypeRepository;

	@Override
	public WineType save(WineType entity) {
		return wineTypeRepository.save(entity);
	}

	@Override
	public WineType update(WineType entity) {
		return wineTypeRepository.save(entity);
	}

	@Override
	public void delete(WineType entity) {
		wineTypeRepository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		wineTypeRepository.deleteById(id);
	}

	@Override
	public void deleteInBatch(List<WineType> entities) {
		wineTypeRepository.deleteInBatch(entities);
	}

	@Override
	public WineType find(Long id) {
		return wineTypeRepository.getOne(id);
	}

	@Override
	public List<WineType> findAll() {
		return wineTypeRepository.findAll();
	}

}

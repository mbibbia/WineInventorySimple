package ch.bibbias.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import ch.bibbias.bean.Wine;
import ch.bibbias.repository.WineRepository;
import ch.bibbias.service.WineService;

public class WineServiceImpl implements WineService {

	@Autowired
	private WineRepository wineRepository;

	@Override
	public Wine save(Wine entity) {
		return wineRepository.save(entity);
	}

	@Override
	public Wine update(Wine entity) {
		return wineRepository.save(entity);
	}

	@Override
	public void delete(Wine entity) {
		wineRepository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		wineRepository.deleteById(id);
	}

	@Override
	public void deleteInBatch(List<Wine> entities) {
		wineRepository.deleteInBatch(entities);
	}

	@Override
	public Wine find(Long id) {
		return wineRepository.getOne(id);
	}

	@Override
	public List<Wine> findAll() {
		return wineRepository.findAll();
	}

}

package ch.bibbias.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.bibbias.bean.Classification;
import ch.bibbias.repository.ClassificationRepository;
import ch.bibbias.service.ClassificationService;

@Component
public class ClassificationServiceImpl implements ClassificationService {

	@Autowired
	private ClassificationRepository repository;

	@Override
	public Classification save(Classification entity) {
		return repository.save(entity);
	}

	@Override
	public Classification update(Classification entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(Classification entity) {
		repository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public void deleteInBatch(List<Classification> entities) {
		repository.deleteInBatch(entities);
	}

	@Override
	public Classification find(Long id) {
		return repository.getOne(id);
	}

	@Override
	public List<Classification> findAll() {
		return repository.findAll();
	}

}

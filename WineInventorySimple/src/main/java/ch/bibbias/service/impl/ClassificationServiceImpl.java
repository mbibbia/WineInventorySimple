package ch.bibbias.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.bibbias.bean.Classification;
import ch.bibbias.repository.ClassificationRepository;
import ch.bibbias.service.ClassificationService;

/**
 * 
 * @author Marco Bibbia
 * 
 *         Persistence Service for classification beans implements interface
 *         ClassificationService, uses ClassificationRepository and implements
 *         bean specific methods.
 *
 */

@Component
public class ClassificationServiceImpl implements ClassificationService {

	@Autowired
	private ClassificationRepository repository;

	/**
	 * Creates new object.
	 */
	@Override
	public Classification save(Classification entity) {
		return repository.save(entity);
	}

	/**
	 * Updates existing object
	 */
	@Override
	public Classification update(Classification entity) {
		return repository.save(entity);
	}

	/**
	 * Deletes an object
	 */
	@Override
	public void delete(Classification entity) {
		repository.delete(entity);
	}

	/**
	 * Deletes object with this id
	 */
	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	/**
	 * Deletes multiple objects
	 */
	@Override
	public void deleteInBatch(List<Classification> entities) {
		repository.deleteInBatch(entities);
	}

	/**
	 * Find an object by id
	 */
	@Override
	public Classification find(Long id) {
		return repository.getOne(id);
	}

	/**
	 * Find all objects
	 */
	@Override
	public List<Classification> findAll() {
		return repository.findAll();
	}

	/**
	 * Find an object by name
	 */
	@Override
	public Classification findByName(String name) {
		return repository.findByName(name);
	}

}

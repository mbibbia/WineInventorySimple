package ch.bibbias.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.bibbias.bean.Image;
import ch.bibbias.repository.ImageRepository;
import ch.bibbias.service.ImageService;

/**
 * 
 * @author Marco Bibbia
 * 
 *         Persistence Service for image beans implements interface
 *         ImageService, uses ImageRepository and implements bean specific
 *         methods.
 *
 */

@Component
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ImageRepository repository;

	/**
	 * Creates new object.
	 */
	@Override
	public Image save(Image entity) {
		return repository.save(entity);
	}

	/**
	 * Updates existing object
	 */
	@Override
	public Image update(Image entity) {
		return repository.save(entity);
	}

	/**
	 * Deletes an object
	 */
	@Override
	public void delete(Image entity) {
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
	public void deleteInBatch(List<Image> entities) {
		repository.deleteInBatch(entities);
	}

	/**
	 * Find an object by id
	 */
	@Override
	public Image find(Long id) {
		return repository.getOne(id);
	}

	/**
	 * Find all objects
	 */
	@Override
	public List<Image> findAll() {
		return repository.findAll();
	}

	/**
	 * Find an object by name
	 */
	@Override
	public Image findByName(String name) {
		return repository.findByName(name);
	}

}

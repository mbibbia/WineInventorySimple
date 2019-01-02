package ch.bibbias.generic;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * 
 * @author Marco Bibbia
 *
 * @param <T>
 * 
 *        Interface with generic persistence methods
 * 
 */

@Service
public interface GenericService<T extends Object> {

	T save(T entity);

	T update(T entity);

	void delete(T entity);

	void delete(Long id);

	void deleteInBatch(List<T> entities);

	T find(Long id);

	List<T> findAll();

}

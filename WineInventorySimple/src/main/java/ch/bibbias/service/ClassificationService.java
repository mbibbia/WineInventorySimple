package ch.bibbias.service;

import org.springframework.stereotype.Service;

import ch.bibbias.bean.Classification;
import ch.bibbias.generic.GenericService;

/**
 * 
 * @author Marco Bibbia
 * 
 *         Service interface for classification beans extends interface
 *         GenericService and adds bean specific methods
 *
 */
@Service
public interface ClassificationService extends GenericService<Classification> {

	Classification findByName(String name);

}

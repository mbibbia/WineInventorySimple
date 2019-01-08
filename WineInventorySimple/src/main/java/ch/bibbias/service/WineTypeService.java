package ch.bibbias.service;

import org.springframework.stereotype.Service;

import ch.bibbias.bean.WineType;
import ch.bibbias.generic.GenericService;

/**
 * 
 * @author Marco Bibbia
 * 
 *         Service interface for wine type beans extends interface
 *         GenericService and adds bean specific methods
 *
 */
@Service
public interface WineTypeService extends GenericService<WineType> {

	WineType findByName(String name);

}

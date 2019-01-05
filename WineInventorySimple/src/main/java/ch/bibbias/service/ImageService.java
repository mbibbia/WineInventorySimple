package ch.bibbias.service;

import org.springframework.stereotype.Service;

import ch.bibbias.bean.Image;
import ch.bibbias.generic.GenericService;

/**
 * 
 * @author Marco Bibbia
 * 
 *         Service interface for image beans extends interface GenericService
 *         and adds bean specific methods
 *
 */
@Service
public interface ImageService extends GenericService<Image> {

	Image findByName(String name);

}

package ch.bibbias.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ch.bibbias.bean.Country;
import ch.bibbias.bean.Producer;
import ch.bibbias.bean.Region;
import ch.bibbias.generic.GenericService;

/**
 * 
 * @author Marco Bibbia
 * 
 *         Service interface for producer beans extends interface GenericService
 *         and adds bean specific methods
 *
 */
@Service
public interface ProducerService extends GenericService<Producer> {

	Producer findByName(String name);

	Producer findByCompany(String company);

	List<Producer> findByPlace(String place);

	List<Producer> findByCountry(Country country);

	List<Producer> findByRegion(Region region);

}

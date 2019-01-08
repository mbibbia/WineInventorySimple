package ch.bibbias.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ch.bibbias.bean.Country;
import ch.bibbias.bean.Region;
import ch.bibbias.generic.GenericService;

/**
 * 
 * @author Marco Bibbia
 * 
 *         Service interface for region beans extends interface GenericService
 *         and adds bean specific methods
 *
 */
@Service
public interface RegionService extends GenericService<Region> {

	Region findByName(String name);

	List<Region> findByCountry(Country country);

}

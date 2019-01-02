package ch.bibbias.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ch.bibbias.bean.Classification;
import ch.bibbias.bean.Country;
import ch.bibbias.bean.Producer;
import ch.bibbias.bean.Region;
import ch.bibbias.bean.Wine;
import ch.bibbias.bean.WineType;
import ch.bibbias.generic.GenericService;

/**
 * 
 * @author Marco Bibbia
 * 
 *         Service interface for wine beans extends interface GenericService and
 *         adds bean specific methods
 *
 */
@Service
public interface WineService extends GenericService<Wine> {

	List<Wine> findByCountry(Country country);

	List<Wine> findByRegion(Region region);

	List<Wine> findByClassification(Classification classification);

	List<Wine> findByType(WineType wineType);

	List<Wine> findByProducer(Producer producer);

}

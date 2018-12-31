package ch.bibbias.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ch.bibbias.bean.Country;
import ch.bibbias.bean.Producer;
import ch.bibbias.bean.Region;
import ch.bibbias.generic.GenericService;

@Service
public interface ProducerService extends GenericService<Producer> {

	Producer findByName(String name);

	Producer findByCompany(String company);

	List<Producer> findByPlace(String place);

	List<Producer> findByCountry(Country country);

	List<Producer> findByRegion(Region region);

}

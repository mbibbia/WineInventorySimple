package ch.bibbias.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.bibbias.bean.Classification;
import ch.bibbias.bean.Country;
import ch.bibbias.bean.Producer;
import ch.bibbias.bean.Region;
import ch.bibbias.bean.Wine;
import ch.bibbias.bean.WineType;

/**
 * 
 * @author Marco Bibbia
 * 
 *         Repository interface for wine beans extends JpaRepository and adds
 *         bean specific methods
 *
 */
@Repository
public interface WineRepository extends JpaRepository<Wine, Long> {

	List<Wine> findByCountry(Country country);

	List<Wine> findByRegion(Region region);

	List<Wine> findByClassification(Classification classification);

	List<Wine> findByType(WineType wineType);

	List<Wine> findByProducer(Producer producer);

}

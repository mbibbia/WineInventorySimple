package ch.bibbias.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.bibbias.bean.Country;
import ch.bibbias.bean.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

	Region findByName(String name);

	List<Region> findByCountry(Country country);

}

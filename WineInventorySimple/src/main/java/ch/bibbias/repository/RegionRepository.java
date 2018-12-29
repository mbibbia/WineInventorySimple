package ch.bibbias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.bibbias.bean.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

}

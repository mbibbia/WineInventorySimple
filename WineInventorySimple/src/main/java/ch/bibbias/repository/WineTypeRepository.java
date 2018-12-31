package ch.bibbias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.bibbias.bean.WineType;

@Repository
public interface WineTypeRepository extends JpaRepository<WineType, Long> {

	WineType findByName(String name);

}

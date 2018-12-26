package ch.bibbias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.bibbias.bean.Wine;

@Repository
public interface WineRepository extends JpaRepository<Wine, Long> {
	

}

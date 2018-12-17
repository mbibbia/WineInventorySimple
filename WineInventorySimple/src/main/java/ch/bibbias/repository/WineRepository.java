package ch.bibbias.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.bibbias.bean.Wine;

public interface WineRepository extends JpaRepository<Wine, Long> {
	

}

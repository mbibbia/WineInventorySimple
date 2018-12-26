package ch.bibbias.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.bibbias.bean.WineType;

public interface WineTypeRepository extends JpaRepository<WineType, Long> {

}

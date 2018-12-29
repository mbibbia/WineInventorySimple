package ch.bibbias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.bibbias.bean.Classification;

@Repository
public interface ClassificationRepository extends JpaRepository<Classification, Long> {

}

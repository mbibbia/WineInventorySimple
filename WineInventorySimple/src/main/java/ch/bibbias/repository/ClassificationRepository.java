package ch.bibbias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.bibbias.bean.Classification;

/**
 * 
 * @author Marco Bibbia
 * 
 *         Repository interface for classification beans extends JpaRepository
 *         and adds bean specific methods
 *
 */
@Repository
public interface ClassificationRepository extends JpaRepository<Classification, Long> {

	Classification findByName(String name);

}

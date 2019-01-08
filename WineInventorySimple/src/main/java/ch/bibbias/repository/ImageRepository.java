package ch.bibbias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.bibbias.bean.Image;

/**
 * 
 * @author Marco Bibbia
 * 
 *         Repository interface for image beans extends JpaRepository and adds
 *         bean specific methods
 *
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

	Image findByName(String name);

}

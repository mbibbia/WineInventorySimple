package ch.bibbias.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Marco Bibbia
 * 
 *         This class is a bean / model of wine type and is used to persist data
 *         with wine type repository (JPA).
 */

@Entity
@Table(name = "WineType")
public class WineType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;

	private String name;

	/**
	 * Returns identification of wine type bean.
	 * 
	 * @return Wine Type Identification (e.g. 1)
	 */
	public long getId() {
		return id;
	}

	/**
	 * Receives identification of wine type and sets value in wine type bean.
	 * 
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Returns name of wien type bean.
	 * 
	 * @return Wine Type Name (e.g. Rotwein)
	 */
	public String getName() {
		return name;
	}

	/**
	 * Receives name of wine type and sets value in wine type bean.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Converts data of wine type into string value.
	 */
	@Override
	public String toString() {
		return this.name;
	}

}

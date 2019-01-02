package ch.bibbias.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author Marco Bibbia
 * 
 *         This class is a bean / model of region and is used to persist data
 *         with region repository (JPA).
 */

@Entity
@Table(name = "Region")
public class Region {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;

	private String name;

	@ManyToOne(fetch = FetchType.EAGER)
	private Country country;

	/**
	 * Returns id of region bean.
	 * 
	 * @return Region Id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Receives identification of region and sets value in region bean.
	 * 
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Returns name of region bean.
	 * 
	 * @return Region Name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Receives name of region and sets value in region bean.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns country object of region bean.
	 * 
	 * @return Region Country Object
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * Receives country of region and sets object to region bean.
	 * 
	 * @param country
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

	/**
	 * Convets region data into string value.
	 */
	@Override
	public String toString() {
		return this.name;
	}

}

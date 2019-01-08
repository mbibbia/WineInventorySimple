package ch.bibbias.bean;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author Marco Bibbia
 * 
 *         This class is a bean / model of country and is used to persist data
 *         with country repository (JPA).
 */

@Entity
@Table(name = "Country")
public class Country {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;

	private String code;

	private String name;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id")
	private Set<Region> regions;

	/**
	 * Returns identifier of country bean.
	 * 
	 * @return Country Id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Receives country id and sets value in country bean.
	 * 
	 * @param id (e.g. 1)
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Returns code of country bean.
	 * 
	 * @return Country Code (e.g. CH)
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Receives code of country and sets value in country bean.
	 * 
	 * @param code (e.g. CH)
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Returns name of country bean.
	 * 
	 * @return Country Name (e.g. Schweiz)
	 */
	public String getName() {
		return name;
	}

	/**
	 * Receives name of country and sets value in country bean.
	 * 
	 * @param name (e.g. Schweiz)
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns a set of related regions of this country
	 * 
	 * @return Set of Region Beans
	 */
	public Set<Region> getRegions() {
		return regions;
	}

	/**
	 * Receives a set of region beans and sets values in country bean.
	 * 
	 * @param Set<Region> (e.g. [Zürich, Aargau]
	 */
	public void setRegions(Set<Region> regions) {
		this.regions = regions;
	}

	/**
	 * Converts country data to a string value.
	 */
	@Override
	public String toString() {
		return this.code + "\t - " + this.name;
	}

}

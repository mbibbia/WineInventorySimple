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
 *         This class is a bean / model of wine classification and is used to
 *         persist data with classification repository (JPA).
 */

@Entity
@Table(name = "Classification")
public class Classification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;

	private String name;

	/**
	 * Returns identifier of classification bean.
	 * 
	 * @return Classification Id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Receives classification id and sets value in classification bean.
	 * 
	 * @param id (e.g. 1)
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Returns name of classification
	 * 
	 * @return Classification Name (e.g. DOC)
	 */
	public String getName() {
		return name;
	}

	/**
	 * Receives name of classification and sets value in classification bean.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Converts classification data to a string value.
	 * 
	 */
	@Override
	public String toString() {
		return this.name;
	}

}

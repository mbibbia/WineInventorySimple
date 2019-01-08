package ch.bibbias.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 
 * @author Marco Bibbia
 * 
 *         This class is a bean / model of wine and is used to persist data with
 *         wine repository (JPA).
 */

@Entity
@Table(name = "Wine")
public class Wine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;

	private String name;

	@OneToOne(fetch = FetchType.LAZY)
	private WineType type;

	@OneToOne(fetch = FetchType.LAZY)
	private Classification classification;

	@OneToOne(fetch = FetchType.LAZY)
	private Country country;

	@OneToOne(fetch = FetchType.LAZY)
	private Region region;

	@OneToOne(fetch = FetchType.LAZY)
	private Producer producer;

	@OneToOne(fetch = FetchType.LAZY)
	private Image image;

	/**
	 * Returns identification of wine bean.
	 * 
	 * @return Wine Identification (e.g. 1)
	 */
	public long getId() {
		return id;
	}

	/**
	 * Receives identification of wine and sets value in wine bean.
	 * 
	 * @param id (e.g. 1)
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Returns name of wine bean.
	 * 
	 * @return Wine Name (e.g. Barolo)
	 */
	public String getName() {
		return name;
	}

	/**
	 * Receives name of wine and sets value in wine bean.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns type of wine bean.
	 * 
	 * @return Wine Type Object
	 */
	public WineType getType() {
		return type;
	}

	/**
	 * Receives type of wine and sets object to wine bean.
	 * 
	 * @param type
	 */
	public void setType(WineType type) {
		this.type = type;
	}

	/**
	 * Returns classification of wine bean.
	 * 
	 * @return Wine Classification Object
	 */
	public Classification getClassification() {
		return classification;
	}

	/**
	 * Receives classification of wine and sets object in wine bean.
	 * 
	 * @param classification
	 */
	public void setClassification(Classification classification) {
		this.classification = classification;
	}

	/**
	 * Returns country of wine bean.
	 * 
	 * @return Country Object
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * Receives country of wine and sets object in wine bean.
	 * 
	 * @param country
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

	/**
	 * Returns region of wine bean.
	 * 
	 * @return Region Object
	 */
	public Region getRegion() {
		return region;
	}

	/**
	 * Receives region of wine and sets object in wine bean.
	 * 
	 * @param region
	 */
	public void setRegion(Region region) {
		this.region = region;
	}

	/**
	 * Returns producer of wine bean.
	 * 
	 * @return Producer Object
	 */
	public Producer getProducer() {
		return producer;
	}

	/**
	 * Receives producer of wine and sets object in wine bean.
	 * 
	 * @param producer
	 */
	public void setProducer(Producer producer) {
		this.producer = producer;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * Converts wine data to string value.
	 */
	@Override
	public String toString() {
		return "Wine [id=" + id + ", Name=" + name + ", Type=" + type + ", Classification=" + classification
				+ ", Country=" + country + ", Region=" + region + ", Producer=" + producer + "]";
	}

}

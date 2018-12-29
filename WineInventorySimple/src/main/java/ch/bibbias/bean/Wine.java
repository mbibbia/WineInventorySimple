package ch.bibbias.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public WineType getType() {
		return type;
	}

	public void setType(WineType type) {
		this.type = type;
	}

	public Classification getClassification() {
		return classification;
	}

	public void setClassification(Classification classification) {
		this.classification = classification;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public Producer getProducer() {
		return producer;
	}

	public void setProducer(Producer producer) {
		this.producer = producer;
	}

	@Override
	public String toString() {
		return "Wine [id=" + id + ", Name=" + name + ", Type=" + type + ", Classification=" + classification
				+ ", Country=" + country + ", Region=" + region + ", Producer=" + producer + "]";
	}

}

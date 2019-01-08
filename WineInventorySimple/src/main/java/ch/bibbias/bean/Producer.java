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
 *         This class is a bean / model of wine producer and is used to persist
 *         data with producer repository (JPA).
 */

@Entity
@Table(name = "Producer")
public class Producer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;

	private String name;

	private String company;

	private String addressLine1;

	private String addressLine2;

	private String zipCode;

	private String place;

	private String phone;

	private String fax;

	private String email;

	private String url;

	@OneToOne(fetch = FetchType.LAZY)
	private Country country;

	@OneToOne(fetch = FetchType.LAZY)
	private Region region;

	/**
	 * Returns identifier of producer bean.
	 * 
	 * @return Producer Id (e.g. 1)
	 */
	public long getId() {
		return id;
	}

	/**
	 * Receives identifier of producer and sets value in producer bean.
	 * 
	 * @param id (e.g. 1)
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Returns name of producer bean.
	 * 
	 * @return Producer Name (e.g. Parusso)
	 */
	public String getName() {
		return name;
	}

	/**
	 * Receives name of producer and sets value in producer bean.
	 * 
	 * @param name (e.g. Parusso)
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns company of producer bean.
	 * 
	 * @return Producer Company (e.g. Parusso Armando di Parusso F.lli Società
	 *         Agricola)
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * Receives company of producer and sets value in producer bean.
	 * 
	 * @param company (e.g. Parusso Armando di Parusso F.lli Società Agricola)
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * Returns address line 1 of producer bean.
	 * 
	 * @return Address Line 1 (e.g. Loc. Bussia 55)
	 */
	public String getAddressLine1() {
		return addressLine1;
	}

	/**
	 * Receives address line 1 of producer and sets value in producer bean.
	 * 
	 * @param addressLine1 (e.g. Loc. Bussia 55)
	 */
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	/**
	 * Returns address line 2 of producer bean.
	 * 
	 * @return Address Line 2
	 */
	public String getAddressLine2() {
		return addressLine2;
	}

	/**
	 * Receives address line 2 of producer and sets value in producer bean.
	 * 
	 * @param addressLine2
	 */
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	/**
	 * Returns zip code of producer bean.
	 * 
	 * @return Producer Zip Code (e.g. 12065)
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * Receives zip code of producer and sets value in producer bean.
	 * 
	 * @param zipCode (e.g. 12065)
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * Returns place of producer bean.
	 * 
	 * @return Producer Place (e.g. Monforte d'Alba)
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * Receives place of producer and sets value in producer bean.
	 * 
	 * @param place (e.g. Monforte d'Alba)
	 */
	public void setPlace(String place) {
		this.place = place;
	}

	/**
	 * Returns phone of producer bean.
	 * 
	 * @return Producer Phone (e.g. 0039 173 78257)
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Receives phone of producer and sets value in producer bean.
	 * 
	 * @param phone (e.g. 0039 173 78257)
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Returns fax of producer bean.
	 * 
	 * @return Producer Fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * Receives fax of producer and sets value in producer bean.
	 * 
	 * @param fax
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * Retruns e-mail address of producer bean.
	 * 
	 * @return Producer E-Mail Address (e.g. info@parusso.com)
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Receives e-mail address of producer and sets value in producer bean.
	 * 
	 * @param email (e.g. info@parusso.com)
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns URL of producer bean.
	 * 
	 * @return Producer URL (e.g. http://parusso.com/)
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Receives URL of producer and sets value in producer bean.
	 * 
	 * @param url (e.g. http://parusso.com/)
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Returns country object of producer bean.
	 * 
	 * @return Producer Country Object
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * Receives country of producer and sets object in producer bean.
	 * 
	 * @param country
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

	/**
	 * Retruns region object of producer bean.
	 * 
	 * @return Producer Region Object
	 */
	public Region getRegion() {
		return region;
	}

	/**
	 * Receives region of producer and sets objects in producer bean.
	 * 
	 * @param region
	 */
	public void setRegion(Region region) {
		this.region = region;
	}

	/**
	 * Converts producer data into string value.
	 */
	@Override
	public String toString() {
		return this.name + " - " + this.place;
	}

}

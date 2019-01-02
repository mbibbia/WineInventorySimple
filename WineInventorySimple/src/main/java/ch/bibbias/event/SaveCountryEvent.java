package ch.bibbias.event;

import org.springframework.context.ApplicationEvent;

import ch.bibbias.bean.Country;

/**
 * 
 * @author Marco Bibbia
 * 
 *         Application Event when a country is saved.
 *
 */
public class SaveCountryEvent extends ApplicationEvent {

	private Country country;

	private static final long serialVersionUID = 1L;

	public SaveCountryEvent(Object source, Country country) {
		super(source);
		this.country = country;

	}

	/**
	 * Returns country bean that has been saved.
	 * 
	 * @return Country
	 */
	public Country getCountry() {
		return this.country;
	}

}

package ch.bibbias.event;

import org.springframework.context.ApplicationEvent;

import ch.bibbias.bean.Country;

public class SaveCountryEvent extends ApplicationEvent {

	private Country country;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SaveCountryEvent(Object source, Country country) {
		super(source);
		this.country = country;

	}

	public Country getCountry() {
		return this.country;
	}

}

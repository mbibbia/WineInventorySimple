package ch.bibbias.event;

import org.springframework.context.ApplicationEvent;

import ch.bibbias.bean.Wine;

/**
 * 
 * @author Marco Bibbia
 * 
 *         Application Event when a single wine is clicked.
 *
 */
public class WineDetailsEvent extends ApplicationEvent {

	private Wine wine;

	private static final long serialVersionUID = 1L;

	public WineDetailsEvent(Object source, Wine wine) {
		super(source);
		this.wine = wine;

	}

	/**
	 * Returns pushed wine bean.
	 * 
	 * @return Wine
	 */
	public Wine getWine() {
		return this.wine;
	}

}

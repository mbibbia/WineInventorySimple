package ch.bibbias.event;

import org.springframework.context.ApplicationEvent;

import ch.bibbias.bean.Wine;

/**
 * 
 * @author Marco Bibbia
 * 
 *         Application Event when a wine is saved.
 *
 */
public class SaveWineEvent extends ApplicationEvent {

	private Wine wine;

	private static final long serialVersionUID = 1L;

	public SaveWineEvent(Object source, Wine wine) {
		super(source);
		this.wine = wine;

	}

	/**
	 * Returns wine bean that has been saved.
	 * 
	 * @return Wine
	 */
	public Wine getWine() {
		return this.wine;
	}

}

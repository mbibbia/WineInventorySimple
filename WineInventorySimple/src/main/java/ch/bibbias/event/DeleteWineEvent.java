package ch.bibbias.event;

import org.springframework.context.ApplicationEvent;

import ch.bibbias.bean.Wine;

/**
 * 
 * @author Marco Bibbia, Christian Jeitziner
 * 
 *         Application Event when a wine is deleted.
 *
 */
public class DeleteWineEvent extends ApplicationEvent {

	private Wine wine;

	private static final long serialVersionUID = 1L;

	public DeleteWineEvent(Object source, Wine wine) {
		super(source);
		this.wine = wine;

	}

	/**
	 * Returns wine bean that has been deleted.
	 * 
	 * @return Wine
	 */
	public Wine getWine() {
		return this.wine;
	}

}

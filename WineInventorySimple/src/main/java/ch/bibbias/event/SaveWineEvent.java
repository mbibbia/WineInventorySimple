package ch.bibbias.event;

import org.springframework.context.ApplicationEvent;

import ch.bibbias.bean.Wine;

public class SaveWineEvent extends ApplicationEvent {

	private Wine wine;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SaveWineEvent(Object source, Wine wine) {
		super(source);
		this.wine = wine;

	}

	public Wine getWine() {
		return this.wine;
	}

}

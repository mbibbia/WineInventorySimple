package ch.bibbias.event;

import org.springframework.context.ApplicationEvent;

import ch.bibbias.bean.WineType;

public class SaveWineTypeEvent extends ApplicationEvent {

	private WineType wineType;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SaveWineTypeEvent(Object source, WineType wineType) {
		super(source);
		this.wineType = wineType;

	}

	public WineType getClassification() {
		return this.wineType;
	}

}

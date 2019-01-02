package ch.bibbias.event;

import org.springframework.context.ApplicationEvent;

import ch.bibbias.bean.WineType;

/**
 * 
 * @author Marco Bibbia
 * 
 *         Application Event when a wine type is saved.
 *
 */
public class SaveWineTypeEvent extends ApplicationEvent {

	private WineType wineType;

	private static final long serialVersionUID = 1L;

	public SaveWineTypeEvent(Object source, WineType wineType) {
		super(source);
		this.wineType = wineType;

	}

	/**
	 * Returns wine type bean that has been saved.
	 * 
	 * @return WineType
	 */
	public WineType getClassification() {
		return this.wineType;
	}

}

package ch.bibbias.event;

import org.springframework.context.ApplicationEvent;

import ch.bibbias.bean.WineType;

/**
 * 
 * @author Marco Bibbia
 * 
 *         Application Event when a single wine type is clicked.
 *
 */
public class WineTypeDetailsEvent extends ApplicationEvent {

	private WineType wineType;

	private static final long serialVersionUID = 1L;

	public WineTypeDetailsEvent(Object source, WineType wineType) {
		super(source);
		this.wineType = wineType;

	}

	/**
	 * Returns pushed wine type bean.
	 * 
	 * @return WineType
	 */
	public WineType getWineType() {
		return this.wineType;
	}

}

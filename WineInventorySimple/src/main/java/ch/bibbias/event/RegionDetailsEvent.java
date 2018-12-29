package ch.bibbias.event;

import org.springframework.context.ApplicationEvent;

import ch.bibbias.bean.Region;

public class RegionDetailsEvent extends ApplicationEvent {

	private Region region;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RegionDetailsEvent(Object source, Region region) {
		super(source);
		this.region = region;

	}

	public Region getRegion() {
		return this.region;
	}

}

package ch.bibbias.event;

import org.springframework.context.ApplicationEvent;

import ch.bibbias.bean.Image;

/**
 * 
 * @author Marco Bibbia
 * 
 *         Application Event when an image is added.
 *
 */
public class ShowImageEvent extends ApplicationEvent {

	private Image image;

	private static final long serialVersionUID = 1L;

	public ShowImageEvent(Object source, Image image) {
		super(source);
		this.image = image;

	}

	/**
	 * Returns image bean that has been saved.
	 * 
	 * @return Wine
	 */
	public Image getImage() {
		return this.image;
	}

}

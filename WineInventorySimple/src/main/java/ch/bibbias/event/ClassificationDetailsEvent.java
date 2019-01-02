package ch.bibbias.event;

import org.springframework.context.ApplicationEvent;

import ch.bibbias.bean.Classification;

/**
 * 
 * @author Marco Bibbia
 * 
 *         Application Event when a single classification is clicked.
 *
 */
public class ClassificationDetailsEvent extends ApplicationEvent {

	private Classification classification;

	private static final long serialVersionUID = 1L;

	public ClassificationDetailsEvent(Object source, Classification classification) {
		super(source);
		this.classification = classification;

	}

	/**
	 * Returns pushed classification bean.
	 * 
	 * @return Classification
	 */
	public Classification getClassification() {
		return this.classification;
	}

}

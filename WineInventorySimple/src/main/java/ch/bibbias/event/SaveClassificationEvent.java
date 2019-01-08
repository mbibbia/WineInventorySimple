package ch.bibbias.event;

import org.springframework.context.ApplicationEvent;

import ch.bibbias.bean.Classification;

/**
 * 
 * @author Marco Bibbia
 * 
 *         Application Event when a classification is saved.
 *
 */
public class SaveClassificationEvent extends ApplicationEvent {

	private Classification classification;

	private static final long serialVersionUID = 1L;

	public SaveClassificationEvent(Object source, Classification classification) {
		super(source);
		this.classification = classification;

	}
	
	/**
	 * Returns classification bean that has been saved.
	 * 
	 * @return Classification
	 */
	public Classification getClassification() {
		return this.classification;
	}

}

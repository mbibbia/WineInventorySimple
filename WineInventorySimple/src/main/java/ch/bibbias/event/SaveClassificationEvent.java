package ch.bibbias.event;

import org.springframework.context.ApplicationEvent;

import ch.bibbias.bean.Classification;

public class SaveClassificationEvent extends ApplicationEvent {

	private Classification classification;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SaveClassificationEvent(Object source, Classification classification) {
		super(source);
		this.classification = classification;

	}

	public Classification getClassification() {
		return this.classification;
	}

}

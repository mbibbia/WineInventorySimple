package ch.bibbias.event;

import org.springframework.context.ApplicationEvent;

import ch.bibbias.bean.Producer;

public class SaveProducerEvent extends ApplicationEvent {

	private Producer producer;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SaveProducerEvent(Object source, Producer producer) {
		super(source);
		this.producer = producer;

	}

	public Producer getProducer() {
		return this.producer;
	}

}

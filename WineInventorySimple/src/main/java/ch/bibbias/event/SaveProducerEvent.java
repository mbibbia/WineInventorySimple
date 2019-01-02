package ch.bibbias.event;

import org.springframework.context.ApplicationEvent;

import ch.bibbias.bean.Producer;

/**
 * 
 * @author Marco Bibbia
 * 
 *         Application Event when a producer is saved.
 *
 */
public class SaveProducerEvent extends ApplicationEvent {

	private Producer producer;

	private static final long serialVersionUID = 1L;

	public SaveProducerEvent(Object source, Producer producer) {
		super(source);
		this.producer = producer;

	}

	/**
	 * Returns producer bean that has been saved.
	 * 
	 * @return Producer
	 */
	public Producer getProducer() {
		return this.producer;
	}

}

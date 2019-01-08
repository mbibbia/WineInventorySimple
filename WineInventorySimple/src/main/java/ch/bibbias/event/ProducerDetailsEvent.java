package ch.bibbias.event;

import org.springframework.context.ApplicationEvent;

import ch.bibbias.bean.Producer;

/**
 * 
 * @author Marco Bibbia
 * 
 *         Application Event when a single producer is clicked.
 *
 */
public class ProducerDetailsEvent extends ApplicationEvent {

	private Producer producer;

	private static final long serialVersionUID = 1L;

	public ProducerDetailsEvent(Object source, Producer producer) {
		super(source);
		this.producer = producer;

	}

	/**
	 * Returns pushed producer bean.
	 * 
	 * @return Producer
	 */
	public Producer getProducer() {
		return this.producer;
	}

}

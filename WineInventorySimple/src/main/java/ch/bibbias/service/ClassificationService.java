package ch.bibbias.service;

import org.springframework.stereotype.Service;

import ch.bibbias.bean.Classification;
import ch.bibbias.generic.GenericService;

@Service
public interface ClassificationService extends GenericService<Classification> {

	Classification findByName(String name);
	
}

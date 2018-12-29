package ch.bibbias.service;

import org.springframework.stereotype.Service;

import ch.bibbias.bean.WineType;
import ch.bibbias.generic.GenericService;

@Service
public interface WineTypeService extends GenericService<WineType> {

	WineType findByName(String name);

}

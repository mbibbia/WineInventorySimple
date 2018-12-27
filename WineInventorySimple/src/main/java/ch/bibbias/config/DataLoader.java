package ch.bibbias.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import ch.bibbias.bean.Wine;
import ch.bibbias.bean.WineType;
import ch.bibbias.service.WineService;
import ch.bibbias.service.WineTypeService;

@Component
public class DataLoader implements ApplicationRunner {

	@Autowired
	private WineTypeService wineTypeService;

	@Autowired
	private WineService wineService;

	@Autowired
	public DataLoader() {

	}

	private void initWine() {
		Wine wine = new Wine();
		wine.setName("Barolo");
		wine.setType("Rotwein");
		wineService.save(wine);

		wine = new Wine();
		wine.setName("Langhe Nebbiolo");
		wine.setType("Rotwein");
		wineService.save(wine);

	}

	private void initWineTypes() {

		WineType wineType = new WineType();

		wineType = new WineType();
		wineType.setName("Rotwein");
		wineTypeService.save(wineType);

		wineType = new WineType();
		wineType.setName("Weisswein");
		wineTypeService.save(wineType);

		wineType = new WineType();
		wineType.setName("Rosé");
		wineTypeService.save(wineType);


	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		initWineTypes();
		initWine();

	}

}

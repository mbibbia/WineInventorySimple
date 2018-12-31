package ch.bibbias.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import ch.bibbias.bean.Classification;
import ch.bibbias.bean.Country;
import ch.bibbias.bean.Producer;
import ch.bibbias.bean.Region;
import ch.bibbias.bean.Wine;
import ch.bibbias.bean.WineType;
import ch.bibbias.service.ClassificationService;
import ch.bibbias.service.CountryService;
import ch.bibbias.service.ProducerService;
import ch.bibbias.service.RegionService;
import ch.bibbias.service.WineService;
import ch.bibbias.service.WineTypeService;

@Component
public class DataLoader implements ApplicationRunner {

	@Autowired
	private WineTypeService wineTypeService;

	@Autowired
	private ClassificationService classificationService;

	@Autowired
	private CountryService countryService;

	@Autowired
	private RegionService regionService;

	@Autowired
	private ProducerService producerService;

	@Autowired
	private WineService wineService;

	@Autowired
	public DataLoader() {

	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		initWineTypes();
		initClassifications();
		initCountries();
		initRegions();
		initProducers();
		initWine();

	}

	private void initWine() {
		Wine wine = new Wine();
		wine.setName("Barolo");
		wine.setType(wineTypeService.findByName("Rotwein"));
		wineService.save(wine);

		wine = new Wine();
		wine.setName("Langhe Nebbiolo");
		wine.setType(wineTypeService.findByName("Rotwein"));
		wineService.save(wine);

	}

	private void initWineTypes() {

		WineType wineType;

		wineType = new WineType();
		wineType.setName("Rotwein");
		wineTypeService.save(wineType);

		wineType = new WineType();
		wineType.setName("Weisswein");
		wineTypeService.save(wineType);

		wineType = new WineType();
		wineType.setName("Rosé");
		wineTypeService.save(wineType);

		wineType = new WineType();
		wineType.setName("Schaumwein weiss");
		wineTypeService.save(wineType);

		wineType = new WineType();
		wineType.setName("Schaumwein rosé");
		wineTypeService.save(wineType);

		wineType = new WineType();
		wineType.setName("Süsswein rot");
		wineTypeService.save(wineType);

		wineType = new WineType();
		wineType.setName("Süsswein weiss");
		wineTypeService.save(wineType);

	}

	private void initClassifications() {

		Classification classification;

		classification = new Classification();
		classification.setName("DOC");
		classificationService.save(classification);

		classification = new Classification();
		classification.setName("DOCG");
		classificationService.save(classification);

		classification = new Classification();
		classification.setName("IGT");
		classificationService.save(classification);

		classification = new Classification();
		classification.setName("VdT");
		classificationService.save(classification);

		classification = new Classification();
		classification.setName("DOP");
		classificationService.save(classification);

		classification = new Classification();
		classification.setName("1er grand cru classé");
		classificationService.save(classification);

		classification = new Classification();
		classification.setName("2ème grand cru classé");
		classificationService.save(classification);

		classification = new Classification();
		classification.setName("AOC");
		classificationService.save(classification);

	}

	private void initCountries() {

		Country country;

		country = new Country();
		country.setCode("CH");
		country.setName("Schweiz");
		countryService.save(country);

		country = new Country();
		country.setCode("FR");
		country.setName("Frankreich");
		countryService.save(country);

		country = new Country();
		country.setCode("IT");
		country.setName("Italien");
		countryService.save(country);

	}

	private void initRegions() {

		Region region;
		Country country;

		country = countryService.findByCode("CH");
		region = new Region();
		region.setName("Zürich");
		region.setCountry(country);
		regionService.save(region);
		
		country = countryService.findByCode("IT");
		region = new Region();
		region.setName("Piemont");
		region.setCountry(country);
		regionService.save(region);
		
	}

	private void initProducers() {

		Producer producer;
		Region region;
		Country country;

		country = countryService.findByCode("IT");
		region = regionService.findByName("Piemont");
		producer = new Producer();
		producer.setName("Parusso");
		producer.setCompany("Parusso Armando di Parusso F.lli Società Agricola");
		producer.setAddressLine1("Loc. Bussia 55");
		producer.setZipCode("12065");
		producer.setPlace("Monforte d'Alba");
		producer.setPhone("0039 173 78257");
		producer.setEmail("info@parusso.com");
		producer.setUrl("http://parusso.com/");
		producer.setCountry(country);
		producer.setRegion(region);
		producerService.save(producer);

	}

}

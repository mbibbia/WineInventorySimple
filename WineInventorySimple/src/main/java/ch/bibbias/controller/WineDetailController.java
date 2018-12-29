package ch.bibbias.controller;

import java.net.URL;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import ch.bibbias.bean.Classification;
import ch.bibbias.bean.Country;
import ch.bibbias.bean.Region;
import ch.bibbias.bean.Wine;
import ch.bibbias.bean.WineType;
import ch.bibbias.config.StageManager;
import ch.bibbias.event.SaveWineEvent;
import ch.bibbias.event.WineDetailsEvent;
import ch.bibbias.service.ClassificationService;
import ch.bibbias.service.CountryService;
import ch.bibbias.service.WineService;
import ch.bibbias.service.WineTypeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

@Controller
public class WineDetailController implements Initializable {

	@FXML
	private Label wineId;

	@FXML
	private Label userId;

	@FXML
	private TextField name;

	@FXML
	private ComboBox<WineType> type;

	@FXML
	private ComboBox<Classification> classification;

	@FXML
	private ComboBox<Country> country;

	@FXML
	private ComboBox<Region> region;

	@FXML
	private ComboBox<String> producer;

	@FXML
	private Button reset;

	@FXML
	private Button saveWine;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Lazy
	@Autowired
	private WineTableController wineTableController;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Autowired
	private WineService wineService;

	@Autowired
	private WineTypeService wineTypeService;

	@Autowired
	private ClassificationService classificationService;

	@Autowired
	private CountryService countryService;

	@Component
	class ShowWineDetailEventHandler implements ApplicationListener<WineDetailsEvent> {

		@Override
		public void onApplicationEvent(WineDetailsEvent event) {

			wineId.setText(Long.toString(event.getWine().getId()));
			name.setText(event.getWine().getName());
			type.setValue(event.getWine().getType());
			classification.setValue(event.getWine().getClassification());
			country.setValue(event.getWine().getCountry());
			region.setValue(event.getWine().getRegion());
			producer.setValue(event.getWine().getProducer());

		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		type.setItems(loadTypes());
		classification.setItems(loadClassifications());
		country.setItems(loadCountries());
		producer.setItems(loadProducers());

	}

	private ObservableList<WineType> loadTypes() {

		ObservableList<WineType> types = FXCollections.observableArrayList(wineTypeService.findAll());

		return types;

	}

	private ObservableList<Classification> loadClassifications() {

		ObservableList<Classification> classifications = FXCollections
				.observableArrayList(classificationService.findAll());

		return classifications;

	}

	private ObservableList<Country> loadCountries() {

		ObservableList<Country> countries = FXCollections.observableArrayList(countryService.findAll());

		return countries;

	}

	private ObservableList<String> loadProducers() {

		ObservableList<String> producers = FXCollections.observableArrayList("", "Parusso", "Gérard Bertrand",
				"Sciavenza");

		return producers;

	}

	private String getName() {
		return name.getText();
	}

	private WineType getType() {
		return type.getValue();
	}

	private Classification getClassification() {
		return classification.getValue();
	}

	private Country getCountry() {
		return country.getValue();
	}

	private Region getRegion() {
		return region.getValue();
	}

	private String getProducer() {
		return producer.getValue();
	}

	@FXML
	void reset(ActionEvent event) {
		clearFields();
	}

	private void clearFields() {

		wineId.setText(null);
		name.clear();
		type.getSelectionModel().clearSelection();
		classification.getSelectionModel().clearSelection();
		country.getSelectionModel().clearSelection();
		region.getSelectionModel().clearSelection();
		producer.getSelectionModel().clearSelection();

	}

	@FXML
	private void handleRegionClicked() {

		ObservableList<Region> regions = FXCollections.observableArrayList(country.getValue().getRegions());

		region.setItems(regions);

	}

	@FXML
	private void saveWine(ActionEvent event) {

		/*
		 * if (validate("Name", getName(), "[a-zA-Z]+") && validate("Type", getType(),
		 * "[a-zA-Z]+") && emptyValidation("Classification", getClassification() &&
		 * emptyValidation("Country", getCountry() {
		 */

		if (wineId.getText() == null || wineId.getText() == "") {

			Wine wine = new Wine();
			wine.setName(getName());
			wine.setType(getType());
			wine.setClassification(getClassification());
			wine.setCountry(getCountry());
			wine.setRegion(getRegion());
			wine.setProducer(getProducer());

			Wine newWine = wineService.save(wine);

			saveAlert(newWine);

			raiseEventSaveWine(newWine);

		} else {
			Wine wine = wineService.find(Long.parseLong(wineId.getText()));
			wine.setName(getName());
			wine.setType(getType());
			wine.setClassification(getClassification());
			wine.setCountry(getCountry());
			wine.setRegion(getRegion());
			wine.setProducer(getProducer());
			Wine updatedWine = wineService.update(wine);
			updateAlert(updatedWine);

			raiseEventSaveWine(updatedWine);
		}

		clearFields();

	}

	private void raiseEventSaveWine(final Wine wine) {
		SaveWineEvent wineEvent = new SaveWineEvent(this, wine);
		applicationEventPublisher.publishEvent(wineEvent);
	}

	private void saveAlert(Wine wine) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Wine saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The wine " + wine.getName() + " has been created and \n id is " + wine.getId() + ".");
		alert.showAndWait();
	}

	private void updateAlert(Wine wine) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("User updated successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The wine " + wine.getName() + " has been updated.");
		alert.showAndWait();
	}

}

package ch.bibbias.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import ch.bibbias.bean.Wine;
import ch.bibbias.bean.WineType;
import ch.bibbias.config.StageManager;
import ch.bibbias.event.NewWineEvent;
import ch.bibbias.event.EditWineDetails;
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
public class WineDetailController implements Initializable, ApplicationListener<EditWineDetails> {

	@FXML
	private Label wineId;

	@FXML
	private Label userId;

	@FXML
	private TextField name;

	@FXML
	private ComboBox<String> type;

	@FXML
	private ComboBox<String> classification;

	@FXML
	private ComboBox<String> country;

	@FXML
	private ComboBox<String> region;

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		type.setItems(loadTypes());
		classification.setItems(loadClassifications());
		country.setItems(loadCountries());
		region.setItems(loadRegions());
		producer.setItems(loadProducers());

	}

	private ObservableList<String> loadTypes() {

		ObservableList<String> types = FXCollections.observableArrayList();

		for (WineType wt : wineTypeService.findAll()) {
			types.add(wt.toString());
		}

		return types;

	}

	private ObservableList<String> loadClassifications() {

		ObservableList<String> classifications = FXCollections.observableArrayList("", "DOC", "DOCG");

		return classifications;

	}

	private ObservableList<String> loadCountries() {

		ObservableList<String> countries = FXCollections.observableArrayList("", "CH", "FR", "IT");

		return countries;

	}

	private ObservableList<String> loadRegions() {

		ObservableList<String> regions = FXCollections.observableArrayList("", "Zürich", "Bordeaux", "Piemont");

		return regions;

	}

	private ObservableList<String> loadProducers() {

		ObservableList<String> producers = FXCollections.observableArrayList("", "Parusso", "Gérard Bertrand",
				"Sciavenza");

		return producers;

	}

	private String getName() {
		return name.getText();
	}

	private String getType() {
		return type.getValue();
	}

	private String getClassification() {
		return classification.getValue();
	}

	private String getCountry() {
		return country.getValue();
	}

	private String getRegion() {
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

			raiseEventNewWine(newWine);

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
		}

		clearFields();

	}

	private void raiseEventNewWine(final Wine wine) {
		NewWineEvent wineEvent = new NewWineEvent(this, wine);
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

	@Override
	public void onApplicationEvent(EditWineDetails event) {
		
		name.setText(event.getWine().getName());
		type.setValue(event.getWine().getType());
		classification.setValue(event.getWine().getClassification());
		country.setValue(event.getWine().getCountry());
		region.setValue(event.getWine().getRegion());
		producer.setValue(event.getWine().getProducer());
		
		
	}

}

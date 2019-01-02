package ch.bibbias.controller;

import java.net.URL;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import ch.bibbias.bean.Country;
import ch.bibbias.bean.Producer;
import ch.bibbias.bean.Region;
import ch.bibbias.config.StageManager;
import ch.bibbias.event.SaveProducerEvent;
import ch.bibbias.event.ProducerDetailsEvent;
import ch.bibbias.service.CountryService;
import ch.bibbias.service.ProducerService;
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

/**
 * 
 * @author Marco Bibbia
 * 
 *         Controller for FXML View ProducerDetail.fxml.
 *
 */

@Controller
public class ProducerDetailController implements Initializable {

	@FXML
	private Label producerId;

	@FXML
	private TextField name;

	@FXML
	private TextField company;

	@FXML
	private TextField addressLine1;

	@FXML
	private TextField addressLine2;

	@FXML
	private TextField zipCode;

	@FXML
	private TextField place;

	@FXML
	private TextField phone;

	@FXML
	private TextField fax;

	@FXML
	private TextField email;

	@FXML
	private TextField url;

	@FXML
	private ComboBox<Country> country;

	@FXML
	private ComboBox<Region> region;

	@FXML
	private Button reset;

	@FXML
	private Button saveProducer;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Autowired
	private ProducerService producerService;

	@Autowired
	private CountryService countryService;

	@Component
	class ShowProducerDetailEventHandler implements ApplicationListener<ProducerDetailsEvent> {

		@Override
		public void onApplicationEvent(ProducerDetailsEvent event) {
			producerId.setText(Long.toString(event.getProducer().getId()));
			name.setText(event.getProducer().getName());
			company.setText(event.getProducer().getCompany());
			addressLine1.setText(event.getProducer().getAddressLine1());
			addressLine2.setText(event.getProducer().getAddressLine2());
			zipCode.setText(event.getProducer().getZipCode());
			place.setText(event.getProducer().getPlace());
			phone.setText(event.getProducer().getPhone());
			fax.setText(event.getProducer().getFax());
			email.setText(event.getProducer().getEmail());
			url.setText(event.getProducer().getUrl());
			country.setValue(event.getProducer().getCountry());
			region.setValue(event.getProducer().getRegion());

		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		country.setItems(loadCountries());

	}

	private ObservableList<Country> loadCountries() {
		return FXCollections.observableArrayList(countryService.findAll());
	}

	private String getName() {
		return name.getText();
	}

	public String getCompany() {
		return company.getText();
	}

	public String getAddressLine1() {
		return addressLine1.getText();
	}

	public String getAddressLine2() {
		return addressLine2.getText();
	}

	public String getZipCode() {
		return zipCode.getText();
	}

	public String getPlace() {
		return place.getText();
	}

	public String getPhone() {
		return phone.getText();
	}

	public String getFax() {
		return fax.getText();
	}

	public String getEmail() {
		return email.getText();
	}

	public String getUrl() {
		return url.getText();
	}

	private Country getCountry() {
		return country.getValue();
	}

	private Region getRegion() {
		return region.getValue();
	}

	@FXML
	void reset(ActionEvent event) {
		clearFields();
	}

	private void clearFields() {

		producerId.setText(null);
		name.clear();
		company.clear();
		addressLine1.clear();
		addressLine2.clear();
		zipCode.clear();
		place.clear();
		phone.clear();
		fax.clear();
		email.clear();
		url.clear();
		country.getSelectionModel().clearSelection();
		region.getSelectionModel().clearSelection();

	}

	@FXML
	private void handleRegionClicked() {

		if (country.getValue() != null) {
			ObservableList<Region> regions = FXCollections.observableArrayList(country.getValue().getRegions());
			region.setItems(regions);
		}
	}

	@FXML
	private void saveProducer(ActionEvent event) {

		/*
		 * if (validate("Name", getName(), "[a-zA-Z]+") && validate("Type", getType(),
		 * "[a-zA-Z]+") && emptyValidation("Classification", getClassification() &&
		 * emptyValidation("Country", getCountry() {
		 */

		if (producerId.getText() == null || producerId.getText() == "") {

			Producer producer = new Producer();
			producer.setName(getName());
			producer.setCompany(getCompany());
			producer.setAddressLine1(getAddressLine1());
			producer.setAddressLine2(getAddressLine2());
			producer.setZipCode(getZipCode());
			producer.setPlace(getPlace());
			producer.setPhone(getPhone());
			producer.setFax(getFax());
			producer.setEmail(getEmail());
			producer.setUrl(getUrl());
			producer.setCountry(getCountry());
			producer.setRegion(getRegion());

			Producer newProducer = producerService.save(producer);

			saveAlert(newProducer);

			raiseEventSaveProducer(newProducer);

		} else {
			Producer producer = producerService.find(Long.parseLong(producerId.getText()));
			producer.setName(getName());
			producer.setCompany(getCompany());
			producer.setAddressLine1(getAddressLine1());
			producer.setAddressLine2(getAddressLine2());
			producer.setZipCode(getZipCode());
			producer.setPlace(getPlace());
			producer.setPhone(getPhone());
			producer.setFax(getFax());
			producer.setEmail(getEmail());
			producer.setUrl(getUrl());
			producer.setCountry(getCountry());
			producer.setRegion(getRegion());

			Producer updatedProducer = producerService.update(producer);
			updateAlert(updatedProducer);

			raiseEventSaveProducer(updatedProducer);
		}

		clearFields();

	}

	private void raiseEventSaveProducer(final Producer producer) {
		SaveProducerEvent producerEvent = new SaveProducerEvent(this, producer);
		applicationEventPublisher.publishEvent(producerEvent);
	}

	private void saveAlert(Producer producer) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Producer saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText(
				"The producer " + producer.getName() + " has been created and \n id is " + producer.getId() + ".");
		alert.showAndWait();
	}

	private void updateAlert(Producer producer) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("User updated successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The producer " + producer.getName() + " has been updated.");
		alert.showAndWait();
	}

}

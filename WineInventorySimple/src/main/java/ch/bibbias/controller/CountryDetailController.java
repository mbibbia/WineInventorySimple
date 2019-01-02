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
import ch.bibbias.config.StageManager;
import ch.bibbias.event.CountryDetailsEvent;
import ch.bibbias.event.SaveCountryEvent;
import ch.bibbias.service.CountryService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

/**
 * 
 * @author Marco Bibbia
 * 
 *         Controller for FXML View CountryDetail.fxml.
 *
 */

@Controller
public class CountryDetailController implements Initializable {
	
	@FXML
	private Label countryId;

	@FXML
	private TextField code;

	@FXML
	private TextField name;

	@FXML
	private Button reset;

	@FXML
	private Button saveCountry;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Autowired
	private CountryService countryService;

	@Component
	class ShowCountryDetailEventHandler implements ApplicationListener<CountryDetailsEvent> {

		@Override
		public void onApplicationEvent(CountryDetailsEvent event) {
			countryId.setText(Long.toString(event.getCountry().getId()));
			code.setText(event.getCountry().getCode());
			name.setText(event.getCountry().getName());
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	private String getCode() {
		return code.getText();
	}

	private String getName() {
		return name.getText();
	}

	@FXML
	void reset(ActionEvent event) {
		clearFields();
	}

	private void clearFields() {

		countryId.setText(null);
		name.clear();
		code.clear();

	}

	@FXML
	private void saveCountry(ActionEvent event) {

		/*
		 * if (validate("Name", getName(), "[a-zA-Z]+") && validate("Type", getType(),
		 * "[a-zA-Z]+") && emptyValidation("Classification", getClassification() &&
		 * emptyValidation("Country", getCountry() {
		 */

		if (countryId.getText() == null || countryId.getText() == "") {

			Country country = new Country();
			country.setCode(getCode());
			country.setName(getName());

			Country newCountry = countryService.save(country);

			saveAlert(newCountry);

			raiseEventSaveCountry(newCountry);

		} else {
			Country country = countryService.find(Long.parseLong(countryId.getText()));
			country.setCode(getCode());
			country.setName(getName());
			Country updatedCountry = countryService.update(country);
			updateAlert(updatedCountry);

			raiseEventSaveCountry(updatedCountry);
		}

		clearFields();

	}

	private void raiseEventSaveCountry(final Country country) {
		SaveCountryEvent countryEvent = new SaveCountryEvent(this, country);
		applicationEventPublisher.publishEvent(countryEvent);
	}

	private void saveAlert(Country country) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Wine saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText(
				"The country " + country.getName() + " has been created and \n id is " + country.getId() + ".");
		alert.showAndWait();
	}

	private void updateAlert(Country country) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("User updated successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The country " + country.getName() + " has been updated.");
		alert.showAndWait();
	}

}
